package org.example.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"Price\"")
@Data
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String chain;
    @Column(name = "material_no")
    Integer materialNo;
    Float price;
}
