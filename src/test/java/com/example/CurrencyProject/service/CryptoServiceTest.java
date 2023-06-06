package com.example.CurrencyProject.service;

import com.example.CurrencyProject.dto.crypto.PeriodCrypto;
import com.example.CurrencyProject.mapper.CryptoMapper;
import com.example.CurrencyProject.model.crypto.Crypto;
import com.example.CurrencyProject.scraper.metal.enums.CurrencyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;


class CryptoServiceTest {

    CryptoMapper cryptoMapper = mock(CryptoMapper.class);

    CryptoService cryptoService ;

    CryptoFactory cryptoFactory = new CryptoFactory();

    CurrencyService currencyService = mock(CurrencyService.class);
    @BeforeEach
    public void init() {

        cryptoService = new CryptoService(cryptoMapper, currencyService);
    }


    @Test
    void getAllCryptos() {

        // given
        when(cryptoMapper.getAllCryptos()).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptos(CurrencyCode.USD);

        // then
        assertThat(cryptoList.size(),equalTo(15));
    }

    @Test
    void getCryptoPriceForDays() {

        // given
        when(cryptoMapper.getCryptosForTime("btc", PeriodCrypto.day))
                .thenReturn(cryptoFactory.createCryptoListFor30Days());

        // when

       List<Crypto> cryptoList =  cryptoService.
               getCryptoPriceForDays("btc",CurrencyCode.USD,PeriodCrypto.day);

        // then
        assertThat(cryptoList.size(), equalTo(30));

    }

    @Test
    void getAllCryptosByMarketCapFall() {

        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosByMarketCapFall(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getMarketCap(),
                greaterThan(cryptoList.get(13).getMarketCap()));
    }


    @Test
    void getAllCryptosByMarketCapGrow() {

        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosByMarketCapGrow(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getMarketCap(),
                lessThan(cryptoList.get(13).getMarketCap()));
    }


    @Test
    void getAllCryptosByPriceGrow() {
        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosByPriceGrow(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getPrice(),
                lessThan(cryptoList.get(13).getPrice()));
    }

    @Test
    void getAllCryptosByPriceFall() {

        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosByPriceFall(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getPrice(),
                greaterThan(cryptoList.get(13).getPrice()));
    }

    @Test
    void getAllCryptosByPercentFall() {

        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosByPercentFall(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getPercentChange(),
                greaterThan(cryptoList.get(13).getPercentChange()));
    }


    @Test
    void getAllCryptosByPercentGrow() {
        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosByPercentGrow(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getPercentChange(),
                lessThan(cryptoList.get(13).getPercentChange()));
    }

    @Test
    void getAllCryptosAlphabet() {

        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosAlphabet(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getName(), equalTo("algo"));
    }


    @Test
    void getAllCryptosReverseAlphabet() {
        // given
        when(cryptoService.getAllCryptos(CurrencyCode.USD)).thenReturn(cryptoFactory.createAllCryptos());

        // when
        List<Crypto> cryptoList = cryptoService.getAllCryptosReverseAlphabet(CurrencyCode.USD);

        // then
        assertThat(cryptoList.get(0).getName(), equalTo("xtz"));
    }


}