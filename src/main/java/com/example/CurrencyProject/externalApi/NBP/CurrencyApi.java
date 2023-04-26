package com.example.CurrencyProject.externalApi.NBP;


import com.example.CurrencyProject.dto.AB.Currency_Dto;
import com.example.CurrencyProject.dto.AB.TableDto;
import com.example.CurrencyProject.model.Group;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
public class CurrencyApi {

    private final String API_URL ;

    private final WebClient webClient;



    HttpClient client = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(10));

    public CurrencyApi(@Value("${api.nbp.url}") String apiUrl , WebClient.Builder webClientBuilder) {



        this.API_URL = apiUrl + "/exchangerates";
        this.webClient = webClientBuilder.baseUrl(API_URL).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(client)).build();

    }





    public List<TableDto> getTableByDate(LocalDate date, Group group) {

        Mono<List<TableDto>> tableAMono = webClient.get().uri(uriBuilder -> uriBuilder
                .path("/tables")
                .path("/"+ group.getGroup())
                .path("/" + date)
                .queryParam("format","json")
                .build()).retrieve().bodyToMono(String.class).map(this::createListTableDto);

        return tableAMono.block();
    }




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


    public Mono<Currency_Dto> getCurrencyBetween(Group group,String currencyCode,
                                           LocalDate startDate, LocalDate endDate) {

        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/rates")
                        .path("/" + group)
                        .path("/" + currencyCode)
                        .path("/" + startDate)
                        .path("/" + endDate)
                        .queryParam("format","json")
                        .build()).retrieve().bodyToMono(Currency_Dto.class);



    }




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

        return currenciesFromTable_Mono;
    }

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
