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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService {


    private final MaterialScrapper materialScrapper;

    private final MaterialMapper materialMapper;



    public MaterialService(MaterialScrapper materialScrapper, MaterialMapper materialMapper) {

        this.materialScrapper = materialScrapper;
        this.materialMapper = materialMapper;
    }

    public List<Material> getAllMaterials() throws Exception {

        List<Material> materials = materialScrapper.getAllMaterials();
        materials.sort(Comparator.comparing(material -> material.getUnit().toLowerCase()));

        Collections.reverse(materials);
        return materials;
    }

    public List<Material> getMaterialsAlphabetically() throws Exception {

        List<Material> materials = getAllMaterials();

        materials.sort(Comparator.comparing(material -> material.getName().toLowerCase()));
        return materials;
    }

    public List<Material> getMaterialsAlphabeticallyReversed() throws Exception {

        List<Material> materials = getMaterialsAlphabetically();

        Collections.reverse(materials);
        return materials;
    }


    public List<Material> getMaterialsByPercentFall() throws Exception {

        List<Material> materials = getAllMaterials();

        materials.sort(Comparator.comparingDouble(Material::getPercentChange).reversed());
        return materials;
    }

    public List<Material> getMaterialsByPercentGrow() throws Exception {

        List<Material> materials = getAllMaterials();

        materials.sort(Comparator.comparingDouble(Material::getPercentChange));
        return materials;
    }

    public List<Material> getMaterialsByChangeGrow() throws Exception {

        List<Material> materials = getAllMaterials();

        materials.sort(Comparator.comparingDouble(Material::getChange));
        return materials;
    }

    public List<Material> getMaterialsByChangeFall() throws Exception {

        List<Material> materials = getAllMaterials();

        materials.sort(Comparator.comparingDouble(Material::getChange).reversed());
        return materials;
    }




    public List<Material> getMaterialsByPriceFall() throws Exception {

        List<Material> materials = getAllMaterials();

        materials.sort(Comparator.comparingDouble(Material::getPrice).reversed());
        return materials;
    }

    public List<Material> getMaterialsByPriceGrow() throws Exception {

        List<Material> materials = getAllMaterials();

        materials.sort(Comparator.comparingDouble(Material::getPrice));
        return materials;
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
