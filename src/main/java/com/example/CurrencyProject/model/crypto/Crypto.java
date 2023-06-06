package com.example.CurrencyProject.model.crypto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crypto {



    private String name;

    private String id;

    private double price;

    private BigInteger marketCap;

    private long totalVolume;

    private double percentChange;

    private String currency;

    private LocalDateTime date ;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crypto crypto = (Crypto) o;
        return Double.compare(crypto.price, price) == 0 && totalVolume == crypto.totalVolume && Double.compare(crypto.percentChange, percentChange) == 0 && Objects.equals(name, crypto.name) && Objects.equals(id, crypto.id) && Objects.equals(marketCap, crypto.marketCap) && Objects.equals(currency, crypto.currency) && dateDifferenceIsLessThanOneMinute(date, crypto.date);
    }

    private boolean dateDifferenceIsLessThanOneMinute(LocalDateTime d1 , LocalDateTime d2) {

       boolean year = d1.getYear() == d2.getYear();
       boolean month = d1.getMonth() == d2.getMonth();
       boolean day = d1.getDayOfYear() == d2.getDayOfYear();
       boolean hour = d1.getHour() == d2.getHour();
       boolean minutes = d1.getMinute() == d2.getMinute();

        return year && month && day && hour && minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, price, marketCap, totalVolume, percentChange, currency, date);
    }

}
