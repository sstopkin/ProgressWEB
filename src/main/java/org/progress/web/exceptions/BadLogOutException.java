package org.progress.web.exceptions;

public class BadLogOutException extends CustomException {
    
    @Override
    public String getMessage() {
        return "При выходе из системы произошла непредвиденная ошибка";
    }
}
