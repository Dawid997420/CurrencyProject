package com.example.CurrencyProject.scraper.metal.enums;

public enum CurrencyCode {

    PLN {
        @Override
        public int getValue() {
            return 4;
        }
    },
    USD {
        @Override
        public int getValue() {
            return 2;
        }
    },
    EUR {
        @Override
        public int getValue() {
            return 1;
        }
    },
    GBP {
        @Override
        public int getValue() {
            return 3;
        }
    };

   public int getValue(){
        return 4;
    }


    public static CurrencyCode getEnum(String value) {

        return switch (value) {
            case "pln", "PLN" -> PLN;
            case "usd", "USD" -> USD;
            case "eur", "EUR" -> EUR;
            case "gbp", "GBP" -> GBP;
            default -> throw new RuntimeException("wrong currency code value, we don't support this" +
                    "currency");
        };
    }

}
