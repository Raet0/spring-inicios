package ec.edu.ups.icc.fundamentos01.products.exception.domain;

import ec.edu.ups.icc.fundamentos01.products.exception.base.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}