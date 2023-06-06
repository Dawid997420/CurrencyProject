package com.example.CurrencyProject.mapper.crypto;

import com.example.CurrencyProject.dto.crypto.PeriodCrypto;
import com.example.CurrencyProject.externalApi.crypto.CryptoApi;
import com.example.CurrencyProject.mapper.CryptoMapper;
import com.example.CurrencyProject.mapper.crypto.CryptoDtoFactory;
import com.example.CurrencyProject.model.crypto.Crypto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.*;

class CryptoMapperTest {




    CryptoMapper cryptoMapper ;

    CryptoApi cryptoApi = mock(CryptoApi.class);

    CryptoDtoFactory cryptoDtoFactory = new CryptoDtoFactory();

    @BeforeEach
    public void init() {

        // then
        cryptoMapper = new CryptoMapper(cryptoApi);

    }


    @Test
    void getAllCryptos() {

        // given
        when(cryptoApi.getAllCrypto()).thenReturn(cryptoDtoFactory.createAllCryptos());

        // when

        List<Crypto> cryptoList = cryptoMapper.getAllCryptos();

        // then
        assertThat(cryptoList.size() , equalTo(6));

    }


    @Test
    void getCryptosForTime() {

        // given
        when(cryptoApi.getCryptoForPeriod("btc", PeriodCrypto.month))
                .thenReturn(cryptoDtoFactory.createCryptoForMonth());

        // when

        List<Crypto> cryptoList = cryptoMapper.getCryptosForTime
                ("btc",PeriodCrypto.month);


        // then
        assertThat(cryptoList.size(), equalTo(10));
    }


}