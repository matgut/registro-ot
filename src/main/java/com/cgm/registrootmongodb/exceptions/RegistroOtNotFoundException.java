package com.cgm.registrootmongodb.exceptions;

public class RegistroOtNotFoundException extends RuntimeException{

    public RegistroOtNotFoundException(String registroId){
        super("No se ha encontrado OT con ID [" + registroId + "]");
    }
}
