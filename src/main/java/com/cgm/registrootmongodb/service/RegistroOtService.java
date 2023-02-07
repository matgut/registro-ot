package com.cgm.registrootmongodb.service;

import com.cgm.registrootmongodb.entity.ResgistroOt;
import com.cgm.registrootmongodb.enumeration.Estado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface RegistroOtService {

    List<ResgistroOt> getAllRegistro();
    //ResgistroOt getRegistro(Long id);
    ResgistroOt  saveRegistroOt(ResgistroOt resgistroOt);

    void deleteRegistroOt(String id);

    List<ResgistroOt> getRegistroByEstado(Estado estado);

    ResgistroOt updateRegistroEstado(String id, Estado estado);
    Optional<ResgistroOt> getById(String id);

    List<Estado> getEstados();


}
