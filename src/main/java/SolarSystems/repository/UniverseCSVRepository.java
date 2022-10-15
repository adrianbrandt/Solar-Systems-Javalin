package SolarSystems.repository;

import SolarSystems.model.Planet;
import SolarSystems.model.PlanetSystem;
import SolarSystems.model.Star;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

// CSV Repo
public class UniverseCSVRepository implements IUniverseRepository, Runnable {

    // File to Read From and Store to
    private final String file = "planets_100.csv";

    private HashMap<String, PlanetSystem> PlanetSystemHash = new HashMap<>();

    public UniverseCSVRepository(String filePath) {
        readFromCsv(new File(filePath));

    }

    // Reading From CSV File
    public void readFromCsv(File filePath) {

        String line;
        try (BufferedReader bufferRead = new BufferedReader(new FileReader(filePath))) {

            while ((line = bufferRead.readLine()) != null) {

                String[] splitter = line.split(",");

                if (!(PlanetSystemHash.containsKey(splitter[0]))) {

                    Star starScan = new Star(splitter[2], Double.parseDouble(splitter[3]), Double.parseDouble(splitter[4]),
                            Double.parseDouble(splitter[5]), splitter[6]);

                    PlanetSystem systemScan = new PlanetSystem(splitter[0], starScan, splitter[1]);

                    PlanetSystemHash.put(splitter[0], systemScan);
                }

                Planet planetScan = new Planet(splitter[7], Double.parseDouble(splitter[8]), Double.parseDouble(splitter[9]),
                        Double.parseDouble(splitter[10]), Double.parseDouble(splitter[11]), Double.parseDouble(splitter[12]),
                        PlanetSystemHash.get(splitter[0]).getCenterStar(), splitter[13]);

                PlanetSystemHash.get(splitter[0]).addPlanet(planetScan);

            }

            // Writing to Same File for "Storage Effect"
            writeToCsv(file, new ArrayList<PlanetSystem>(PlanetSystemHash.values()));


        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        } catch (IOException ioexc) {
            System.out.println(ioexc.getLocalizedMessage());
        }
    }

    // Writing to CSV
    public void writeToCsv(String filePath, ArrayList<PlanetSystem> planetsystem) {

        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {


                    for (PlanetSystem planetSystem : planetsystem) {

                        for (Planet planet : planetSystem.getPlanets()) {


                            bufferedWriter.write(planetSystem.getName() + "," + planetSystem.getPictureUrl() + "," + planetSystem.getCenterStar().getName()
                                    + "," + planetSystem.getCenterStar().getMass() + "," + planetSystem.getCenterStar().getRadius() + ","
                                    + planetSystem.getCenterStar().getEffectiveTemperature() + "," + planetSystem.getCenterStar().getPictureUrl()
                                    + "," + planet.getName() + "," + planet.getMass() + "," + planet.getRadius() + "," + planet.getSemiMajorAxis() + ","
                                    + planet.getEccentricity() + "," + planet.getOrbitalPeriod() + "," + planet.getPictureUrl());


                            bufferedWriter.newLine();
                        }


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        writeThread.start();
    }
    

    @Override
    public PlanetSystem getPlanetSystem(String planetSystemName) {
        for (PlanetSystem planetSystem : PlanetSystemHash.values()) {
            if (planetSystem.getName().equals(planetSystemName)) {
                return planetSystem;
            }
        }

        return null;
    }

    @Override
    public ArrayList<PlanetSystem> getPlanetSystems() {
        return new ArrayList<PlanetSystem>(PlanetSystemHash.values());
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
    public void delPlanet(String planetSystem, String planet) {
        PlanetSystemHash.get(planetSystem).getPlanets().removeIf(obj -> obj.getName().equals(planet));
        writeToCsv(file, new ArrayList<PlanetSystem>(PlanetSystemHash.values()));
    }

    @Override
    public void addPlanet(String planetSystem, String name, Double mass, Double radius, Double semiaxis, Double orbital, Double eccen, String pictureurl){
        Planet planet = new Planet(name, mass, radius, semiaxis, orbital, eccen,(PlanetSystemHash.get(planetSystem).getCenterStar()), pictureurl);
        PlanetSystemHash.get(planetSystem).addPlanet(planet);
        writeToCsv(file, new ArrayList<PlanetSystem>(PlanetSystemHash.values()));
    }

    @Override
    public void updPlanet(String planetSystem, String name, Double mass, Double radius, Double semiaxis, Double orbital, Double eccen, String pictureurl, String prev) {
        Planet planet = new Planet(name, mass, radius, semiaxis, orbital, eccen,(PlanetSystemHash.get(planetSystem).getCenterStar()), pictureurl);
        PlanetSystemHash.get(planetSystem).getPlanets().removeIf(obj -> obj.getName().equals(prev));
        PlanetSystemHash.get(planetSystem).addPlanet(planet);
        writeToCsv(file, new ArrayList<PlanetSystem>(PlanetSystemHash.values()));
    }

    @Override
    public void run() {

    }
}
