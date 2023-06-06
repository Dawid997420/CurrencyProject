package com.example.CurrencyProject.scraper.metal.enums;

public enum Period {

    Day {

        @Override
        public String getTime() {
            return "1day";
        }

    },
    Month{
        @Override
        public String getTime() {
            return "1month";
        }
    },
    Year {
        @Override
        public String getTime() {
            return "1year";
        }
    },
    Max {
        @Override
        public String getTime() {
            return super.getTime();
        }
    };

    public String getTime() {
    return "";
    }

    public static Period getPeriodFromDays(String days) {

        if (days.equals("1")) {
            return Period.Day;
        } else if ( days.equals("30")) {
            return Period.Month;
        } else if ( days.equals("365")) {
            return Period.Year;
        } else if ( days.equals("max")) {
            return Period.Max;
        } else {
            throw new RuntimeException("Allowed values From this Material [ 1day, 30day, 1year, max ]");
        }

    }
}
