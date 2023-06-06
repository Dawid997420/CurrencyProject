package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.model.AssetType;
import com.example.CurrencyProject.model.TimeOption;
import com.example.CurrencyProject.service.TimeOptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("time")
public class AssetTypeTimeController {


    private final TimeOptionService timeService ;

    public AssetTypeTimeController(TimeOptionService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("{assetType}")
    public ResponseEntity<List<TimeOption>> getAllowedOptions(@PathVariable String assetType) {

        try {
            AssetType asset = AssetType.valueOf(assetType);

            return ResponseEntity.ok(timeService.getTimeOptions(asset));
        } catch (Exception e) {

            throw new RuntimeException("Asset not exist " + e) ;
        }

    }

}
