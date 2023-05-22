package com.cgm.registrootmongodb.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus httpStatus, Object resultObj){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", httpStatus.value());
        map.put("result", resultObj);

        return new ResponseEntity<Object>(map,httpStatus);
    }
}
