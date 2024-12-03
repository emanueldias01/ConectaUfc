package br.com.pet.conectaufc.service.cadeira;

import br.com.pet.conectaufc.dto.cadeira.CadeiraRequestDTO;
import br.com.pet.conectaufc.dto.cadeira.CadeiraResponseDTO;
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

        //verificar se a cadeira ja foi registrada

        Cadeira cadeira = new Cadeira(dto);

        repository.save(cadeira);

        return new CadeiraResponseDTO(cadeira);
    }

    public List<CadeiraResponseDTO> listaTodasAsCadeiras(){
        return repository.findAllByOrderByNomeAsc().stream().map(CadeiraResponseDTO::new).toList();
    }


}
