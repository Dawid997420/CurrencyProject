package com.example.CurrencyProject.service;


import com.example.CurrencyProject.dto.crypto.PeriodCrypto;
import com.example.CurrencyProject.mapper.CryptoMapper;
import com.example.CurrencyProject.model.crypto.Crypto;
import com.example.CurrencyProject.model.currency.Currency;
import com.example.CurrencyProject.model.currency.Group;
import com.example.CurrencyProject.scraper.metal.enums.CurrencyCode;
import com.example.CurrencyProject.utils.MathMapper;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CryptoService {


    private final CryptoMapper cryptoMapper ;

    private final CurrencyService currencyService ;

    private final MathMapper mathMapper = new MathMapper();

    public CryptoService(CryptoMapper cryptoMapper, CurrencyService currencyService) {

        this.cryptoMapper = cryptoMapper;
        this.currencyService = currencyService;
    }



    public List<Crypto> getCryptoPriceForDays(String cryptoCode , CurrencyCode currencyCode , PeriodCrypto period ){

        List<Crypto> cryptoList = cryptoMapper.getCryptosForTime(cryptoCode, period );
      if (currencyCode.equals(CurrencyCode.USD)) {


          cryptoList.forEach(crypto -> crypto.setCurrency(currencyCode.name()));

          return cryptoList;
      } else if ( currencyCode.equals(CurrencyCode.PLN)) {

          cryptoList.forEach(crypto -> crypto.setCurrency(currencyCode.name()));

          return getCryptoPricePLN(cryptoList);
      } else {
          throw new RuntimeException("Currency error exception");
      }

    }


    private boolean isNumberOfDaysAllowed(int days){

        return days == 1 || days == 7 || days == 14 || days == 30
                || days == 90 || days == 180 || days == 365;
    }


    public List<Crypto> getAllCryptos(CurrencyCode currencyCode) {

        List<Crypto> cryptoList = cryptoMapper.getAllCryptos();

        if ( currencyCode.equals(CurrencyCode.USD)) {

            cryptoList.forEach(crypto -> crypto.setCurrency("USD"));
            return cryptoList;
        } else if ( currencyCode.equals(CurrencyCode.PLN)) {

            cryptoList.forEach(crypto -> crypto.setCurrency("PLN"));
            return getCryptoPricePLN(cryptoList);
        } else {
            throw new RuntimeException("Crypto Service Unknown error");
        }
    }


    public List<Crypto> getAllCryptosAlphabet(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptos(currencyCode);

        allCryptos.sort(Comparator.comparing(crypto -> crypto.getName().toLowerCase()));
        return allCryptos;
    }

    public List<Crypto> getAllCryptosReverseAlphabet(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptosAlphabet(currencyCode);
        Collections.reverse(allCryptos);
        return allCryptos;
    }


    public List<Crypto> getAllCryptosByPercentFall(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptos(currencyCode);

        allCryptos.sort(Comparator.comparingDouble(Crypto::getPercentChange).reversed());
        return allCryptos;
    }


    public List<Crypto> getAllCryptosByPercentGrow(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptos(currencyCode);

        allCryptos.sort(Comparator.comparingDouble(Crypto::getPercentChange));
        return allCryptos;
    }


    public List<Crypto> getAllCryptosByPriceGrow(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptos(currencyCode);

        allCryptos.sort(Comparator.comparingDouble(Crypto::getPrice));
        return allCryptos;
    }

    public List<Crypto> getAllCryptosByPriceFall(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptos(currencyCode);

        allCryptos.sort(Comparator.comparingDouble(Crypto::getPrice).reversed());
        return allCryptos;
    }

    public List<Crypto> getAllCryptosByMarketCapFall(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptos(currencyCode);

        allCryptos.sort((crypto1, crypto2) -> crypto2.getMarketCap().compareTo(crypto1.getMarketCap()));
        return allCryptos;
    }

    public List<Crypto> getAllCryptosByMarketCapGrow(CurrencyCode currencyCode) {

        List<Crypto> allCryptos = getAllCryptos(currencyCode);

        allCryptos.sort(Comparator.comparing(Crypto::getMarketCap));
        return allCryptos;
    }




    private List<Crypto> getCryptoPricePLN(List<Crypto> cryptoList) {

        Currency currency = currencyService.getCurrencyActual(Group.A,"EUR");

        double usdPricePLN = currency.getMidPrice();
        cryptoList.forEach(crypto ->  crypto.setPrice(
                mathMapper.roundToTwoDecimalPlace(crypto.getPrice() * usdPricePLN)));

        return cryptoList;
    }


}
