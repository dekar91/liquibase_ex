import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.team42.analyzer.jsonApi.ApiResponse;
import ru.team42.analyzer.jsonApi.ResponseError;

@ControllerAdvice
@ResponseBody
public class ServiceErrorHandler {


    private ApiResponse<Void> buildApiResponse(String code, String title, String source) {
        ResponseError error = new ResponseError.Builder(code)
                .withTitle(title)
                .withSource(source)
                .build();

        return new ApiResponse.Builder<Void>()
                .addResponseErrorMessage(error)
                .build();
    }

    private ApiResponse<Void> buildApiResponse(String code, String title) {
        return buildApiResponse(code,  title, null);
    }

    private ApiResponse<Void> buildApiResponse(int code, String title) {
        return buildApiResponse(String.valueOf(code), title, null);
    }



    private ApiResponse<Void> buildApiResponse(ru.tcsbank.marketplace.admin.jsonApi.ApiResponseException exception) {
        return buildApiResponse(exception.getCode(), exception.getTitle(), exception.getSource());
    }

    private ApiResponse<Void> buildApiResponse(HttpStatusCodeException exception) {
        return buildApiResponse(exception.getRawStatusCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(ru.tcsbank.marketplace.admin.jsonApi.ApiResponseException.class)
    public ApiResponse<Void> handleException(ru.tcsbank.marketplace.admin.jsonApi.ApiResponseException ex) {
        return buildApiResponse(ex);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ApiResponse<Void> handleException(HttpStatusCodeException ex) {
        return buildApiResponse(ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<Void> handleException(BadCredentialsException ex) {
        return buildApiResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ApiResponse<Void> handleException(InsufficientAuthenticationException ex) {
        return buildApiResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> handleException(AccessDeniedException ex) {
        return buildApiResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());

    }

    @ExceptionHandler
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<Void> requestHandlingNoHandlerFound(final NoHandlerFoundException ex) {
        return buildApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception ex) {
        return buildApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
