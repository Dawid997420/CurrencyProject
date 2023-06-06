package com.example.CurrencyProject.scraper.metal;


import com.example.CurrencyProject.dto.metal.MetalDto;
import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.metal.enums.*;
import com.example.CurrencyProject.utils.MathMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MetalScrapper {

    @Autowired
    private Environment env;


    private final MathMapper mathMapper =new MathMapper();


    private String appUrl ;
    public MetalScrapper(){

    }

    public MetalScrapper(@Value("${api.metal.url}") String appUrl ) {

        this.appUrl = appUrl;
    }


    private String generateTimeStamp() {

        Instant instant = Instant.now();

        long timestamp = instant.getEpochSecond();

        return String.valueOf(timestamp);

    }

    @Cacheable(cacheNames = "MaterialForTimePeriod")
    public List<Material> getMaterialForTimePeriod(Metal metal, CurrencyCode code,
                                                   WeightCode weightCode, Period period ) throws IOException {

        List<Material> materialListForTimePeriod = new ArrayList<>();
        String timeStampNow = generateTimeStamp();

        setAppUrl("/charts/get-data/?metal_code=" + metal.name() +
                "&currency_id=" + code.getValue() +
                "&weight_code="+weightCode +"" +
                "&period="+ period.getTime() +
                "&callback=" +renderRandomJQuery()+
                timeStampNow +
                "&_=" +
                timeStampNow);

       Document document = Jsoup.connect(appUrl).get();

       return buildMaterialList(document.text(), metal,weightCode, code);
    }

    private List<Material> buildMaterialList(String materialForTime , Metal metal,
                                             WeightCode weightCode, CurrencyCode currencyCode) {

        List<Material> resultValue ;

        int startIndex = materialForTime.indexOf("{");
        int endIndex = materialForTime.indexOf("}");

        String value1 = materialForTime.substring(startIndex + 1, endIndex);
        value1 =value1.replace("data:","");
        int indexS = value1.indexOf("s");

        value1 = value1.substring(0,indexS-2);

        List<MetalDto> metalDtoList = convertStringToDoubles(value1);

      resultValue = metalDtoList.stream().map( metalDto ->
              Material.builder().name(metal.name()).data(metalDto.getDateTime())
                .unit(weightCode.getWeight()).price(metalDto.getPrice())
                .currency(String.valueOf(currencyCode)).build()).toList();


        return resultValue;
    }

    private List<MetalDto> convertStringToDoubles(String input) {

        input = input.substring(1, input.length() - 1);

        input = input.replace("[","");
        input = input.replace("]","");
        input = input.replace(" ","");

        String[] elements = input.split(",");

        List<double[]> result = new ArrayList<>();

        List<LocalDateTime> timestamps = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        for ( int i = 0 ; i < elements.length ; i++) {

            if (i % 2 == 0) {
                timestamps.add( createDateFromString(elements[i]) );
                //LocalDateTime dateTime = new Lo
            } else {
                prices.add(Double.parseDouble(elements[i]));
            }
        }

        List<MetalDto> metalDtoValues = new ArrayList<>();

        for ( int j = 0 ; j < prices.size() ; j++ ) {

           MetalDto metalDto = MetalDto.builder().dateTime(timestamps.get(j))
                    .price(prices.get(j)).build();

            metalDtoValues.add(metalDto);
        }

      return metalDtoValues;
    }

    private LocalDateTime createDateFromString(String timestamp) {

        long timeStampMillis = Long.parseLong(timestamp);

        Instant instant = Instant.ofEpochMilli(timeStampMillis);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();


    }

    @Cacheable(cacheNames = "MetalActual")
    public Mono<Material> getMetalActual(Metal metal, Weight weight) throws Exception {


        Document document = getSite();

        return Mono.fromCallable( () -> {
          return   Material.builder().name(metal.name())
                  .data(getDataDate(document))
                  .unit(weight.name()).price(getMetalPrice(document,metal,weight))
                  .change(getChange(document,metal,weight))
                  .percentChange(getPercentChange(document,metal))
                  .currency("PLN").build();
        });

    }

    @Cacheable(cacheNames = "getSite")
    public Document getSite() throws Exception {

       setAppUrl("/pl/wykresy/kurs-srebra/gram/#chart");
        return Jsoup.connect(appUrl).get();
    }

    private String renderRandomJQuery() {

        Random random = new Random();

        int randomNumber = random.nextInt(99) + 1;

        return "jQuery3610060131425164148" + randomNumber + "_";
    }


    private void setAppUrl(String value) {

        String url = this.env.getProperty("api.metal.url");
        this.appUrl = url + value;
    }



    private double getMetalPrice(Document document , Metal metal,Weight weight) {


        Elements metals = document.select("div.live_metal_prices_l");

        String priceValue = metals.select(".live_metal_prices_li__"+ metal +
                ".live_metal_prices_li" +
                " > [href] > .live_metal_prices_li_in > .live_metal_prices_li_txt").text();



        priceValue = priceValue.replace("zł","");

        return getPriceFromString(priceValue,weight);
    }




    private double getPriceFromString(String priceValue, Weight weight) {

        String result = priceValue.replace(",",".");
        result = result.replace(" ", "");
        double price = Double.parseDouble( result);

       return calculateValueByWeight(price,weight,true);

    }


    private double calculateValueByWeight(double value,Weight weight,boolean rounded ) {

        if ( weight.equals(Weight.gram)) {

            if( rounded) return mathMapper.roundToTwoDecimalPlace(value / 31.1035);
            else return mathMapper.roundToSixDecimalPlace(value / 31.1035);
        } else if ( weight.equals(Weight.ounce)) {
            return value;
        } else {
            if (rounded)
            return mathMapper.roundToTwoDecimalPlace(value * 32.150722);
            else
            return mathMapper.roundToSixDecimalPlace(value * 32.150722);
        }

    }

    private double getPercentChange(Document document, Metal metal) {

        Elements metals = document.select("div.live_metal_prices_l");


            String changeValue = metals.select(".live_metal_prices_li__" + metal +
                    ".live_metal_prices_li > [href] > .live_metal_prices_li_in > " +
                    ".live_metal_prices_li_msg__down.live_metal_prices_li_msg").text();

            if ( changeValue.length() < 1) {

               changeValue = metals.select(".live_metal_prices_li__" + metal +
                        ".live_metal_prices_li > [href] > .live_metal_prices_li_in > " +
                        ".live_metal_prices_li_msg__up.live_metal_prices_li_msg").text();
            }


        double percentChange = 0;

        int startIndex = changeValue.indexOf("(");
        int endIndex = changeValue.indexOf(")");

        if (startIndex != -1 && endIndex != -1) {

            String result = changeValue.substring(startIndex + 1, endIndex);
            result = result.replace("%","");
            return Double.parseDouble(result);

        } else {
            throw new RuntimeException("Error while parsing percent getPercentChange()");
        }

    }

    private double getChange(Document document, Metal metal,Weight weight) {

        Elements metals = document.select("div.live_metal_prices_l");

       // System.out.println(document);

        String changeValue = metals.select(".live_metal_prices_li__" + metal +
                ".live_metal_prices_li > [href] > .live_metal_prices_li_in > " +
                ".live_metal_prices_li_msg__down.live_metal_prices_li_msg").text();

        if ( changeValue.length() < 1) {

            changeValue = metals.select(".live_metal_prices_li__" + metal +
                    ".live_metal_prices_li > [href] > .live_metal_prices_li_in > " +
                    ".live_metal_prices_li_msg__up.live_metal_prices_li_msg").text();
        }


        int endIndex = changeValue.indexOf("zł");

        if (endIndex != -1) {
            String trimmedValue = changeValue.substring(0, endIndex );

            trimmedValue =trimmedValue.replace(",",".");
            trimmedValue= trimmedValue.replace(" ", "");

            double value =Double.parseDouble(trimmedValue);
            return calculateValueByWeight(value,weight,true);
        }

        throw new RuntimeException("Error: Change couldn't be found");

    }



    private LocalDateTime getDataDate( Document document) {

        Elements timeDiv = document.select("div.chart-block-first-left");
        String time =  timeDiv.select("h4").text();

        Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d" +
                "\\s\\d\\d:\\d\\d:\\d\\d");
        Matcher matcher = pattern.matcher(time);

        if (matcher.find()) {

            String value = matcher.group(0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            return LocalDateTime.parse(value,formatter);
        } else {
            throw new RuntimeException("Error while parsing Date");
        }

    }

    public void setEnv(Environment env) {
        this.env = env;
    }

}
