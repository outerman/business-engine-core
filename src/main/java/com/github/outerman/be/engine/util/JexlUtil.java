package com.github.outerman.be.engine.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.ObjectContext;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;

import com.github.outerman.be.api.constant.BusinessEngineException;

/**
 * Java Expression Language util
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
        JexlContext context = new ObjectContext<T>(jexl, data);
        return context;
    }

    /**
     * evaluate expression with data
     * @param expression
     * @param data
     * @return
     * @throws BusinessEngineException
     */
    public static <T> Object evaluate(String expression, T data) throws BusinessEngineException {
        return evaluate(expression, data, null);
    }

    /**
     * evaluate expression with data and params
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

    public static List<ASTIdentifier> getIdentifier(JexlNode node) {
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
