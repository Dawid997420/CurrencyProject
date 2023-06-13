package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.dto.crypto.PeriodCrypto;
import com.example.CurrencyProject.model.Sort;
import com.example.CurrencyProject.model.crypto.Crypto;
import com.example.CurrencyProject.scraper.metal.enums.CurrencyCode;
import com.example.CurrencyProject.service.CryptoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Tag(name = "Cryptos")
@RequestMapping("cryptos")
@RestController
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/price/{sort}")
    public List<Crypto> getAllCryptoByPriceGrow(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "50") int size,
                                                @PathVariable String sort) {

        Sort sortOperation = Sort.valueOf(sort);

        if ( sortOperation.equals(Sort.ASC)) {

            try {
                CurrencyCode code = CurrencyCode.PLN;
                List<Crypto> allCryptos = cryptoService.getAllCryptosByPriceGrow(code);

                return getCryptosByPage(allCryptos,page,size);
            } catch (RuntimeException e) {

                throw new RuntimeException("Not Allowed Currency Code");
            }

        } else {

            try {
                CurrencyCode code = CurrencyCode.PLN;
                List<Crypto> allCryptos = cryptoService.getAllCryptosByPriceFall(code);
                return getCryptosByPage(allCryptos,page,size);

            } catch (RuntimeException e) {

                throw new RuntimeException("Not Allowed Currency Code");
            }
        }

    }




    @GetMapping("/percentChange/{sort}")
    public List<Crypto> getAllCryptoByPercentGrow(@PathVariable String sort
            , @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "50") int size) {

        Sort sortOperation = Sort.valueOf(sort);
        CurrencyCode code = CurrencyCode.PLN;

        if ( sortOperation.equals(Sort.ASC)) {

        List<Crypto> allCryptos = cryptoService.getAllCryptosByPercentGrow(code);
        return getCryptosByPage(allCryptos,page,size);

        } else {

        List<Crypto> allCryptos = cryptoService.getAllCryptosByPercentFall(code);
        return getCryptosByPage(allCryptos,page,size);
        }

    }



    @GetMapping("/marketCap/{sort}")
    public List<Crypto> getAllCryptoByMarketCapFall(@PathVariable String sort,
                                                  @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "50") int size) {
        Sort sortOperation = Sort.valueOf(sort);
        CurrencyCode code = CurrencyCode.PLN;

        if ( sortOperation.equals(Sort.DESC)) {

            List<Crypto> allCryptos = cryptoService.getAllCryptosByMarketCapFall(code);
            return getCryptosByPage(allCryptos,page,size);
        } else {

            List<Crypto> allCryptos = cryptoService.getAllCryptosByMarketCapGrow(code);
            return getCryptosByPage(allCryptos,page,size);
        }

    }






    @GetMapping("/abc/{sort}")
    public List<Crypto> getAllCryptoAlphabetically(@PathVariable String sort,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "50") int size) {

        Sort sortOperation = Sort.valueOf(sort);
        CurrencyCode code = CurrencyCode.PLN;

        if ( sortOperation.equals(Sort.ASC)) {


            List<Crypto> allCryptos = cryptoService.getAllCryptosAlphabet(code);
            return getCryptosByPage(allCryptos,page,size);
        } else {

            List<Crypto> allCryptos = cryptoService.getAllCryptosReverseAlphabet(code);

            return getCryptosByPage(allCryptos,page,size);
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



    @GetMapping("days/{cryptoCode}/{days}")
    public List<Crypto> getCryptoPriceForDays(@PathVariable String cryptoCode,
                                             @PathVariable String days) throws ParseException {

        CurrencyCode code = CurrencyCode.PLN;
        try {

            PeriodCrypto period = PeriodCrypto.getPeriod(days);
            return cryptoService.getCryptoPriceForDays(cryptoCode,code,period);
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
