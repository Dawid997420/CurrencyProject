package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.exception.CurrencyNotFoundException;
import com.example.CurrencyProject.model.currency.Currency;
import com.example.CurrencyProject.mapper.CurrencyMapper;
import com.example.CurrencyProject.model.currency.Group;
import com.example.CurrencyProject.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest(CurrencyController.class)
@WithMockUser
public class CurrencyControllerTest {


    private final String url = "http://localhost:7777/currency";

    @MockBean
    private CurrencyMapper currencyMapper;

    @MockBean
    private CurrencyService currencyService;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyController currencyController;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init(){

        // given
        objectMapper.registerModule(new JavaTimeModule());

        Currency currencyExpected = Currency.builder()
                .effectiveDate(LocalDate.of(2002,12,1))
                .code("USD").currency("dolar amerykański").midPrice(121.12)
                .build();



        // when


        // then
    }

    @Test
    void currencyList() throws Exception {

        // given

        List<Currency> currencyList = Arrays.asList( Currency.builder().effectiveDate(
                LocalDate.of(2023,2,1)).code("USD").currency("USA").build());

        when(currencyService.getCurrencies()).thenReturn(Mono.just(currencyList));
        // when


        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    void getCurrencyForDays() throws Exception {

        when(currencyService.getCurrencyForDays(Group.A,"USD",364))
                .thenReturn(Mono.just(Arrays.asList(Currency.builder().build()) ));

        // then

        mockMvc.perform(MockMvcRequestBuilders.get("/currency/days/A/USD/364"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCurrencyForDaysShouldGetStatus400() throws Exception {

        when(currencyService.getCurrencyForDays(Group.A,"USD",384))
                .thenThrow(new IllegalArgumentException("Wrong numbers of days"));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/days/A/USD/384"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest() );
    }






    @Test
    void getCurrencyForDaysShouldGetStatus500() throws Exception {

        when(currencyService.getCurrencyForDays(Group.A,"USD",Integer.parseInt("0001")))
                .thenThrow(new RuntimeException("Internal Server Error"));

        // then

        mockMvc.perform(MockMvcRequestBuilders.get("/currency/days/A/USD/0001"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError() );
    }

    @Test
    void getCurrencyActualShouldBeOk() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/A/USD"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCurrencyActualShouldThrowBadRequestException() throws Exception {

        // given

        when(currencyService.getCurrencyActual(Group.A,"USD"))
                .thenThrow(CurrencyNotFoundException.class);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/A/USD"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void getCurrencyActualShouldThrowBadRequestException2() throws Exception {

        // given

        when(currencyService.getCurrencyActual(Group.A,"USD"))
                .thenThrow(new RuntimeException("Run time exception"));
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/A/USD"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    }


    @Test
    void getCurrencyForYears() throws Exception {


        // given
        List<Currency> currencyList = Arrays.asList(Currency.builder().build(),
                Currency.builder().build(),Currency.builder().build());

        when(currencyService.getCurrencyForYears(Group.A,"USD",3))
                .thenReturn(currencyList);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/years/A/USD/3"))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }


    @Test
    void getCurrencyForYearsShouldThrowIllegalArgExc() throws Exception {

        // given
        List<Currency> currencyList = Arrays.asList(Currency.builder().build(),
                Currency.builder().build(),Currency.builder().build());

        when(currencyService.getCurrencyForYears(Group.A,"USD",35))
                .thenThrow(IllegalArgumentException.class);

        // when
        List<Currency> result = (List<Currency>) currencyController.getCurrencyForYears
                ("A","USD",35).getBody();

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/years/A/USD/35"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void getCurrencyForYearsShouldThrowInternalServerError() throws Exception {

        // given
        List<Currency> currencyList = Arrays.asList(Currency.builder().build(),
                Currency.builder().build(),Currency.builder().build());

        when(currencyService.getCurrencyForYears(Group.A,"USD",12))
                .thenThrow(new RuntimeException("Internal server error"));

        // when
        List<Currency> result   = (List<Currency>) currencyController.getCurrencyForYears
                ("A","USD",35).getBody();

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/years/A/USD/12"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    }




}
