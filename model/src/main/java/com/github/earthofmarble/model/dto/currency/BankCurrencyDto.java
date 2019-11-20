package com.github.earthofmarble.model.dto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.earthofmarble.utility.mapper.annotation.Convertible;
import com.github.earthofmarble.utility.mapper.annotation.ReferencedField;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by earthofmarble on Nov, 2019
 */
@Convertible
public class BankCurrencyDto {
    @JsonProperty("Cur_ID")
    private Integer id;
    @JsonProperty("Date")
    private Timestamp date;
    @JsonProperty("Cur_Abbreviation")
    private String abbreviation;
    @JsonProperty("Cur_Scale")
    private Integer scale;
    @JsonProperty("Cur_Name")
    private String name;
    @JsonProperty("Cur_OfficialRate")
    private Double rate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "BankCurrencyDto{" +
                "id=" + id +
                ", date=" + date +
                ", abbreviation='" + abbreviation + '\'' +
                ", scale=" + scale +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}
