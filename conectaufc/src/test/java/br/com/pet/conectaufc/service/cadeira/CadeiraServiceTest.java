package br.com.pet.conectaufc.service.cadeira;

import br.com.pet.conectaufc.dto.cadeira.CadeiraRequestDTO;
import br.com.pet.conectaufc.dto.cadeira.CadeiraResponseDTO;
import br.com.pet.conectaufc.repository.cadeira.CaderiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class CadeiraServiceTest {

    @Mock
    CaderiaRepository caderiaRepository;

    @InjectMocks
    CadeiraService cadeiraService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar cadeira com sucesso")
    void criaCadeira(){
        CadeiraRequestDTO dtoArgumento = new CadeiraRequestDTO("calculo");
        CadeiraResponseDTO dtoResultado = new CadeiraResponseDTO(1L, "calculo");

        assertThat(cadeiraService.criaCadeira(dtoArgumento).nome()).isEqualTo(dtoResultado.nome());
    }

}