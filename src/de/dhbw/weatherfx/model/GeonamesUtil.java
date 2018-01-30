package de.dhbw.weatherfx.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author behrends
 */
public class GeonamesUtil {


    // Simple wrapper class for JSON structure of GeonamesUtil search result to use with GSON
    private class GeonamesSearchResult {
        public City[] geonames;
    }


    public static List<City> getCities(String query) {
        try{
            String user = Props.geonamesUser == null ? "demo" : Props.geonamesUser;
            String queryString = "username=" + user + "&cities=cities1000&type=json&name_startsWith=" + query;
            URL url = new URI("http", "api.geonames.org", "/search", queryString, null).toURL();
            Reader reader = new InputStreamReader(url.openStream());
            City[] cities = (new Gson()).fromJson(reader, GeonamesSearchResult.class).geonames;
            return Arrays.asList(cities);
        } catch (URISyntaxException | IOException e) {
            System.err.println("An error occurred while requesting data from geonames API.");
            Logger.getLogger(GeonamesUtil.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception x) {
            // TODO: handle case where daily limit of 30000 credits at geonames.org is reached
            x.printStackTrace();
        }
        return null;
    }
}

