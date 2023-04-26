package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.exception.CurrencyNotFoundException;
import com.example.CurrencyProject.mapper.CurrencyMapper;
import com.example.CurrencyProject.model.AB.Currency;
import com.example.CurrencyProject.model.Group;
import com.example.CurrencyProject.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("currency")
@RestController
public class CurrencyController {




    private final CurrencyService currencyService;

    public CurrencyController( CurrencyService currencyService) {

        this.currencyService = currencyService;
    }


    @GetMapping
    public ResponseEntity<List<Currency>> currencyList() {

        return ResponseEntity.ok(currencyService.getCurrencies().block());
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
