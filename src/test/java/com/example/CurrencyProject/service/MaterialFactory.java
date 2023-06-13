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

        List<String> materials = Arrays.asList("złoto","miedź","platyna","pallad",
                "nikiel","aluminium","ołów","cynk","ropa wti", "miedź comex","bawełna",
                "benzyna","canola","cukier","diesel", "drewno", "gaz ziemny", "kakao",
                "kukurydza","mleko", "olej opałowy", "olej palmowy", "olej sojowy", "pszenica",
                "ryż","rzepak","soja","sok pomarańczowy", "śruta sojowa", "wieprzowina","wołowina");

        List<Double> prices = Arrays.asList(75.0,1973.85,8264.0,23.52, 1408.0,1031.85,
                20961.0,2222.5,2293.5, 2298.5,71.0, 3.73, 84.93,2.51,655.1, 24.42 , 694.0
                , 145.34, 199.36 ,142.01, 201.05 ,12.0);

        List<String> jednostki = Arrays.asList("uncja","tona", "uncja", "uncja", "tona",
                "tona","tona", "tona", "baryłka", "funt", "funt", "galon", "tona","funt",
                "tona","1000 stóp deskowych","mln btu", "tona", "kilogram", "funt","buszel");

        List<Double> changes = Arrays.asList(0.90, 0.82, 0.72, 0.89 ,4.01,-0.14,-1.5,
                -3.12, 113.0, 0.0, 4.5, -0.92, -0.03,-0.22, -1.52, 3.11 , 9.2 ,1.11, -4.01,
                5.21, 9.01 , 7.12);

        List<Double> percents = Arrays.asList(-1.2,2.4, 1.01 ,0.3, -0.5, 0.245, 3.1, 6.0,
                12.0, 8.0 ,21.0 , -12.0 , 9.0 , 21.0 ,0.214 , 0.53, -0.95 , 0.13 ,-0.23 , 0.1 ,
                -0.3, 0.22 , 0.1234);

        for ( int i =0 ; i < 20; i++) {
            Material material = Material.builder().name(materials.get(i))
                    .data(LocalDateTime.of(2023,11,11,2,1))
                    .unit(jednostki.get(i)).price(prices.get(i)).change(changes.get(i))
                    .percentChange(percents.get(i) ).currency("PLN").build();

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
