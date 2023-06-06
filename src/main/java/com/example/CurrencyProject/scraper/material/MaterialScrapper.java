package com.example.CurrencyProject.scraper.material;

import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.utils.MathMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;


@Component
public class MaterialScrapper {


    @Autowired
    private Environment env;

    private final MathMapper mathMapper = new MathMapper();

    private String appUrl ;

    public MaterialScrapper(@Value("${api.material.url}") String appUrl) {
        this.appUrl = appUrl;
    }

    public MaterialScrapper(){}


    @Cacheable(cacheNames = "AllMaterials")
    public List<Material> getAllMaterials() throws IOException {

        List<Material> materialList = new ArrayList<>();
        List<String> names = getAllMaterialsNames(getSite());

        Document document = getSite();

        for ( int i = 0 ; i < names.size() ; i++) {
           materialList.add( buildMaterial(document,i));
        }

        return materialList;
    }


    private Material buildMaterial(Document document,int number){

     return Material.builder().name(getAllMaterialsNames(document).get(number))
                 .id(getAllMaterialsId().get(number))
                .data(getAllMaterialsDates(document).get(number))
                .unit(getAllMaterialsUnits(document).get(number))
                .price(getAllMaterialsPrices(document).get(number))
                .change(getAllMaterialsChanges(document).get(number))
                .percentChange(getAllMaterialsPercentChanges(document).get(number))
                .currency(getAllMaterialsCurrencies(document).get(number)).build();
    }


    public Document getSite() throws IOException {

        setAppUrl("/surowce/notowania");
        return Jsoup.connect(appUrl).get();
    }


    private void setAppUrl(String value) {
        String url = this.env.getProperty("api.material.url");
        this.appUrl = url + value;
    }


    private List<String> getAllMaterialsId() {

        List<String> ids = new ArrayList<>();

        ids.add("ROPA");ids.add("ZLOTO");ids.add("MIEDZ");
        ids.add("SREBRO");ids.add("PALLAD");ids.add("PLATYNA");ids.add("NIKIEL");
        ids.add("ALUMINIUM");ids.add("OLOW");ids.add("CYNK");ids.add("ROPA_WTI");
        ids.add("MIEDZ_COMEX");ids.add("BAWELNA");
        ids.add("BENZYNA");
        ids.add("CANOLA");ids.add("CUKIER");ids.add("DIESEL");
        ids.add("DREWNO");ids.add("GAZ_ZIEMNY");
        ids.add("KAKAO");ids.add("KAUCZUK");
        ids.add("KAWA");ids.add("KUKURYDZA");ids.add("MLEKO");
        ids.add("OLEJ_OPALOWY");ids.add("OLEJ_PALMOWY");
        ids.add("OLEJ_SOJOWY");
        ids.add("PSZENICA");ids.add("RYZ");ids.add("RZEPAK");
        ids.add("SOJA");ids.add("SOKPOMARANCZOWY");
        ids.add("SRUTA_SOJOWA");ids.add("WIEPRZOWINA");ids.add("WOLOWINA");

        return ids;
    }

    private List<String> getAllMaterialsNames(Document document) {

        List<String> allNames = new ArrayList<>();

        Elements elements = document.select("tbody > tr > .textNowrap.colWalor");

        for (Element element : elements) {
           allNames.add( element.text() );
        }

        return allNames;
    }

    private List<String> getAllMaterialsCurrencyAndUnit(Document document) {

        List<String> currenciesAndUnits = new ArrayList<>();

        Elements elements = document.select("tbody > tr > .colWaluta");

        for (Element element : elements) {
            currenciesAndUnits.add( element.text() );
        }

        return currenciesAndUnits;
    }



    private List<Double> getAllMaterialsPrices(Document document) {

        List<Double> prices = new ArrayList<>();

        Elements elements = document.select(".change.colKurs");

        for (Element element : elements) {

              String elementToParse = element.text();

             elementToParse= elementToParse.replace(",",".");
             elementToParse = elementToParse.replace(" ","");

            prices.add(Double.parseDouble(elementToParse));
        }

        return prices;
    }


    private List<Double> getAllMaterialsChanges(Document document) {

        List<Double> changes = new ArrayList<>();

        Elements elements = document.select(".change.colZmiana");

        for (Element element : elements) {
            String elementToParse = element.text().replace(",",".");
            elementToParse = elementToParse.replace(" ","");

            changes.add( Double.parseDouble(elementToParse) );
        }
        return changes;
    }

    private List<Double> getAllMaterialsPercentChanges(Document document) {

        List<Double> percentChanges = new ArrayList<>();

        Elements elements = document.select(".change.colZmianaProcentowa");

        for (Element element : elements) {
            String percent = element.text().replace(",",".");
            percent = percent.replace(" ","");
            percent = percent.replace("%","");

            percentChanges.add(Double.valueOf(percent));
        }

        return percentChanges;
    }





    private List<String> getAllMaterialsCurrencies(Document document){

       List<String> currenciesAndUnits = getAllMaterialsCurrencyAndUnit(document);
       List<String> currencies= new ArrayList<>();

       for ( String str : currenciesAndUnits) {

           String[] parts = str.split("/");
           currencies.add(parts[0]);
       }
       return currencies;
    }

    private List<String> getAllMaterialsUnits(Document document){

        List<String> currenciesAndUnits = getAllMaterialsCurrencyAndUnit(document);
        List<String> currencies= new ArrayList<>();

        for ( String str : currenciesAndUnits) {

            String[] parts = str.split("/");
            currencies.add(parts[1]);
        }
        return currencies;
    }



    private List<LocalDateTime> getAllMaterialsDates(Document document) {

        List<LocalDateTime> dates = new ArrayList<>();

        Elements elements = document.select("tbody > tr > .colAktualizacja");

        for (Element element : elements) {

            dates.add(setDate(element.text()));
        }
        return dates;
    }


    private LocalDateTime setDate(String data) {

        LocalDateTime today = LocalDateTime.now();

        data = "2023 "+ data;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");


        LocalDateTime dateTime =  LocalDateTime.parse(data, formatter);

        if ( dateTime.isAfter(today) ) {
           dateTime =  dateTime.withYear(today.minusYears(1L).getYear());
        }
        return dateTime;
    }




    public void setEnv(Environment env) {
        this.env = env;
    }


}
