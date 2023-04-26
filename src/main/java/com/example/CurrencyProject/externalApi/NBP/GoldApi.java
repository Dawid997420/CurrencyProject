package com.example.CurrencyProject.externalApi.NBP;


import com.example.CurrencyProject.dto.GoldDto;
import com.example.CurrencyProject.model.Group;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Component
public class GoldApi {

    private final String API_URL ;

    private final WebClient webClient;

    public GoldApi(@Value("${api.nbp.url}") String apiUrl, WebClient.Builder webClientBuilder){
        this.API_URL = apiUrl + "/cenyzlota";
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }


    public List<GoldDto> getActualGoldValue() {

        Mono<List<GoldDto>> goldValueMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("format","json")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map( this::createListGoldValueDto);

       return goldValueMono.block();
    }


    public Mono<List<GoldDto>> getGoldMostActualDays(int days ) {

        Mono<List<GoldDto>> goldDaysMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/last")
                        .path("/"+days)
                        .queryParam("format","json")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::createListGoldValueDto);

        return goldDaysMono;
    }


    public Mono<List<GoldDto>> getGoldBetween(LocalDate startDate, LocalDate endDate) {

        return webClient.get().uri(uriBuilder -> uriBuilder
                .path("/"+startDate)
                .path("/"+endDate)
                .queryParam("format","json")
                .build()).retrieve()
                .bodyToMono(String.class)
                .map(this::createListGoldValueDto);

    }



    private List<GoldDto> createListGoldValueDto(String responseBody) {

            ObjectMapper objectMapper = new ObjectMapper( );
            objectMapper.registerModule(new JavaTimeModule());

            List<GoldDto> goldValueList = null; // Mapping response body to List<GoldValueDto>
            try {
                goldValueList = objectMapper
                        .readValue(responseBody, new TypeReference<List<GoldDto>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return goldValueList;
    }



}
