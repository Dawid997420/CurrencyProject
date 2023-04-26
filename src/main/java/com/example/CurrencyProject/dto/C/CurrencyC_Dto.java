package com.example.CurrencyProject.dto.C;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyC_Dto {


    private String table ;

    private String currency ;

    private String code ;

    private String no ;


    private List<RateC> rates ;

}
