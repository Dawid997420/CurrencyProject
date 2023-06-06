package com.example.CurrencyProject.dto.crypto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoDto {

   private String n;
   private String t ;
   private double p ;
   private long mc;
   private int mcr;
   private long v;
   private String h24 ;
   private String l24 ;
   private String pc24 ;
   private String pcp24 ;
   private String mc24 ;
   private Double mcp ;
   private double mcpr ;
   private long s ;
   private long ts;
   private long ms;
   private String ath;
   private String athp;
   private String at1;
   private double p1;
   private double p24;
   private double p7;
   private double p14;
   private double p30;
   private double p200;
   private double p365;
   private long v7 ;
   private long v14 ;
   private long v30 ;
   private boolean has_earn ;
   private int earn_rate ;
   private boolean earn_promo;
   private double[] wph ;
   private double change;


}
