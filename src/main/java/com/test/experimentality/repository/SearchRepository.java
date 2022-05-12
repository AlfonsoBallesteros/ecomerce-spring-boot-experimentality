package com.test.experimentality.repository;

import com.test.experimentality.domain.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SearchRepository extends JpaRepository<Search, UUID> {

    @Query("SELECT search.product.id AS product_id FROM Search search GROUP BY product_id ORDER BY COUNT(product_id) DESC")
    List<UUID> selectMoreSearch(Pageable pageable);

}
