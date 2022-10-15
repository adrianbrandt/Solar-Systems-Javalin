package SolarSystems.controller;

import io.javalin.http.Context;
import SolarSystems.model.Planet;
import SolarSystems.repository.IUniverseRepository;
import SolarSystems.repository.UniverseCSVRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class PlanetController {
    private IUniverseRepository universityRepository;
    private UniverseCSVRepository universeCSVRepository;

    public PlanetController(IUniverseRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    // Fetching All Planets in Current Solar System With Sorting Functionality
    public void getPlanets(Context context) {
        String planetSystemName = context.pathParam("planet-system-id");
        String sortBy = context.queryParam("sort_by");

        ArrayList<Planet> planets = universityRepository.getPlanets(planetSystemName);

        // Sorting Function
        if (sortBy != null) {
            switch (sortBy) {
                case "name":
                    Collections.sort(planets);
                    break;
                case "mass":
                    planets.sort((o1, o2) -> (int) (o1.getMass() - o2.getMass()));
                    break;
                case "radius":
                    planets.sort((o1, o2) -> {
                        if (o1.getRadius() < o2.getRadius())
                            return -1;
                        else if(o1.getRadius() > o2.getRadius())
                            return 1;
                        return 0;
                    });
                    break;
            }
        }

        context.json(planets);
    }

    // Get Planet by Planet ID and Planet System ID
    public void getPlanet(Context context) {
        String planetSystemName = context.pathParam("planet-system-id");
        String planetName = context.pathParam("planet-id");

        context.json(universityRepository.getPlanet(planetSystemName, planetName));
    }

    // Delete Planet
    public void delPlanet(Context context){
        String planetSystem = context.pathParam(":planet-system-id");
        String planet = context.pathParam(":planet-id");
        universityRepository.delPlanet(planetSystem, planet);

        String pth = "/planet-systems/" + planetSystem;
        context.redirect(pth);

    }

    // Adding Planet
    public void addPlanet(Context context){
        String name = context.formParam("name");
        Double mass = Double.valueOf(Objects.requireNonNull(context.formParam("mass")));
        Double radius = Double.valueOf(Objects.requireNonNull(context.formParam("radius")));
        Double semiAxis = Double.valueOf(Objects.requireNonNull(context.formParam("semiMajorAxis")));
        Double eccentricity = Double.valueOf(Objects.requireNonNull(context.formParam("eccentricity")));
        Double orbitalPeriod = Double.valueOf(Objects.requireNonNull(context.formParam("orbitalPeriod")));
        String imgUrl = context.formParam("pictureUrl");
        String pSystem = context.pathParam(":planet-system-id");
        universityRepository.addPlanet(pSystem, name, mass, radius, semiAxis, eccentricity, orbitalPeriod, imgUrl);

        String sti = "/planet-systems/" + pSystem;
        context.redirect(sti);
    }

    // Updating Planet Info
    public void updPlanet(Context context){
        String name = context.formParam("name");
        Double mass = Double.valueOf(Objects.requireNonNull(context.formParam("mass")));
        Double radius = Double.valueOf(Objects.requireNonNull(context.formParam("radius")));
        Double semiAxis = Double.valueOf(Objects.requireNonNull(context.formParam("semiMajorAxis")));
        Double eccentricity = Double.valueOf(Objects.requireNonNull(context.formParam("eccentricity")));
        Double orbitalPeriod = Double.valueOf(Objects.requireNonNull(context.formParam("orbitalPeriod")));
        String imgUrl = context.formParam("pictureUrl");
        String prev = context.pathParam(":planet-id");
        String pSystem = context.pathParam(":planet-system-id");
        universityRepository.updPlanet(pSystem, name, mass, radius, semiAxis, eccentricity, orbitalPeriod, imgUrl, prev);

        String sti = "/planet-systems/" + pSystem;
        context.redirect(sti);
    }
}
