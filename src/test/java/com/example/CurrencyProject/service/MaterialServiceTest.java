package com.example.CurrencyProject.service;

import com.example.CurrencyProject.dto.material.MaterialSymbol;
import com.example.CurrencyProject.externalApi.material.MaterialApi;
import com.example.CurrencyProject.mapper.MaterialMapper;
import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.material.MaterialScrapper;
import com.example.CurrencyProject.scraper.metal.enums.*;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;

class MaterialServiceTest {

    MaterialScrapper materialScrapper = mock(MaterialScrapper.class);

   MaterialMapper materialMapper = mock(MaterialMapper.class);

    MaterialService materialService;

    MaterialFactory materialFactory =new MaterialFactory() ;

    @BeforeEach
    void init(){

        // given

        // when

        // then
        materialService = new MaterialService(materialScrapper,materialMapper);
    }


    @Test
    void getMaterialForDays() {

        // given
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime monthBef = today.minusMonths(1);

        when( materialMapper.getMaterialPricesForPeriod(MaterialSymbol.ALUMINIUM,monthBef,today))
               .thenReturn(materialFactory.createMaterialBetweenTenDays("gold"));

        // then
        List<Material> materials =materialService
                .getMaterialForDays(MaterialSymbol.ALUMINIUM,30L);

        System.out.println(materials);
        // assertThat(materials.size(),equalTo());
    }


}