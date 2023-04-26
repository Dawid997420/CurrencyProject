package com.example.CurrencyProject.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;

class CountryCurrencyMapperTest {

    @Test
    void getCountryName() {

        // given
        String code = "USD";
        String countryExpected = "Stany Zjednoczone";
        // when
        String result = CountryCurrencyMapper.getCountryName(code);

        // then
        assertThat(countryExpected,equalTo(result));

    }
}