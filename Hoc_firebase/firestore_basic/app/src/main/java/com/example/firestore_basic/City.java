package com.example.firestore_basic;

public class City {
    private String name;
    private String country;
    private long population;

    public City() {}

    public City(String name, String country, long population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public long getPopulation() {
        return population;
    }
}

