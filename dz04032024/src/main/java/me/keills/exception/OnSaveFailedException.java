package me.keills.exception;

import org.springframework.dao.DataAccessException;

public class OnSaveFailedException extends DataAccessException {
    public OnSaveFailedException(String msg) {
        super(msg);
    }
}
