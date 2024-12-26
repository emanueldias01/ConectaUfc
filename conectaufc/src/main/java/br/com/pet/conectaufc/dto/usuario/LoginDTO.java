package br.com.pet.conectaufc.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
