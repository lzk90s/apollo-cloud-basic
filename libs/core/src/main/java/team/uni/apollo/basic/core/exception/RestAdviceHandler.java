package team.uni.apollo.basic.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.uni.apollo.basic.core.rest.R;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class RestAdviceHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestAdviceHandler.class);

    private static final String DIVIDER = ",";

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R baseBusinessException(BizException e) {
        logger.warn(e.getMessage(), e);
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R illegalArgumentException(IllegalArgumentException e) {
        logger.error(e.getMessage(), e);
        return R.error(400, "非法参数: " + e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R nullPointerException(NullPointerException e) {
        logger.error(e.getMessage(), e);
        return R.error(500, "服务内部错误(NullPointer)");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R runtimeException(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return R.error(500, e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public R<Object> validationExceptionHandler(BindException e) {
        logger.error(e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append("; ");
        }
        return R.error(400, sb.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return R.error(400, String.join(",", msgList));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R handleException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return R.error(400, "不支持' " + e.getMethod() + "'请求");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R exceptionHandler(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append("; ");
        }
        return R.error(400, sb.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public R sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return R.error(400, "数据冲突，请检查数据有效性");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R defaultHandler(Throwable e) {
        logger.error(e.getMessage(), e);
        return R.error(500, "服务内部错误");
    }
}
