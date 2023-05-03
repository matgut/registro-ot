package com.cgm.registrootmongodb.service;

import com.cgm.registrootmongodb.entity.ResgistroOt;
import com.cgm.registrootmongodb.enumeration.Estado;
import com.cgm.registrootmongodb.repository.RegistroOtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        return registroOtRepository.save(resgistroOt);
    }

    @Override
    public void deleteRegistroOt(String id) {
        registroOtRepository.deleteById(id);
    }

    @Override
    public List<ResgistroOt> getRegistroByEstado(Estado estado) {
        List<ResgistroOt> listByEstado = registroOtRepository.findByEstado(estado);
        if(listByEstado.isEmpty()){
            return null;
        }
        return listByEstado;
    }

    @Override
    public ResgistroOt updateRegistroEstado(String id,Estado estado) {
        Optional<ResgistroOt> findRegistro = registroOtRepository.findById(id);
        if(findRegistro.isPresent()){
            ResgistroOt updRegistro = findRegistro.get();
            updRegistro.setEstado(estado);
            updRegistro.setFcActualizacion(this.getFechaActualFormateada("dd/MM/YYYY HH:mm"));
            return registroOtRepository.save(updRegistro);
        }
        return null;
    }

    @Override
    public Optional<ResgistroOt> getById(String id) {
        return registroOtRepository.findById(id);
    }

    @Override
    public List<Estado> getEstados() {
        return new ArrayList<Estado>(Arrays.asList(Estado.values()));
    }

    private String getFechaActualFormateada(String formatDate){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(formatDate);
        return dateFormat.format(date);
    }


}
