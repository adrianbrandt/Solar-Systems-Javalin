package SolarSystems.controller;

import io.javalin.http.Context;
import SolarSystems.repository.IUniverseRepository;

public class PlanetSystemController {
    private IUniverseRepository universityRepository;

    public PlanetSystemController(IUniverseRepository universeRepository) {
        this.universityRepository = universeRepository;
    }

    // Get All Planet Systems
    public void getAllPlanetSystems(Context context) {
        context.json(universityRepository.getPlanetSystems());
    }

    // Get Planet System Info by System ID
    public void getPlanetSystem(Context context) {
        String planetSystemName = context.pathParam("planet-system-id");
        context.json(universityRepository.getPlanetSystem(planetSystemName));
    }
}