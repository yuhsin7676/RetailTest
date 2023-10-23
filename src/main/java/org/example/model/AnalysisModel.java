package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnalysisModel {
    private String chain;
    private Integer l3ProductCategoryCode;
    private String l3ProductCategoryName;
    private LocalDate month;
    private Integer regularVolume;
    private Integer promoVolume;
    private Float promoPercent;
}
