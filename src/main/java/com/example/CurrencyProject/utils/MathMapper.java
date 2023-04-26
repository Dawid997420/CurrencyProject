package com.example.CurrencyProject.utils;

public class MathMapper {


    public double roundToSixDecimalPlace(double number) {


        return Math.round(number * 100000000.0) / 100000000.0;
    }


    public double roundToTwoDecimalPlace(double number) {

        return Math.round(number * 100.0) / 100.0;
    }


}
