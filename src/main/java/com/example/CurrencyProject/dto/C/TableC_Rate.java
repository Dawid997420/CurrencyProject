package com.example.CurrencyProject.dto.C;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TableC_Rate {

   private String currency;

   private String code ;

   private double bid ;

   private double ask ;


}
