package com.example.CurrencyProject.scraper.metal;

import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.metal.enums.*;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;


class MetalScrapperTest {


    MetalScrapper metalScrapper = spy(MetalScrapper.class);


    Environment environment =mock(Environment.class);

    @BeforeEach
    public void init() throws Exception {


        String path = Path.of("src").toRealPath().toString();
        path = path + "\\test\\" + "java\\com\\example\\CurrencyProject\\scraper\\metal\\metal.html";

        File f = new File(path);

        Document document = Jsoup.parse(f);

        // then
        when(environment.getProperty("api.metal.url"))
                .thenReturn("https://www.coininvest.com");
        metalScrapper.setEnv(environment);
        when(metalScrapper.getSite()).thenReturn(document);

    }

    @Test
    void test(){

    }

    @Test
    void getMetalActualShouldGiveSilver() throws Exception {

        // given
          Material expectedMaterial = Material.builder().name("silver")
                  .data(LocalDateTime.of(2023,5,17,14,8,10))
                  .unit("gram").price(3.18).change(0.01).percentChange(0.37).currency("PLN")
                  .build();

          Material material = metalScrapper.getMetalActual(Metal.silver, Weight.gram).block();

        // when

        // then
        assertThat(material, equalTo(expectedMaterial));
    }


    @Test
    void getMetalActualShouldGivePlatinum() throws Exception {

        // given
        Material expectedMaterial = Material.builder().name("platinum")
                .data(LocalDateTime.of(2023,5,17,14,8,10))
                .unit("ounce").price(4529.96).change(80.64).percentChange(1.81).currency("PLN")
                .build();

        Material material = metalScrapper.getMetalActual(Metal.platinum,Weight.ounce).block();

        // when

        // then
        assertThat(material, equalTo(expectedMaterial));
    }



    @Test
    void getMetalActualShouldGivePalladium() throws Exception {

        // given
        Material expectedMaterial = Material.builder().name("palladium")
                .data(LocalDateTime.of(2023,5,17,14,8,10))
                .unit("kilogram").price(201439.06).change(1905.25).percentChange(0.95).currency("PLN")
                .build();

        Material material = metalScrapper.getMetalActual(Metal.palladium,Weight.kilogram).block();

        // when

        // then
        assertThat(material, equalTo(expectedMaterial));
    }



    @Test
    void getMetalActualShouldGiveGold() throws Exception {

        // given
        Material expectedMaterial = Material.builder().name("gold")
                .data(LocalDateTime.of(2023,5,17,14,8,10))
                .unit("gram").price(266.15).change(1.65).percentChange(0.62).currency("PLN")
                .build();

        Material material = metalScrapper.getMetalActual(Metal.gold,Weight.gram).block();

        // when

        // then
        assertThat(material, equalTo(expectedMaterial));
    }


    @Test
    void getMaterialForTimePeriodShouldGiveSilver() throws IOException {

        // given
        List<Material> materialList = metalScrapper
                .getMaterialForTimePeriod(Metal.silver, CurrencyCode.PLN, WeightCode.gr, Period.Year);

        // when

        // then
        assertThat(materialList.size(),equalTo(310));

    }


}