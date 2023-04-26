package com.example.CurrencyProject.service;

import com.example.CurrencyProject.exception.CurrencyNotFoundException;
import com.example.CurrencyProject.mapper.CurrencyMapper;
import com.example.CurrencyProject.model.AB.Currency;
import com.example.CurrencyProject.model.Group;
import com.example.CurrencyProject.utils.MathMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class CurrencyService {


    private final CurrencyMapper currencyMapper;

    private final MathMapper mathMapper = new MathMapper();

    public CurrencyService(CurrencyMapper currencyMapper) {
        this.currencyMapper = currencyMapper;
    }


    public Currency getCurrencyActual(Group group ,String code) {

        List<Currency> currencyForTwoDays= currencyMapper.getCurrencyMostActualDays(group,code,2);

        if ( currencyForTwoDays == null ) {

            throw new CurrencyNotFoundException("Currency couldn't be found !! Maybe wrong group or currency code ");
        }

        return createOneCurrency(currencyForTwoDays);
    }

    public Mono<List<Currency>> getCurrencyForDays(Group group, String code , int numberDays) {

        if ( numberDays > 365 ) {

            throw new IllegalArgumentException("Maximum number of days is 365!");
        }

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");

        LocalDate today = LocalDate.now(polishZone);
        LocalDate daysLater = today.minusDays(numberDays);

        Mono<List<Currency>> currencyBetween = currencyMapper.getCurrencyBetween
                (group,code,daysLater,today);

        if ( currencyBetween == null ) {

            throw new CurrencyNotFoundException(" Currency couldn't be found !! Maybe wrong group or currency code ");
        }

        return currencyBetween;
    }

    public List<Currency> getCurrencyMaximum( Group group, String code ) {

        int years = 0 ;

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        LocalDate today = LocalDate.now(polishZone);

        LocalDate dateMaximumStart = LocalDate.of(2002,1,1);
        LocalDate dateMaximumEnd = dateMaximumStart.plusDays(365);

        List<Currency> maximumCurrency = new ArrayList<>();
        for ( int i =21 ; i > 0 ; i--) {

            try {
                maximumCurrency = getCurrencyForYears(group, code, i);

                if ( maximumCurrency.size() > 1) {

                    break;

                }
            } catch (Exception e) {

            }

        }

        return maximumCurrency;
    }


    public List<Currency> getCurrencyForYears(Group group, String code, int numberYears) {

        if ( !areNumberOfYearsAllowed(numberYears) ) {

            throw new IllegalArgumentException("Maximum date is 2002 01 01 " + numberYears
                    +  " to much number of years");
        }

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");

        LocalDate endDay = LocalDate.now(polishZone).minusYears(numberYears-1);

        LocalDate startDay = endDay.minusDays(365);

        List<Mono<List<Currency>>> currencyList = new ArrayList<>();

        for (int i = 0; i < numberYears; i++) {

            Mono<List<Currency>> result = currencyMapper.getCurrencyBetween(group, code, startDay, endDay);
            currencyList.add(result);
            startDay = endDay;
            endDay = startDay.plusYears(1);
        }


        List<List<Currency>> allYears = Mono.just(currencyList)
                .flatMapMany(Flux::fromIterable)
                .concatMap(mono -> mono)
                .collectList()
                .block();

        return createOneList(allYears);
    }





    public Mono<List<Currency>> getCurrencies() {

        Mono<List<Currency>> currencyListBMono = currencyMapper.getCurrenciesTableMostActualDays(Group.B, 2)
                .map(this::createTableCurrencies);
        Mono<List<Currency>> currencyListAMono = currencyMapper.getCurrenciesTableMostActualDays(Group.A, 2)
                .map(this::createTableCurrencies);

        return Mono.zip(currencyListBMono, currencyListAMono, (listB, listA) -> {
            List<Currency> fullCurrencyList = new ArrayList<>();
            fullCurrencyList.addAll(listA);
            fullCurrencyList.addAll(listB);
            return fullCurrencyList;
        });
    }

    private List<Currency> getCurrencyMostActualDaysLimited(Group group, String code, int numberDays) {

        List<Currency> currencyForDays = currencyMapper.getCurrencyMostActualDays(group,code,numberDays);

        if ( currencyForDays == null) {
            throw new CurrencyNotFoundException("Currency Not found, maybe group or code is wrong");
        }
        return currencyForDays;

    }



    private boolean areNumberOfYearsAllowed(int numberOfYears ) {


        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        LocalDate today = LocalDate.now(polishZone);
        LocalDate maximumAllowedDate = LocalDate.of(2002,1,2);

        if ( today.minusYears(numberOfYears).isBefore(maximumAllowedDate)) {

            return false;
        }
        return true;
    }


    private List<Currency> createOneList(List<List<Currency>> allYears) {

        List<Currency> oneListOfAllYears = new ArrayList<>();
         allYears.forEach(oneListOfAllYears::addAll);

        oneListOfAllYears.sort(Comparator.comparing(Currency::getEffectiveDate));

        return oneListOfAllYears;

    }

    private Currency createOneCurrency( List<Currency> twoDaysCurrency) {

        sortCurrencyArrayByDate(twoDaysCurrency);

        Currency dayBefore = twoDaysCurrency.get(0);
        Currency actualDay = twoDaysCurrency.get(1);

        return buildCurrency(dayBefore,actualDay);
    }


    private List<Currency> createTableCurrencies(List<List<Currency>> tableForTwoDays) {

        tableForTwoDays =sortTableByDate(tableForTwoDays);

        List<Currency> tableActual = tableForTwoDays.get(1);
        List<Currency> tableBefore = tableForTwoDays.get(0);

        return buildTable(tableBefore,tableActual);
    }

    private List<Currency> sortCurrencyArrayByDate(List<Currency> currencyList) {

        List<Currency> sortedList = new ArrayList<>(currencyList); // tworzenie kopii listy
        sortedList.sort(Comparator.comparing(Currency::getEffectiveDate));
        return sortedList;
    }


    private List<List<Currency>> sortTableByDate(List<List<Currency>> currenciesList) {

        List<List<Currency>> sortedTable = new ArrayList<>(currenciesList);
        sortedTable.sort(Comparator.comparing(day-> day.get(0).getEffectiveDate()));

        return sortedTable;
    }

    private List<Currency> buildTable( List<Currency> tableBefore , List<Currency> tableActual ) {

        return tableActual.stream().map(currencyActual -> {

            String code = currencyActual.getCode();
            Currency currencyBefore = findCurrencyByCode(code,tableBefore);

            return buildCurrency(currencyBefore,currencyActual);
        }).toList();

    }

    private Currency buildCurrency(Currency dayBefore , Currency actualDay) {

        double change = calculateChange(dayBefore , actualDay);
        double percentChange = calculatePercent(actualDay,change);

        return Currency.builder().effectiveDate(actualDay.getEffectiveDate()).code(actualDay.getCode())
                .currency(actualDay.getCurrency()).country(actualDay.getCountry())
                .group(actualDay.getGroup()).midPrice(actualDay.getMidPrice())
                .change(mathMapper.roundToSixDecimalPlace(change))
                .percentChange(mathMapper.roundToTwoDecimalPlace(percentChange)).build();
    }

    private Currency findCurrencyByCode(String code, List<Currency> table) {

        return table.stream().filter(currency -> currency.getCode().equals(code)).toList()
                .get(0);

    }


    private double calculateChange(Currency currencyBefore , Currency currencyNow) {

       return  currencyNow.getMidPrice() - currencyBefore.getMidPrice() ;
    }

    private double calculatePercent(Currency currencyActual, double change ) {

        return ( change / currencyActual.getMidPrice()) * 100;
    }






}
