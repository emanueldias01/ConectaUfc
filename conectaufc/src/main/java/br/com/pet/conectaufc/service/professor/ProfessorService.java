package br.com.pet.conectaufc.service.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorRequestDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorResponseDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorUpdateDTO;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.material.MaterialRepository;
import br.com.pet.conectaufc.repository.professor.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    MaterialRepository materialRepository;

    public ProfessorResponseDTO criaProfessor(ProfessorRequestDTO dto){

        if(professorRepository.findByNome(dto.nome()).isPresent()){
            throw new IllegalArgumentException("Ja existe um professor registrado com esse nome");
        }

        Professor professor = new Professor(dto);

        professorRepository.save(professor);

        return new ProfessorResponseDTO(professor);
    }

    public ProfessorResponseDTO buscaProfessorPorId(Long id){
        return new ProfessorResponseDTO(professorRepository.getReferenceById(id));
    }

    public Page<ProfessorResponseDTO> listaTodosOsProfessores(Pageable pageable){
        return professorRepository.findAll(pageable).map(ProfessorResponseDTO::new);
    }

    public Page<ProfessorResponseDTO> listaProfessoresDeUmaDeterminadaCadeira(Long idCadeira, Pageable pageable){
        Page<Long> idProfessores = professorRepository.buscaProfessoresDeCadeiraEspecifica(idCadeira, pageable);

        return idProfessores
                .map(i -> {
                    Professor professor = professorRepository.getReferenceById(i);
                    return new ProfessorResponseDTO(professor);
                });
    }

    public ProfessorResponseDTO atualizaNomeDoProfessor(ProfessorUpdateDTO dto){
        Professor professor = professorRepository.getReferenceById(dto.id());

        if(professorRepository.findByNome(dto.nome()).isPresent()){
            throw new IllegalArgumentException("Já existe um professor com o nome em que deseja atualizar");
        }

        professor.setNome(dto.nome());

        professorRepository.save(professor);

        return new ProfessorResponseDTO(professor);
    }

    public void deletaProfessor(Long id){
        professorRepository.deleteById(id);
        materialRepository.deletaTodoMaterialDoProfessor(id);
    }

}
