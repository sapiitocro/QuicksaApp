package com.example.juancarlos.quicksaapp.Model;

public class Viaje {
    private long id;
    private String guardia;
    private String operador;
    private String material;
    private String origen;
    private String destino;


    public Viaje() {

    }

    public Viaje(String operador, String guardia, String origen, String destino, String material) {
        this.operador = operador;
        this.guardia = guardia;
        this.origen = origen;
        this.destino = destino;
        this.material = material;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }


}
