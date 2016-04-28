package de.dhbw.weatherfx.model;

import java.io.Serializable;

/**
 * Created by behrends on 08/04/16.
 */
public class City implements Serializable {
    private String name, countryName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return name + ", " + countryName;
    }
}
