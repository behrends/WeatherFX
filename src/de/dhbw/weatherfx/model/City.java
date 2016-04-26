package de.dhbw.weatherfx.model;

import java.io.Serializable;

/**
 * Created by behrends on 08/04/16.
 */
public class City implements Serializable {
    private String name;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
