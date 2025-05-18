package dev.cristian.app.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.cristian.app.exception.exceptions.EstadoInvalidoException;
import dev.cristian.app.exception.exceptions.OperacionNoPermitidaException;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private <T> ResponseEntity<ApiResponse<T>> crearRespuesta(String mensaje, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(mensaje));
    }

    private <T> ResponseEntity<ApiResponse<T>> crearRespuesta(String mensaje, List<String> errores, HttpStatus status) {
        String mensajeCompleto = mensaje + ": " + String.join("; ", errores);
        return ResponseEntity.status(status).body(ApiResponse.error(mensajeCompleto));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return crearRespuesta("Error de validación", errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiResponse<Void>> handleRecursoDuplicado(RecursoDuplicadoException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidFormat(InvalidFormatException ex) {
        String mensaje;
        JsonMappingException.Reference ref = ex.getPath().get(0);
        String campo = ref.getFieldName();
        Class<?> targetType = ex.getTargetType();

        if (targetType.isEnum()) {
            Object[] valores = targetType.getEnumConstants();
            List<String> valoresValidos = List.of(valores).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            mensaje = String.format("Valor inválido para el campo '%s'. Valores permitidos: %s", campo, String.join(", ", valoresValidos));
        } else {
            mensaje = "Error en el formato del campo: " + campo;
        }

        return crearRespuesta(mensaje, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ResponseEntity<ApiResponse<Void>> handleOperacionNoPermitida(OperacionNoPermitidaException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<ApiResponse<Void>> handleEstadoInvalido(EstadoInvalidoException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Void>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleEnumConversionError(MethodArgumentTypeMismatchException ex) {
        String mensaje = "Valor no válido para el parámetro: " + ex.getName();
        return crearRespuesta(mensaje, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return crearRespuesta("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
