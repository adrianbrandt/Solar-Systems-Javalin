package SolarSystems;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;
import SolarSystems.controller.PlanetController;
import SolarSystems.controller.PlanetSystemController;
import SolarSystems.repository.UniverseCSVRepository;

// Main Application for Web Based Interface
public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7001);

        app.config.enableWebjars();

        // Main Page of Website
        app.before("/", ctx -> ctx.redirect("/planet-systems"));

        // Using Json instead of CSV. Uncommenting this will need work on the JSON repo.
        //UniverseJSONRepository universeJSONRepository = new UniverseJSONRepository("planets_100.json");


        // Creating Repository using CSV. Only Planets_100 are used for this task.
        UniverseCSVRepository universeCSVRepository = new UniverseCSVRepository("planets_100.csv");
        // Starting Controller for Planet System and Planets
        PlanetSystemController planetSystemController = new PlanetSystemController(universeCSVRepository);
        PlanetController planetController = new PlanetController(universeCSVRepository);

        // Path to all Planet Systems and their components
        app.get("/planet-systems", new VueComponent("planet-system-overview"));
        app.get("/planet-systems/:planet-system-id", new VueComponent("planet-system-detail"));

        // Path to Planets, Create Planet and Update Planet Components
        app.get("/planet-systems/:planet-system-id/planets/:planet-id", new VueComponent("planet-detail"));
        app.get("/planet-systems/:planet-system-id/createplanet", new VueComponent("planet-create"));
        app.get("/planet-systems/:planet-system-id/planets/:planet-id/update", new VueComponent("planet-update"));

        // Setting Controllers to Their Paths - PlanetSystems
        app.get("api/planet-systems", planetSystemController::getAllPlanetSystems);
        app.get("api/planet-systems/:planet-system-id", planetSystemController::getPlanetSystem);

        // Setting Controllers to Their Paths - Planets
        app.get("/api/planet-systems/:planet-system-id/planets", planetController::getPlanets);
        app.get("/api/planet-systems/:planet-system-id/planets/:planet-id", planetController::getPlanet);

        // Setting Controllers to Their Paths - Planet Edits
        app.get("/api/planet-systems/:planet-system-id/planets/:planet-id/delete", planetController::delPlanet);
        app.post("/api/planet-systems/:planet-system-id/createplanet",planetController::addPlanet);
        app.post("/api/planet-systems/:planet-system-id/planets/:planet-id/update", planetController::updPlanet);
    }
}
