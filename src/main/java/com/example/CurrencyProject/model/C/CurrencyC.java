package com.example.CurrencyProject.model.C;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyC {

    private LocalDate effectiveDate ;

    private String code ;

    private String currency ;

    private String country ;

    private double buyPrice ;

    private double sellPrice ;


}
