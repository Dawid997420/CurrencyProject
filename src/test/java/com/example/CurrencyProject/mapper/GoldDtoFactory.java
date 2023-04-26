package com.example.CurrencyProject.mapper;

import com.example.CurrencyProject.dto.GoldDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoldDtoFactory {


    public List<GoldDto> createActualGoldDto(LocalDate date, double price) {
        return Collections.singletonList(GoldDto.builder().data(date).cena(price).build());
    }

    public List<GoldDto> createGoldMostActualDaysTen() {

        List<GoldDto> goldDtoList = new ArrayList<>();
        double[] prices =  { 211.2, 201.5,222.1,222.4 , 229.2, 218.2, 230.2, 210 , 245, 280.2,
                290.2,302.2,299.2,282.2};

        List<LocalDate> localDates = Arrays.asList(LocalDate.of(2023,1,1),
        LocalDate.of(2023,1,2),LocalDate.of(2023,1,3),
        LocalDate.of(2023,1,4),LocalDate.of(2023,1,5),
        LocalDate.of(2023,1,6),LocalDate.of(2023,1,7),
        LocalDate.of(2023,1,8),LocalDate.of(2023,1,9),
        LocalDate.of(2023,1,10),LocalDate.of(2023,1,11));

        for (int i = 0 ; i< 10 ; i++) {
            goldDtoList.add(GoldDto.builder().cena(prices[i])
                    .data(localDates.get(i)).build());
        }
        return goldDtoList;

    }

}
