package org.example.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "\"Products\"")
@Data
public class Product {
    @Id
    @Column(name = "material_no")
    Integer materialNo;
    @Column(name = "material_desc_rus")
    String materialDescRUS;
    @Column(name = "l3_product_category_code")
    Integer l3ProductCategoryCode;
    @Column(name = "l3_product_category_name")
    String l3ProductCategoryName;
}
