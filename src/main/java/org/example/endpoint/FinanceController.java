package org.example.endpoint;

import org.example.dao.PriceRepository;
import org.example.dao.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FinanceController {

    @Autowired
    PriceRepository priceRepository;

    @GetMapping("/prices")
    protected List<Price> findAllPrices(){ // TODO Можно возвращать объект
        return priceRepository.findAll();
    }

    @GetMapping("/prices/{id}")
    protected Price getPrice(@PathVariable("id") int id){
        return priceRepository.findById(id).orElseGet(null);
    }

    @PostMapping(value = "/prices", consumes = MediaType.APPLICATION_JSON_VALUE)
    protected Price addPrice(@RequestBody Price price){
        return priceRepository.save(price);
    }

    @PutMapping(value = "/prices", consumes = MediaType.APPLICATION_JSON_VALUE)
    protected Price setPrice(@RequestBody Price price){
        return priceRepository.save(price);
    }

    @DeleteMapping("/prices/{id}")
    protected void deletePrice(@PathVariable("id") int id) {
        priceRepository.deleteById(id);
    }

}