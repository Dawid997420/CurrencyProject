package com.example.CurrencyProject.mapper.crypto;

import com.example.CurrencyProject.dto.crypto.CryptoDto;
import com.example.CurrencyProject.dto.crypto.CryptoObjects;
import com.example.CurrencyProject.dto.crypto.DataHistory;

import java.util.Map;

public class CryptoDtoFactory {




    public DataHistory createCryptoForMonth() {

        return DataHistory.builder().values(Map.of("2023-04-25 12:30:18 UTC",24870.0,
                "2023-04-26 12:30:18 UTC",26244.1,
                "2023-04-27 12:30:18 UTC",26246.7,
                "2023-04-28 12:30:18 UTC",26656.6,
                "2023-04-29 12:30:18 UTC",26634.2 ,
                "2023-04-30 12:30:18 UTC", 26506.9,
                "2023-05-01 12:30:18 UTC",25904.4,
                "2023-05-02 12:30:18 UTC",25617.9,
                "2023-05-03 12:30:18 UTC",25873.1,
                "2023-05-04 12:30:18 UTC",26361.6)).build();

    }

    public CryptoObjects createAllCryptos() {

    return CryptoObjects.builder().data(Map.of("btc",createCryptoDto("bitcoin","btc"),
                "Ethereum",createCryptoDto("eth","eth"),
                "xrp",createCryptoDto("xrp","xrp"),
                "ltc",createCryptoDto("Litecoin","ltc"),
                "xmr",createCryptoDto("Monero","xmr"),
                "snx", createCryptoDto("Synthetix","snx")))
                .build();
    }

    public CryptoDto createCryptoDto(String name , String id) {

        return CryptoDto.builder().n(name).t(id).p(24811.1)
                .mc(480889473399L).mcr(1).v(10908839019L).h24("25393.00")
                .l24("24735.00").pc24("-558.19858984993").pcp24("-2.20109")
                .mc24("-9883827793.3605").mcp(null).mcpr(-2.01393).s(19382356)
                .ts(21000000).ms(21000000).ath("59717.00").athp("-58.45")
                .at1("51.30").p1(-0.18).p24(-2.2).p7(-0.05).p14( -1.52)
                .p30( -0.3).p200(16.32).p365(-9.51).v7(60860139284L)
                .v14(137016360083L).v30(382072085792L).has_earn(false)
                .earn_rate(0).earn_promo(false)
                .wph(new double[]{25226.4, 25344.3, 24719.3, 24946.4, 24809.6})
                .change(-2.13).build();
    }

}
