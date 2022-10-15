package SolarSystems.repository;

import SolarSystems.model.Planet;
import SolarSystems.model.PlanetSystem;
import java.util.ArrayList;

// Interface for UniverseRepo
// This is Where Interface Functions are Initialized
public interface IUniverseRepository {
    PlanetSystem getPlanetSystem(String planetSystemName);
    ArrayList<PlanetSystem> getPlanetSystems();
    Planet getPlanet(String planetSystemName, String planet);
    ArrayList<Planet> getPlanets(String planetSystemName);
    void delPlanet(String planetSystemName, String planet);
    void addPlanet(String planetSystem, String name, Double mass, Double radius, Double semiaxis, Double orbital, Double eccen, String pictureurl);
    void updPlanet(String planetSystem, String name, Double mass, Double radius, Double semiaxis, Double orbital, Double eccen, String pictureurl, String prev);
}

