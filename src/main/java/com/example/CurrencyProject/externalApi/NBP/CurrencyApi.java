package com.example.CurrencyProject.externalApi.NBP;


import com.example.CurrencyProject.dto.AB.Currency_Dto;
import com.example.CurrencyProject.dto.AB.TableDto;
import com.example.CurrencyProject.model.currency.Group;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Component
public class CurrencyApi {

    private final String API_URL ;

    private final WebClient webClient;




    public CurrencyApi(@Value("${api.nbp.url}") String apiUrl , WebClient.Builder webClientBuilder) {



        this.API_URL = apiUrl + "/exchangerates";
        this.webClient = webClientBuilder.baseUrl(API_URL).defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
         .build();

    }




    @Cacheable(cacheNames="TableByDate")
    public List<TableDto> getTableByDate(LocalDate date, Group group) {

        Mono<List<TableDto>> tableAMono = webClient.get().uri(uriBuilder -> uriBuilder
                .path("/tables")
                .path("/"+ group.getGroup())
                .path("/" + date)
                .queryParam("format","json")
                .build()).retrieve().bodyToMono(String.class).map(this::createListTableDto);

        return tableAMono.block();
    }



    @Cacheable(cacheNames="CurrencyByDate")
    public Currency_Dto getCurrencyByDate(LocalDate date, String currencyCode, Group group) {

        Mono<Currency_Dto> currencyAB_DtoMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rates")
                        .path("/" + group.getGroup())
                        .path("/"+currencyCode)
                        .path("/"+date)
                        .build()).retrieve().bodyToMono(Currency_Dto.class);

        return currencyAB_DtoMono.block();

    }

    @Cacheable(cacheNames="CurrencyBetween")
    public Mono<Currency_Dto> getCurrencyBetween(Group group,String currencyCode,
                                           LocalDate startDate, LocalDate endDate) {

        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/rates")
                        .path("/" + group)
                        .path("/" + currencyCode)
                        .path("/" + startDate)
                        .path("/" + endDate)
                        .queryParam("format","json")
                        .build()).retrieve().bodyToMono(Currency_Dto.class).cache();

    }



    @Cacheable(cacheNames="ActualCurrency")
    public Currency_Dto getActualCurrency(String currencyCode , Group group) {

        Mono<Currency_Dto> currencyValueMidMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rates")
                        .path("/" + group.getGroup())
                        .path("/" + currencyCode)
                        .queryParam("format","json")
                        .build()).retrieve()
                .bodyToMono(Currency_Dto.class);

        return currencyValueMidMono.block();
    }


    @Cacheable(cacheNames="CurrenciesTableMostActualDays")
    public Mono<List<TableDto>> getCurrenciesTableMostActualDays(Group group, int days) {

        Mono<List<TableDto>> currenciesFromTable_Mono =  webClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/tables")
                        .path("/" + group.getGroup())
                        .path("/last")
                        .path("/"+days)
                        .queryParam("format","json")
                        .build()).retrieve()
                .bodyToMono(String.class)
                .map(this::createListTableDto);

        return currenciesFromTable_Mono.cache();
    }

    @Cacheable(cacheNames="CurrencyMostActualDays")
    public Currency_Dto getCurrencyMostActualDays( Group group, String code, int days) {

        Mono<Currency_Dto> currenciesFromTable_Mono =  webClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/rates")
                        .path("/" + group.getGroup())
                        .path("/" + code)
                        .path("/last")
                        .path("/"+days)
                        .queryParam("format","json")
                        .build()).retrieve()
                .bodyToMono(Currency_Dto.class);

        return currenciesFromTable_Mono.block();
    }

    @Cacheable(cacheNames="ActualCurrenciesTable")
    public List<TableDto> getActualCurrenciesTable(Group group) {

        Mono<List<TableDto>> currenciesFromTableC_Mono =  webClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/tables")
                        .path("/" + group.getGroup())
                        .queryParam("format","json")
                        .build()).retrieve()
                .bodyToMono(String.class)
                .map(this::createListTableDto);

        return currenciesFromTableC_Mono.block();
    }







    private List<TableDto> createListTableDto(String responseBody) {

        ObjectMapper objectMapper = new ObjectMapper( );
        objectMapper.registerModule(new JavaTimeModule());
        List<TableDto> table_dtos;
        try {
            table_dtos = objectMapper
                    .readValue(responseBody, new TypeReference<List<TableDto>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return table_dtos;
    }







}
