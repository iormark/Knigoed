package info.knigoed.dto;

import java.io.Serializable;
import java.util.Map;

public class Message implements Serializable {
    public static final String MSG_ERROR_DEFAULT = "Произошла ошибка. Мы уже знаем о проблеме. Простите.";
    private String error;
    private String success;
    private Map<String, String> fieldErrors;
    private Map<String, String> fieldSuccess;

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    public String getSuccess() {
        return success;
    }

    public void addFieldError(String field, String message) {
        fieldErrors.put(field, message);
    }
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldSuccess(Map<String, String> fieldSuccess) {
        this.fieldSuccess = fieldSuccess;
    }
    public Map<String, String> getFieldSuccess() {
        return fieldSuccess;
    }
}
