package org.progress.web.exceptions;

public class DisabledUserException extends CustomException {

    @Override
    public String getMessage() {
        return "Учетная запись отключена";
    }
}
