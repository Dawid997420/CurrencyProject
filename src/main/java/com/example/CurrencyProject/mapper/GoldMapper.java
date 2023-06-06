package com.example.CurrencyProject.mapper;

import com.example.CurrencyProject.dto.GoldDto;
import com.example.CurrencyProject.externalApi.NBP.GoldApi;
import com.example.CurrencyProject.model.material.Material;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Component
public class GoldMapper {


    private final GoldApi goldApi;

    public GoldMapper(GoldApi goldApi) {
        this.goldApi = goldApi;
    }

    public Material getActualGoldValue() {

        return materialBuilder(goldApi.getActualGoldValue());
    }

    public Mono<List<Material>> getGoldMostActualDays(int days) {

        return goldApi.getGoldMostActualDays(days).map(this::materialArrayBuilder);
    }


    public Mono<List<Material>> getGoldBetween(LocalDate startDate,LocalDate endDate) {

        return goldApi.getGoldBetween(startDate,endDate).map(this::materialArrayBuilder);
    }

    private List<Material> materialArrayBuilder(List<GoldDto> goldDtoList)  {

        return goldDtoList.stream().map( goldDto -> {
           return Material.builder().name("gold").data(goldDto.getData().atStartOfDay())
                   .unit("gram").price(goldDto.getCena()).build() ;
        }).toList();

    }

    private Material materialBuilder(List<GoldDto> goldDtoList) {

        return goldDtoList.stream().map( goldDto -> {
            return Material.builder().data(goldDto.getData().atStartOfDay()).name("gold")
                    .unit("gram").price(goldDto.getCena()).build();
        }).toList().get(0);

    }



}
