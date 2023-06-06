package com.example.CurrencyProject.service;

import com.example.CurrencyProject.mapper.GoldMapper;
import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.utils.MathMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GoldService {


    private final GoldMapper goldMapper;

    private final MathMapper mathMapper = new MathMapper();


    public GoldService( GoldMapper goldMapper) {
        this.goldMapper = goldMapper;
    }



    public Mono<Material> getActualGoldValue() {

      Mono<List<Material>> goldPriceForTwoDays = goldMapper.getGoldMostActualDays(2)
              .map(this::sortMaterialArrayByDate);

        return createOneMaterial(goldPriceForTwoDays);
    }



    public  Mono<List<Material>> getGoldForDays( int days ) {

        if ( days > 367 ) {

            throw new IllegalArgumentException("Maximum number of days is 365");
        }

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");

        LocalDate today = LocalDate.now(polishZone);
        LocalDate daysLater = today.minusDays(days);
        Mono<List<Material>> goldBetween = goldMapper.getGoldBetween(daysLater,today);

        if ( goldBetween == null) {
            throw new RuntimeException("Internal server error :" +
                    " We don't have this values yet try more days");
        }
        return goldBetween;
    }



    public List<Material> getGoldForYears(int years) {

        if ( !areNumberOfYearsAllowed(years) ) {
            throw new IllegalArgumentException
                    ("Illegal Argument Exception , we don't have data before 2013");

        }
        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        LocalDate endDay = LocalDate.now(polishZone).minusYears(years-1);
        LocalDate startDay = endDay.minusDays(365);

        List<Mono<List<Material>>> materialList = new ArrayList<>();

        for (int i = 0; i < years; i++) {

            Mono<List<Material>> result = goldMapper.getGoldBetween( startDay, endDay);
            materialList.add(result);
            startDay = endDay;
            endDay = startDay.plusYears(1);
        }

        List<List<Material>> allYears = Mono.just(materialList)
                .flatMapMany(Flux::fromIterable)
                .concatMap(mono->mono).collectList().block();

        return createOneList(allYears);
    }


    private List<Material> createOneList(List<List<Material>> allYears) {

        List<Material> oneListOfAllYears = new ArrayList<>();
        allYears.forEach(oneListOfAllYears::addAll);

        oneListOfAllYears.sort(Comparator.comparing(Material::getData));

        return oneListOfAllYears;
    }



    private boolean areNumberOfYearsAllowed(int numberOfYears ) {

        ZoneId polishZone = ZoneId.of("Europe/Warsaw");
        LocalDate today = LocalDate.now(polishZone);
        LocalDate maximumAllowedDate = LocalDate.of(2013,1,2);

        if ( today.minusYears(numberOfYears).isBefore(maximumAllowedDate)) {

            return false;
        }
        return true;
    }


    private Mono<Material> createOneMaterial(Mono<List<Material>> twoDaysMaterial) {

      return   twoDaysMaterial.map( materials -> {
            Material beforeDay = materials.get(0);
            Material actualDay = materials.get(1);

            return buildMaterial(beforeDay,actualDay);
        });

    }

    private Material buildMaterial(Material beforeDay, Material actualDay) {

        double change = calculateChange(beforeDay,actualDay);
        double percent = calculatePercent(beforeDay,change);

        return Material.builder().data(actualDay.getData()).name(actualDay.getName())
                .unit(actualDay.getUnit())
                .price(actualDay.getPrice()).change(mathMapper.roundToSixDecimalPlace(change))
                .percentChange(mathMapper.roundToTwoDecimalPlace(percent)).currency("PLN").build();
    }

    private double calculateChange(Material materialBefore , Material materialActual) {

        return  materialActual.getPrice() - materialBefore.getPrice() ;
    }

    private double calculatePercent(Material materialActual, double change ) {

        return ( change / materialActual.getPrice()) * 100;
    }

    private List<Material> sortMaterialArrayByDate(List<Material> materials) {

        List<Material> sortedMaterials = new ArrayList<>(materials);
        sortedMaterials.sort(Comparator.comparing(Material::getData));

        return materials;
    }





}
