package org.example.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dao.entity.Actual;
import org.example.model.AnalysisModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnalysisControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void analysByMonthChainCategory() throws Exception{
        MvcResult result = mockMvc.perform(get("/analys"))
                .andExpect(status().isOk()).andReturn();

        List<AnalysisModel> expected = objectMapper.readValue("""
                [
                {"chain":"Chain 2","l3ProductCategoryCode":100001,"l3ProductCategoryName":"Vegetables","month":"2023-08-01","regularVolume":30,"promoVolume":10,"promoPercent":25.0},
                {"chain":"Chain 2","l3ProductCategoryCode":100001,"l3ProductCategoryName":"Vegetables","month":"2023-09-01","regularVolume":40,"promoVolume":0,"promoPercent":0.0},
                {"chain":"Chain 1","l3ProductCategoryCode":100002,"l3ProductCategoryName":"Fruits","month":"2023-08-01","regularVolume":3,"promoVolume":2,"promoPercent":40.0},
                {"chain":"Chain 1","l3ProductCategoryCode":100002,"l3ProductCategoryName":"Fruits","month":"2023-09-01","regularVolume":5,"promoVolume":0,"promoPercent":0.0},
                {"chain":"Chain 1","l3ProductCategoryCode":100001,"l3ProductCategoryName":"Vegetables","month":"2023-08-01","regularVolume":20,"promoVolume":15,"promoPercent":42.857143},
                {"chain":"Chain 1","l3ProductCategoryCode":100001,"l3ProductCategoryName":"Vegetables","month":"2023-09-01","regularVolume":35,"promoVolume":0,"promoPercent":0.0},
                {"chain":"Chain 2","l3ProductCategoryCode":100003,"l3ProductCategoryName":"Meat","month":"2023-08-01","regularVolume":700,"promoVolume":0,"promoPercent":0.0},
                {"chain":"Chain 2","l3ProductCategoryCode":100003,"l3ProductCategoryName":"Meat","month":"2023-09-01","regularVolume":700,"promoVolume":0,"promoPercent":0.0},
                {"chain":"Chain 2","l3ProductCategoryCode":100002,"l3ProductCategoryName":"Fruits","month":"2023-08-01","regularVolume":7,"promoVolume":0,"promoPercent":0.0},
                {"chain":"Chain 2","l3ProductCategoryCode":100002,"l3ProductCategoryName":"Fruits","month":"2023-09-01","regularVolume":7,"promoVolume":0,"promoPercent":0.0},
                {"chain":"Chain 1","l3ProductCategoryCode":100003,"l3ProductCategoryName":"Meat","month":"2023-08-01","regularVolume":100,"promoVolume":200,"promoPercent":66.666664},
                {"chain":"Chain 1","l3ProductCategoryCode":100003,"l3ProductCategoryName":"Meat","month":"2023-09-01","regularVolume":300,"promoVolume":0,"promoPercent":0.0}
                ]
                """, new TypeReference<List<AnalysisModel>>(){});
        List<AnalysisModel> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<AnalysisModel>>(){});
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void findAllActuals() throws Exception{
        MvcResult result = mockMvc.perform(get("/actuals"))
                .andExpect(status().isOk()).andReturn();

        List<Actual> expected = objectMapper.readValue("""
                [
                {"id":1,"date":"2023-08-22","product":10000001,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":20,"sales":800.0,"promo":"Regular"},
                {"id":4,"date":"2023-08-26","product":10000002,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":3,"sales":150.0,"promo":"Regular"},
                {"id":5,"date":"2023-08-27","product":10000003,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":100,"sales":10000.0,"promo":"Regular"},
                {"id":7,"date":"2023-08-29","product":10000001,"ch3ShipToCode":1000003,"chain":"Chain 2","volume":30,"sales":1350.0,"promo":"Regular"},
                {"id":9,"date":"2023-08-29","product":10000002,"ch3ShipToCode":1000003,"chain":"Chain 2","volume":3,"sales":165.0,"promo":"Regular"},
                {"id":10,"date":"2023-08-29","product":10000002,"ch3ShipToCode":1000004,"chain":"Chain 2","volume":4,"sales":220.0,"promo":"Regular"},
                {"id":11,"date":"2023-08-29","product":10000003,"ch3ShipToCode":1000003,"chain":"Chain 2","volume":300,"sales":31500.0,"promo":"Regular"},
                {"id":12,"date":"2023-08-29","product":10000003,"ch3ShipToCode":1000004,"chain":"Chain 2","volume":400,"sales":42000.0,"promo":"Regular"},
                {"id":13,"date":"2023-09-22","product":10000001,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":20,"sales":800.0,"promo":"Regular"},
                {"id":14,"date":"2023-09-25","product":10000001,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":15,"sales":600.0,"promo":"Regular"},
                {"id":15,"date":"2023-09-26","product":10000002,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":2,"sales":100.0,"promo":"Regular"},
                {"id":16,"date":"2023-09-26","product":10000002,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":3,"sales":150.0,"promo":"Regular"},
                {"id":17,"date":"2023-09-27","product":10000003,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":100,"sales":10000.0,"promo":"Regular"},
                {"id":18,"date":"2023-09-27","product":10000003,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":200,"sales":20000.0,"promo":"Regular"},
                {"id":19,"date":"2023-09-29","product":10000001,"ch3ShipToCode":1000003,"chain":"Chain 2","volume":30,"sales":1350.0,"promo":"Regular"},
                {"id":20,"date":"2023-09-29","product":10000001,"ch3ShipToCode":1000004,"chain":"Chain 2","volume":10,"sales":450.0,"promo":"Regular"},
                {"id":21,"date":"2023-09-29","product":10000002,"ch3ShipToCode":1000003,"chain":"Chain 2","volume":3,"sales":165.0,"promo":"Regular"},
                {"id":22,"date":"2023-09-29","product":10000002,"ch3ShipToCode":1000004,"chain":"Chain 2","volume":4,"sales":220.0,"promo":"Regular"},
                {"id":23,"date":"2023-09-29","product":10000003,"ch3ShipToCode":1000003,"chain":"Chain 2","volume":300,"sales":31500.0,"promo":"Regular"},
                {"id":24,"date":"2023-09-29","product":10000003,"ch3ShipToCode":1000004,"chain":"Chain 2","volume":400,"sales":42000.0,"promo":"Regular"},
                {"id":2,"date":"2023-08-25","product":10000001,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":15,"sales":550.0,"promo":"Promo"},
                {"id":3,"date":"2023-08-26","product":10000002,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":2,"sales":90.0,"promo":"Promo"},
                {"id":6,"date":"2023-08-27","product":10000003,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":200,"sales":15000.0,"promo":"Promo"},
                {"id":8,"date":"2023-08-29","product":10000001,"ch3ShipToCode":1000004,"chain":"Chain 2","volume":10,"sales":300.0,"promo":"Promo"}
                ]
                """, new TypeReference<List<Actual>>(){});
        List<Actual> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Actual>>(){});
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void findAllActualsByChainAndProduct() throws Exception{
        MvcResult result = mockMvc.perform(get("/actuals").param("chains", "Chain 1").param("products", "10000001"))
                .andExpect(status().isOk()).andReturn();
        List<Actual> expected = objectMapper.readValue("""
                [
                {"id":1,"date":"2023-08-22","product":10000001,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":20,"sales":800.0,"promo":"Regular"},
                {"id":13,"date":"2023-09-22","product":10000001,"ch3ShipToCode":1000001,"chain":"Chain 1","volume":20,"sales":800.0,"promo":"Regular"},
                {"id":14,"date":"2023-09-25","product":10000001,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":15,"sales":600.0,"promo":"Regular"},
                {"id":2,"date":"2023-08-25","product":10000001,"ch3ShipToCode":1000002,"chain":"Chain 1","volume":15,"sales":550.0,"promo":"Promo"}
                ]
                """, new TypeReference<List<Actual>>(){});
        List<Actual> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Actual>>(){});
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

}