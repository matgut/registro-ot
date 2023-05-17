package com.cgm.registrootmongodb.exceptions;

public class EstadoNotFoundException extends RuntimeException{

    public EstadoNotFoundException(String estado){
        super("El estado " + estado +" no fue encontrado o no corresponde con los definidos, favor de consultar endpoint /api/v1/registro-ot/estados");
    }
}
