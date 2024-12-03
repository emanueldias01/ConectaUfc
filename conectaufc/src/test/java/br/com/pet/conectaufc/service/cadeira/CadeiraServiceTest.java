package br.com.pet.conectaufc.service.cadeira;

import br.com.pet.conectaufc.dto.cadeira.CadeiraProfessorRequestDTO;
import br.com.pet.conectaufc.dto.cadeira.CadeiraRequestDTO;
import br.com.pet.conectaufc.dto.cadeira.CadeiraResponseDTO;
import br.com.pet.conectaufc.dto.cadeira.CadeiraUpdateDTO;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.material.Material;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.cadeira.CaderiaRepository;
import br.com.pet.conectaufc.repository.material.MaterialRepository;
import br.com.pet.conectaufc.repository.professor.ProfessorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CadeiraServiceTest {

    @Mock
    CaderiaRepository caderiaRepository;

    @Mock
    ProfessorRepository professorRepository;

    @Mock
    MaterialRepository materialRepository;

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

    @Test
    @DisplayName("Nao cria cadeira por conta de nome já existente")
    void naoCriaCadeiraNomeRepetido(){
        String nomeRepetido = "calculo";
        CadeiraRequestDTO dtoRef = new CadeiraRequestDTO(nomeRepetido);
        Cadeira cadeiraRepetida = new Cadeira(dtoRef);

        when(caderiaRepository.findByNome(nomeRepetido)).thenReturn(Optional.of(cadeiraRepetida));

        Assertions.assertThrows(IllegalArgumentException.class, () -> cadeiraService.criaCadeira(dtoRef));
    }

    @Test
    @DisplayName("Atualiza nome da cadeira")
    void atualizaNomeDaCadeira(){
        String nomeAtual = "nome";
        String nomeAtualizado = "emon";

        Cadeira cadeira = new Cadeira(new CadeiraRequestDTO(nomeAtual));
        CadeiraUpdateDTO dto = new CadeiraUpdateDTO(1l, nomeAtualizado);

        when(caderiaRepository.getReferenceById(dto.id())).thenReturn(cadeira);

        assertThat(cadeiraService.atualizaNomeDaCadeira(dto).nome()).isEqualTo(nomeAtualizado);
    }

    @Test
    @DisplayName("Nao consegue atualizar nome devido a nome ja pertencer a uma cadeira")
    void naoAtualizaNomeCadeiraNomeRepetido(){
        String nomeAtual = "nome";
        String nomeAtualizado = "emon";

        Cadeira cadeira = new Cadeira(new CadeiraRequestDTO(nomeAtual));
        CadeiraUpdateDTO dto = new CadeiraUpdateDTO(1l, nomeAtualizado);

        when(caderiaRepository.getReferenceById(dto.id())).thenReturn(cadeira);
        when(caderiaRepository.findByNome(nomeAtualizado)).thenReturn(Optional.of(new Cadeira(new CadeiraRequestDTO(nomeAtualizado))));

        Assertions.assertThrows(IllegalArgumentException.class, () -> cadeiraService.atualizaNomeDaCadeira(dto));
    }

    @Test
    @DisplayName("Deve adicionar um professor a uma cadeira")
    void addProfessorEmUmaCadeira(){
        CadeiraProfessorRequestDTO dtoArgumento = new CadeiraProfessorRequestDTO(1l, 1l);
        Cadeira cadeiraRef = new Cadeira();
        Professor professorRef = new Professor();

        when(caderiaRepository.getReferenceById(dtoArgumento.idCadeira())).thenReturn(cadeiraRef);
        when(professorRepository.getReferenceById(dtoArgumento.idProfessor())).thenReturn(professorRef);

        assertThat(cadeiraService.adicionaProfessorAUmaCadeira(dtoArgumento).nomeProfessor()).isEqualTo(professorRef.getNome());
    }

    @Test
    @DisplayName("Nao cadastra professor na cadeira pois já está cadastrado")
    void naoAddProfessorJaEstaCadastrado(){
        CadeiraProfessorRequestDTO dtoArgumento = new CadeiraProfessorRequestDTO(1l, 1l);
        Cadeira cadeiraRef = new Cadeira();
        Professor professorRef = new Professor();

        when(caderiaRepository.getReferenceById(dtoArgumento.idCadeira())).thenReturn(cadeiraRef);
        when(professorRepository.getReferenceById(dtoArgumento.idProfessor())).thenReturn(professorRef);

        cadeiraRef.addProfessorNaCadeira(professorRef);

        Assertions.assertThrows(IllegalArgumentException.class, () -> cadeiraService.adicionaProfessorAUmaCadeira(dtoArgumento));

    }

    @Test
    @DisplayName("Remove professor da cadeira")
    void removeProfessorDaCadeira(){
        CadeiraProfessorRequestDTO dtoArgumento = new CadeiraProfessorRequestDTO(1l, 1l);
        Cadeira cadeiraRef = new Cadeira();
        Professor professorRef = new Professor();

        when(caderiaRepository.getReferenceById(dtoArgumento.idCadeira())).thenReturn(cadeiraRef);
        when(professorRepository.getReferenceById(dtoArgumento.idProfessor())).thenReturn(professorRef);

        cadeiraRef.addProfessorNaCadeira(professorRef);

        assertThat(cadeiraService.removeProfessorDaCadeira(dtoArgumento).nomeProfessor()).isEqualTo(professorRef.getNome());
    }

    @Test
    @DisplayName("Nao remove professor da cadeira pois ele nao esta na cadeira")
    void naoRemoveProfessorPoisNaoEstaNaCadeira(){
        CadeiraProfessorRequestDTO dtoArgumento = new CadeiraProfessorRequestDTO(1l, 1l);
        Cadeira cadeiraRef = new Cadeira();
        Professor professorRef = new Professor();

        when(caderiaRepository.getReferenceById(dtoArgumento.idCadeira())).thenReturn(cadeiraRef);
        when(professorRepository.getReferenceById(dtoArgumento.idProfessor())).thenReturn(professorRef);


        Assertions.assertThrows(IllegalArgumentException.class, () -> cadeiraService.removeProfessorDaCadeira(dtoArgumento));

    }

    @Test
    @DisplayName("Apaga material da cadeira excluida")
    void apagaMaterialCadeiraExcluida(){

        Long id = 1L;
        Cadeira cadeira = new Cadeira(new CadeiraRequestDTO("nome"));

        Professor professor = new Professor();

        Material material = new Material();

        professor.addMaterialDeProfessor(material);

        cadeira.addProfessorNaCadeira(professor);

        when(caderiaRepository.getReferenceById(id)).thenReturn(cadeira);

        cadeiraService.deletaCadeira(id);

        //simulacao de que a query deu certo
        professor.removeMaterial(material);

        assertThat(professor.hasThisMaterial(material)).isFalse();
    }

}