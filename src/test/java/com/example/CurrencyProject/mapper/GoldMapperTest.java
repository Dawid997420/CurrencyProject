package com.example.CurrencyProject.mapper;

import com.example.CurrencyProject.dto.GoldDto;
import com.example.CurrencyProject.externalApi.NBP.GoldApi;
import com.example.CurrencyProject.model.material.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;

class GoldMapperTest {

    GoldMapper goldMapper;

    GoldApi goldApi = mock(GoldApi.class);



    GoldDtoFactory goldDtoFactory = new GoldDtoFactory();

    @BeforeEach
    public void init() {

        // given


        // when




        // then

        goldMapper = new GoldMapper(goldApi);




    }


    @Test
    void getActualGoldValue() {

        // given

        LocalDate date = LocalDate.of(2023,12,2);
        double price = 232;

        List<GoldDto> goldDtoList= goldDtoFactory.createActualGoldDto(date, price);

        when(goldApi.getActualGoldValue()).thenReturn(goldDtoList);

        Material goldValueExpected = Material.builder().name("gold").unit("gram").data
                (date.atStartOfDay()).price(price).build();

        // when


        Material goldValueResult = goldMapper.getActualGoldValue();

        // then
        assertThat(goldValueResult,equalTo(goldValueExpected));

    }

    @Test
    void getGoldMostActualDays() {

        // given
        List<GoldDto> tenGoldDtos = goldDtoFactory.createGoldMostActualDaysTen();
        when(goldApi.getGoldMostActualDays(10)).thenReturn( Mono.just(tenGoldDtos));

        // when
        List<Material> result = goldMapper.getGoldMostActualDays(10).block();

        // then
        assertThat(result.size(), equalTo(10));

    }

    @Test
    void getGoldBetween() {

        // given
        LocalDate startDate = LocalDate.of(2022,2,2);
        LocalDate endDate = LocalDate.of(2022,5,2);

        List<GoldDto> goldList = goldDtoFactory.createGoldMostActualDaysTen();

        when(goldApi.getGoldBetween(startDate,endDate)).thenReturn(Mono.just(goldList));

        // when
        List<Material> resultList =  goldMapper.getGoldBetween(startDate,endDate).block();

        // then
        assertThat(resultList.size(),equalTo(10));
    }


}