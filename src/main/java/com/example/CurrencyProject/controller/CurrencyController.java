package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.exception.CurrencyNotFoundException;
import com.example.CurrencyProject.model.Sort;
import com.example.CurrencyProject.model.currency.Currency;
import com.example.CurrencyProject.model.currency.Group;
import com.example.CurrencyProject.service.CurrencyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "Currencies")
@RequestMapping("currencies")
@RestController
public class CurrencyController {




    private final CurrencyService currencyService;

    public CurrencyController( CurrencyService currencyService) {

        this.currencyService = currencyService;
    }

    @GetMapping("/change/{sort}")
    public ResponseEntity<List<Currency>> getCurrenciesByChangeFall(@RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "50") int size,
                                                                    @PathVariable String sort ) {
        Sort sortOption = Sort.valueOf(sort);

        if (sortOption.equals(Sort.DESC)) {

            List<Currency> allCurrencies = currencyService.getAllCryptosByChangeFall();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        } else  {
            List<Currency> allCurrencies = currencyService.getAllCryptosByChangeGrow();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        }

    }


    @GetMapping("/percentChange/{sort}")
    public ResponseEntity<List<Currency>> getCurrenciesByPercentGrow(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "50") int size,
                                                                     @PathVariable String sort ) {
        Sort sortOperation = Sort.valueOf(sort);

        if ( sortOperation.equals(Sort.ASC)) {

            List<Currency> allCurrencies = currencyService.getAllCryptosByPercentChangeGrow();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        } else {
            List<Currency> allCurrencies = currencyService.getAllCryptosByPercentChangeFall();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        }

    }


    @GetMapping("/price/{sort}")
    public ResponseEntity<List<Currency>> getCurrenciesByPriceGrow(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "50") int size,
                                                                   @PathVariable String sort ) {
        Sort sortOperation = Sort.valueOf(sort);

        if ( sortOperation.equals(Sort.ASC)) {

            List<Currency> allCurrencies = currencyService.getAllCryptosByMidPriceGrow();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        } else {
            List<Currency> allCurrencies = currencyService.getAllCryptosByMidPriceFall();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        }

    }




    @GetMapping("/abc/{sort}")
    public ResponseEntity<List<Currency>> getCurrenciesAlphabetically(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "50") int size,
                                                                      @PathVariable String sort) {
        Sort sortOperation = Sort.valueOf(sort);

        if ( sortOperation.equals(Sort.ASC)) {

            List<Currency> allCurrencies = currencyService.getAllCryptosAlphabetically();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        } else {
            List<Currency> allCurrencies = currencyService.getAllCryptosAlphabeticallyReversed();
            List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);
            return ResponseEntity.ok(currencyPage);
        }

    }



    @GetMapping
    public ResponseEntity<List<Currency>> getCurrencies(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "50") int size) {

        List<Currency> allCurrencies = currencyService.getCurrencies().block();

        List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);

        return ResponseEntity.ok(currencyPage);
    }

    private List<Currency> getCurrenciesByPage(List<Currency> allCurrencies ,int page,int size) {

        int startIndex = ( page - 1) * size ;
        int endIndex = Math.min(startIndex+size , allCurrencies.size());

        if ( startIndex >= allCurrencies.size() ) {
            return Collections.emptyList();
        } else {
            return allCurrencies.subList(startIndex,endIndex);
        }
    }


    @GetMapping("/{group}/{code}")
    public ResponseEntity<?> getCurrencyActual(@PathVariable String group, @PathVariable String code) {

        try {

            Currency currency = currencyService.getCurrencyActual(Group.valueOf(group),code);
            return ResponseEntity.ok(currency);
        } catch ( CurrencyNotFoundException e) {

            return  ResponseEntity.badRequest().body(e.getMessage());
        } catch ( Exception e) {

            return ResponseEntity.internalServerError().body("Internal Server Error");
        }

    }


    @GetMapping("days/{group}/{code}/{days}")
    public ResponseEntity<?> getCurrencyForDays(@PathVariable String group,@PathVariable String code,
                                                             @PathVariable Integer days) {
        try {

            List<Currency> currencyList = currencyService.
                    getCurrencyForDays(Group.valueOf(group), code, days).block();

            return ResponseEntity.ok(currencyList);
        } catch (IllegalArgumentException exception) {

            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body("Internal Server Error: try request bigger number of days)");
        }
    }

    @GetMapping("/years/{group}/{code}/{years}")
    public ResponseEntity<?> getCurrencyForYears(@PathVariable String group, @PathVariable String code,
                                                                    @PathVariable Integer years) {

        try {

            List<Currency> currencyForYears = currencyService.getCurrencyForYears(Group.valueOf(group)
                    ,code,years);

            return ResponseEntity.ok(currencyForYears);

        } catch (IllegalArgumentException exception) {

            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch ( Exception e) {

            return ResponseEntity.internalServerError().body("Internal server error ");
        }

    }


  


}
