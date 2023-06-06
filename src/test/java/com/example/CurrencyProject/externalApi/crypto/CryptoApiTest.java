package com.example.CurrencyProject.externalApi.crypto;

import com.example.CurrencyProject.dto.crypto.CryptoHistory;
import com.example.CurrencyProject.dto.crypto.CryptoObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CryptoApiTest {


    @Autowired
    private WebTestClient webTestClient;


    private String allCryptosUrl = "https://mkt-api.kminfra.net/api/web";
    private String historyCryptoUrl = "https://api.kriptomat.io/public/prices-history";


    @BeforeEach
    public void init(){
        webTestClient = WebTestClient.bindToServer().baseUrl(allCryptosUrl)
                .build() ;
    }



    @Test
    void getAllCrypto() {

        // given
        initWebClient(allCryptosUrl);

        webTestClient.get().uri(UriBuilder::build)
                .exchange().expectStatus().isOk().expectBody(CryptoObjects.class)
                .consumeWith(response -> {
                    CryptoObjects cryptoObjects = response.getResponseBody();
                    assertNotNull(cryptoObjects);
                });


    }

    @Test
    void getCryptoForPeriod() {

        // given
        initWebClient(historyCryptoUrl);

        // then
        webTestClient.get().uri(uriBuilder -> uriBuilder
                        .path("/btc")
                        .queryParam("period","year").build())
                .exchange().expectStatus().isOk().expectBody(CryptoHistory.class)
                .consumeWith(response -> {
                   CryptoHistory cryptoHistory = response.getResponseBody();
                   assertNotNull(cryptoHistory);
                });

    }

    void initWebClient(String url) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl(url).build();
    }


}