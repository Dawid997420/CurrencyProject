package com.example.CurrencyProject.dto.AB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TableDto {


   private String table ;

   private String no ;

   private LocalDate effectiveDate;

   private List<RateTable> rates;
}

