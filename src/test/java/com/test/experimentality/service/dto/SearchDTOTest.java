package com.test.experimentality.service.dto;

import com.test.experimentality.rest.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SearchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchDTO.class);
        SearchDTO searchDTO1 = new SearchDTO();
        searchDTO1.setId(UUID.randomUUID());
        SearchDTO searchDTO2 = new SearchDTO();
        assertThat(searchDTO1).isNotEqualTo(searchDTO2);
        searchDTO2.setId(searchDTO1.getId());
        assertThat(searchDTO1).isEqualTo(searchDTO2);
        searchDTO2.setId(UUID.randomUUID());
        assertThat(searchDTO1).isNotEqualTo(searchDTO2);
        searchDTO1.setId(null);
        assertThat(searchDTO1).isNotEqualTo(searchDTO2);
    }
}
