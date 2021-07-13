package com.pokebattle.pokedex.exception;

import com.pokebattle.pokedex.data.dto.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class PokeDexControllerAdvice {

    private static ErrorDTO toErroDTO(ConstraintViolation<?> x) {
        var dto = new ErrorDTO();
        dto.setMessage(x.getMessage());
        dto.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        dto.setError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        return dto;
    }

    public ErrorDTO toDefaultErrorDTO() {
        var dto = new ErrorDTO();
        dto.setMessage("Opa! Um erro inesperado ocorreu. Por favor, tente novamente ou entre em contato");
        dto.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        dto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return dto;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintValidationException(ConstraintViolationException e) {

        var errorDTO = e.getConstraintViolations()
                .stream()
                .map(PokeDexControllerAdvice::toErroDTO)
                .findFirst()
                .orElse(toDefaultErrorDTO());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleUnexpectedError(Exception e) {
        log.error("An unexpected error occurred {}", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(toDefaultErrorDTO());
    }

}
