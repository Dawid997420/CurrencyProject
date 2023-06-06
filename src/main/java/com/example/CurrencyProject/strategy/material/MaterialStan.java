package com.example.CurrencyProject.strategy.material;

import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import com.example.CurrencyProject.scraper.metal.enums.Metal;
import com.example.CurrencyProject.service.GoldService;

import java.util.List;

public class MaterialStan {

    MaterialStrategy materialStrategy ;

    public MaterialStrategy getMaterialStrategy() {
        return materialStrategy;
    }

    public MaterialStan(Metal metal, GoldService goldService, MetalScrapper metalScrapper) {
        if (metal.equals(Metal.gold)) {
            this.materialStrategy = new GoldStrategy( metalScrapper);
        } else if ( metal.equals(Metal.silver)){
            this.materialStrategy = new SilverStrategy(metalScrapper);
        } else if ( metal.equals(Metal.platinum)) {
            this.materialStrategy = new PlatinumStrategy(metalScrapper);
        } else if ( metal.equals(Metal.palladium)) {
            this.materialStrategy = new PalladiumStrategy(metalScrapper);
        }
    }

    public  List<Material> getMaterialForDays(String days) {
     
        return materialStrategy.getMaterialForDays(days);
    }

}
