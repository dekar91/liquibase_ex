package ru.tcsbank.marketplace.admin.jsonApi;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Класс исключений для Json:API
 */
public class ApiResponseException extends HttpClientErrorException {

    /**
     * Код ошибки, может отличаться от кодов http
     */
    private String code;

    /**
     * Источник(объект) на котором возникла ошибка
     */
    private String source;

    /**
     * Текст ошибки
     */
    private String title;

    /**
     * HTTP сод ответа.
     */
    private HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;

    public ApiResponseException(HttpStatus httpCode, String code, String title, String source) {
        super(httpCode, title);

        this.httpStatusCode = httpCode;
        this.code = code;
        this.source = source;
        this.title = title;
    }

    public ApiResponseException(String code, String title, String source) {
        this(HttpStatus.BAD_REQUEST, code, title, source);
    }

    public ApiResponseException(String code, String title) {
        this(code, title, "");
    }

    public ApiResponseException(HttpStatus code, String title) {
        this(code, String.valueOf(code.value()), title, "");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}