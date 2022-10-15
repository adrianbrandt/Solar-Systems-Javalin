package SolarSystems.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import SolarSystems.model.Planet;
import SolarSystems.model.PlanetSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// JSON Repo. Currently not in use
public class UniverseJSONRepository implements IUniverseRepository {

    private ArrayList<PlanetSystem> planetReads = new ArrayList<>();

    public UniverseJSONRepository(String filnavn) {
        PlanetListReader(filnavn);
    }

    public void PlanetListWriter(String filePath, List<PlanetSystem> planetSystems){

        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, planetSystems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  PlanetListReader(String filePath) {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);

        try {
            PlanetSystem[] planetSystemsFromFile = objectMapper.readValue(file, PlanetSystem[].class);

            planetReads.addAll(Arrays.asList(planetSystemsFromFile));

        } catch (IOException e) {
            e.printStackTrace();
        }

        PlanetListWriter(filePath, planetReads);

    }

    @Override
    public PlanetSystem getPlanetSystem(String planetSystemName) {
        for (PlanetSystem planetSystem : planetReads) {
            if (planetSystem.getName().equals(planetSystemName)) {
                return planetSystem;
            }
        }

        return null;
    }

    @Override
    public ArrayList<PlanetSystem> getPlanetSystems() {
        return new ArrayList<>(planetReads);
    }

    @Override
    public Planet getPlanet(String planetSystemName, String planetName) {
        return getPlanetSystem(planetSystemName).getPlanet(planetName);
    }

    @Override
    public ArrayList<Planet> getPlanets(String planetSystemName) {
        return getPlanetSystem(planetSystemName).getPlanets();
    }

    @Override
    public void delPlanet(String planetSystemName, String planet) {

    }

    @Override
      public void addPlanet(String planetSystem, String name, Double mass, Double radius, Double semiaxis, Double orbital, Double eccen, String pictureurl) {

    }

    @Override
    public void updPlanet(String planetSystem, String name, Double mass, Double radius, Double semiaxis, Double orbital, Double eccen, String pictureurl, String prev) {

    }

}
