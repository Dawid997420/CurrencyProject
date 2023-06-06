package com.example.CurrencyProject.service;

import com.example.CurrencyProject.model.material.Material;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaterialFactory {

    public List<Material> createAllMaterials() {

        List<Material> allMaterials = new ArrayList<>();

        List<String> materials = Arrays.asList("silver","gold","platinum","palladium");
        List<Double> prices = Arrays.asList(99.0,266.77, 145.34, 199.36);

        for ( int i =0 ; i < 4; i++) {
            Material material = Material.builder().name(materials.get(i))
                    .data(LocalDateTime.of(2023,11,11,2,1))
                    .unit("gram").price(prices.get(i)).change(21.9)
                    .percentChange(0.12).currency("PLN").build();

            allMaterials.add(material);
        }

        return allMaterials;
    }

    public Material createMaterial(String name) {

        return Material.builder().name(name).currency("PLN").percentChange(20.9).change(12.12)
                .unit("gram").price(212.0)
                .data(LocalDateTime.of(2001,12,1,13,21,12))
                .build();

    }



    public List<Material> createMaterialForTwoDays(String name) {

        List<Material> materialList = new ArrayList<>();

        List<LocalDate> localDates = Arrays.asList(LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,2));

        double[] prices = { 200,201};

        for ( int i = 0 ; i < 2 ; i++) {

            materialList.add(Material.builder().name(name)
                    .data(localDates.get(i).atStartOfDay())
                    .price(prices[i]).build());
        }
        return materialList;
    }

    public List<Material> createMaterialBetweenTenDays(String name) {

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        int days = 10;

        LocalDate today = LocalDate.now(polishZone);


        List<Material> materialList = new ArrayList<>();

        List<LocalDate> localDates = Arrays.asList(today,today.minusDays(1),
                today.minusDays(2),today.minusDays(3),today.minusDays(4),
                today.minusDays(5),today.minusDays(6),
                today.minusDays(7),today.minusDays(8),
                today.minusDays(9));

        double[] prices = { 200,201,202,201,200,199,204,210,242,220,215,209};

        for ( int i = 0 ; i < 10 ; i++) {

            materialList.add(Material.builder().name(name)
                    .data(localDates.get(i).atStartOfDay())
                    .price(prices[i]).build());
        }
        return materialList;
    }

}
