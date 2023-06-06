package com.example.CurrencyProject.externalApi.material;

import com.example.CurrencyProject.dto.material.MaterialDto;
import com.example.CurrencyProject.dto.material.MaterialSymbol;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MaterialApi {

    private final String materialUrl ="https://www.bankier.pl/new-charts/get-data";

    private WebClient webClient;

    public MaterialApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(materialUrl).defaultHeader(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Cacheable(cacheNames = "MaterialPricesForPeriodTime")
    public MaterialDto getMaterialPricesForPeriodTime(MaterialSymbol symbol
            ,String startDate, String endDate) {

        return webClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("date_from",startDate)
                .queryParam("date_to",endDate)
                .queryParam("symbol",symbol.getSymbol())
                .queryParam("intraday","false")
                .queryParam("type","area").build())
                .retrieve().bodyToMono(MaterialDto.class).block();
    }

    @Cacheable(cacheNames = "MaterialPricesMaxPeriod")
    public MaterialDto getMaterialPricesMaxPeriod(MaterialSymbol symbol) {

        return webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParam("symbol",symbol.getSymbol())
                        .queryParam("intraday","false")
                        .queryParam("type","area")
                        .queryParam("max_period","true").build())
                .retrieve().bodyToMono(MaterialDto.class).block();
    }



}
