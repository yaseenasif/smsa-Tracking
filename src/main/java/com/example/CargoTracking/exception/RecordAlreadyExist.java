package com.example.CargoTracking.exception;

public class RecordAlreadyExist extends RuntimeException{
  public RecordAlreadyExist(String message){
    super(message);
  }
}
