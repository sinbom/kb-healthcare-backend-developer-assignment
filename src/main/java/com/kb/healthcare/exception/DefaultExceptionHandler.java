package com.kb.healthcare.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(
            ErrorResponseException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(
            ConversionNotSupportedException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodValidationException(
            MethodValidationException exception,
            HttpHeaders httpHeaders,
            HttpStatus httpStatusCode,
            WebRequest webRequest
    ) {
        logging(
                httpStatusCode,
                webRequest,
                exception
        );

        return resolveErrorResponse(httpStatusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest request
    ) {
        logging(
                httpStatusCode,
                request,
                exception
        );

        String message = exception.getAllErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("잘못된 요청이에요.");

        return ResponseEntity.status(httpStatusCode.value())
                .body(
                        new RestErrorResponse(
                                httpStatusCode.value(),
                                message
                        )
                );
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<?> handleConstraintViolationException(
            ConstraintViolationException exception,
            WebRequest request
    ) {
        logging(
                BAD_REQUEST,
                request,
                exception
        );

        String message = exception.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("잘못된 요청이에요.");

        return badRequest()
                .body(
                        new RestErrorResponse(
                                BAD_REQUEST.value(),
                                message
                        )
                );
    }

    @ExceptionHandler(value = BusinessException.class)
    ResponseEntity<?> handleBusinessException(
            BusinessException exception,
            WebRequest request
    ) {
        logging(
                BAD_REQUEST,
                request,
                exception
        );

        return badRequest()
                .body(
                        new RestErrorResponse(
                                exception.getCode(),
                                exception.getMessage()
                        )
                );
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handleInternalServerException(
            Exception exception,
            WebRequest request
    ) {
        logging(
                INTERNAL_SERVER_ERROR,
                request,
                exception
        );

        return internalServerError()
                .body(
                        new RestErrorResponse(
                                INTERNAL_SERVER_ERROR.value(),
                                "서비스에 문제가 발생했어요! 잠시 후 다시 시도해 주세요."
                        )
                );
    }

    private void logging(
            HttpStatusCode httpStatusCode,
            WebRequest webRequest,
            Throwable exception
    ) {
        if (!httpStatusCode.is5xxServerError()) {
            log.debug(
                    "REQUEST CLIENT EXCEPTION",
                    exception
            );

            return;
        }

        log.error(
                "REQUEST SERVER EXCEPTION",
                exception
        );
    }

    private ResponseEntity<Object> resolveErrorResponse(HttpStatusCode httpStatusCode) {
        String message = httpStatusCode.is4xxClientError() ?
                "잘못된 요청이에요." : "서비스에 문제가 발생했어요! 잠시 후 다시 시도해 주세요.";

        return status(httpStatusCode.value())
                .body(
                        new RestErrorResponse(
                                httpStatusCode.value(),
                                message
                        )
                );
    }

}

