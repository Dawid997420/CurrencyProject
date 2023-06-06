package com.example.CurrencyProject.mapper;

import com.example.CurrencyProject.dto.material.MaterialDto;
import com.example.CurrencyProject.dto.material.MaterialSymbol;
import com.example.CurrencyProject.externalApi.material.MaterialApi;
import com.example.CurrencyProject.model.material.Material;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class MaterialMapper {

    private final MaterialApi materialApi;

    public MaterialMapper(MaterialApi materialApi) {
        this.materialApi = materialApi;
    }

    public List<Material> getMaterialPricesForPeriod(MaterialSymbol symbol, LocalDateTime startDate,
                                                     LocalDateTime endDate) {

        String startDateStr = String.valueOf(startDate.toEpochSecond(ZoneOffset.UTC) * 1000);
        String endDateStr =String.valueOf( endDate.toEpochSecond(ZoneOffset.UTC) * 1000);


        return buildMaterialList(symbol,
                materialApi.getMaterialPricesForPeriodTime(symbol,startDateStr,endDateStr) );
    }


    public List<Material> getMaterialPricesMaxPeriod(MaterialSymbol symbol){

        return buildMaterialList(symbol,materialApi.getMaterialPricesMaxPeriod(symbol));
    }

    private List<Material> buildMaterialList(MaterialSymbol symbol, MaterialDto materialDtos) {

        return materialDtos.getMain().stream().map(obj ->{
            return Material.builder().name(symbol.getSymbol()).id(symbol.name())
                    .data(convertTimeStampToLocalDate(obj.get(0)))
                    .currency(symbol.getCurrencyCode()).price(Double.parseDouble(obj.get(1))).build();
        }).toList();
    }

    private LocalDateTime convertTimeStampToLocalDate(String timestampMillis) {

        long timestamp =  Long.parseLong(timestampMillis);
        Instant instant = Instant.ofEpochMilli(timestamp);

        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
