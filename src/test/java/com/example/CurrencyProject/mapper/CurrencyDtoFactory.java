package com.example.CurrencyProject.mapper;

import com.example.CurrencyProject.dto.AB.Currency_Dto;
import com.example.CurrencyProject.dto.AB.Rate;
import com.example.CurrencyProject.dto.AB.TableDto;
import com.example.CurrencyProject.dto.AB.RateTable;
import com.example.CurrencyProject.dto.C.CurrencyC_Dto;
import com.example.CurrencyProject.dto.C.RateC;
import com.example.CurrencyProject.dto.C.TableC_Dto;
import com.example.CurrencyProject.dto.C.TableC_Rate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CurrencyDtoFactory {


    public List<TableDto> createListTableDtoTwoDays(String table, String no, LocalDate effectiveData){

        if ( table.equals("A") || table.equals("a")) {

            return Arrays.asList(TableDto.builder().table(table).no(no)
                    .effectiveDate(effectiveData).rates(createListRatesTableA()).build(),
                    TableDto.builder().table(table).no(no)
                            .effectiveDate(effectiveData.plusDays(1))
                            .rates(createListRatesOtherValuesTableA()).build());
        } else  {
            return Arrays.asList(TableDto.builder().table(table).no(no)
                            .effectiveDate(effectiveData).rates(createListRatesTableB()).build(),
                    TableDto.builder().table(table).no(no)
                            .effectiveDate(effectiveData.plusDays(1))
                            .rates(createListRatesOtherValuesTableB()).build());
        }

    }




    public Currency_Dto createCurrency(String table , String currency, String code, List<Rate> rates) {

     return Currency_Dto.builder().table(table).currency(currency).code(code)
                .rates(rates).build();
    }


    public Currency_Dto createCurrencyBetween(String table , String currency, String code) {

        return Currency_Dto.builder().table(table).currency(currency).code(code)
                .rates(createRatesForOneCurrency()).build();
    }

    public List<Rate> createRatesForOneCurrency() {

        List<Rate> rates = new ArrayList<>();

        double[] mids = {4.1255,4.3168,2.8918,4.5499, 4.1998, 4.6917, 4.2407, 4.6805, 4.012308, 4.7033,
                4.3236,0.1258, 4.032345, 4.1994, 4.6283, 4.031434, 4.4142, 4.4156, 4.9472};


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

        for ( int i = 0; i < 19 ; i++ ){
           rates.add(Rate.builder().effectiveDate(dates.get(i)).mid(mids[i]).build());
        }

        return rates;
    }




    public CurrencyC_Dto createCurrencyC(String currency, String code, List<RateC> rates){

        return CurrencyC_Dto.builder().table("C").currency(currency).code(code)
                .rates(rates).build();
    }

    public List<TableDto> createListTableActual(String table, String no, LocalDate effectiveData) {

        if ( table.equals("A") || table.equals("a")) {
            return Collections.singletonList(TableDto.builder().table(table).no(no)
                    .effectiveDate(effectiveData).rates(createListRatesTableA()).build());
        } else  {
         return Collections.singletonList(TableDto.builder().table(table).no(no)
                    .effectiveDate(effectiveData).rates(createListRatesTableB()).build());
        }

    }




    public List<TableC_Dto> createListTableActualC(String table, String no, LocalDate effectiveData) {


            return Collections.singletonList(TableC_Dto.builder().table(table).no(no)
                    .effectiveDate(effectiveData).rates(createListRatesTableC()).build());

    }



    public List<TableDto> createListTableBetweenDate(String table, String no, LocalDate startDate,
                                                     LocalDate endDate) {

        if ( table.equals("A") || table.equals("a")) {
            return Arrays.asList(TableDto.builder().table(table).no(no)
                    .effectiveDate(startDate).rates(createListRatesTableA()).build());
        } else  {
            return Arrays.asList(TableDto.builder().table(table).no(no)
                    .effectiveDate(startDate).rates(createListRatesTableB()).build());
        }

    }



    public List<TableC_Rate> createListRatesTableC() {

        // TABLE-A "2023-04-03"

        List<TableC_Rate> tableCRates = new ArrayList<>();

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

            TableC_Rate rate = TableC_Rate.builder().code(codes[i])
                    .currency(currencies[i])
                    .bid(mids[i] - 0.1)
                    .ask(mids[i] + 0.2).build();

            tableCRates.add(rate);
        }

        return tableCRates;
    }



    public List<RateTable> createListRatesTableA() {

        // TABLE-A "2023-04-03"

        List<RateTable> tableABRates = new ArrayList<>();

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

           RateTable rate = RateTable.builder().code(codes[i])
                    .currency(currencies[i]).mid(mids[i]).build();
           tableABRates.add(rate);
        }
        return tableABRates;

    }



    public List<RateTable> createListRatesOtherValuesTableA() {

        // TABLE-A "2023-04-05"

        List<RateTable> tableABRates = new ArrayList<>();

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

            RateTable rate = RateTable.builder().code(codes[i])
                    .currency(currencies[i]).mid(mids[i]).build();
            tableABRates.add(rate);
        }
        return tableABRates;
    }



    public List<RateTable> createListRatesTableB() {

        // TABLE-B "2023-04-03"

        List<RateTable> tableABRates = new ArrayList<>();

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

            RateTable rate = RateTable.builder().code(codes[i])
                    .currency(currencies[i]).mid(mids[i]).build();
            tableABRates.add(rate);
        }
        return tableABRates;
    }
 public List<RateTable> createListRatesOtherValuesTableB() {

        // TABLE-B "2023-04-19"

        List<RateTable> tableABRates = new ArrayList<>();

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

            RateTable rate = RateTable.builder().code(codes[i])
                    .currency(currencies[i]).mid(mids[i]).build();
            tableABRates.add(rate);
        }
        return tableABRates;
    }




    public List<Rate> createListRateAB() {

        List<Rate> rateList = new ArrayList<>();
            Rate rate = Rate.builder().mid(4.2244)
                    .effectiveDate(LocalDate.of(2023,4,19)).build();

            rateList.add(rate);
            return rateList;

    }

    public List<RateC> createListRateC() {

        List<RateC> ratesC = new ArrayList<>();
        RateC rateC1 = RateC.builder().bid(4.2244).ask(4.200)
                .effectiveDate(LocalDate.of(2023,4,19)).build();

        ratesC.add(rateC1);
        return ratesC;

    }

}
