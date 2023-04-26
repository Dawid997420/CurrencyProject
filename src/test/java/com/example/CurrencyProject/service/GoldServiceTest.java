package com.example.CurrencyProject.service;

import com.example.CurrencyProject.mapper.GoldMapper;
import com.example.CurrencyProject.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;

class GoldServiceTest {


    GoldMapper goldMapper = mock(GoldMapper.class);

    GoldService goldService ;

    MaterialFactory materialFactory = new MaterialFactory();

    @BeforeEach
    void init(){
        // given

        // when

        // then
        goldService = new GoldService(goldMapper);
    }

    @Test
    void getActualGoldValue() {

        // given
        List<Material> listFor2Days = materialFactory.createMaterialForTwoDays("gold");
        when(goldMapper.getGoldMostActualDays(2)).thenReturn(Mono.just(listFor2Days) );

        // when
        Material result = goldService.getActualGoldValue().block();

        // then
        assertThat(result.getChange(),equalTo(1.0));
        assertThat(result.getPercentChange(),equalTo(0.5));

    }

    @Test
    void getGoldForDays() {

        // given
        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        int days = 10;

        LocalDate today = LocalDate.now(polishZone);
        LocalDate daysLater = today.minusDays(days);

        List<Material> materialList = materialFactory.createMaterialBetweenTenDays("gold");

        when(goldMapper.getGoldBetween(daysLater,today)).thenReturn(Mono.just(materialList));

        // when
        List<Material> materials = goldService.getGoldForDays(days).block();

        // then
        assertThat(materials.size(),equalTo(10));
    }


    @Test
    void getGoldForDaysShouldThrowArgumentException() {

        // given

        // when

        // then
        assertThrows(IllegalArgumentException.class,() -> goldService.getGoldForDays(400));
    }

}