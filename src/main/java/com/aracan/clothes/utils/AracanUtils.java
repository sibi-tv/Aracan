package com.aracan.clothes.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Contains Utility Methods, AKA generic methods that can be used in any of the classes
// If we need a unique id for example we would be using this class
public class AracanUtils {
    private AracanUtils() {

    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
