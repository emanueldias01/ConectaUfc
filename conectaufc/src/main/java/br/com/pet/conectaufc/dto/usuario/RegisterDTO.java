package br.com.pet.conectaufc.dto.usuario;

import br.com.pet.conectaufc.model.usuario.UsuarioRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String matricula,
        @NotBlank
        UsuarioRole role
) {
}
