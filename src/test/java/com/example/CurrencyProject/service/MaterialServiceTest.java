package com.example.CurrencyProject.service;

import com.example.CurrencyProject.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;

class MaterialServiceTest {

    GoldService goldService = mock(GoldService.class);

    MaterialService materialService;


    @BeforeEach
    void init(){

        // given

        // when

        // then
        materialService = new MaterialService(goldService);
    }

    @Test
    void getAllMaterials() {

        // given
        Material material = Material.builder().name("gold").price(211).build();
        when(goldService.getActualGoldValue()).thenReturn(Mono.just(material));

        // when

        List<Material> materials =  materialService.getAllMaterials();
        // then

        assertThat(materials.size(),equalTo(1));

    }


}