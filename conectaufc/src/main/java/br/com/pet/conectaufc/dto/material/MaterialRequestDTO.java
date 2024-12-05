package br.com.pet.conectaufc.dto.material;

public record MaterialRequestDTO(
        String nome,
        Long idProfessor,
        Long idCadeira,
        String link
) {
}
