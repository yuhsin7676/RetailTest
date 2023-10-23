package org.example.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "\"Customers\"")
@Data
public class Customer {
    @Id
    @Column(name = "ch3_ship_to_code")
    Integer ch3ShipToCode;
    @Column(name = "ch3_ship_to_name")
    String ch3ShipToName;
    @Column(name = "chain")
    String chainName;
}
