package com.github.earthofmarble.model.dto.currency;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class CurrencyDto implements IDto {

    private Integer id;
    private String name;
    private Integer scale;
    private Double rate;
    private String abbreviation;

    public CurrencyDto() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scale=" + scale +
                ", rate=" + rate +
                ", abbreviation='" + abbreviation + '\'' +
                '}';
    }
}
