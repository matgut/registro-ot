package com.cgm.registrootmongodb.controller;

import com.cgm.registrootmongodb.entity.ResgistroOt;
import com.cgm.registrootmongodb.enumeration.Estado;
import com.cgm.registrootmongodb.service.RegistroOtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/registro-ot")
public class RegistroOtController {

    @Autowired
    public RegistroOtService registroOtService;

    @Value("${registroot.security.token}")
    private String TOKEN;


    @PostMapping
    @Operation(summary = "Guarda registro OT")
    public ResponseEntity<?> saveRegistro(@RequestBody ResgistroOt resgistroOt, @Parameter(in = ParameterIn.HEADER) @RequestHeader Map<String, String> headers){
        try{
            if (!validaToken(headers)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(registroOtService.saveRegistroOt(resgistroOt),HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Obtiene registros OT")
    public ResponseEntity<?> getAllRegistros(@RequestHeader Map<String, String> headers){
        try{
            if (!validaToken(headers)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return ResponseEntity.ok(registroOtService.getAllRegistro());
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina registro OT por ID")
    public ResponseEntity<?> deleteRegistroOT(@Parameter(in = ParameterIn.PATH) @PathVariable String id,@RequestHeader Map<String, String> headers){
        try{
            if (!validaToken(headers)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            registroOtService.deleteRegistroOt(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{estado}")
    @Operation(summary = "Obtiene registros OT por Estados")
    public ResponseEntity<List<ResgistroOt>> getRegistrosByEstado(@Parameter(in = ParameterIn.PATH) @PathVariable Estado estado,@RequestHeader Map<String, String> headers){
        try{

            if (!validaToken(headers)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<ResgistroOt> list = registroOtService.getRegistroByEstado(estado);

            if(list == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/{estado}")
    @Operation(summary = "Actualiza registro OT por estado")
    public ResponseEntity<ResgistroOt> updRegistrosByIdEstado(@Parameter(in = ParameterIn.PATH) @PathVariable String id,@Parameter(in = ParameterIn.PATH) @PathVariable Estado estado,@RequestHeader Map<String, String> headers){

        try{
            if (!validaToken(headers)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            ResgistroOt res = registroOtService.updateRegistroEstado(id, estado);
            if(res == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(res,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/estados")
    @Operation(summary = "Obtiene Estados de registo OT permitidos")
    public ResponseEntity<List<Estado>> getEstadosOt(@RequestHeader Map<String, String> headers){

        try{
            if (!validaToken(headers)){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Estado> listEstados = registroOtService.getEstados();

            if(listEstados == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(listEstados,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public boolean validaToken(Map<String, String> headers) {
        return Objects.equals(headers.get("token"), TOKEN);
    }


}
