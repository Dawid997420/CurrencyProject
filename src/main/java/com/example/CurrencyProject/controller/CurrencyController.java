package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.exception.CurrencyNotFoundException;
import com.example.CurrencyProject.model.currency.Currency;
import com.example.CurrencyProject.model.currency.Group;
import com.example.CurrencyProject.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequestMapping("currency")
@RestController
public class CurrencyController {




    private final CurrencyService currencyService;

    public CurrencyController( CurrencyService currencyService) {

        this.currencyService = currencyService;
    }

    @GetMapping("/change/fall")
    public ResponseEntity<List<Currency>> getCurrenciesByChangeFall(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "50") int size) {

        List<Currency> allCurrencies = currencyService.getAllCryptosByChangeFall();
        List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);

        return ResponseEntity.ok(currencyPage);
    }

    @GetMapping("/change/grow")
    public ResponseEntity<List<Currency>> getCurrenciesByChangeGrow(@RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "50") int size) {

        List<Currency> allCurrencies = currencyService.getAllCryptosByChangeGrow();
        List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);

        return ResponseEntity.ok(currencyPage);
    }



    @GetMapping("/percent/grow")
    public ResponseEntity<List<Currency>> getCurrenciesByPercentGrow(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "50") int size) {

        List<Currency> allCurrencies = currencyService.getAllCryptosByPercentChangeGrow();
        List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);

        return ResponseEntity.ok(currencyPage);
    }

    @GetMapping("/percent/fall")
    public ResponseEntity<List<Currency>> getCurrenciesByPercentFall(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "50") int size) {

        List<Currency> allCurrencies = currencyService.getAllCryptosByPercentChangeFall();
        List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);

        return ResponseEntity.ok(currencyPage);
    }



    @GetMapping("/price/grow")
    public ResponseEntity<List<Currency>> getCurrenciesByPriceGrow(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "50") int size) {

        List<Currency> allCurrencies = currencyService.getAllCryptosByMidPriceGrow();
        List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);

       return ResponseEntity.ok(currencyPage);
    }


    @GetMapping("/price/fall")
    public ResponseEntity<List<Currency>> getCurrenciesByPriceFall(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "50") int size) {

        List<Currency> allCurrencies = currencyService.getAllCryptosByMidPriceFall();
        List<Currency> currencyPage = getCurrenciesByPage(allCurrencies,page,size);

        return ResponseEntity.ok(currencyPage);
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


    @GetMapping("days/{group}/{code}/{numberDays}")
    public ResponseEntity<?> getCurrencyForDays(@PathVariable String group,@PathVariable String code,
                                                             @PathVariable Integer numberDays) {
        try {

            List<Currency> currencyList = currencyService.
                    getCurrencyForDays(Group.valueOf(group), code, numberDays).block();

            return ResponseEntity.ok(currencyList);
        } catch (IllegalArgumentException exception) {

            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body("Internal Server Error: try request bigger number of days)");
        }
    }

    @GetMapping("/years/{group}/{code}/{numberYears}")
    public ResponseEntity<?> getCurrencyForYears(@PathVariable String group, @PathVariable String code,
                                                                    @PathVariable Integer numberYears) {

        try {

            List<Currency> currencyForYears = currencyService.getCurrencyForYears(Group.valueOf(group)
                    ,code,numberYears);

            return ResponseEntity.ok(currencyForYears);

        } catch (IllegalArgumentException exception) {

            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch ( Exception e) {

            return ResponseEntity.internalServerError().body("Internal server error ");
        }

    }


    @GetMapping("/max/years/{group}/{code}")
    public ResponseEntity<?> getCurrencyMaximum(@PathVariable String group, @PathVariable String code) {

        try {

            List<Currency> currencyForYears = currencyService.getCurrencyMaximum(Group.valueOf(group)
                    ,code);

            return ResponseEntity.ok(currencyForYears);
        } catch (IllegalArgumentException exception) {

            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch ( Exception e) {

            return ResponseEntity.internalServerError().body("Internal server error ");
        }

    }



}
