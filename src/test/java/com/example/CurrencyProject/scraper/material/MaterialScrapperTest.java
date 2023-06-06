package com.example.CurrencyProject.scraper.material;

import com.example.CurrencyProject.model.material.Material;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;


class MaterialScrapperTest {

    MaterialScrapper materialScrapper = spy(MaterialScrapper.class);

    Environment environment =mock(Environment.class);

    @BeforeEach
    public void init() throws IOException {

       String path = Path.of("src").toRealPath().toString() ;
       path = path + "\\test\\" + "java\\com\\example\\CurrencyProject\\scraper\\material\\material.html";

       File f = new File(path);

       Document document = Jsoup.parse(f);

       // then
        when(environment.getProperty("api.material.url"))
                .thenReturn("https://www.bankier.pl");

        materialScrapper.setEnv(environment);

        when(materialScrapper.getSite()).thenReturn(document);

    }

    @Test
    void getSite() throws IOException {

        // given
        Document document = materialScrapper.getSite();

        //then
        assertNotNull(document);
    }

    @Test
    void getAllMaterials() throws IOException {

        // given
        List<Material> allMaterials = materialScrapper.getAllMaterials();

        System.out.println(allMaterials);
        // then
        assertThat(allMaterials.size(),equalTo(35));
    }


}