package com.github.outerman.be.engine.businessDoc.businessTemplate;


import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.jexl3.internal.Debugger;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.Parser;

import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.engine.util.JexlUtil;

/**
 * Created by shenxy on 14/7/17.
 *
 * "金额来源"字段的解析
 */
public class AmountGetter {
    //常量
    private final static String STR_0 = "0";
    public  final static String EMPTY = "";
    private final static String AMOUNT_AMOUNT = "amount";
    public final static String AMOUNT_TAXINCLUSIVEAMOUNT = "taxInclusiveAmount";
    private final static String AMOUNT_MINUS_AMOUNT = "-amount";
    private final static String AMOUNT_MINUS_TAX = "-tax";
    private final static String AMOUNT_MINUS_TAXINCLUSIVEAMOUNT = "-taxInclusiveAmount";
    private final static String AMOUNT_TAX = "tax";
    private final static String AMOUNT_DEDUCTIBLEINPUTTAX = "deductibleInputTax";
    private final static String EXT1_MINUS_AMOUNT = "ext1-amount";
    private final static String AMOUNT_MINUS_EXT1 = "amount-ext1";
    private final static String AMOUNT_TAXDEDUCTIBLEINPUTTAX = "tax-deductibleInputTax";
    private final static String PRIVILEGE_TAX_AMOUNT = "privilegeTaxAmount";
    private final static String AMOUNT_EXT0 = "ext0";
    private final static String AMOUNT_EXT1 = "ext1";
    private final static String AMOUNT_EXT2 = "ext2";
    private final static String AMOUNT_EXT3 = "ext3";
    private final static String AMOUNT_EXT4 = "ext4";
    private final static String AMOUNT_EXT5 = "ext5";
    private final static String AMOUNT_EXT6 = "ext6";
    private final static String AMOUNT_EXT7 = "ext7";
    private final static String AMOUNT_EXT8 = "ext8";
    private final static String AMOUNT_EXT9 = "ext9";
    private final static String ISDEDUCTION = "isDeduction";
    private final static String MAOHAO = ":";
    private final static String JINGHAO = "#";


    /**
     * 获取金额方式
     * @param sort
     * @param fiBillDocTemplate
     * @return
     */
    public static Double getAmount(AcmSortReceiptDetail sort, DocAccountTemplateItem fiBillDocTemplate) {
        Double amount = null;
        String source = fiBillDocTemplate.getFundSource();
        if(AMOUNT_AMOUNT.equals(source)){// 不含税金额
            amount = sort.getAmount();
        }else if(AMOUNT_TAX.equals(source)){// 税额
            amount = sort.getTax();
        }else if(AMOUNT_TAXINCLUSIVEAMOUNT.equals(source)){// 价税合计金额
            amount = sort.getTaxInclusiveAmount();
        }else if(AMOUNT_MINUS_AMOUNT.equals(source)){// 负金额
            amount = -1* sort.getAmount();
        }else if(AMOUNT_MINUS_TAX.equals(source)){// 负税额
            amount =  -1*sort.getTax();
        }else if(AMOUNT_MINUS_TAXINCLUSIVEAMOUNT.equals(source)){// 负价税合计金额
            amount =  -1*sort.getTaxInclusiveAmount();
        } else if (PRIVILEGE_TAX_AMOUNT.equals(source)) {
            amount = sort.getPrivilegeTaxAmount();
        } else if(EXT1_MINUS_AMOUNT.equals(source)){
            amount =  sort.getExt1()-sort.getAmount();
        }else if(AMOUNT_MINUS_EXT1.equals(source)){
            amount =  sort.getAmount()-sort.getExt1();
        }else if(AMOUNT_EXT0.equals(source)){// 扩展字段0
            amount = sort.getExt0();
        }else if(AMOUNT_EXT1.equals(source)){// 扩展字段1
            amount = sort.getExt1();
        }else if(AMOUNT_EXT2.equals(source)){// 扩展字段2
            amount = sort.getExt2();
        }else if(AMOUNT_EXT3.equals(source)){// 扩展字段3
            amount = sort.getExt3();
        }else if(AMOUNT_EXT4.equals(source)){// 扩展字段4
            amount = sort.getExt4();
        }else if(AMOUNT_EXT5.equals(source)){// 扩展字段5
            amount = sort.getExt5();
        }else if(AMOUNT_EXT6.equals(source)){// 扩展字段6
            amount = sort.getExt6();
        }else if(AMOUNT_EXT7.equals(source)){// 扩展字段7
            amount = sort.getExt7();
        }else if(AMOUNT_EXT8.equals(source)){// 扩展字段8
            amount = sort.getExt8();
        }else if(AMOUNT_EXT9.equals(source)){// 扩展字段9
            amount = sort.getExt9();
        }else if(source.contains(MAOHAO)){// 表达式
            String[] split = source.split(MAOHAO);
            if(split[0].equals(ISDEDUCTION)){
                amount = extracted(sort, split,sort.getIsDeduction());
            }else if(split[0].equals("isQualification")){
                if(sort.getIsQualification() != null && sort.getIsQualification()==1){
                    if(split[1].contains(JINGHAO)){
                        String[] split2 = split[1].split("#");
                        amount = extracted(sort, split2,sort.getIsDeduction());
                    }else{
                        amount = extracted(sort, split,sort.getIsQualification());
                    }
                }
                if(sort.getIsQualification() == null || sort.getIsQualification()==0){
                    amount = extracted(sort, split,(byte)0);
                }

            }
        }
        if(amount == null){
            return 0D;
        }
        return amount;
    }


    private static Double extracted(AcmSortReceiptDetail sort, String[] split2,Byte deduction) {
        Double amount = null;
        if(deduction != null && deduction == 1){// 是
            if(AMOUNT_AMOUNT.equals(split2[1])){// 不含税金额
                amount = sort.getAmount();
            }else if(AMOUNT_TAXINCLUSIVEAMOUNT.equals(split2[1])){// 含税金额
                amount = sort.getTaxInclusiveAmount();
            }else if(AMOUNT_TAX.equals(split2[1])){// 税额
                amount = sort.getTax();
            }else if(AMOUNT_MINUS_AMOUNT.equals(split2[1])){// 负不含税金额
                amount =  -1*sort.getAmount();
            }else if(AMOUNT_MINUS_TAX.equals(split2[1])){// 负税额
                amount = -1*sort.getTax();
            }else if(AMOUNT_DEDUCTIBLEINPUTTAX.equals(split2[1])){// 可抵扣进项税额
                amount = sort.getDeductibleInputTax();
            }else if(AMOUNT_TAXDEDUCTIBLEINPUTTAX.equals(split2[1])){// 可抵扣进项税额
                amount = sort.getTax()-sort.getDeductibleInputTax();
            }

        }else if(deduction == null || deduction == 0){// 否
            if(AMOUNT_AMOUNT.equals(split2[2])){// 不含税金额
                amount = sort.getAmount();
            }else if(AMOUNT_TAX.equals(split2[2])){// 税额
                amount = sort.getTax();
            }else if(AMOUNT_TAXINCLUSIVEAMOUNT.equals(split2[2])){// 价税合计金额
                amount = sort.getTaxInclusiveAmount();
            }else if(AMOUNT_MINUS_AMOUNT.equals(split2[2])){// 负不含税金额
                amount = -1*sort.getAmount();
            }else if(AMOUNT_TAXINCLUSIVEAMOUNT.equals(split2[2])){// 负价税合计金额
                amount = -1*sort.getTaxInclusiveAmount();
            }else if(STR_0.equals(split2[2])){
                amount = 0D;
            }
        }
        return amount;
    }

    private static String handleFundSource(String fundsource) {
        if (!fundsource.contains(MAOHAO)) {
            return fundsource;
        }
        String[] array1 = fundsource.split(MAOHAO);
        for (int index = 0, length = array1.length; index < length; index++) {
            String str = array1[index];
            if (!str.contains(JINGHAO)) {
                continue;
            }
            String[] array2 = str.split(JINGHAO);
            array1[index] = String.format("(%s ? %s : %s)", array2[0], array2[1], array2[2]);
        }
        return String.format("(%s ? %s : %s)", array1[0], array1[1], array1[2]);
    }

    public static Double getAmount(AcmSortReceiptDetail detail, String fundsource) {
        fundsource = handleFundSource(fundsource);
        Double result = (Double) JexlUtil.evaluate(fundsource, detail);
        return result;
    }

    public static String fundsource2Chinese(String fundsource) {
        fundsource = handleFundSource(fundsource);
        Parser lparser = new Parser(new StringReader(";"));
        ASTJexlScript script = lparser.parse(null, fundsource, null, false, true);
        List<ASTIdentifier> identifierList = JexlUtil.getIdentifier(script);
        for (ASTIdentifier identifier : identifierList) {
            try {
                Field nameField = identifier.getClass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(nameField, CommonUtil.getColumnNameByFieldName(identifier.getName()));
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

}
