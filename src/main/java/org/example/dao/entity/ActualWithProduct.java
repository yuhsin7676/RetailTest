package org.example.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.model.PromoSign;

import java.time.LocalDate;

@Entity
@Table(name = "\"Actuals\"")
@Data
public class ActualWithProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate date;
    @ManyToOne
    @JoinColumn(name = "material_no", referencedColumnName = "material_no")
    Product product;
    @Column(name = "ch3_ship_to_code")
    Integer ch3ShipToCode;
    String chain;
    Integer volume;
    Float sales;
    @Enumerated(EnumType.STRING)
    PromoSign promo;
}
