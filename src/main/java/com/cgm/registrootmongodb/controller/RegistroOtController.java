package com.cgm.registrootmongodb.controller;

import com.cgm.registrootmongodb.entity.ResgistroOt;
import com.cgm.registrootmongodb.enumeration.Estado;
import com.cgm.registrootmongodb.response.CustomResponse;
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

    private String MSG_INVALID_TOKEN = "Token Invalido";
    @PostMapping
    @Operation(summary = "Guarda registro OT")
    public ResponseEntity<?> saveRegistro(@RequestBody ResgistroOt resgistroOt, @Parameter(in = ParameterIn.HEADER) @RequestHeader Map<String, String> headers){
        try{
            if(msgValidaToken(headers) != null) return msgValidaToken(headers);
            return CustomResponse.generateResponse("Registro guardado correctamente!",HttpStatus.CREATED,registroOtService.saveRegistroOt(resgistroOt));
        }catch(Exception e){
            System.out.println(e.getMessage());
            return CustomResponse.generateResponse("Ha ocurrido una excepción, favor revisar!",HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @GetMapping
    @Operation(summary = "Obtiene registros OT")
    public ResponseEntity<?> getAllRegistros(@RequestHeader Map<String, String> headers){
        try{
            if(msgValidaToken(headers) != null) return msgValidaToken(headers);
            //return ResponseEntity.ok(registroOtService.getAllRegistro());
            return CustomResponse.generateResponse("Registros obtenidos correctamente!",HttpStatus.OK,registroOtService.getAllRegistro());
        }catch(Exception e){
            System.out.println(e.getMessage());
            return CustomResponse.generateResponse("Ha ocurrido una excepción, favor revisar!",HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina registro OT por ID")
    public ResponseEntity<?> deleteRegistroOT(@Parameter(in = ParameterIn.PATH) @PathVariable String id,@RequestHeader Map<String, String> headers){
        try{
            if(msgValidaToken(headers) != null) return msgValidaToken(headers);
            registroOtService.deleteRegistroOt(id);
            //return new ResponseEntity<>(HttpStatus.OK);
            return CustomResponse.generateResponse("Registro eliminado correctamente!",HttpStatus.OK,null);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return CustomResponse.generateResponse("Ha ocurrido una excepción, favor revisar!",HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @GetMapping("byEstado/{estado}")
    @Operation(summary = "Obtiene registros OT por Estados")
    public ResponseEntity<?> getRegistrosByEstado(@Parameter(in = ParameterIn.PATH) @PathVariable Estado estado,@RequestHeader Map<String, String> headers){
        try{

            if(msgValidaToken(headers) != null) return msgValidaToken(headers);

            List<ResgistroOt> list = registroOtService.getRegistroByEstado(Estado.valueOf(estado.toString().toUpperCase()));

            if(list == null || list.size() == 0){
                //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                return CustomResponse.generateResponse("No se han encontrado registros para el estado "+estado,HttpStatus.OK,null);
            }
            return CustomResponse.generateResponse("Registros obtenidos correctamente!",HttpStatus.OK,list);
            //return new ResponseEntity<>(list,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return CustomResponse.generateResponse("Ha ocurrido una excepción, favor revisar!",HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @PutMapping("/{id}/{estado}")
    @Operation(summary = "Actualiza registro OT por estado")
    public ResponseEntity<?> updRegistrosByIdEstado(@Parameter(in = ParameterIn.PATH) @PathVariable String id,@Parameter(in = ParameterIn.PATH) @PathVariable Estado estado,@RequestHeader Map<String, String> headers){

        try{
            if(msgValidaToken(headers) != null) return msgValidaToken(headers);

            ResgistroOt res = registroOtService.updateRegistroEstado(id, estado);
            if(res == null){
                //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                return CustomResponse.generateResponse("No se han encontrado registro para el id "+id,HttpStatus.NOT_FOUND,null);
            }
            return CustomResponse.generateResponse("Registro actualizado correctamente!",HttpStatus.OK,res);

        }catch(Exception e){
            System.out.println(e.getMessage());
            return CustomResponse.generateResponse("Ha ocurrido una excepción, favor revisar!",HttpStatus.INTERNAL_SERVER_ERROR,null);
        }

    }

    @GetMapping("/estados")
    @Operation(summary = "Obtiene Estados de registo OT permitidos")
    public ResponseEntity<?> getEstadosOt(@RequestHeader Map<String, String> headers){

        try{
            if(msgValidaToken(headers) != null) return msgValidaToken(headers);

            List<Estado> listEstados = registroOtService.getEstados();

            if(listEstados == null){
                return CustomResponse.generateResponse("No se han encontrado registros",HttpStatus.NOT_FOUND,null);
            }
            return CustomResponse.generateResponse("Registros obtenidos correctamente!",HttpStatus.OK,listEstados);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private boolean validaToken(Map<String, String> headers) {
        return Objects.equals(headers.get("token"), TOKEN);
    }
    private ResponseEntity<Object> msgValidaToken(@RequestHeader Map<String, String> headersValid){

        if (!validaToken(headersValid)){
            return CustomResponse.generateResponse(MSG_INVALID_TOKEN,HttpStatus.UNAUTHORIZED,null);
        }
        return null;
    }


}
