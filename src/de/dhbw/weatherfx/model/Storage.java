package de.dhbw.weatherfx.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by behrends on 26/04/16.
 */
public class Storage {
    public static void saveCitiesToFile(City[] cities) {
        // each time we save cities to disk, the file contents get replaced with new data
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("cities.data");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cities);
        } catch (Exception x) {
            System.err.println("Something went wrong while saving cities to file");
        }
    }

    public static List<City> readCitiesFromFile() {
        List<City> cities = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("cities.data");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            City[] cityArray = (City[])objectInputStream.readObject();
            cities = Arrays.asList(cityArray);
        }
        catch(Exception x) {
            City[] citiesArray = new City[]{new City("Basel", "Switzerland"), new City("Freiburg", "Germany")};
            cities = Arrays.asList(citiesArray);
            System.err.println("Error while reading cities from file, using default cities.");
            saveCitiesToFile(citiesArray);
        }
        finally {
            return cities;
        }

    }
}
