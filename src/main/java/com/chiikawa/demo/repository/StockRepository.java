package com.chiikawa.demo.repository;

import com.chiikawa.demo.entity.Stock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long > {
            // Select * from stocks where products_id in (1,3) order by <sorting_param>
    List<Stock> findByProductIdIn(List<Long> productIds, Sort createdAt);
}
