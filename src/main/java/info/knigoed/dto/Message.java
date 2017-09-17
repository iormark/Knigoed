package info.knigoed.dto;

import java.io.Serializable;

public class Message implements Serializable {
    public static final String MSG_ERROR_DEFAULT = "Произошла ошибка. Мы уже знаем о проблеме. Простите.";
    private Message.Type type = Type.error;

    public enum Type {
        success, error
    }

    private String field = null;
    private String message = null;


    public Message() {}

    public Message(Type type, String field ,String message) {
        this.type = type;
        this.field = field;
        this.message = message;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(String message) {
        this.type = Type.success;
        this.message = message;
    }

    public void setError(String message) {
        this.type = Type.error;
        this.message = message;
    }
}
