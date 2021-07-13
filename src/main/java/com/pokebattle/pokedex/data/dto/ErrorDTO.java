package com.pokebattle.pokedex.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

    private String message;
    private Integer code;
    private String error;

}
