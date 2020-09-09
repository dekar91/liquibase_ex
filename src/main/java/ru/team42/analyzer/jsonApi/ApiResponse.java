package ru.team42.analyzer.jsonApi;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Класс-обертка ответа на все запросы к API</p>
 * <p>Каждый модуль должен возвращать результат, используя эту обертку.</p>
 * <p>Результат предварительно можно прогнать через конвертеры, для преобразования к нужному формату.</p><br/>
 *
 * <p>Пример использования в сервисе, где не было ошибок:</p>
 * <pre><code>
 *     List<Person> persons = new LinkedList<>();
 *     return new ApiResponse.Builder&lt;List&lt;Person&gt;&gt;()
 *                 .withData(persons)
 *                 .build();
 * </code></pre>
 *
 * <p>Пример использования в сервисе, где случились ошибки:</p>
 * <pre><code>
 *     ResponseError validationError = new ResponseError.Builder(ResponseErrorCode.VALIDATION)
 *                 .withTitle("Неправильный формат электронной почты")
 *                 .withSource("email")
 *                 .build();
 *
 *     return new ApiResponse.Builder()
 *                 .addResponseErrorMessage(error)
 *                 .build();
 * </code></pre>
 *
 * @param <T> Тип возвращаемого результата.
 *           <p><strong>Предупреждение:</strong> параметр типа специально не расширяет интерфейс {@code Serializable},
 *           так как это вносит ограничение на использование интерфейсов для коллекций, сужая их до конкретных
 *           реализаций. А учитывая, что ответы всегда будут конвертироваться в JSON строку, был сделан выбор в сторону
 *           удобства использования класса.</p>
 */
public final class ApiResponse<T> {
    /**
     * Результат выполнения запроса
     */
    private T data;

    /**
     * Список ошибок, произошедших во время выполнения запроса
     */
    private List<ResponseError> errors;

    /**
     * Дополнительная мета-информация (например, инфо по паджинации)
     */
    private final MetaInfo meta;

    private ApiResponse() {
        meta = new MetaInfo();
        errors = new LinkedList<>();
    }

    public ApiResponse(T data, List errors, MetaInfo meta) {
        this.data = data;
        this.errors = errors;
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public MetaInfo getMeta() {
        return meta;
    }

    /**
     * Shortcut-метод для инициализации ответа, содержащего только data.
     * @param data - информация для возврата
     * @param <T> - тип класса
     * @return Собранный ApiResponse с данными типа T
     */
    public static <T> ApiResponse<T> buildWithData(T data) {
        return new Builder<T>().withData(data).build();
    }

    /**
     * <p>Используйте этот класс для построения ответа на запросы к API</p>
     * @param <T> тип результата
     */
    public static class Builder<T> {
        private final ApiResponse<T> response;

        public Builder() {
            response = new ApiResponse<>();
        }

        /**
         * <p>Конструирование ответа в случае успешного запроса.</p>
         * @return Возвращает класс-обертку с полученным результатом и без списка ошибок.
         */
        private ApiResponse<T> buildSuccessResponse() {
            response.errors = Collections.emptyList();
            return response;
        }

        /**
         * <p>Конструирование ответа в случае возникновения ошибок во время выполнения запроса.</p>
         * <p>При возникновении ошибок клиенту не будет приходить никакого результата в ответе, чтобы не давать ему в пользование недостоверные данные.
         * Под <i>недостоверными данными</i> подразумевается, что раз во время запроса возникли ошибки, то мы не можем гарантировать, что выполнили
         * бизнес-задачу полностью и, следовательно, не можем выдавать результат, завершенный частично.</p>
         * @return Возвращает класс-обертку со списком ошибок и без результата.
         */
        private ApiResponse<T> buildFailResponse() {
            response.data = null;
            return response;
        }

        /**
         * <p>Конструирование ответа на запрос.</p>
         * <p>Ответ может содержать либо результат, либо список ошибок.</p>
         * @return Возвращает класс-обертку с результатом или списком ошибок.
         */
        public ApiResponse<T> build() {
            if (response.errors.isEmpty()) {
                return buildSuccessResponse();
            } else {
                return buildFailResponse();
            }
        }

        public Builder<T> withData(T data) {
            response.data = data;
            return this;
        }

        public Builder<T> withPageable(long totalElements, int pageSize, int totalPages, int pageNumber) {
            PageableInfo pageableInfo = new PageableInfo(totalElements, pageSize, totalPages, pageNumber);
            response.meta.setPageable(pageableInfo);
            return this;
        }

        public Builder<T> withErrorList(List<ResponseError> errors) {
            response.errors = ObjectUtils.defaultIfNull(errors, new LinkedList<>());
            return this;
        }

        public Builder<T> addResponseErrorMessage(ResponseError error) {
            response.errors = ObjectUtils.defaultIfNull(response.errors, new LinkedList<>());
            response.errors.add(error);
            return this;
        }
    }
}