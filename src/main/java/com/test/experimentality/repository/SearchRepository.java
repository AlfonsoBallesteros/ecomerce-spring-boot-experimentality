package com.test.experimentality.repository;

import com.test.experimentality.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SearchRepository extends JpaRepository<Search, UUID> {


}
