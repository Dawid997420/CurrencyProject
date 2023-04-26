package com.example.CurrencyProject.service;

import com.example.CurrencyProject.exception.CurrencyNotFoundException;
import com.example.CurrencyProject.mapper.CurrencyMapper;
import com.example.CurrencyProject.model.AB.Currency;
import com.example.CurrencyProject.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.* ;

class CurrencyServiceTest {

    CurrencyMapper currencyMapper = mock(CurrencyMapper.class);

    CurrencyService currencyService ;

    CurrencyFactory currencyFactory = new CurrencyFactory();

    @BeforeEach
    public void init() {
        // given




        // when


        // then
//        when(currencyService.getCurrencies()).thenReturn();

        currencyService = new CurrencyService(currencyMapper);
    }

    @Test
    void getCurrencies() {


        // given
        List<List<Currency>> tableA = currencyFactory.getListOfListCurrenciesTable(Group.A);
        List<List<Currency>> tableB = currencyFactory.getListOfListCurrenciesTable(Group.B);

        when(currencyMapper.getCurrenciesTableMostActualDays(Group.A,2)).thenReturn(Mono.just( tableA ));
        when(currencyMapper.getCurrenciesTableMostActualDays(Group.B,2)).thenReturn(Mono.just( tableB ));

        // when

        List<Currency> currencies = currencyService.getCurrencies().block();


        // then
        assertThat(currencies.get(0).getChange(),equalTo(-6.0E-4));


    }


    @Test
    void getCurrencyForDaysShouldReturnCorrectValue() {

        // given
        List<Currency> currenciesBetween = currencyFactory.createCurrencyBetweenFor19("USD","dolar amerykanski"
                ,Group.A );

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        LocalDate today = LocalDate.now(polishZone);
        LocalDate daysLater = today.minusDays(99);

        when(currencyMapper.getCurrencyBetween(Group.A,"USD",
                daysLater, today ))
                .thenReturn(Mono.just(currenciesBetween));

        // when
        List<Currency> resultCurrencies = currencyService.getCurrencyForDays(Group.A,"USD",99).block();


        // then
        assertThat(resultCurrencies.size(),equalTo(19));
    }

    @Test
    public void getCurrencyForDaysShouldThrowIllegalArgumentException() {

        // given

        // when

        // then
        assertThrows(IllegalArgumentException.class,
                () -> currencyService.getCurrencyForDays(Group.A,"USD",366));
    }

    @Test
    public void getCurrencyForDaysShouldCurrencyNotFoundExceptionWhenBetweenNull() {

        // given
        ZoneId polishZone = ZoneId.of("Europe/Warsaw");

        LocalDate today = LocalDate.now(polishZone);
        LocalDate daysLater = today.minusDays(356);
        when(currencyMapper.getCurrencyBetween(Group.A,"USX",daysLater,today))
                .thenReturn(null);
        // when

        // then
        assertThrows(CurrencyNotFoundException.class,
                () -> currencyService.getCurrencyForDays(Group.A,"USD",356));

    }


    @Test
    public void getCurrencyMostActualDaysLimitedShouldThrowCurrencyNotFoundException() {

        // given
        ZoneId polishZone = ZoneId.of("Europe/Warsaw");

        LocalDate today = LocalDate.now(polishZone);
        LocalDate daysLater = today.minusDays(20);



        when(currencyMapper.getCurrencyMostActualDays(Group.A,"USX",20))
                .thenReturn(null);
        // when

        // then
        assertThrows(CurrencyNotFoundException.class,
                () -> currencyService.getCurrencyForDays(Group.A,"USX",20));

    }





    @Test
    void getCurrencyActualShouldThrowException() {

        // given
        when(currencyMapper.getCurrencyMostActualDays(Group.B,"USX",2)).thenReturn(null);

        // when

        // then
        assertThrows( CurrencyNotFoundException.class
                ,() -> currencyService.getCurrencyActual(Group.B,"USX"));
    }


    @Test
    void getCurrencyActual() {

        // given
        List<Currency> currenciesBetween = currencyFactory.createCurrencyBetweenFor2("USD",
                "dolar Amerykanski", Group.A);

        Currency currency = currenciesBetween.get(1);
        currency.setChange(0.1913);
        currency.setPercentChange(4.43);

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        LocalDate today = LocalDate.now(polishZone);
        LocalDate daysLater = today.minusDays(2);

//        when(currencyMapper.getActualCurrency("USD",Group.A))
//                .thenReturn((currenciesBetween.get(0)));


        when(currencyMapper.getCurrencyMostActualDays(Group.A,"USD",2))
                .thenReturn(currenciesBetween);


        // when
        Currency result = currencyService.getCurrencyActual(Group.A,"USD");


        // then
        assertThat(result , equalTo(currency));
        assertNotEquals(result.getChange(),0.0);


    }

    @Test
    void getCurrencyForYears() {

        // given
        List<Currency> currencyList = currencyFactory.
                createCurrencyBetweenFor19("USD","dolar amerykanski",Group.A);

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        LocalDate endDay = LocalDate.now(polishZone);
        LocalDate startDay = endDay.minusDays(365);

        when(currencyMapper.getCurrencyBetween(Group.A,"USD",startDay,endDay))
                .thenReturn(Mono.just(currencyList));

        LocalDate endDay2 = LocalDate.now(polishZone).minusDays(365);
        LocalDate startDay2 = endDay2.minusDays(365);
        when(currencyMapper.getCurrencyBetween(Group.A,"USD",startDay2,endDay2))
                .thenReturn(Mono.just(currencyList));

        // when
        List<Currency> currencyForTwoYears = currencyService.getCurrencyForYears
                (Group.A,"USD",2);


        // then
        assertThat(currencyForTwoYears.size(), equalTo(38));


    }


    @Test
    void getCurrencyForYearsShouldThrowIllegalArgumentException() {

        // given

        // when

        // then
        assertThrows(IllegalArgumentException.class,() -> currencyService.getCurrencyForYears
                (Group.A,"USD",22));

    }


    @Test
    void getCurrencyMaximum() {

        // given

        List<Currency> currencyList = currencyFactory.createCurrencyBetweenFor19("USD",
                "dolar amerykanski",Group.A);

        currencyList.addAll(currencyFactory.createCurrencyBetweenFor2
                ("USD","dolar amerykanski",Group.A));

        LocalDate startDay = LocalDate.of(2024,4,24);
        LocalDate endDay = LocalDate.of(2023,4,24);

        when(currencyMapper.getCurrencyBetween(Group.A, "USD",startDay, endDay))
                .thenReturn(Mono.just(currencyList));

        when(currencyService.getCurrencyForYears(Group.A,"USD",12))
                .thenReturn(currencyList);
        // when

        List<Currency> result = currencyService.getCurrencyMaximum(Group.A,"USD");

        // then
        assertThat(result,equalTo(currencyList));

    }


}