package soohyunj.interviewsimulator.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soohyunj.interviewsimulator.exception.AccessDeniedRequestException;
import soohyunj.interviewsimulator.exception.InvalidLoginInfoException;
import soohyunj.interviewsimulator.exception.MethodArgumentInvalidException;
import soohyunj.interviewsimulator.web.dto.CommonResponse;
import soohyunj.interviewsimulator.domain.interview.NotFoundMemberQaException;
import soohyunj.interviewsimulator.domain.simulation.DuplicateSimulationNameException;
import soohyunj.interviewsimulator.domain.simulation.YetStartedSimulationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidLoginInfoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResponse<?> handleInvalidLoginInfoException(InvalidLoginInfoException e) {
        log.error("InvalidLoginInfoException Error", e);
        return CommonResponse.unauthorized(e.getErrorCode());
    }

    @ExceptionHandler(AccessDeniedRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResponse<?> handleAccessDeniedRequestException(AccessDeniedRequestException e) {
        log.error("AccessDeniedRequestException Error", e);
        return CommonResponse.forbidden(e.getErrorCode());
    }

    @ExceptionHandler(NotFoundMemberQaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<?> handleNotFoundResourceException(NotFoundMemberQaException e) {
        log.error("NotFoundResourceException Error", e);
        return CommonResponse.notFound(e.getErrorCode());
    }

    @ExceptionHandler(DuplicateSimulationNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResponse<?> handleDuplicateSimulationNameException(DuplicateSimulationNameException e) {
        log.error("DuplicateSimulationNameException Error", e);
        return CommonResponse.conflict(e.getErrorCode());
    }

    @ExceptionHandler(YetStartedSimulationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleYetStartedSimulationException(YetStartedSimulationException e) {
        log.error("YetStartedSimulationException Error", e);
        return CommonResponse.badRequest(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<String> handleValidationExceptions(MethodArgumentInvalidException e) {
        return CommonResponse.badRequest(e.getErrorCode());
    }


}
