package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.dto.crypto.PeriodCrypto;
import com.example.CurrencyProject.mapper.CryptoMapper;
import com.example.CurrencyProject.mapper.CurrencyMapper;
import com.example.CurrencyProject.model.crypto.Crypto;
import com.example.CurrencyProject.scraper.metal.enums.CurrencyCode;
import com.example.CurrencyProject.service.CryptoFactory;
import com.example.CurrencyProject.service.CryptoService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(CryptoController.class)
@WithMockUser
class CryptoControllerTest {

    private final String url = "http://localhost:7777/crypto";

    @MockBean
    private CryptoMapper cryptoMapper;

    @MockBean
    private CryptoService cryptoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CryptoController cryptoController;

    ObjectMapper objectMapper = new ObjectMapper();

    CryptoFactory cryptoFactory = new CryptoFactory();

    @BeforeEach
    public void init() {

        // given
        objectMapper.registerModule(new JavaTimeModule());


    }

    @Test
    void getAllCrypto() throws Exception {

        // given
        List<Crypto> allCrypto = Arrays.asList(cryptoFactory.createCrypto("bitcoin","btc"),
                cryptoFactory.createCrypto("ethernet","eth"),
                cryptoFactory.createCrypto("dogecoin","doge"));

        when(cryptoService.getAllCryptos(CurrencyCode.PLN)).thenReturn(allCrypto);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/crypto/PLN"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void getCryptoPriceForDays() throws Exception {

        // given
       when(cryptoService.getCryptoPriceForDays("btc",CurrencyCode.PLN, PeriodCrypto.day))
               .thenReturn(cryptoFactory.createCryptoListFor30Days());

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/crypto/btc/PLN/30"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}