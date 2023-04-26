package com.example.CurrencyProject.dto.AB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency_Dto {

    private String table ;
    private String currency ;

    private String code ;


    private List<Rate> rates ;



}
