package me.keills.exception;

import org.springframework.dao.DataAccessException;

public class OnLoadFailedException extends DataAccessException {
    public OnLoadFailedException(String msg) {
        super(msg);
    }
}
