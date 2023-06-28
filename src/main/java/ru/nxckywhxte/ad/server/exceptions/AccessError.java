package ru.nxckywhxte.ad.server.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccessError extends Exception {
    private int status;
    private String message;
    private Date timestamp;

    public AccessError () {
        this.status = 403;
        this.message = "Ошибка доступа! Проверьте данные и повторите попытку";
        this.timestamp = new Date();
    }
}
