package com.github.outerman.be.util;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Debugger;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;

import com.github.outerman.be.contant.BusinessEngineException;

/**
 * Java Expression Language Util
 * @author gaoxue
 */
public final class JexlUtil {

    private static JexlEngine jexl = new JexlBuilder().create();

    private static JexlExpression createExpression(String expression) throws BusinessEngineException {
        try {
            JexlExpression jexlExpression = jexl.createExpression(expression);
            return jexlExpression;
        } catch (JexlException ex) {
            throw new BusinessEngineException("", ex.getMessage(), null, ex);
        }
    }

    private static <T> JexlContext getContext(T data) {
        JexlContext context = new MapContext();
        BeanMap map = new org.apache.commons.beanutils.BeanMap(data);
        for (Entry<Object, Object> entry : map.entrySet()) {
            context.set(entry.getKey().toString(), entry.getValue());
        }
        return context;
    }

    /**
     * Return true if the expression is valid
     * @param expression
     * @return true if the expression is valid
     */
    public static boolean isExprValid(String expression) {
        boolean result = true;
        try {
            createExpression(expression);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    /**
     * Evaluate the expression with data
     * @param expression
     * @param data
     * @return
     * @throws BusinessEngineException
     */
    public static <T> Object evaluate(String expression, T data) throws BusinessEngineException {
        return evaluate(expression, data, null);
    }

    /**
     * Evaluate the expression with data, variables in params
     * @param expression
     * @param data
     * @param params
     * @return
     * @throws BusinessEngineException
     */
    public static <T> Object evaluate(String expression, T data, Map<String, Object> params) throws BusinessEngineException {
        JexlExpression jexlExpression = createExpression(expression);
        JexlContext context = getContext(data);
        if (params != null) {
            for (Entry<String, Object> param : params.entrySet()) {
                context.set(param.getKey(), param.getValue());
            }
        }
        try {
            Object result = jexlExpression.evaluate(context);
            return result;
        } catch (JexlException ex) {
            throw new BusinessEngineException("", ex.getMessage(), null, ex);
        }
    }

    /**
     * Parse the expression with JEXL, get {@code ASTIdentifier} node name list
     * @param expression
     * @return
     */
    public static List<String> getIdentifierName(String expression) {
        Parser lparser = new Parser(new StringReader(";"));
        ASTJexlScript script = lparser.parse(null, expression, null, false, true);
        List<ASTIdentifier> identifierList = getIdentifier(script);
        Set<String> nameSet = new HashSet<>();
        for (ASTIdentifier identifier : identifierList) {
            nameSet.add(identifier.getName());
        }
        return new ArrayList<>(nameSet);
    }

    /**
     * Replace the expression with parsedMap
     * @param expression
     * @param parsedMap
     * @return
     */
    public static String getParsedText(String expression, Map<String, String> parsedMap) {
        Parser lparser = new Parser(new StringReader(";"));
        ASTJexlScript script = lparser.parse(null, expression, null, false, true);
        List<ASTIdentifier> identifierList = JexlUtil.getIdentifier(script);
        for (ASTIdentifier identifier : identifierList) {
            String name = identifier.getName();
            if (!parsedMap.containsKey(name)) {
                continue;
            }
            try {
                Field nameField = identifier.getClass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(identifier, parsedMap.get(name));
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                // do nothing
            }
        }
        Debugger debug = new Debugger();
        debug.setIndentation(2);
        debug.debug(script);
        String parsedText = debug.toString();
        return parsedText;
    }

    /**
     * Get {@code ASTIdentifier} node list with root {@code node}
     * @param node
     * @return
     */
    private static List<ASTIdentifier> getIdentifier(JexlNode node) {
        List<ASTIdentifier> result = new ArrayList<>();
        int num = node.jjtGetNumChildren();
        if (num == 0) {
            if (node instanceof ASTIdentifier) {
                result.add((ASTIdentifier) node);
            }
            return result;
        }
        for (int index = 0; index < num; index++) {
            JexlNode child = node.jjtGetChild(index);
            result.addAll(getIdentifier(child));
        }
        return result;
    }

    private JexlUtil() {
        // final util class, avoid instantiate
    }
}
