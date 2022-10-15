package SolarSystems.model;

public abstract class NaturalSatellite extends CelestialBody {
    private double semiMajorAxis, eccentricity, orbitalPeriod;
    private CelestialBody centralCelestialBody;

    // Global Unit to be Used
    public static final double ASTRONOMICAL_UNITS_IN_KM = 149597871;

    // Constructors
    public NaturalSatellite(){}

    public NaturalSatellite(String name, double mass, double radius, double semiMajorAxis, double eccentricity, double orbitalPeriod, CelestialBody centralCelestialBody) {
        super(name, mass, radius);
        this.semiMajorAxis = semiMajorAxis;
        this.eccentricity = eccentricity;
        this.orbitalPeriod = orbitalPeriod;
        this.centralCelestialBody = centralCelestialBody;
    }

    public NaturalSatellite(String name, double mass, double radius, double semiMajorAxis, double eccentricity, double orbitalPeriod, CelestialBody centralCelestialBody, String pictureUrl) {
        super(name, mass, radius, pictureUrl);
        this.semiMajorAxis = semiMajorAxis;
        this.eccentricity = eccentricity;
        this.orbitalPeriod = orbitalPeriod;
        this.centralCelestialBody = centralCelestialBody;
    }

    // Some Calculations if Needed
    public double orbitingVelocity(double distance) {
        double velocity = Math.sqrt(((Planet.GRAVITATIONAL_CONSTANT * centralCelestialBody.getMass()) / convertKmToMeter(distance)));
        return convertMeterToKilometer(velocity);
    }

    public double distanceToCentralBody(double degrees) {
        double currentDistanceInAU = ((semiMajorAxis * (1 - Math.pow(eccentricity,2))) / (1 + eccentricity * Math.cos(Math.toRadians(degrees))));
        return ASTRONOMICAL_UNITS_IN_KM * currentDistanceInAU;
    }

    // Calculations Stages
    private double degreesAtDayNumber(double day) {
        return (day / this.getOrbitalPeriod()) * 360;
    }

    private double convertKmToMeter(double distance) {
        return distance * 1000;
    }

    private double convertMeterToKilometer(double distance) {
        return distance / 1000;
    }

    // Getters and Setters
    public double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public CelestialBody getCentralCelestialBody() {
        return centralCelestialBody;
    }

    // ToString Method to print elements
    @Override
    public String toString() {
        return super.toString() + String.format(" and does a full orbit around the %s in %.0f days", centralCelestialBody.getName(), orbitalPeriod);
    }
}
