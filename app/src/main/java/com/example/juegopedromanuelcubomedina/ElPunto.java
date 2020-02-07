package com.example.juegopedromanuelcubomedina;

public class ElPunto {

    private double latitud;
    private double longitud;
    private String score;

    public ElPunto() {}

    public ElPunto(double latitud, double longitud, String score) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.score = score;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return "ElPunto{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                ", score='" + score + '\'' +
                '}';
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getScore() {
        return score;
    }
}
