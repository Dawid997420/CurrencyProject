package com.example.CurrencyProject.externalApi.material;

import com.example.CurrencyProject.dto.material.MaterialDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MaterialApiTest {


    @Autowired
    private WebTestClient webTestClient ;

    private final String materialUrl = "https://www.bankier.pl/new-charts/get-data";

    @BeforeEach
    public void init() {
        webTestClient = WebTestClient.bindToServer().baseUrl(materialUrl)
                .build();
    }

    @Test
    void getMaterialPricesForPeriodTime() {

        webTestClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("date_from","1669942800000")
                .queryParam("date_to","1685664000000")
                .queryParam("symbol","ROPA")
                .queryParam("intraday","false")
                .queryParam("type","area").build()).exchange().
                expectStatus().isOk().expectBody(MaterialDto.class)
                .consumeWith(response-> {
                    MaterialDto materialDto = response.getResponseBody();

                    System.out.println(materialDto);
                    assertNotNull(materialDto);

                });
    }


    @Test
    void testGetMaterialPricesForPeriodTime() {
    }
}