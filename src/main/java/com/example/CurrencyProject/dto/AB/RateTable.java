package com.example.CurrencyProject.dto.AB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateTable {


    private String currency;

    private String code;

    private double mid;

}
