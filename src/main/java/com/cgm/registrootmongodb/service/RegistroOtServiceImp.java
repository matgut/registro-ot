package com.cgm.registrootmongodb.service;

import com.cgm.registrootmongodb.entity.ResgistroOt;
import com.cgm.registrootmongodb.enumeration.Estado;
import com.cgm.registrootmongodb.exceptions.EstadoNotFoundException;
import com.cgm.registrootmongodb.exceptions.RegistroOtNotFoundException;
import com.cgm.registrootmongodb.repository.RegistroOtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
public class RegistroOtServiceImp implements RegistroOtService{

    @Autowired
    RegistroOtRepository registroOtRepository;

    private String PREFIX_OT = "OT/";

    @Override
    public List<ResgistroOt> getAllRegistro() {
        return registroOtRepository.findAll();
    }

    @Override
    public ResgistroOt saveRegistroOt(ResgistroOt resgistroOt) {
        resgistroOt.setNumeroOt(PREFIX_OT + resgistroOt.getNumeroOt());
        resgistroOt.setFcCreacion(this.getFechaActualFormateada("dd/MM/YYYY HH:mm"));
        resgistroOt.setFcActualizacion(this.getFechaActualFormateada("dd/MM/YYYY HH:mm"));
        return registroOtRepository.save(resgistroOt);
    }

    @Override
    public void deleteRegistroOt(String id) {
        registroOtRepository.findById(id).orElseThrow(() -> new RegistroOtNotFoundException(id));
        registroOtRepository.deleteById(id);
    }

    @Override
    public List<ResgistroOt> getRegistroByEstado(String estado) throws EstadoNotFoundException {

        String toUpperEstado = estado.toUpperCase();

        if(!isvalidEstado(toUpperEstado)){
            return null;
        }

        Estado estadoToEnum = Estado.valueOf(toUpperEstado);
        return registroOtRepository.findByEstado(estadoToEnum);
    }



    @Override
    public ResgistroOt updateRegistroEstado(String id,Estado estado) {
        ResgistroOt updRegistro = registroOtRepository.findById(id).orElseThrow(() -> new RegistroOtNotFoundException(id));
        updRegistro.setEstado(estado);
        updRegistro.setFcActualizacion(this.getFechaActualFormateada("dd/MM/YYYY HH:mm"));
        return registroOtRepository.save(updRegistro);
    }

    @Override
    public List<Estado> getEstados() {
        return new ArrayList<Estado>(Arrays.asList(Estado.values()));
    }

    @Override
    public List<ResgistroOt> getRegistroByEstadoFilterNot(String estado) {
        String toUpperEstado = estado.toUpperCase();

        if(!isvalidEstado(toUpperEstado)){
            return null;
        }

        Estado estadoToEnum = Estado.valueOf(toUpperEstado);
        return registroOtRepository.findNotEstado(estadoToEnum);

    }

    private String getFechaActualFormateada(String formatDate){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(formatDate);
        return dateFormat.format(date);
    }

    private boolean isvalidEstado(String enumName){
        try {
            for (Estado estados : Estado.values()) {
                if (estados.name().equals(enumName)) {
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


}
