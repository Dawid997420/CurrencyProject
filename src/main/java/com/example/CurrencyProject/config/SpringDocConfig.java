package com.example.CurrencyProject.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@OpenAPIDefinition
public class SpringDocConfig {


    @Bean
    public OpenAPI baseOpenApi() {
        return new OpenAPI().info(new Info().title("Currency Project")
                .version("1.0.0").description("This project provides current price " +
                        "data of currencies, materials and cryptocurrencies and " +
                        "their historical data price, for each of them historical data" +
                        " allowed can be different"))
                .tags(getTags());
    }


    List<Tag> getTags() {

        List<Tag> tagList = new ArrayList<>();

        Tag currencies = new Tag();
        currencies.setName("Currencies");

        Tag materials = new Tag();
        materials.setName("Materials");

        Tag cryptos = new Tag();
        cryptos.setName("Cryptos");
    return tagList;
    }

}

