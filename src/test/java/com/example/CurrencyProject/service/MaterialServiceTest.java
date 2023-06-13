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

import static org.hamcrest.Matchers.*;
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


    @Test
    void getAllMaterials() throws Exception {

        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getAllMaterials();

        // then
        assertThat(result,equalTo(materials));

    }


    @Test
    void getMaterialsAlphabetically() throws Exception {

        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsAlphabetically();

        // then
        assertThat(result.get(0).getName(), equalTo("aluminium") );
    }



    @Test
    void getMaterialsAlphabeticallyReversed() throws Exception {

        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsAlphabeticallyReversed();

        // then
        assertThat(result.get(0).getName(), equalTo("złoto") );

    }


    @Test
    void getMaterialsByPriceFall() throws Exception {


        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsByPriceFall();

        // then
        assertThat(result.get(0).getPrice(), greaterThan(result.get(10).getPrice()) );

    }

    @Test
    void getMaterialsByPriceGrow() throws Exception {
        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsByPriceGrow();

        // then
        assertThat(result.get(0).getPrice(), lessThan(result.get(10).getPrice()) );
    }

    @Test
    void getMaterialsByPercentFall() throws Exception {
        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsByPercentFall();

        // then
        assertThat(result.get(0).getPercentChange(), greaterThan(result.get(10).getPercentChange()) );
    }

    @Test
    void getMaterialsByPercentGrow() throws Exception {
        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsByPercentGrow();

        // then
        assertThat(result.get(0).getPercentChange(), lessThan(result.get(10).getPercentChange()) );
    }

    @Test
    void getMaterialsByChangeFall() throws Exception {

        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsByChangeFall();

        // then
        assertThat(result.get(0).getChange(), greaterThan(result.get(10).getChange()) );
    }

    @Test
    void getMaterialsByChangeGrow() throws Exception {

        // given
        List<Material> materials = materialFactory.createAllMaterials();
        when(materialScrapper.getAllMaterials()).thenReturn(materials);

        // when
        List<Material> result = materialService.getMaterialsByChangeGrow();

        // then
        assertThat(result.get(0).getChange(), lessThan(result.get(10).getChange()) );
    }
}