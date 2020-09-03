package ru.team42.analyzer.jsonApi;

/**
 * <p>Информация об ошибке, содержащейся в ответе.</p>
 */
public final class ResponseError {
    /**
     * <p>Код ошибки - обязательное поле.</p>
     */
    private String code;

    /**
     * <p>Сообщение об ошибке - может не заполняться.</p>
     */
    private String title;

    /**
     * <p>Параметр запроса, который привел к ошибке, либо идентификатор объекта, на котором произошла ошибка</p>
     * <p>Во втором случае информация о том к какому именно объекту относится идентификатор может быть получена из
     * кода ошибки.</p><br/>
     *
     * <p>Например, если ошибка произошла при покупке билета с идентификатором 1, то следует вернуть в ответе</p>
     * <pre>
     * {@code code = WRONG_TICKET;}
     * {@code source = 1;}
     * </pre>
     *
     * <p>Может не заполняться.</p>
     */
    private String source;

    private ResponseError() {
        // NOOP
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public static class Builder {
        private final ResponseError error;

        public Builder(String code) {
            error = new ResponseError();
            error.code = code;
        }

        public ResponseError build() {
            return error;
        }

        public Builder withTitle(String title) {
            error.title = title;
            return this;
        }

        public Builder withSource(String source) {
            error.source = source;
            return this;
        }
    }
}
