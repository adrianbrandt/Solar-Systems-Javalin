package SolarSystems.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Star.class, name = "star")
})

public abstract class CelestialBody implements Comparable<CelestialBody> {
    private String name, pictureUrl;
    private double mass, radius;

    // Celestial Body Constructors.
    public CelestialBody(){}

    public CelestialBody(String name, double mass, double radius) {
        this(name, mass, radius, "");
    }

    public CelestialBody(String name, double mass, double radius, String pictureUrl) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.pictureUrl = pictureUrl;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    // ToString Method to Print Data
    @Override
    public String toString() {
        return String.format("%s has a mass of %.0fkg and a radius of %.0fkm", name, mass, radius);
    }

    // Comparing Method
    @Override
    public int compareTo(CelestialBody otherCelestialBody) {
        return this.name.compareTo(otherCelestialBody.getName());
    }
}
