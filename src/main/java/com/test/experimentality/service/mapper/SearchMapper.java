package com.test.experimentality.service.mapper;

import com.test.experimentality.domain.Search;
import com.test.experimentality.service.dto.SearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface SearchMapper extends EntityMapper<SearchDTO, Search> {

    @Mapping(source = "product.id", target = "productId")
    SearchDTO toDto(Search search);

    @Mapping(source = "productId", target = "product")
    Search toEntity(SearchDTO searchDTO);

    default Search fromId(UUID id) {
        if (id == null) {
            return null;
        }
        Search search = new Search();
        search.setId(id);
        return search;
    }
}
