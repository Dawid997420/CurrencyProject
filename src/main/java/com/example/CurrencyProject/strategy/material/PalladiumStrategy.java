package com.example.CurrencyProject.strategy.material;

import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import com.example.CurrencyProject.scraper.metal.enums.CurrencyCode;
import com.example.CurrencyProject.scraper.metal.enums.Metal;
import com.example.CurrencyProject.scraper.metal.enums.Period;
import com.example.CurrencyProject.scraper.metal.enums.WeightCode;

import java.io.IOException;
import java.util.List;

public class PalladiumStrategy implements MaterialStrategy {


    private final MetalScrapper metalScrapper;

    public PalladiumStrategy(MetalScrapper metalScrapper) {
    this.metalScrapper =metalScrapper;
    }


    @Override
    public List<Material> getMaterialForDays(String days) {

        try {
            return metalScrapper.getMaterialForTimePeriod(Metal.palladium, CurrencyCode.PLN,
                    WeightCode.gr, Period.getPeriodFromDays(days) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
