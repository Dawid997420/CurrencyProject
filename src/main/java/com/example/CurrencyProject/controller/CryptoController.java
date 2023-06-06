package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.dto.crypto.PeriodCrypto;
import com.example.CurrencyProject.model.crypto.Crypto;
import com.example.CurrencyProject.scraper.metal.enums.CurrencyCode;
import com.example.CurrencyProject.service.CryptoService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@RequestMapping("crypto")
@RestController
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/price/grow/{currencyCode}")
    public List<Crypto> getAllCryptoByPriceGrow(@PathVariable String currencyCode
            , @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "50") int size) {

        try {
            CurrencyCode code = CurrencyCode.getEnum(currencyCode);
            List<Crypto> allCryptos = cryptoService.getAllCryptosByPriceGrow(code);

            return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }
    }


    @GetMapping("/price/fall/{currencyCode}")
    public List<Crypto> getAllCryptoByPriceFall(@PathVariable String currencyCode,
            @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "50") int size) {

        try {

            CurrencyCode code = CurrencyCode.getEnum(currencyCode);

            List<Crypto> allCryptos = cryptoService.getAllCryptosByPriceFall(code);


            return getCryptosByPage(allCryptos,page,size);

        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }

    }


    @GetMapping("/percent/grow/{currencyCode}")
    public List<Crypto> getAllCryptoByPercentGrow(@PathVariable String currencyCode
            , @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "50") int size) {

        try {
            CurrencyCode code = CurrencyCode.getEnum(currencyCode);
            List<Crypto> allCryptos = cryptoService.getAllCryptosByPercentGrow(code);

            return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }
    }



    @GetMapping("/percent/fall/{currencyCode}")
    public List<Crypto> getAllCryptoByPercentFall(@PathVariable String currencyCode,
                                                @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "50") int size) {

        try {

            CurrencyCode code = CurrencyCode.getEnum(currencyCode);

            List<Crypto> allCryptos = cryptoService.getAllCryptosByPercentFall(code);

            return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }

    }


    @GetMapping("/marketCap/fall/{currencyCode}")
    public List<Crypto> getAllCryptoByMarketCapFall(@PathVariable String currencyCode,
                                                  @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "50") int size) {
        try {

            CurrencyCode code = CurrencyCode.getEnum(currencyCode);

            List<Crypto> allCryptos = cryptoService.getAllCryptosByMarketCapFall(code);

            return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }

    }



    @GetMapping("/marketCap/grow/{currencyCode}")
    public List<Crypto> getAllCryptoByMarketCapGrow(@PathVariable String currencyCode,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "50") int size) {
        try {

            CurrencyCode code = CurrencyCode.getEnum(currencyCode);

            List<Crypto> allCryptos = cryptoService.getAllCryptosByMarketCapGrow(code);

            return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }

    }



    @GetMapping("/a/{currencyCode}")
    public List<Crypto> getAllCryptoAlphabetically(@PathVariable String currencyCode,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "50") int size) {
        try {
            CurrencyCode code = CurrencyCode.getEnum(currencyCode);

            List<Crypto> allCryptos = cryptoService.getAllCryptosAlphabet(code);

            return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }
    }

    @GetMapping("/z/{currencyCode}")
    public List<Crypto> getAllCryptoReverseAlphabetically(@PathVariable String currencyCode,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "50") int size) {
        try {
            CurrencyCode code = CurrencyCode.getEnum(currencyCode);

            List<Crypto> allCryptos = cryptoService.getAllCryptosReverseAlphabet(code);

            return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {

            throw new RuntimeException("Not Allowed Currency Code");
        }
    }



    @GetMapping("/{currencyCode}")
    public List<Crypto> getAllCrypto(@PathVariable String currencyCode, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "50") int size) {

        try {
            CurrencyCode code = CurrencyCode.getEnum(currencyCode);
            List<Crypto> allCryptos = cryptoService.getAllCryptos(code);

           return getCryptosByPage(allCryptos,page,size);
        } catch (RuntimeException e) {
            throw new RuntimeException("Not Allowed currency code, allowed is only {USD, PLN, GBP, EUR} error" + e);
        }
    }



    @GetMapping("/{cryptoName}/{currencyCode}/{days}")
    public List<Crypto> getCryptoPriceForDays(@PathVariable String cryptoName,
                                              @PathVariable String currencyCode, @PathVariable String days) throws ParseException {

        try {
            CurrencyCode code = CurrencyCode.getEnum(currencyCode);
            PeriodCrypto period = PeriodCrypto.getPeriod(days);

            return cryptoService.getCryptoPriceForDays(cryptoName,code,period);
        } catch (RuntimeException e) {

            throw new RuntimeException("You provide wrong days number, currency code or " +
                    "cryptoName "+ e);
        }

    }


    private List<Crypto> getCryptosByPage(List<Crypto> cryptos , int page, int size){

        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, cryptos.size());

        if (startIndex >= cryptos.size()) {
            return Collections.emptyList();
        } else {
            return cryptos.subList(startIndex, endIndex);
        }

    }



}
