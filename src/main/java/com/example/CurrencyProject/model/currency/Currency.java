package com.example.CurrencyProject.model.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Currency {


    private LocalDate effectiveDate ;

    private String code ;

    private String currency ;

    private String country ;

    private Group group ;

    private double midPrice ;

    private double change ;

    private double percentChange ;


}
