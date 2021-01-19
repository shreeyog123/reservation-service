package com.myhotel.reservationservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericErrorResponse {

    public final Integer errorCode;

    public final String errorMessage;


}
