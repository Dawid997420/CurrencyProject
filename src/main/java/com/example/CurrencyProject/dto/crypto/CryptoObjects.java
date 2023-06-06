package com.example.CurrencyProject.dto.crypto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoObjects {

    private Map<String,CryptoDto> data ;

}
