package ec.edu.ups.icc.fundamentos01.products.exception.domain;

import ec.edu.ups.icc.fundamentos01.products.exception.base.ApplicationException;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApplicationException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}