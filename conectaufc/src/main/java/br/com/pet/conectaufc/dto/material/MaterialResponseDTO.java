package br.com.pet.conectaufc.dto.material;

import br.com.pet.conectaufc.model.material.Material;

public record MaterialResponseDTO(
    Long id,
    String nome,
    String nomeProfessor,
    String nomeCadeira,
    String link
) {
    public MaterialResponseDTO(Material material) {
        this(material.getId(),
                material.getNome(),
                material.getProfessor().getNome(),
                material.getCadeira().getNome(),
                material.getLink());
    }
}
