package com.example.CurrencyProject.mapper.material;

import com.example.CurrencyProject.dto.material.MaterialSymbol;
import com.example.CurrencyProject.externalApi.material.MaterialApi;
import com.example.CurrencyProject.mapper.MaterialMapper;
import com.example.CurrencyProject.model.material.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MaterialMapperTest {

    MaterialApi materialApi = mock(MaterialApi.class);

    MaterialMapper materialMapper ;



    MaterialDtoFactory materialDtoFactory = new MaterialDtoFactory();

    @BeforeEach
    public void init() {

        // given
        materialMapper = new MaterialMapper(materialApi);

    }


    @Test
    void getMaterialPricesForPeriod() {

        // given
        when(materialApi.getMaterialPricesForPeriodTime(MaterialSymbol.DREWNO,
                "1640995200000","1640995200000"))
                .thenReturn(materialDtoFactory.createMaterialDto());


        // when
        List<Material> materialList = materialMapper.getMaterialPricesForPeriod
                (MaterialSymbol.DREWNO,
                 LocalDateTime.of(2022,1,1,0,0,0),
                LocalDateTime.of(2023,1,1,0,0,0));

        System.out.println(materialList);
        // then
        assertNotNull(materialList);

    }


}