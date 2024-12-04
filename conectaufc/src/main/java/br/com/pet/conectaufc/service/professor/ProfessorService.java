package br.com.pet.conectaufc.service.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorRequestDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorResponseDTO;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.professor.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    public ProfessorResponseDTO criaProfessor(ProfessorRequestDTO dto){
        //verifica se professor ja existe pelo nome

        Professor professor = new Professor(dto);

        professorRepository.save(professor);

        return new ProfessorResponseDTO(professor);
    }

    public ProfessorResponseDTO buscaProfessorPorId(Long id){
        return new ProfessorResponseDTO(professorRepository.getReferenceById(id));
    }

    public List<ProfessorResponseDTO> listaTodosOsProfessores(){
        return professorRepository.findAll().stream().map(ProfessorResponseDTO::new).toList();
    }
}
