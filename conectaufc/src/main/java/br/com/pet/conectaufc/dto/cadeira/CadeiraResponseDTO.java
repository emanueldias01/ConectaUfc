package br.com.pet.conectaufc.dto.cadeira;

import br.com.pet.conectaufc.model.cadeira.Cadeira;

public record CadeiraResponseDTO(
        Long id, String nome
) {
    public CadeiraResponseDTO(Cadeira cadeira) {
        this(cadeira.getId(), cadeira.getNome());
    }
}
