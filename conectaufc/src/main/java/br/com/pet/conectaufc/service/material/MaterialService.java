package br.com.pet.conectaufc.service.material;

import br.com.pet.conectaufc.dto.material.MaterialRequestDTO;
import br.com.pet.conectaufc.dto.material.MaterialResponseDTO;
import br.com.pet.conectaufc.dto.material.MaterialUpdateDTO;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.material.Material;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.cadeira.CadeiraRepository;
import br.com.pet.conectaufc.repository.material.MaterialRepository;
import br.com.pet.conectaufc.repository.professor.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public MaterialResponseDTO buscaMaterialPorId(Long id){
        return new MaterialResponseDTO(materialRepository.getReferenceById(id));
    }

    public Page<MaterialResponseDTO> listaTodosOsMateriaisDeCertaCadeiraEDeCertoProfessor(Long idCadeira, Long idProfessor, Pageable pageable){
        return materialRepository.buscaMateriaisDaCadeiraEProfessorEspecifico(idCadeira, idProfessor, pageable).map(MaterialResponseDTO::new);
    }

    public Page<MaterialResponseDTO> listarTodosOsMateirais(Pageable pageable){
        return materialRepository.findAllByOrderByNomeAsc(pageable).map(MaterialResponseDTO::new);
    }

    public MaterialResponseDTO editaMaterial(MaterialUpdateDTO dto){
        Material material = materialRepository.getReferenceById(dto.id());

        if(materialRepository.findByNome(dto.nome()).isPresent() || materialRepository.findByLink(dto.link()).isPresent()){
            throw new IllegalArgumentException("Os campos que está tentando editar já estao preenchidos com outro material");
        }

        material.atualizaMaterial(dto);

        materialRepository.save(material);

        return new MaterialResponseDTO(material);
    }
}
