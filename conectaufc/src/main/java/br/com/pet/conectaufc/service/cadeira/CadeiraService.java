package br.com.pet.conectaufc.service.cadeira;

import br.com.pet.conectaufc.dto.cadeira.*;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.cadeira.CadeiraRepository;
import br.com.pet.conectaufc.repository.material.MaterialRepository;
import br.com.pet.conectaufc.repository.professor.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CadeiraService {
    @Autowired
    CadeiraRepository cadeiraRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    MaterialRepository materialRepository;

    public CadeiraResponseDTO criaCadeira(CadeiraRequestDTO dto){

        if(cadeiraRepository.findByNome(dto.nome()).isPresent()){
            throw new IllegalArgumentException("Cadeira já existe");
        }

        Cadeira cadeira = new Cadeira(dto);

        cadeiraRepository.save(cadeira);

        return new CadeiraResponseDTO(cadeira);
    }

    public Page<CadeiraResponseDTO> listaTodasAsCadeiras(Pageable pageable){
        return cadeiraRepository.findAllByOrderByNomeAsc(pageable).map(CadeiraResponseDTO::new);
    }

    public CadeiraResponseDTO buscaCadeiraPeloId(Long id){
        return new CadeiraResponseDTO(cadeiraRepository.getReferenceById(id));
    }

    public CadeiraResponseDTO atualizaNomeDaCadeira(CadeiraUpdateDTO dto){

        if (cadeiraRepository.findByNome(dto.nome()).isPresent()){
            throw new IllegalArgumentException("Já existe uma cadeira com o nome que está tentando atualizar");
        }

        Cadeira cadeira = cadeiraRepository.getReferenceById(dto.id());

        cadeira.setNome(dto.nome());

        cadeiraRepository.save(cadeira);

        return new CadeiraResponseDTO(cadeira);
    }

    public void deletaCadeira(Long id){
        if(!materialRepository.buscaMateriaisDeCadeiraEspecifica(id).isEmpty()){
            materialRepository.deletaTodoMaterialDaCadeira(id);
        }

        professorRepository.removeVinculoDeProfessorComCadeira(id);

        cadeiraRepository.deleteById(id);
    }

    public CadeiraProfessorResponseDTO adicionaProfessorAUmaCadeira(CadeiraProfessorRequestDTO dto){
        Cadeira cadeira = cadeiraRepository.getReferenceById(dto.idCadeira());
        Professor professor = professorRepository.getReferenceById(dto.idProfessor());

        if(cadeira.getProfessores().contains(professor)){
            throw new IllegalArgumentException("Esse professor já está cadastrado na cadeira");
        }

        cadeira.addProfessorNaCadeira(professor);
        professorRepository.salvaProfessorNaquelaCadeira(dto.idProfessor(), dto.idCadeira());

        cadeiraRepository.save(cadeira);

        return new CadeiraProfessorResponseDTO(dto.idCadeira(), cadeira.getNome(), professor.getNome());
    }

    public CadeiraProfessorResponseDTO removeProfessorDaCadeira(CadeiraProfessorRequestDTO dto){
        Cadeira cadeira = cadeiraRepository.getReferenceById(dto.idCadeira());
        Professor professor = professorRepository.getReferenceById(dto.idProfessor());

        if(!cadeira.getProfessores().contains(professor)){
            throw new IllegalArgumentException("Esse professor nao esta na cadeira");
        }

        cadeira.removeProfessorDaCadeira(professor);
        materialRepository.removeMaterialDoProfessorReferenteACadeira(professor.getId(), cadeira.getId());

        cadeiraRepository.save(cadeira);

        return new CadeiraProfessorResponseDTO(dto.idCadeira(), cadeira.getNome(), professor.getNome());
    }
}
