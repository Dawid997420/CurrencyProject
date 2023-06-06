package com.example.CurrencyProject.service;

import com.example.CurrencyProject.model.crypto.Crypto;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CryptoFactory {


    public List<Crypto> createAllCryptos() {

        List<Crypto> cryptoList = new ArrayList<>();

        List<String> names = Arrays.asList("btc","eth","xmr","xrp","lol","enj",
                "bch","xlm","doge","xem","trx","xtz","uni","atom","algo","neo");

        List<Double> prices = Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0
                ,10.0,11.0,12.0,13.0,14.0,15.0,16.0);

        List<BigInteger> marketCaps = Arrays.asList(new BigInteger("999999"),
                new BigInteger("999998"),new BigInteger("888888"),new BigInteger("77"),
                new BigInteger("5"), new BigInteger("987654"),new BigInteger("332211"),
                new BigInteger("6969696969"),new BigInteger("321576"),new BigInteger("900909"),
                new BigInteger("111111"),new BigInteger("2222222"),new BigInteger("999999"),
                new BigInteger("333333"),new BigInteger("444444"),new BigInteger("909"));

        List<Long> totalVolumes = Arrays.asList(112121L,443344L, 99999L, 12L,4312L , 5634L, 1L,65L,
                123L,9L,54L,90L,43L,41231241L,12111111L,424L,99L);

        List<Double> percentChanges = Arrays.asList(0.3, 0.99, 0.12, 4.2, 5.3, 0.4, 0.053, 0.9, 0.4,
                1.3,0.32, 1.3 , 3.2, 3.5 , 3.5 ,0.19, 0.6);



        for ( int i = 0 ; i < 15 ; i++) {

            Crypto crypto = Crypto.builder().name(names.get(i)).id(names.get(i))
                            .price(prices.get(i)).marketCap(marketCaps.get(i))
                            .totalVolume(totalVolumes.get(i)).percentChange(percentChanges.get(i))
                            .currency("USD")
                            .date(LocalDateTime.of(2022,11,23,23,4))
                    .build();

            cryptoList.add(crypto);
        }

        return cryptoList;
    }

    public Crypto createCrypto(String name,String id  ) {

        return  Crypto.builder().name(name)
                .id(id).price(1313412513514.3).marketCap(new BigInteger("1221424124125122112412412"))
                .totalVolume(31444444411L)
                .percentChange(0.5).currency("PLN").date(LocalDateTime.now()).build();
    }

    public List<Crypto> createCryptoListFor30Days() {

            List<Crypto> cryptoForDays = new ArrayList<>();

            long timeStampStart = 1681142400000L ;
            long timeStampNext = 14400000L ;

            for ( int i =0 ; i < 30 ; i++) {
                LocalDateTime localDateTime = LocalDateTime.ofInstant
                        (Instant.ofEpochSecond(timeStampStart  + (timeStampNext * i))
                        , ZoneId.systemDefault());
                Crypto cryptoDto  = Crypto.builder().date(localDateTime)
                        .price(122 + i ).build();
                cryptoForDays.add(cryptoDto);
            }
            return cryptoForDays;


    }


}
