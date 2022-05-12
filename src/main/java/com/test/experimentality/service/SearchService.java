package com.test.experimentality.service;

import com.test.experimentality.domain.Product;
import com.test.experimentality.repository.ProductRepository;
import com.test.experimentality.repository.SearchRepository;
import com.test.experimentality.service.dto.ProductDTO;
import com.test.experimentality.service.mapper.ProductMapper;
import com.test.experimentality.service.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final Logger log = LoggerFactory.getLogger(SearchService.class);

    private final SearchRepository searchRepository;

    private final SearchMapper searchMapper;

    /**
     * Get all the products.
     *
     * @param
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UUID> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return searchRepository.selectMoreSearch(pageable);
    }

}
