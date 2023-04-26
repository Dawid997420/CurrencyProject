package com.example.CurrencyProject.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryCurrencyMapper {


    private static final Map<String, String> COUNTRY_CURRENCY_MAP = new HashMap<>();

    static {

        COUNTRY_CURRENCY_MAP.put("THB", "Tajlandia");
        COUNTRY_CURRENCY_MAP.put("USD", "Stany Zjednoczone");
        COUNTRY_CURRENCY_MAP.put("AUD", "Australia");
        COUNTRY_CURRENCY_MAP.put("HKD", "Hongkong");
        COUNTRY_CURRENCY_MAP.put("CAD", "Kanada");
        COUNTRY_CURRENCY_MAP.put("NZD", "Nowa Zelandia");
        COUNTRY_CURRENCY_MAP.put("SGD", "Singapur");
        COUNTRY_CURRENCY_MAP.put("EUR", "EUGiW");
        COUNTRY_CURRENCY_MAP.put("HUF", "Węgry");
        COUNTRY_CURRENCY_MAP.put("CHF", "Szwajcaria");
        COUNTRY_CURRENCY_MAP.put("GBP", "Wielka Brytania");
        COUNTRY_CURRENCY_MAP.put("UAH", "Ukraina");
        COUNTRY_CURRENCY_MAP.put("JPY", "Japonia");
        COUNTRY_CURRENCY_MAP.put("CZK", "Czechy");
        COUNTRY_CURRENCY_MAP.put("DKK", "Dania");
        COUNTRY_CURRENCY_MAP.put("ISK", "Islandia");
        COUNTRY_CURRENCY_MAP.put("NOK", "Norwegia");
        COUNTRY_CURRENCY_MAP.put("SEK", "Szwecja");
        COUNTRY_CURRENCY_MAP.put("RON", "Rumunia");
        COUNTRY_CURRENCY_MAP.put("BGN", "Bułgaria");
        COUNTRY_CURRENCY_MAP.put("TRY", "Turcja");
        COUNTRY_CURRENCY_MAP.put("ILS", "Izrael");
        COUNTRY_CURRENCY_MAP.put("CLP", "Chile");
        COUNTRY_CURRENCY_MAP.put("PHP", "Filipiny");
        COUNTRY_CURRENCY_MAP.put("MXN", "Meksyk"); //////

        COUNTRY_CURRENCY_MAP.put("ZAR", "RPA");
        COUNTRY_CURRENCY_MAP.put("BRL", "Brazylia");
        COUNTRY_CURRENCY_MAP.put("MYR", "Malezja");
        COUNTRY_CURRENCY_MAP.put("IDR", "Indonezja");
        COUNTRY_CURRENCY_MAP.put("INR", "Indie");
        COUNTRY_CURRENCY_MAP.put("KRW", "Korea Południowa");
        COUNTRY_CURRENCY_MAP.put("CNY", "Chiny");
        COUNTRY_CURRENCY_MAP.put("XDR", "Międzynarodowy Fundusz Walutowy");
        COUNTRY_CURRENCY_MAP.put("AFN", "Afganistan");
        COUNTRY_CURRENCY_MAP.put("MGA", "Madagaskar");
        COUNTRY_CURRENCY_MAP.put("PAB", "Panama");
        COUNTRY_CURRENCY_MAP.put("ETB", "Etiopia");
        COUNTRY_CURRENCY_MAP.put("VES", "Wenezuela");
        COUNTRY_CURRENCY_MAP.put("BOB", "Boliwia");
        COUNTRY_CURRENCY_MAP.put("CRC", "Kostaryka");
        COUNTRY_CURRENCY_MAP.put("SVC", "Salwador");
        COUNTRY_CURRENCY_MAP.put("NIO", "Nikaragua");
        COUNTRY_CURRENCY_MAP.put("GMD", "Gambia");
        COUNTRY_CURRENCY_MAP.put("MKD", "Macedonia Północna");
        COUNTRY_CURRENCY_MAP.put("DZD", "Algieria");
        COUNTRY_CURRENCY_MAP.put("BHD", "Bahrajn");
        COUNTRY_CURRENCY_MAP.put("IQD", "Irak");
        COUNTRY_CURRENCY_MAP.put("JOD", "Jordania");
        COUNTRY_CURRENCY_MAP.put("KWD", "Kuwejt");
        COUNTRY_CURRENCY_MAP.put("LYD", "Libia");
        COUNTRY_CURRENCY_MAP.put("RSD", "Serbia");
        COUNTRY_CURRENCY_MAP.put("TND", "Tunezja");
        COUNTRY_CURRENCY_MAP.put("MAD", "Maroko");
        COUNTRY_CURRENCY_MAP.put("AED", "Zjednoczone Emiraty Arabskie");
        COUNTRY_CURRENCY_MAP.put("STN", "Wyspy Świętego Tomasza i Książęca");
        COUNTRY_CURRENCY_MAP.put("BSD", "Bahamy");
        COUNTRY_CURRENCY_MAP.put("BBD", "Barbados");

        COUNTRY_CURRENCY_MAP.put("BZD", "Belize");
        COUNTRY_CURRENCY_MAP.put("BND", "Brunei");
        COUNTRY_CURRENCY_MAP.put("FJD", "Fidżi");
        COUNTRY_CURRENCY_MAP.put("GYD", "Gujana");
        COUNTRY_CURRENCY_MAP.put("JMD", "Jamajka");
        COUNTRY_CURRENCY_MAP.put("LRD", "Liberia");
        COUNTRY_CURRENCY_MAP.put("NAD", "Namibia");
        COUNTRY_CURRENCY_MAP.put("SRD", "Surinam");
        COUNTRY_CURRENCY_MAP.put("TTD", "Trynidad i Tobago");
        COUNTRY_CURRENCY_MAP.put("XCD", "Karaiby Wschodnie");
        COUNTRY_CURRENCY_MAP.put("SBD", "Wyspy Salomona");
        COUNTRY_CURRENCY_MAP.put("ZWL", "Zimbabwe");
        COUNTRY_CURRENCY_MAP.put("VND", "Wietnam");
        COUNTRY_CURRENCY_MAP.put("AMD", "Armenia");
        COUNTRY_CURRENCY_MAP.put("CVE", "Republika Zielonego Przylądka");
        COUNTRY_CURRENCY_MAP.put("AWG", "Aruba");
        COUNTRY_CURRENCY_MAP.put("BIF", "Burundi");
        COUNTRY_CURRENCY_MAP.put("XOF", "Benin");
        COUNTRY_CURRENCY_MAP.put("XAF", "Kamerun");
        COUNTRY_CURRENCY_MAP.put("XPF", "Francuskie Terytoria Pacyfiku");
        COUNTRY_CURRENCY_MAP.put("DJF", "Dżibuti");
        COUNTRY_CURRENCY_MAP.put("GNF", "Gwinea");
        COUNTRY_CURRENCY_MAP.put("KMF", "Komory");
        COUNTRY_CURRENCY_MAP.put("CDF", "Demokratyczna Republika Konga");
        COUNTRY_CURRENCY_MAP.put("RWF", "Rwanda");
        COUNTRY_CURRENCY_MAP.put("EGP", "Egipt");
        COUNTRY_CURRENCY_MAP.put("GIP", "Gibraltar");
        COUNTRY_CURRENCY_MAP.put("LBP", "Liban");
        COUNTRY_CURRENCY_MAP.put("SSP", "Sudan Południowy");
        COUNTRY_CURRENCY_MAP.put("SDG", "Sudan");
        COUNTRY_CURRENCY_MAP.put("SYP", "Syria");
        COUNTRY_CURRENCY_MAP.put("GHS", "Ghana");
        COUNTRY_CURRENCY_MAP.put("HTG", "Haiti");
        COUNTRY_CURRENCY_MAP.put("PYG", "Paragwaj");
        COUNTRY_CURRENCY_MAP.put("ANG", "Antyle Holenderskie");
        COUNTRY_CURRENCY_MAP.put("PGK", "Papua-Nowa Gwinea");
        COUNTRY_CURRENCY_MAP.put("LAK", "Laos");
        COUNTRY_CURRENCY_MAP.put("MWK", "Malawi");
        COUNTRY_CURRENCY_MAP.put("ZMW", "Zambia");
        COUNTRY_CURRENCY_MAP.put("AOA", "Angola");
        COUNTRY_CURRENCY_MAP.put("MMK", "Mjanma (Birma)");
        COUNTRY_CURRENCY_MAP.put("GEL", "Gruzja");
        COUNTRY_CURRENCY_MAP.put("MDL", "Mołdawia");
        COUNTRY_CURRENCY_MAP.put("ALL", "Albania");

        COUNTRY_CURRENCY_MAP.put("HNL", "Honduras");
        COUNTRY_CURRENCY_MAP.put("SLE", "Sierra Leone");
        COUNTRY_CURRENCY_MAP.put("SZL", "Eswatini");
        COUNTRY_CURRENCY_MAP.put("LSL", "Lesotho");
        COUNTRY_CURRENCY_MAP.put("AZN", "Azerbejdżan");
        COUNTRY_CURRENCY_MAP.put("MZN", "Mozambik");
        COUNTRY_CURRENCY_MAP.put("NGN", "Nigeria");
        COUNTRY_CURRENCY_MAP.put("ERN", "Erytrea");
        COUNTRY_CURRENCY_MAP.put("TWD", "Tajwan");
        COUNTRY_CURRENCY_MAP.put("TMT", "Turkmenistan");
        COUNTRY_CURRENCY_MAP.put("MRU", "Mauretania");
        COUNTRY_CURRENCY_MAP.put("TOP", "Tonga");
        COUNTRY_CURRENCY_MAP.put("MOP", "Makau");
        COUNTRY_CURRENCY_MAP.put("ARS", "Argentyna");
        COUNTRY_CURRENCY_MAP.put("DOP", "Dominikana");
        COUNTRY_CURRENCY_MAP.put("COP", "Kolumbia");
        COUNTRY_CURRENCY_MAP.put("CUP", "Kuba");
        COUNTRY_CURRENCY_MAP.put("UYU", "Urugwaj");
        COUNTRY_CURRENCY_MAP.put("BWP", "Botswana");
        COUNTRY_CURRENCY_MAP.put("GTQ", "Gwatemala");
        COUNTRY_CURRENCY_MAP.put("IRR", "Iran");
        COUNTRY_CURRENCY_MAP.put("YER", "Jemen");
        COUNTRY_CURRENCY_MAP.put("QAR", "Katar");
        COUNTRY_CURRENCY_MAP.put("OMR", "Oman");
        COUNTRY_CURRENCY_MAP.put("SAR", "Arabia Saudyjska");
        COUNTRY_CURRENCY_MAP.put("KHR", "Kambodża");
        COUNTRY_CURRENCY_MAP.put("BYN", "Białoruś");
        COUNTRY_CURRENCY_MAP.put("RUB", "Rosja");
        COUNTRY_CURRENCY_MAP.put("LKR", "Sri Lanka");
        COUNTRY_CURRENCY_MAP.put("MVR", "Malediwy");
        COUNTRY_CURRENCY_MAP.put("MUR", "Mauritius");
        COUNTRY_CURRENCY_MAP.put("NPR", "Nepal");
        COUNTRY_CURRENCY_MAP.put("PKR", "Pakistan");
        COUNTRY_CURRENCY_MAP.put("SCR", "Seszele");
        COUNTRY_CURRENCY_MAP.put("PEN", "Peru");
        COUNTRY_CURRENCY_MAP.put("KGS", "Kirgistan");
        COUNTRY_CURRENCY_MAP.put("TJS", "Tadżykistan");
        COUNTRY_CURRENCY_MAP.put("UZS", "Uzbekistan");
        COUNTRY_CURRENCY_MAP.put("KES", "Kenia");
        COUNTRY_CURRENCY_MAP.put("SOS", "Somalia");
        COUNTRY_CURRENCY_MAP.put("TZS", "Tanzania");
        COUNTRY_CURRENCY_MAP.put("UGX", "Uganda");
        COUNTRY_CURRENCY_MAP.put("BDT", "Bangladesz");
        COUNTRY_CURRENCY_MAP.put("WST", "Samoa");
        COUNTRY_CURRENCY_MAP.put("KZT", "Kazachstan");
        COUNTRY_CURRENCY_MAP.put("MNT", "Mongolia");
        COUNTRY_CURRENCY_MAP.put("VUV", "Vanuatu");
        COUNTRY_CURRENCY_MAP.put("BAM", "Bośnia i Hercegowina");


    }

    public static String getCountryName(String currencyCode) {
        return COUNTRY_CURRENCY_MAP.getOrDefault(currencyCode, "Unknown");
    }


}
