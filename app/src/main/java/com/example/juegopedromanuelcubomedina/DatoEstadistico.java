package com.example.juegopedromanuelcubomedina;

import java.time.LocalDate;
import java.util.Objects;

public class DatoEstadistico {

    private String puntuacion;
    private String tiempo;
    private LocalDate fecha;
    private String numjuego;

    public DatoEstadistico(LocalDate fecha, String puntuacion, String tiempo, String numjuego) {
        this.puntuacion = puntuacion;
        this.tiempo = tiempo;
        this.fecha = fecha;
        this.numjuego=numjuego;
    }

    public String getNumjuego() {
        return numjuego;
    }

    public void setNumjuego(String numjuego) {
        this.numjuego = numjuego;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public String getTiempo() {
        return tiempo;
    }

    public LocalDate getFecha() {
        return fecha;
    }


    @Override
    public String toString() {
        return "DatoEstadistico{" +
                "puntuacion='" + puntuacion + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", fecha=" + fecha +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatoEstadistico that = (DatoEstadistico) o;
        return Objects.equals(puntuacion, that.puntuacion) &&
                Objects.equals(tiempo, that.tiempo) &&
                Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(puntuacion, tiempo, fecha);
    }
}
