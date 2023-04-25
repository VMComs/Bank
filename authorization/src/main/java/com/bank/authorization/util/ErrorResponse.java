package com.bank.authorization.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Класс для создания объекта ошибки сущности User, включающий в себя message и timestamp
 * Используется в ErrorHandler, при возникновении ошибок
 */

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp timestamp;
}
