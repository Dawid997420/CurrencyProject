package com.example.CurrencyProject.dto.C;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateC {



    private LocalDate effectiveDate ;
    private double bid ;

    private double ask ;

}
