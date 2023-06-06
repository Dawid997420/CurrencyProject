package com.example.CurrencyProject.service;

import com.example.CurrencyProject.model.currency.Currency;
import com.example.CurrencyProject.model.currency.Group;
import com.example.CurrencyProject.utils.CountryCurrencyMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class CurrencyFactory {




    public Currency createCurrency(LocalDate date ,String code, String currency, String country ,
                                   Group group, double midPrice, double change, double percentChange){

        return Currency.builder().effectiveDate(date).code(code).currency(currency).country(country)
                .group(group).midPrice(midPrice).change(change).percentChange(percentChange).build();
    }


    public List<Currency> createCurrencyBetweenFor2(String code, String currency, Group group) {

        List<Currency> currencyList = new ArrayList<>();

        List<LocalDate> dates = Arrays.asList(LocalDate.of(2022,2,1),
                LocalDate.of(2022,2,2));

        double[] mids = {4.1255,4.3168};


        for ( int i = 0 ; i < 2 ; i++) {

            currencyList.add(Currency.builder().effectiveDate(dates.get(i)).code(code)
                    .currency(currency).country(CountryCurrencyMapper.getCountryName(code))
                    .group(group).midPrice(mids[i]).build());
        }

        return currencyList;
    }



    public List<Currency> createCurrencyBetweenFor19(String code, String currency, Group group) {

        List<Currency> currencyList = new ArrayList<>();

        List<LocalDate> dates = Arrays.asList(LocalDate.of(2022,2,1),
                LocalDate.of(2022,2,2),LocalDate.of(2022,2,3),
                LocalDate.of(2022,2,4),LocalDate.of(2022,2,5)
                ,LocalDate.of(2022,2,6),LocalDate.of(2022,2,7),
                LocalDate.of(2022,2,8),LocalDate.of(2022,2,9),
                LocalDate.of(2022,2,10),LocalDate.of(2022,2,11),
                LocalDate.of(2022,2,12),LocalDate.of(2022,2,13),
                LocalDate.of(2022,2,14),LocalDate.of(2022,2,15),
                LocalDate.of(2022,2,16),LocalDate.of(2022,2,17),
                LocalDate.of(2022,2,18), LocalDate.of(2022,2,19),
                LocalDate.of(2022,2,20));

        double[] mids = {4.1255,4.3168,2.8918,4.5499, 4.1998, 4.6917, 4.2407, 4.6805, 4.012308, 4.7033,
                4.3236,0.1258, 4.032345, 4.1994, 4.6283, 4.031434, 4.4142, 4.4156, 4.9472};


        for ( int i = 0 ; i < 19 ; i++) {

           currencyList.add(Currency.builder().effectiveDate(dates.get(i)).code(code)
                    .currency(currency).country(CountryCurrencyMapper.getCountryName(code))
                    .group(group).midPrice(mids[i]).build());
        }

        return currencyList;
    }



    public List<List<Currency>> getListOfListCurrenciesTable(Group group) {

        List<List<Currency>> list =new ArrayList<>();

        if ( group.equals(Group.A)) {

            List<Currency> currenciesActualA = createListCurrencyActualA();
            List<Currency> currenciesBeforeA = createListCurrencyBeforeA();

            list.add(currenciesActualA);
            list.add(currenciesBeforeA);

            return list;
        } else {

            List<Currency> currenciesActualB = createListCurrencyActualB();
            List<Currency> currenciesBeforeB = createListCurrencyBeforeB();

            list.add(currenciesActualB);
            list.add(currenciesBeforeB);

            return list;
        }
    }




    public List<Currency> createListCurrencyActualA() {

        // TABLE-A

        List<Currency> tableA = new ArrayList<>();



        LocalDate date = LocalDate.of(2023,3,12);

        String[] codes = {"THB","USD","AUD","HKD","CAD","NZD","SGD","EUR","HUF","CHF"
                ,"GBP","UAH","JPY","CZK","DKK","ISK","NOK","SEK","RON"};

        String[] currencies = {"bat (Tajlandia)","dolar amerykański","dolar australijski",
                "dolar Hongkongu","dolar kanadyjski","dolar nowozelandzki","dolar singapurski"
                ,"euro","forint (Węgry)","frank szwajcarski","funt szterling"
                ,"hrywna (Ukraina)","jen (Japonia)","korona czeska","korona duńska","korona islandzka",
                "korona norweska","korona szwedzka","lej rumuński"};

        double[] mids = {0.1255,4.3168,2.8918,0.5499, 3.1998, 2.6917, 3.2407, 4.6805, 0.012308, 4.7033,
                5.3236,0.1258, 0.032345, 0.1994, 0.6283, 0.031434, 0.4142, 0.4156, 0.9472};


        for ( int i = 0 ; i < 19 ; i++) {
            tableA.add(Currency.builder().effectiveDate(date)
                    .code(codes[i]).currency(currencies[i])
                    .country(CountryCurrencyMapper.getCountryName(codes[i]))
                    .group(Group.A).midPrice(mids[i]).build());
        }
        return tableA;
    }


    public List<Currency> createListCurrencyBeforeA() {

        // TABLE-A

        List<Currency> tableA = new ArrayList<>();



        LocalDate date = LocalDate.of(2023,3,11);

        String[] codes = {"THB", "USD", "AUD", "HKD", "CAD", "NZD", "SGD", "EUR", "HUF", "CHF"
                , "GBP", "UAH", "JPY", "CZK", "DKK", "ISK", "NOK", "SEK", "RON"};

        String[] currencies = {"bat (Tajlandia)", "dolar amerykański", "dolar australijski",
                "dolar Hongkongu", "dolar kanadyjski", "dolar nowozelandzki", "dolar singapurski"
                , "euro", "forint (Węgry)", "frank szwajcarski", "funt szterling"
                , "hrywna (Ukraina)", "jen (Japonia)", "korona czeska", "korona duńska", "korona islandzka",
                "korona norweska", "korona szwedzka", "lej rumuński"};


        double[] mids = {0.1261, 4.2739, 2.8628, 0.5444, 3.1727, 2.6991, 3.2248, 4.6803, 0.012411, 4.7240,
                5.3379, 0.1158, 0.032522, 0.1997, 0.6282, 0.031348,0.4112, 0.4140, 0.9489};


        for ( int i = 0 ; i < 19 ; i++) {
            tableA.add(Currency.builder().effectiveDate(date)
                    .code(codes[i]).currency(currencies[i])
                    .country(CountryCurrencyMapper.getCountryName(codes[i]))
                    .group(Group.A).midPrice(mids[i]).build());
        }
        return tableA;
    }



    public List<Currency> createListCurrencyActualB() {

        // TABLE-B
        List<Currency> tableB = new ArrayList<>();

        LocalDate date = LocalDate.of(2023,3,12);

        String[] codes = {"AFN","MGA","PAB","ETB","VES","BOB","CRC","SVC","NIO","GMD"
                ,"MKD","DZD","BHD","IQD","JOD","KWD","LYD","RSD","TND"};

        String[] currencies = {"afgani (Afganistan)","ariary (Madagaskar)","balboa (Panama)",
                "birr etiopski","boliwar soberano (Wenezuela)","boliwiano (Boliwia)","colon kostarykański"
                ,"colon salwadorski","cordoba oro (Nikaragua)","dalasi (Gambia)","denar (Macedonia Północna)"
                ,"dinar algierski","dinar bahrajski","dinar iracki","dinar jordański","dinar kuwejcki",
                "dinar libijski", "dinar serbski","dinar tunezyjski"};

        double[] mids = {0.049719,0.000981,4.2739,0.0785, 0.1751, 0.6163, 0.007947, 0.4882, 0.1169,
                0.0670, 0.075991,0.031513,11.3366, 0.003258, 6.0212, 13.9315, 0.8962, 0.0399, 1.4061};

        for ( int i = 0 ; i < 19 ; i++) {
            tableB.add(Currency.builder().effectiveDate(date)
                    .code(codes[i]).currency(currencies[i])
                    .country(CountryCurrencyMapper.getCountryName(codes[i]))
                    .group(Group.A).midPrice(mids[i]).build());
        }
        return tableB;
    }


    public List<Currency> createListCurrencyBeforeB() {

        // TABLE-B
        List<Currency> tableB = new ArrayList<>();

        LocalDate date = LocalDate.of(2023,3,11);

        String[] codes = {"AFN","MGA","PAB","ETB","VES","BOB","CRC","SVC","NIO","GMD"
                ,"MKD","DZD","BHD","IQD","JOD","KWD","LYD","RSD","TND"};

        String[] currencies = {"afgani (Afganistan)","ariary (Madagaskar)","balboa (Panama)",
                "birr etiopski","boliwar soberano (Wenezuela)","boliwiano (Boliwia)","colon kostarykański"
                ,"colon salwadorski","cordoba oro (Nikaragua)","dalasi (Gambia)","denar (Macedonia Północna)"
                ,"dinar algierski","dinar bahrajski","dinar iracki","dinar jordański","dinar kuwejcki",
                "dinar libijski", "dinar serbski","dinar tunezyjski"};

        double[] mids = {0.049522,0.000962 ,4.2244 ,0.0775 , 0.1721,0.6087 ,  0.0079, 0.4824, 0.1154,
                0.0670, 0.075072,0.031149,11.1972 , 0.003201, 5.9506 , 13.7691, 0.8855, 0.0395, 1.3551};


        for ( int i = 0 ; i < 19 ; i++) {
            tableB.add(Currency.builder().effectiveDate(date)
                    .code(codes[i]).currency(currencies[i])
                    .country(CountryCurrencyMapper.getCountryName(codes[i]))
                    .group(Group.A).midPrice(mids[i]).build());
        }
        return tableB;
    }



}
