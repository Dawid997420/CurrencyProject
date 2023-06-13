package com.example.CurrencyProject.service;

import com.example.CurrencyProject.model.AssetType;
import com.example.CurrencyProject.model.TimeOption;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeOptionService {


    public List<TimeOption> getTimeOptions(AssetType assetType) {

        if ( assetType.equals(AssetType.Material)) {

        return createTimeForMaterial();
        } else if ( assetType.equals(AssetType.Crypto)) {

        return createTimeForCrypto();
        } else {

        return createTimeForCurrency();
        }

    }


    public List<TimeOption> createTimeForMaterial() {

        List<TimeOption> timeForMaterial = new ArrayList<>();


        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Material).name("1 mies").value("30").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Material).name("1 rok").value("365").build());


        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Material).name("5 lat").value("1825").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Material).name("max").value("max").build());

        return timeForMaterial;
    }

    public List<TimeOption> createTimeForCrypto() {

        List<TimeOption> timeForMaterial = new ArrayList<>();

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Crypto).name("1 dzien").value("1").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Crypto).name("1 mies").value("30").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Crypto).name("1 rok").value("365").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Crypto).name("max").value("max").build());
        return timeForMaterial;
    }


    public List<TimeOption> createTimeForCurrency() {

        List<TimeOption> timeForMaterial = new ArrayList<>();

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Currency).name("1 mies").value("30").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Currency).name("6 mies").value("180").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Currency).name("1 rok").value("365").build());

        timeForMaterial.add(TimeOption.builder()
                .assetType(AssetType.Currency).name("10 lat").value("3650").build());

        return timeForMaterial;
    }


}
