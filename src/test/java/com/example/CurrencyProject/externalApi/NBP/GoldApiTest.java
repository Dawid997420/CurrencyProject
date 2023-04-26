package com.example.CurrencyProject.externalApi.NBP;

import com.example.CurrencyProject.dto.GoldDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GoldApiTest {


    @Autowired
    private WebTestClient webTestClient;


    @Value("${api.nbp.url}")
    private String apiUrl ;


    @BeforeEach
    public void init() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl(apiUrl).build();
    }

    @Test
    void getActualGoldValue() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                .path("/cenyzlota")
                .queryParam("format","json")
                .build()).exchange().expectStatus().isOk()
                .expectBodyList(GoldDto.class);

    }


    @Test
    void getGoldMostActualDays() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                .path("/cenyzlota")
                .queryParam("format","json")
                .build()).exchange().expectStatus().isOk()
                .expectBodyList(GoldDto.class);
    }


    @Test
    void getGoldBetween() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/cenyzlota")
                        .path("/2022-02-25")
                        .path("/2022-11-25")
                        .queryParam("format","json")
                        .build()).exchange().expectStatus().isOk()
                .expectBodyList(GoldDto.class);

    }

}