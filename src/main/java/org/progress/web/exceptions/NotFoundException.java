package org.progress.web.exceptions;

public class NotFoundException extends CustomException {
    
    @Override
    public String getMessage() {
        return "По данному запросу контент не найден";
    }
}
