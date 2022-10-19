package com.is442.oop.data.payloads.response;

public class DataResponse {
    private Object data;
    private String message;

    public DataResponse(Object data){
        // Data
        this.data = data;
    }

    public DataResponse(Object data, String resourceName){
        // Variable formatted message
        this.data = data;
        this.message = String.format("Successful action - Resource: %s.", resourceName);
    }
    

    public DataResponse(Object data, Exception e) {
        this.data = data;
        this.message = e.getMessage();
    }

    public void setData(Object data){
        this.data = data;
    }

    public Object getData(){
        return data;
    }
    
    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
