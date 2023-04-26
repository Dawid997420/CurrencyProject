package com.example.CurrencyProject.externalApi.NBP;

import com.example.CurrencyProject.dto.AB.Currency_Dto;
import com.example.CurrencyProject.dto.AB.TableDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CurrencyApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${api.nbp.url}")
    private String apiUrl;


    @BeforeEach
    public void init(){
       webTestClient = WebTestClient.bindToServer().baseUrl(apiUrl)
               .build() ;
    }

    @Test
    void getActualCurrencyShouldBeOk() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                .path("/exchangerates")
                .path("/rates")
                .path("/A")
                .path("/USD")
                .queryParam("format","json")
                .build()).exchange().expectStatus().isOk().expectBody(Currency_Dto.class)
                .consumeWith(response -> {
                    Currency_Dto currencyABDto = response.getResponseBody();
                    assertNotNull(currencyABDto);
                    assertEquals(currencyABDto.getTable(),"A");
                    assertEquals(currencyABDto.getCode(),"USD");
                    assertNotNull(currencyABDto.getRates().get(0));

                });;

    }





    @Test
    void getActualCurrenciesTableB() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/tables")
                        .path("/b")
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk().expectBodyList(TableDto.class)
                .consumeWith(response -> {
                    List<TableDto> table_dtos = response.getResponseBody();
                    assertNotNull(table_dtos);
                    assertThat(table_dtos.get(0).getTable(),equalTo("B"));
                    assertThat(table_dtos.get(0).getRates().size(), greaterThan(10));
                });
    }

    @Test
    void getActualCurrenciesTableA() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/tables")
                        .path("/a")
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk().expectBodyList(TableDto.class)
                .consumeWith(response -> {
                    List<TableDto> table_dtos = response.getResponseBody();
                    assertNotNull(table_dtos);
                    assertThat(table_dtos.get(0).getTable(),equalTo("A"));
                    assertThat(table_dtos.get(0).getRates().size(), greaterThan(10));
                });
    }

    @Test
    void getCurrencyByDateA() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/rates")
                        .path("/a")
                        .path("/USD")
                        .path("/"+ LocalDate.of(2023,4,13))
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk().expectBody(Currency_Dto.class)
                .consumeWith(response -> {
                    Currency_Dto currencyABDto = response.getResponseBody();
                    assertNotNull(currencyABDto);
                    assertEquals(currencyABDto.getTable(),"A");
                    assertEquals(currencyABDto.getCode(),"USD");
                    assertNotNull(currencyABDto.getRates().get(0));

                });

    }

    @Test
    void getCurrencyByDateB() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/rates")
                        .path("/b")
                        .path("/ALL")
                        .path("/"+ LocalDate.of(2023,4,12))
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk().expectBody(Currency_Dto.class)
                .consumeWith(response -> {
                    Currency_Dto currencyABDto = response.getResponseBody();
                    assertNotNull(currencyABDto);
                    assertEquals(currencyABDto.getTable(),"B");
                    assertEquals(currencyABDto.getCode(),"ALL");
                    assertNotNull(currencyABDto.getRates().get(0));

                });
    }

    @Test
    void getTableA_ByDate() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/tables")
                        .path("/a")
                        .path("/2023-02-14")
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk().expectBodyList(TableDto.class)
                .consumeWith(response -> {
                    List<TableDto> table_dtos = response.getResponseBody();
                    assertNotNull(table_dtos);
                    assertThat(table_dtos.get(0).getTable(),equalTo("A"));
                    assertThat(table_dtos.get(0).getRates().size(), greaterThan(10));
                });


    }


    @Test
    void getTableB_ByDate() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/tables")
                        .path("/b")
                        .path("/2023-02-08")
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk().expectBodyList(TableDto.class)
                .consumeWith(response -> {
                    List<TableDto> table_dtos = response.getResponseBody();
                    assertNotNull(table_dtos);
                    assertThat(table_dtos.get(0).getTable(),equalTo("B"));
                    assertThat(table_dtos.get(0).getRates().size(), greaterThan(10));
                });

    }



    @Test
    void getCurrenciesTableMostActualDays() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/tables")
                        .path("/a")
                        .path("/last")
                        .path("/2")
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk().expectBodyList(TableDto.class)
                .consumeWith(response -> {
                    List<TableDto> table_dtos = response.getResponseBody();
                    assertNotNull(table_dtos);
                    assertThat(table_dtos.get(0).getTable(),equalTo("A"));
                    assertThat(table_dtos.size(), equalTo(2));
                });
    }


    @Test
    void getCurrencyBetween() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                .path("/exchangerates")
                .path("/rates")
                .path("/a")
                .path("/USD")
                .path("/2022-04-28")
                .path("/2023-04-20")
                .queryParam("format","json").build())
                .exchange().expectStatus().isOk().expectBody(Currency_Dto.class);


    }


    @Test
    void getCurrencyMostActualDays() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/exchangerates")
                        .path("/rates")
                        .path("/a")
                        .path("/USD")
                        .path("/last")
                        .path("/60")
                        .queryParam("format","json").build())
                .exchange().expectStatus().isOk().expectBody(Currency_Dto.class);

    }


}