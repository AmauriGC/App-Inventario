package dev.cristian.app.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.cristian.app.exception.exceptions.EstadoInvalidoException;
import dev.cristian.app.exception.exceptions.OperacionNoPermitidaException;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> crearRespuesta(String mensaje, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", mensaje);
        response.put("timestamp", new Date());
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<Map<String, Object>> crearRespuesta(String mensaje, List<String> errores, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", mensaje);
        response.put("errors", errores);
        response.put("timestamp", new Date());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return crearRespuesta("Error de validación", errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoDuplicado(RecursoDuplicadoException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormat(InvalidFormatException ex) {
        String mensaje;
        JsonMappingException.Reference ref = ex.getPath().get(0);
        String campo = ref.getFieldName();
        Class<?> targetType = ex.getTargetType();

        if (targetType.isEnum()) {
            Object[] valores = targetType.getEnumConstants();
            List<String> valoresValidos = Arrays.stream(valores)
                    .map(Object::toString)
                    .toList();

            mensaje = String.format("Valor inválido para el campo '%s'. Valores permitidos: %s", campo, String.join(", ", valoresValidos));
        } else {
            mensaje = "Error en el formato del campo: " + campo;
        }

        return crearRespuesta(mensaje, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperacionNoPermitidaException.class)
    public ResponseEntity<Map<String, Object>> handleOperacionNoPermitida(OperacionNoPermitidaException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleEstadoInvalido(EstadoInvalidoException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleEnumConversionError(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Valor no válido para el parámetro: " + ex.getName());
    }
}
