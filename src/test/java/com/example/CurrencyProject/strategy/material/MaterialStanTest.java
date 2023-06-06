package com.example.CurrencyProject.strategy.material;

import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import com.example.CurrencyProject.scraper.metal.enums.Metal;
import com.example.CurrencyProject.service.GoldService;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.hamcrest.MatcherAssert.*;

class MaterialStanTest {

    @Test
    void constructorShouldWorkWithPlatinum() {
        // given
        GoldService goldService = mock(GoldService.class);
        MetalScrapper metalScrapper = mock(MetalScrapper.class);

        // when
        MaterialStan materialStan = new MaterialStan(Metal.palladium,goldService,metalScrapper);

        // then
        assertThat(materialStan.getMaterialStrategy(),instanceOf(PalladiumStrategy.class));
    }

    @Test
    void constructorShouldWorkWithPalladium() {
        // given
        GoldService goldService = mock(GoldService.class);
        MetalScrapper metalScrapper = mock(MetalScrapper.class);
        // when
        MaterialStan materialStan = new MaterialStan(Metal.platinum,goldService,metalScrapper);
        // then
        assertThat(materialStan.getMaterialStrategy(),instanceOf(PlatinumStrategy.class));
    }




}