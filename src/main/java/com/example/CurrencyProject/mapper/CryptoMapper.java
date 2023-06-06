package com.example.CurrencyProject.mapper;


import com.example.CurrencyProject.dto.crypto.*;
import com.example.CurrencyProject.externalApi.crypto.CryptoApi;
import com.example.CurrencyProject.model.crypto.Crypto;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CryptoMapper {


    private final CryptoApi cryptoApi;

    public CryptoMapper(CryptoApi cryptoApi) {
        this.cryptoApi = cryptoApi;
    }


    public List<Crypto> getAllCryptos() {

       CryptoObjects cryptoObjects = cryptoApi.getAllCrypto();

       return createListCryptos(cryptoObjects);
    }


    public List<Crypto> getCryptosForTime(String cryptoCode, PeriodCrypto period) {

       DataHistory dataHistory = cryptoApi.getCryptoForPeriod(cryptoCode,period);

       return createCryptosForTime(dataHistory,cryptoCode);
    }







    private List<Crypto> createCryptosForTime(DataHistory dataHistory,String id) {

        List<Crypto> cryptoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz");


        for ( Map.Entry<String,Double> entry : dataHistory.getValues().entrySet() )  {

            LocalDateTime data = LocalDateTime.parse(entry.getKey(), formatter);

            Double price = entry.getValue();
            Crypto crypto = Crypto.builder()
                    .date(data).id(id).price(price).build();

            cryptoList.add(crypto);
        }
        return cryptoList;
    }


    private List<Crypto> createListCryptos(CryptoObjects cryptoObjects) {

        List<Crypto> cryptoList = new ArrayList<>();

        for (Map.Entry<String, CryptoDto> entry : cryptoObjects.getData().entrySet()) {

           CryptoDto cryptoDto = entry.getValue();

           Crypto crypto = Crypto.builder().name(cryptoDto.getN()).id(cryptoDto.getT()).price(cryptoDto.getP())
                           .marketCap(new BigInteger(String.valueOf(cryptoDto.getMc())))
                                   .totalVolume(cryptoDto.getV())
                   .percentChange(cryptoDto.getChange()).currency("USD")
                           .date(LocalDateTime.now()).build();

           cryptoList.add(crypto);
        }

        return cryptoList;
    }




}
