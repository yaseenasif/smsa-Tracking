package com.example.CargoTracking.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String meassage){
        super(meassage);
    }
}
