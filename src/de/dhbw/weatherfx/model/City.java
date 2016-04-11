package de.dhbw.weatherfx.model;

/**
 * Created by behrends on 08/04/16.
 */
public class City {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
