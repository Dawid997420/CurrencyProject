package com.example.CurrencyProject.strategy.material;

import com.example.CurrencyProject.model.material.Material;

import java.util.List;

public interface MaterialStrategy {
    List<Material> getMaterialForDays(String days) ;
}
