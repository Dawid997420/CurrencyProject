package com.example.CurrencyProject.mapper;

import com.example.CurrencyProject.dto.AB.Currency_Dto;
import com.example.CurrencyProject.dto.AB.TableDto;
import com.example.CurrencyProject.externalApi.NBP.CurrencyApi;
import com.example.CurrencyProject.model.AB.Currency;
import com.example.CurrencyProject.model.Group;
import com.example.CurrencyProject.utils.CountryCurrencyMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@Component
public class CurrencyMapper {

    private final CurrencyApi currencyApi;



    public CurrencyMapper(CurrencyApi currencyApi) {
        this.currencyApi = currencyApi;
    }


    public Currency getActualCurrency(String currencyCode, Group group) {

        Currency_Dto currencyDto = currencyApi.getActualCurrency
                (currencyCode, group);

        return buildCurrency(currencyDto);

    }


    public Mono<List<Currency>> getCurrencyBetween(Group group, String currencyCode ,
                                             LocalDate startDate, LocalDate endDate) {

        return currencyApi.getCurrencyBetween(group,currencyCode,startDate,endDate)
                .map(this::buildCurrencyList);
    }


    public List<Currency> getActualCurrenciesTable(Group group) {

        List<TableDto> currenciesTableADto = currencyApi.getActualCurrenciesTable(group);


      return buildListCurrencies(currenciesTableADto.get(0));
    }



    public List<Currency> getCurrencyMostActualDays(Group group, String code , int days) {

    return buildCurrencyList( currencyApi.getCurrencyMostActualDays(group,code,days));

    }

    public Mono<List<List<Currency>>> getCurrenciesTableMostActualDays(Group group, int days) {

        Mono<List<TableDto>> tableForEachDay = currencyApi.getCurrenciesTableMostActualDays(group,days);

        return tableForEachDay.map(this::buildListForEachDay);

    }

    private List<List<Currency>> buildListForEachDay( List<TableDto> tableDtos ) {

     return  tableDtos.stream().map(this::buildListCurrencies).toList();
    }





    private Currency buildCurrency(Currency_Dto currencyABDto) {

        return Currency.builder()
                .effectiveDate(currencyABDto.getRates().get(0).getEffectiveDate())
                .code(currencyABDto.getCode())
                .country(CountryCurrencyMapper.getCountryName(currencyABDto.getCode()))
                .currency(currencyABDto.getCurrency())
                .midPrice(currencyABDto.getRates().get(0).getMid())
                .group(setGroup(currencyABDto.getTable()))
                .build();
    }


    private List<Currency> buildCurrencyList(Currency_Dto currencyABDto) {

        return currencyABDto.getRates().stream().map( rate -> {
          return   Currency.builder().effectiveDate(rate.getEffectiveDate())
                    .code(currencyABDto.getCode()).currency(currencyABDto.getCurrency())
                    .country(CountryCurrencyMapper.getCountryName(currencyABDto.getCode()))
                    .group(Group.valueOf(currencyABDto.getTable()))
                    .midPrice(rate.getMid()).build();

        }).toList();
    }



    private List<Currency> buildListCurrencies(TableDto table_Dto) {


        return table_Dto.getRates().stream().map(rate -> Currency.builder()
                .effectiveDate(table_Dto.getEffectiveDate())
                .country(CountryCurrencyMapper.getCountryName(rate.getCode()))
                 .code(rate.getCode()).currency(rate.getCurrency())
                .group(setGroup(table_Dto.getTable()))
                .midPrice(rate.getMid()).build()).toList();
    }



    private Group setGroup(String table) {

        if( table.equals("A") || table.equals("a") ) {
            return Group.A;
        } else if ( table.equals("B") || table.equals("b") ) {
            return Group.B;
        }
        throw new IllegalArgumentException("Group no exist");
    }




}
