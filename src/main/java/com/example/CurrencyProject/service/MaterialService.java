package com.example.CurrencyProject.service;

import com.example.CurrencyProject.dto.material.MaterialSymbol;
import com.example.CurrencyProject.exception.MaterialNotFoundException;
import com.example.CurrencyProject.mapper.MaterialMapper;
import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.material.MaterialScrapper;
import com.example.CurrencyProject.scraper.metal.enums.Metal;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import com.example.CurrencyProject.strategy.material.MaterialStan;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {


    private final MaterialScrapper materialScrapper;

    private final MaterialMapper materialMapper;



    public MaterialService(MaterialScrapper materialScrapper, MaterialMapper materialMapper) {

        this.materialScrapper = materialScrapper;
        this.materialMapper = materialMapper;
    }

    public List<Material> getAllMaterials() throws Exception {

        return materialScrapper.getAllMaterials();
    }


    public List<Material> getMaterialForDays(MaterialSymbol symbol, long days) {

        LocalDateTime today = todayDate();

        LocalDateTime dateBefore = today.minusDays(days);

       return materialMapper.getMaterialPricesForPeriod(symbol,dateBefore,today);
    }


    public List<Material> getMaterialMaximum(MaterialSymbol symbol) {

        return materialMapper.getMaterialPricesMaxPeriod(symbol);
    }

    public List<Material> getMaterialForYears(MaterialSymbol symbol , long years) {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dateBefore = today.minusYears(years);

        return materialMapper.getMaterialPricesForPeriod(symbol,dateBefore,today);
    }

    private LocalDateTime todayDate() {

        LocalDateTime today = LocalDateTime.now();
        today = today.withHour(0);
        today = today.withMinute(0);
        today = today.withSecond(0);
        return today;
    }

}
