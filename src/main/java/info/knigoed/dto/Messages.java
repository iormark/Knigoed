package info.knigoed.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Messages implements Serializable {
    public static final String MSG_ERROR_DEFAULT = "Произошла ошибка. Мы уже знаем о проблеме. Простите.";
    private Message message;
    private List<Message> fieldErrors = new ArrayList<>();

    public void addError(Message message) {
        this.message = message;
    }

    public void addFieldError(String field, String message) {
        Message error = new Message(Message.Type.error, field, message);
        fieldErrors.add(error);
    }

    public Message getMessage() {
        return message;
    }

    public List<Message> getFieldErrors() {
        return fieldErrors;
    }
}
