package com.example.CurrencyProject.externalApi.crypto;

import com.example.CurrencyProject.dto.crypto.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

@Component
public class CryptoApi {

    private final String allCryptosUrl = "https://mkt-api.kminfra.net/api/web";

    private final String historyCryptoUrl= "https://api.kriptomat.io/public/prices-history";

    private WebClient webClient;

    public CryptoApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(historyCryptoUrl).defaultHeader(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Cacheable(cacheNames = "AllCrypto")
    public CryptoObjects getAllCrypto() {
        initWebClient(allCryptosUrl);
        return webClient.get().uri(UriBuilder::build).retrieve()
                .bodyToMono(CryptoObjects.class).block();
    }

   @Cacheable(cacheNames = "CryptoForPeriod")
   public DataHistory getCryptoForPeriod(String cryptoCode, PeriodCrypto period) {
        initWebClient(historyCryptoUrl);
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path("/"+cryptoCode)
                .queryParam("period",period.name())
                .build()).retrieve().bodyToMono(CryptoHistory.class).block().getData();
   }

   public void initWebClient(String url) {
       this.webClient = WebClient.builder().baseUrl(url).defaultHeader(HttpHeaders.CONTENT_TYPE,
               MediaType.APPLICATION_JSON_VALUE).build();
   }


}
