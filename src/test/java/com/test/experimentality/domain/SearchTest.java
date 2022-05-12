package com.test.experimentality.domain;

import com.test.experimentality.rest.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SearchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Search.class);
        Search search1 = new Search();
        search1.setId(UUID.randomUUID());
        Search search2 = new Search();
        search2.setId(search1.getId());
        assertThat(search1).isEqualTo(search2);
        search2.setId(UUID.randomUUID());
        assertThat(search1).isNotEqualTo(search2);
        search1.setId(null);
        assertThat(search1).isNotEqualTo(search2);
    }
}
