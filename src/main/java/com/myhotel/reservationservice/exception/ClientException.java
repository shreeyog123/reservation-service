package com.myhotel.reservationservice.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {

    private Integer errorCode;

    public ClientException(String errorMessage, Integer errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }


}
