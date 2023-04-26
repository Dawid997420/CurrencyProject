package com.example.CurrencyProject.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.Matchers.equalTo;

class MathMapperTest {

    @Test
    void roundToSixDecimalPlace() {

        // given
        MathMapper mathMapper = new MathMapper();

        double number = 3.1292631323111141313331;
        double expectedNumber = 3.12926313;

        // when
        double result = mathMapper.roundToSixDecimalPlace(number);

        assertThat(result,equalTo(expectedNumber ));
    }


}