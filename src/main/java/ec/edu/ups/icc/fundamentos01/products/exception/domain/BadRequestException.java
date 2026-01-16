package ec.edu.ups.icc.fundamentos01.products.exception.domain;

import ec.edu.ups.icc.fundamentos01.products.exception.base.ApplicationException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}