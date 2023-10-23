package org.example.dao;

import org.example.dao.entity.Actual;
import org.example.dao.entity.ActualWithProduct;
import org.example.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActualRepository extends JpaRepository<Actual, Long> {

    List<Actual> findActualByChainIn(List<String> chains);

    List<Actual> findActualByProductIn(List<Integer> products);

    List<Actual> findActualByChainInAndProductIn(List<String> chains, List<Integer> products);


}
