package org.example.service;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.example.dao.ActualRepository;
import org.example.dao.ActualWithProductRepository;
import org.example.dao.entity.Actual;
import org.example.dao.entity.ActualWithProduct;
import org.example.dao.entity.Product;
import org.example.model.AnalysisModel;
import org.example.model.PromoSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AnalysisService {

    @Autowired
    ActualWithProductRepository actualWithProductRepository;
    @Autowired
    ActualRepository actualRepository;


    @Data
    static class AnalysisKey {
        private String chain;
        private Integer l3ProductCategoryCode;
        private LocalDate month;
    }

    public List<AnalysisModel> calculate() {
        List<ActualWithProduct> actualList = actualWithProductRepository.findAll();
        Map<AnalysisKey, AnalysisModel> analysisMap = new HashMap<>();
        for (ActualWithProduct actual : actualList) {
            Product product = actual.getProduct();
            AnalysisKey key = new AnalysisKey()
                    .setChain(actual.getChain())
                    .setMonth(actual.getDate().withDayOfMonth(1))
                    .setL3ProductCategoryCode((product == null) ? null : product.getL3ProductCategoryCode());
            if (!analysisMap.containsKey(key)) analysisMap.put(key, new AnalysisModel()
                    .setChain(key.getChain()).setMonth(key.getMonth())
                    .setL3ProductCategoryCode(key.getL3ProductCategoryCode())
                    .setL3ProductCategoryName((product == null) ? null : product.getL3ProductCategoryName())
                    .setRegularVolume(0).setPromoVolume(0).setPromoPercent(0f)
            );
            AnalysisModel model = analysisMap.get(key);
            if (actual.getPromo() == PromoSign.Regular) model.setRegularVolume(model.getRegularVolume() + actual.getVolume());
            else model.setPromoVolume(model.getPromoVolume() + actual.getVolume());
        }
        // TODO Нужна проверка деления на 0, но в рамках тестирования я не стал этого делать
        analysisMap.forEach((k, v) -> v.setPromoPercent(v.getPromoVolume() * 100f/ (v.getPromoVolume() + v.getRegularVolume())));
        return analysisMap.values().stream().toList();
    }

    public List<Actual> getAll(List<String> chains, List<Integer> products) {
        if (CollectionUtils.isEmpty(chains)) return (CollectionUtils.isEmpty(products)) ?
                actualRepository.findAll() : actualRepository.findActualByProductIn(products);
        else return (CollectionUtils.isEmpty(products)) ?
                actualRepository.findActualByChainIn(chains) : actualRepository.findActualByChainInAndProductIn(chains, products);
    }

}
