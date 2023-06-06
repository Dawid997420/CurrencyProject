package com.example.CurrencyProject.strategy.material;

import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import com.example.CurrencyProject.scraper.metal.enums.CurrencyCode;
import com.example.CurrencyProject.scraper.metal.enums.Metal;
import com.example.CurrencyProject.scraper.metal.enums.Period;
import com.example.CurrencyProject.scraper.metal.enums.WeightCode;
import com.example.CurrencyProject.service.GoldService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoldStrategy implements MaterialStrategy {



    private final MetalScrapper metalScrapper ;

    public GoldStrategy( MetalScrapper metalScrapper) {

        this.metalScrapper = metalScrapper;
    }

    @Override
    public List<Material> getMaterialForDays(String days) {
        try {

            return metalScrapper.getMaterialForTimePeriod
                            (Metal.gold, CurrencyCode.PLN, WeightCode.gr
                                    ,Period.getPeriodFromDays(days) );

        } catch (Exception e) {
            throw new RuntimeException("wrong [days] value for this Material");
        }
    }

}

