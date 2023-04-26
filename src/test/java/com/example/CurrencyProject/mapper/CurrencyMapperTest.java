package com.example.CurrencyProject.mapper;

import com.example.CurrencyProject.dto.AB.Currency_Dto;
import com.example.CurrencyProject.dto.AB.TableDto;
import com.example.CurrencyProject.externalApi.NBP.CurrencyApi;
import com.example.CurrencyProject.model.AB.Currency;
import com.example.CurrencyProject.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;

class CurrencyMapperTest {


    CurrencyMapper currencyMapper;

    CurrencyApi currencyApi = mock(CurrencyApi.class);

    CurrencyDtoFactory currencyDtoFactory = new CurrencyDtoFactory();

    @BeforeEach
    public void init() {

        //given




        //when


        // then

        currencyMapper = new CurrencyMapper(currencyApi);

    }




    @Test
    void getActualCurrencyByCodeShouldGiveCorrectObject() {

        // given
        Currency_Dto currencyDtoA = currencyDtoFactory.createCurrency("A","test worked"
                ,"USD", currencyDtoFactory.createListRateAB());

        when(currencyApi.getActualCurrency("USD",Group.A))
                .thenReturn(currencyDtoA);


        // when
        Currency currencyResult = currencyMapper
                .getActualCurrency("USD",Group.A);


        // then
        assertThat(currencyResult.getCurrency(),equalTo(currencyDtoA.getCurrency()));


    }

    @Test
    void getAllActualCurrenciesTableA() {

        // given
        List<TableDto> tableA_Dto = currencyDtoFactory.createListTableActual("A","073/A/NBP/2023"
                ,LocalDate.of(2023,4,12));

        when(currencyApi.getActualCurrenciesTable(Group.A)).thenReturn(tableA_Dto);

        // when
        List<Currency> tableA = currencyMapper.getActualCurrenciesTable(Group.A);

        // then
        assertThat(tableA.size(),equalTo(19));
        assertThat(tableA.get(4).getGroup() , equalTo(Group.A) );
    }

    @Test
    void getAllActualCurrenciesTableB() {

        // given
        List<TableDto> tableB_Dto = currencyDtoFactory.createListTableActual("B","073/A/NBP/2023"
                ,LocalDate.of(2023,4,12));

        when(currencyApi.getActualCurrenciesTable(Group.B)).thenReturn(tableB_Dto);

        // when
        List<Currency> tableB = currencyMapper.getActualCurrenciesTable(Group.B);

        // then
        assertThat(tableB.size(),equalTo(19));
        assertThat(tableB.get(4).getGroup() , equalTo(Group.B));

    }




    @Test
    void getCurrenciesTableMostActualDays() {
        // given
        Mono<List<TableDto>> tableTwoDays = Mono.just(currencyDtoFactory.createListTableDtoTwoDays
                ("A","073/A/NBP/2023", LocalDate.of(2023,4,12)));

        when(currencyApi.getCurrenciesTableMostActualDays(Group.A,2)).thenReturn(tableTwoDays);

        // when
        List<List<Currency>> listForEachDay = currencyMapper.getCurrenciesTableMostActualDays(Group.A,2).block();

        // then
        assertThat(listForEachDay.size(),equalTo(2));
        assertThat(listForEachDay.get( 1)
                .get(0).getGroup(),equalTo(Group.A));

    }


    @Test
    void getCurrencyBetween() {

        // given
        Currency_Dto currencyDto = currencyDtoFactory.createCurrencyBetween("A","USA","USD");

       when(currencyApi.getCurrencyBetween(Group.A,"USD",LocalDate.of(2022,2,1),
                LocalDate.of(2022,2,19))).thenReturn(Mono.just(currencyDto));

        // when
        List<Currency> currencyBetween = currencyMapper.getCurrencyBetween(Group.A,"USD",LocalDate.of(2022,2,1)
        , LocalDate.of(2022,2,19)).block();

        // then
        assertThat(currencyBetween.size(), equalTo(19));

    }


    @Test
    void getCurrencyMostActualDays() {

        // given
        Currency_Dto currencyDto = currencyDtoFactory
                .createCurrencyBetween("A","USA","USD");

        when(currencyApi.getCurrencyMostActualDays(Group.A,"USA",19))
                .thenReturn(currencyDto);
        // when

       List<Currency> currencyResult = currencyMapper
               .getCurrencyMostActualDays(Group.A,"USA",19);

        // then
        assertThat(currencyResult.size() , equalTo(19));

    }


}