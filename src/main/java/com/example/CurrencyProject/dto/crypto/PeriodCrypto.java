package com.example.CurrencyProject.dto.crypto;

public enum PeriodCrypto {

    day,week,month,year,all,
    ;

    public static PeriodCrypto getPeriod(String value) {

        switch (value) {
            case "1", "day", "one", "DAY", "ONE" -> {
                return day;
            }
            case "7", "week", "seven", "WEEK", "SEVEN" -> {
                return week;
            }
            case "30", "month", "MONTH" -> {
                return month;
            }
            case "365", "year", "rok", "YEAR" -> {
                return year;
            }
            case "max", "all", "MAX", "ALL" -> {
                return all;
            }
            default -> {
                throw new RuntimeException("u provided wrong value");
            }
        }

    }

}
