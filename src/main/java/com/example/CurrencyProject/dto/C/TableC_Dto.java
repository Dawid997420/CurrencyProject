package com.example.CurrencyProject.dto.C;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableC_Dto {

   private String table ;

   private String no;

   private LocalDate effectiveDate;

   private List<TableC_Rate> rates ;

}