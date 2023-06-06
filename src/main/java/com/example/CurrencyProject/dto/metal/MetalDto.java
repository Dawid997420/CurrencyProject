package com.example.CurrencyProject.dto.metal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetalDto {

    private LocalDateTime dateTime;

    private double price ;  ;

}
