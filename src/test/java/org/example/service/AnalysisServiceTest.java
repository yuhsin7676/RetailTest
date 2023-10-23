package org.example.service;

import org.example.dao.ActualWithProductRepository;
import org.example.dao.entity.ActualWithProduct;
import org.example.dao.entity.Product;
import org.example.model.AnalysisModel;
import org.example.model.PromoSign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Спринг тут излишен
class AnalysisServiceTest {

    @Mock
    private ActualWithProductRepository repository;
    @InjectMocks
    private AnalysisService service;

    @Test
    void calculate() {
        Mockito.when(repository.findAll()).thenReturn(List.of(
                new ActualWithProduct()
                        .setDate(LocalDate.of(2000, 1, 1)).setSales(40f)
                        .setVolume(4).setPromo(PromoSign.Regular).setChain("chain1").setCh3ShipToCode(1)
                        .setProduct(new Product().setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots")),
                new ActualWithProduct()
                        .setDate(LocalDate.of(2000, 1, 2)).setSales(30f)
                        .setVolume(4).setPromo(PromoSign.Promo).setChain("chain1").setCh3ShipToCode(1)
                        .setProduct(new Product().setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots")),
                new ActualWithProduct()
                        .setDate(LocalDate.of(2000, 2, 5)).setSales(30f)
                        .setVolume(6).setPromo(PromoSign.Regular).setChain("chain1").setCh3ShipToCode(1)
                        .setProduct(new Product().setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots")),
                new ActualWithProduct()
                        .setDate(LocalDate.of(2000, 3, 1)).setSales(40f)
                        .setVolume(4).setPromo(PromoSign.Regular).setChain("chain1").setCh3ShipToCode(1)
                        .setProduct(new Product().setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots")),
                new ActualWithProduct()
                        .setDate(LocalDate.of(2000, 3, 2)).setSales(30f)
                        .setVolume(4).setPromo(PromoSign.Regular).setChain("chain1").setCh3ShipToCode(1)
                        .setProduct(new Product().setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots"))
        ));

        List<AnalysisModel> expected = List.of(
                new AnalysisModel()
                        .setMonth(LocalDate.of(2000, 1, 1)).setChain("chain1")
                        .setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots")
                        .setPromoVolume(4).setRegularVolume(4).setPromoPercent(50f),
                new AnalysisModel()
                        .setMonth(LocalDate.of(2000, 2, 1)).setChain("chain1")
                        .setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots")
                        .setPromoVolume(0).setRegularVolume(6).setPromoPercent(0f),
                new AnalysisModel()
                        .setMonth(LocalDate.of(2000, 3, 1)).setChain("chain1")
                        .setL3ProductCategoryCode(3).setL3ProductCategoryName("Boots")
                        .setPromoVolume(0).setRegularVolume(8).setPromoPercent(0f)
        );

        List<AnalysisModel> actual = service.calculate();
        assertEquals(expected, actual);
    }

}