package com.lolpowerranks.rankingservice.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class ExceptionResponse {
    String message;
    String exceptionType;
}
