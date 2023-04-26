package com.example.CurrencyProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {


    private String name;

    private LocalDate data;

    private String unit ;

    private double price;

    private double change;
    
    private double percentChange;


}
