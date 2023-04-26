package com.example.CurrencyProject.dto.AB;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    private LocalDate effectiveDate ;

    private double mid ;

}
