package org.progress.web.exceptions;

public class IncorrectPasswordException extends CustomException {
    
    @Override
    public String getMessage() {
        return "Старый пароль неверен";
    }
}
