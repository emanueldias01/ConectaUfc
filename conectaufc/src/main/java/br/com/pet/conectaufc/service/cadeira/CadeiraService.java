package br.com.pet.conectaufc.service.cadeira;

import br.com.pet.conectaufc.dto.cadeira.CadeiraRequestDTO;
import br.com.pet.conectaufc.dto.cadeira.CadeiraResponseDTO;
import br.com.pet.conectaufc.dto.cadeira.CadeiraUpdateDTO;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.repository.cadeira.CaderiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadeiraService {
    @Autowired
    CaderiaRepository repository;

    public CadeiraResponseDTO criaCadeira(CadeiraRequestDTO dto){

        if(repository.findByNome(dto.nome()).isPresent()){
            throw new IllegalArgumentException("Cadeira já existe");
        }

        Cadeira cadeira = new Cadeira(dto);

        repository.save(cadeira);

        return new CadeiraResponseDTO(cadeira);
    }

    public List<CadeiraResponseDTO> listaTodasAsCadeiras(){
        return repository.findAllByOrderByNomeAsc().stream().map(CadeiraResponseDTO::new).toList();
    }

    public CadeiraResponseDTO atualizaNomeDaCadeira(CadeiraUpdateDTO dto){

        if (repository.findByNome(dto.nome()).isPresent()){
            throw new IllegalArgumentException("Já existe uma cadeira com o nome que está tentando atualizar");
        }

        Cadeira cadeira = repository.getReferenceById(dto.id());

        cadeira.setNome(dto.nome());

        repository.save(cadeira);

        return new CadeiraResponseDTO(cadeira);
    }
}
