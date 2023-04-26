package com.example.CurrencyProject.service;

import com.example.CurrencyProject.exception.MaterialNotFoundException;
import com.example.CurrencyProject.model.Material;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {


    private final GoldService goldService;


    public MaterialService(GoldService goldService) {
        this.goldService = goldService;
    }

    public List<Material> getAllMaterials() {

       Mono<Material> goldValueMono = goldService.getActualGoldValue();
       Mono<Material> goldValueMono2 = goldService.getActualGoldValue();



       return Mono.zip(goldValueMono,goldValueMono2)
               .map( list -> {
                   List<Material> allMaterials = new ArrayList<>();
                   allMaterials.add(list.getT1());

                   return allMaterials;
               })
               .block();

    }


    public List<Material> getMaterialForDays( String name , int days) {

        if ( name.equals("gold")) {

            return goldService.getGoldForDays(days).block();
        } else {

            throw new MaterialNotFoundException("Material with this name couldn't be found");
        }

    }


    public List<Material> getMaterialForYears(String name , int years) {

        if ( name.equals("gold")) {

            return goldService.getGoldForYears(years);
        } else {

            throw new MaterialNotFoundException("Material with this name couldn't be found");
        }

    }


}
