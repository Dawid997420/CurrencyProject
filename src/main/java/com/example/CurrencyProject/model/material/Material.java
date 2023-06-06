package com.example.CurrencyProject.model.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {


    private String name;

    private String id ;

    private LocalDateTime data;

    private String unit ;

    private double price;

    private double change;
    
    private double percentChange;

    private String currency ;


}
