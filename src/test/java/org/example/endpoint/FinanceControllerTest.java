package org.example.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.PriceRepository;
import org.example.dao.entity.Price;
import org.example.model.AnalysisModel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FinanceControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PriceRepository repository; // Для прямого обращения к БД

    @Test
    @Order(1)
    void findAllPrices() throws Exception {
        MvcResult result = mockMvc.perform(get("/prices"))
                .andExpect(status().isOk()).andReturn();
        List<Price> expected = objectMapper.readValue("""
            [
            {"id":1,"chain":"Chain 1","materialNo":10000001,"price":40.0},
            {"id":2,"chain":"Chain 1","materialNo":10000002,"price":50.0},
            {"id":3,"chain":"Chain 1","materialNo":10000003,"price":100.0},
            {"id":4,"chain":"Chain 2","materialNo":10000001,"price":45.0},
            {"id":5,"chain":"Chain 2","materialNo":10000002,"price":55.0},
            {"id":6,"chain":"Chain 2","materialNo":10000003,"price":105.0}
            ]
            """, new TypeReference<List<Price>>(){});
        List<Price> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Price>>(){});
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    @Order(2)
    void getPrice() throws Exception {
        MvcResult result = mockMvc.perform(get("/prices/2"))
                .andExpect(status().isOk()).andReturn();
        Price expected = new Price().setId(2).setChain("Chain 1").setMaterialNo(10000002).setPrice(50f);
        Price actual = objectMapper.readValue(result.getResponse().getContentAsString(), Price.class);
        assertEquals(expected, actual);

    }

    @Test
    @Order(3)
    void addPrice() throws Exception {
        MvcResult result = mockMvc.perform(post("/prices").contentType(MediaType.APPLICATION_JSON).content("""
            {"chain":"Chain 3","materialNo":10000003,"price":80.0}
            """)).andExpect(status().isOk()).andReturn();
        Price expected = new Price().setId(7).setChain("Chain 3").setMaterialNo(10000003).setPrice(80f);
        Price actual = objectMapper.readValue(result.getResponse().getContentAsString(), Price.class);
        assertEquals(expected, actual);
        Price actualDB = repository.findById(7).orElse(null);
        assertEquals(expected, actualDB);
    }

    @Test
    @Order(4)
    void setPrice() throws Exception {
        MvcResult result = mockMvc.perform(put("/prices").contentType(MediaType.APPLICATION_JSON).content("""
            {"id":7,"chain":"Chain 3","materialNo":10000003,"price":90.0}
            """)).andExpect(status().isOk()).andReturn();
        Price expected = new Price().setId(7).setChain("Chain 3").setMaterialNo(10000003).setPrice(90f);
        Price actual = objectMapper.readValue(result.getResponse().getContentAsString(), Price.class);
        assertEquals(expected, actual);
        Price actualDB = repository.findById(7).orElse(null);
        assertEquals(expected, actualDB);
    }

    @Test
    @Order(5)
    void deletePrice() throws Exception {
        MvcResult result = mockMvc.perform(delete("/prices/7"))
                .andExpect(status().isOk()).andReturn();
        Price actual = repository.findById(7).orElse(null);
        assertNull(actual);
    }
}