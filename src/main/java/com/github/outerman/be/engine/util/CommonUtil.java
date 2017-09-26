package com.github.outerman.be.engine.util;

import java.util.ArrayList;
import java.util.List;

import com.github.outerman.be.api.vo.SetTaxRateDto;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;

/**
 * 工具类
 * @author gaoxue
 */
public final class CommonUtil {

    static List<Long> simple = new ArrayList<>();
    static List<Long> general = new ArrayList<>();
    static List<Long> special = new ArrayList<>();

    private static void initTaxRate(ITemplateProvider provider) {
        if (!general.isEmpty() && !simple.isEmpty()) {
            return;
        }
        List<SetTaxRateDto> taxRateList = provider.getTaxRateList(0L);
        if (taxRateList == null || taxRateList.isEmpty()) {
            return;
        }
        for (SetTaxRateDto setTaxRateDto : taxRateList) {
            Long id = setTaxRateDto.getId();
            // 1一般 2简易 3其他
            if (setTaxRateDto.getType().equals(1L)) {
                general.add(id);
            }
            if (setTaxRateDto.getType().equals(2L)) {
                simple.add(id);
            }
            if (setTaxRateDto.getType().equals(3L)) {
                special.add(id);
            }
        }
    }

    public static boolean isSimple(Long taxRateId, ITemplateProvider provider) {
        initTaxRate(provider);
        return simple.contains(taxRateId);
    }

    public static boolean isGeneral(Long taxRateId, ITemplateProvider provider) {
        initTaxRate(provider);
        return general.contains(taxRateId);
    }

    public static boolean isSpecial(Long taxRateId, ITemplateProvider provider) {
        initTaxRate(provider);
        return special.contains(taxRateId);
    }

}
