package com.example.SaleCampaignApplication.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;


    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s is not found with %s : %s", resourceName , fieldName , fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


}
