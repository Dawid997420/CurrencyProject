package com.example.CurrencyProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeOption {

    private AssetType assetType;
    private String name ;
    private String value ;

}
