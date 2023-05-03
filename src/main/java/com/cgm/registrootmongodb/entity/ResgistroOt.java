package com.cgm.registrootmongodb.entity;

import com.cgm.registrootmongodb.enumeration.Estado;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResgistroOt {

    @Id
    private String  registroId;

    private String nombreSistema;
    private String numeroOt;
    private String descipcion;
    private Estado estado;

    private String fcCreacion;
    private String fcActualizacion;

}
