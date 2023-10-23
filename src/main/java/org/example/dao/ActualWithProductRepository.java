package org.example.dao;

import org.example.dao.entity.ActualWithProduct;
import org.example.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActualWithProductRepository extends JpaRepository<ActualWithProduct, Long> {
}
