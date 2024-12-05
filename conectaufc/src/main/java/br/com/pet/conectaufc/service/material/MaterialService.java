package br.com.pet.conectaufc.service.material;

import br.com.pet.conectaufc.dto.material.MaterialRequestDTO;
import br.com.pet.conectaufc.dto.material.MaterialResponseDTO;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.material.Material;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.cadeira.CadeiraRepository;
import br.com.pet.conectaufc.repository.material.MaterialRepository;
import br.com.pet.conectaufc.repository.professor.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    CadeiraRepository cadeiraRepository;


    @Autowired
    ProfessorRepository professorRepository;

    public MaterialResponseDTO criaMaterial(MaterialRequestDTO dto){
        Professor professor = professorRepository.getReferenceById(dto.idProfessor());
        Cadeira cadeira = cadeiraRepository.getReferenceById(dto.idCadeira());

        Material material = new Material(dto, professor, cadeira);

        if(!professor.getMateriais().isEmpty()) {
            for (Material m : professor.getMateriais()) {
                if (m.getLink().equals(dto.link()) || m.getNome().equals(dto.nome()) && m.getCadeira().getId().equals(dto.idCadeira())) {
                    throw new IllegalArgumentException("Esse material já foi registrado por esse professor");
                }
            }
        }

        materialRepository.save(material);

        return new MaterialResponseDTO(material);
    }
}
