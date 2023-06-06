package com.example.CurrencyProject.scraper.metal.enums;

public enum WeightCode {
    gr {
        @Override
        public String getWeight() {
            return "gram";
        }
    } ,oz {
        @Override
        public String getWeight() {
            return "ounce";
        }
    },kg {
        @Override
        public String getWeight() {
            return "kilogram";
        }
    }

    ;
    public String getWeight() {
        return "gram";
    }
}
