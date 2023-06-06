package com.example.CurrencyProject.mapper.material;

import com.example.CurrencyProject.dto.material.MaterialDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaterialDtoFactory {


    public MaterialDto createMaterialDto() {

        List<List<String>> objects=new ArrayList<>();

        List<String> timeStamps = Arrays.asList("1675123200000","1677542400000",
                "1680220800000","1682640000000","1685491200000","1685577600000",
                "1574985600000","1572480000000","1569801600000","1567123200000");

        List<String> prices = Arrays.asList("61.19","66.41","74.23","79.23",
                "77.64","74.69","62.67","61.16","56.73","52.81");

        for ( int i = 0 ; i < 40 ; i++) {
            List<String> strings = Arrays.asList( timeStamps.get(i%10) ,prices.get(i%10) );
            objects.add(strings);
        }
       return MaterialDto.builder().main(objects).build();
    }

}
