package org.example.endpoint;

import org.example.dao.entity.Actual;
import org.example.dao.entity.ActualWithProduct;
import org.example.model.AnalysisModel;
import org.example.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnalysisController {

    @Autowired
    AnalysisService service;

    @GetMapping("/analys")
    protected List<AnalysisModel> analysByMonthChainCategory() {
        return service.calculate();
    }

    @GetMapping("/actuals")
    protected List<Actual> findAllActuals(
            @Nullable @RequestParam List<String> chains,
            @Nullable @RequestParam List<Integer> products) {
        return service.getAll(chains, products);
    }

}