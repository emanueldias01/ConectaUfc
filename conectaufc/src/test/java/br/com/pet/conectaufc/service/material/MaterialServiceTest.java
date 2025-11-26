package br.com.pet.conectaufc.service.material;

import br.com.pet.conectaufc.dto.cadeira.CadeiraRequestDTO;
import br.com.pet.conectaufc.dto.material.MaterialRequestDTO;
import br.com.pet.conectaufc.dto.material.MaterialUpdateDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorRequestDTO;
import br.com.pet.conectaufc.exceptions.BussinessException;
import br.com.pet.conectaufc.exceptions.InvalidFieldsException;
import br.com.pet.conectaufc.model.cadeira.Cadeira;
import br.com.pet.conectaufc.model.material.Material;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.cadeira.CadeiraRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MaterialServiceTest {

    @Mock
    CadeiraRepository cadeiraRepository;

    @Mock
    MaterialRepository materialRepository;

    @Mock
    ProfessorRepository professorRepository;

    @InjectMocks
    MaterialService materialService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar material")
    void criaMaterial(){
        Professor professor = new Professor();
        Cadeira cadeira = new Cadeira();

        String nomeMaterial = "slide aula 1";
        Long idProfessor = 1L;
        Long idCadeira = 1L;
        String link = "googledrive.com/slide";

        MaterialRequestDTO dtoArgumento = new MaterialRequestDTO(nomeMaterial, idProfessor, idCadeira, link);

        when(professorRepository.getReferenceById(idProfessor)).thenReturn(professor);
        when(cadeiraRepository.getReferenceById(idCadeira)).thenReturn(cadeira);

        assertThat(materialService.criaMaterial(dtoArgumento).link()).isEqualTo(link);
    }

    @Test
    @DisplayName("Nao cria material devido a material já existir por conta de link repetido")
    void naoCriaMaterialRepetidoLink(){

        Long idProfessor = 1L;
        Long idCadeira = 1L;

        Professor professor = new Professor(idProfessor, "nomeProf");
        Cadeira cadeira = new Cadeira(idCadeira, "calculo");

        String nomeMaterial = "slide aula 1";

        String link = "googledrive.com/slide";

        MaterialRequestDTO dtoArgumento = new MaterialRequestDTO(nomeMaterial, idProfessor, idCadeira, link);

        when(professorRepository.getReferenceById(idProfessor)).thenReturn(professor);
        when(cadeiraRepository.getReferenceById(idCadeira)).thenReturn(cadeira);

        String nomeMaterialDiferente="1 alua edils";

        MaterialRequestDTO dtoConstrutor = new MaterialRequestDTO(nomeMaterialDiferente, idProfessor, idCadeira, link);

        Material material = new Material(dtoConstrutor, professor, cadeira);
        professor.addMaterialDeProfessor(material);

        Assertions.assertThrows(BussinessException.class, () -> materialService.criaMaterial(dtoArgumento));
    }

    @Test
    @DisplayName("Nao deve criar material devido a nome repetido de material na mesma cadeira com o mesmo professor")
    void naoCriaMaterialRepetidoNome(){
        Long idProfessor = 1L;
        Long idCadeira = 1L;

        Professor professor = new Professor(idProfessor, "nomeProf");
        Cadeira cadeira = new Cadeira(idCadeira, "calculo");

        String nomeMaterial = "slide aula 1";

        String link = "googledrive.com/slide";
        String linkArgumento = "googledrive.com/aaa";

        MaterialRequestDTO dtoArgumento = new MaterialRequestDTO(nomeMaterial, idProfessor, idCadeira, link);

        when(professorRepository.getReferenceById(idProfessor)).thenReturn(professor);
        when(cadeiraRepository.getReferenceById(idCadeira)).thenReturn(cadeira);

        MaterialRequestDTO dtoConstrutor = new MaterialRequestDTO(nomeMaterial, idProfessor, idCadeira, linkArgumento);

        Material material = new Material(dtoConstrutor, professor, cadeira);
        professor.addMaterialDeProfessor(material);

        Assertions.assertThrows(BussinessException.class, () -> materialService.criaMaterial(dtoArgumento));
    }

    @Test
    @DisplayName("Nao deve criar material devido a nome repetido de material na mesma cadeira com o mesmo professor")
    void naoCriaMaterialRepetidoNomeELink(){
        Long idProfessor = 1L;
        Long idCadeira = 1L;

        Professor professor = new Professor(idProfessor, "nomeProf");
        Cadeira cadeira = new Cadeira(idCadeira, "calculo");

        String nomeMaterial = "slide aula 1";

        String link = "googledrive.com/slide";

        MaterialRequestDTO dtoArgumento = new MaterialRequestDTO(nomeMaterial, idProfessor, idCadeira, link);

        when(professorRepository.getReferenceById(idProfessor)).thenReturn(professor);
        when(cadeiraRepository.getReferenceById(idCadeira)).thenReturn(cadeira);

        MaterialRequestDTO dtoConstrutor = new MaterialRequestDTO(nomeMaterial, idProfessor, idCadeira, link);

        Material material = new Material(dtoConstrutor, professor, cadeira);
        professor.addMaterialDeProfessor(material);

        Assertions.assertThrows(BussinessException.class, () -> materialService.criaMaterial(dtoArgumento));
    }

    @Test
    @DisplayName("Deve conseguir editar material")
    void editaMaterial(){

        Material materialRef = new Material(new MaterialRequestDTO("nome", 1L, 1L, "googledrive.com/aaa"), new Professor(), new Cadeira());

        Long idMaterial = 1L;
        String nomeAtualizado = "materialAtualizado";
        String linkAtualizado = "googledrive.com/atualizado";

        MaterialUpdateDTO dtoArgumento = new MaterialUpdateDTO(idMaterial, nomeAtualizado, linkAtualizado);

        when(materialRepository.findById(idMaterial)).thenReturn(Optional.of(materialRef));
        when(materialRepository.findByNome(dtoArgumento.nome())).thenReturn(Optional.empty());
        when(materialRepository.findByLink(dtoArgumento.link())).thenReturn(Optional.empty());

        assertThat(materialService.editaMaterial(dtoArgumento).nome()).isEqualTo(nomeAtualizado);
        assertThat(materialService.editaMaterial(dtoArgumento).link()).isEqualTo(linkAtualizado);
    }

    @Test
    @DisplayName("Nao edita material devido a nome de atualizacao estar em uso")
    void naoEditaMaterialNomeEmUso(){
        Material materialRef = new Material(new MaterialRequestDTO("nome", 1L, 1L, "googledrive.com/aaa"), new Professor(), new Cadeira());

        Long idMaterial = 1L;
        String nomeAtualizado = "materialAtualizado";
        String linkAtualizado = "googledrive.com/atualizado";

        MaterialUpdateDTO dtoArgumento = new MaterialUpdateDTO(idMaterial, nomeAtualizado, linkAtualizado);

        when(materialRepository.getReferenceById(idMaterial)).thenReturn(materialRef);
        when(materialRepository.findByNome(dtoArgumento.nome())).thenReturn(Optional.of(new Material()));
        when(materialRepository.findByLink(dtoArgumento.link())).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> materialService.editaMaterial(dtoArgumento));
    }

    @Test
    @DisplayName("Nao edita material devido a nome de atualizacao estar em uso")
    void naoEditaMaterialLinkEmUso(){
        Material materialRef = new Material(new MaterialRequestDTO("nome", 1L, 1L, "googledrive.com/aaa"), new Professor(), new Cadeira());

        Long idMaterial = 1L;
        String nomeAtualizado = "materialAtualizado";
        String linkAtualizado = "googledrive.com/atualizado";

        MaterialUpdateDTO dtoArgumento = new MaterialUpdateDTO(idMaterial, nomeAtualizado, linkAtualizado);

        when(materialRepository.findById(idMaterial)).thenReturn(Optional.of(materialRef));
        when(materialRepository.findByNome(dtoArgumento.nome())).thenReturn(Optional.empty());
        when(materialRepository.findByLink(dtoArgumento.link())).thenReturn(Optional.of(new Material()));

        Assertions.assertThrows(InvalidFieldsException.class, () -> materialService.editaMaterial(dtoArgumento));
    }

    @Test
    @DisplayName("Nao cria material devido nome e link estar em uso")
    void naoEditaMaterialLinkENomeEmUso(){
        Material materialRef = new Material(new MaterialRequestDTO("nome", 1L, 1L, "googledrive.com/aaa"), new Professor(), new Cadeira());

        Long idMaterial = 1L;
        String nomeAtualizado = "materialAtualizado";
        String linkAtualizado = "googledrive.com/atualizado";

        MaterialUpdateDTO dtoArgumento = new MaterialUpdateDTO(idMaterial, nomeAtualizado, linkAtualizado);

        when(materialRepository.findById(idMaterial)).thenReturn(Optional.of(materialRef));
        when(materialRepository.findByNome(dtoArgumento.nome())).thenReturn(Optional.of(new Material()));
        when(materialRepository.findByLink(dtoArgumento.link())).thenReturn(Optional.of(new Material()));

        Assertions.assertThrows(InvalidFieldsException.class, () -> materialService.editaMaterial(dtoArgumento));
    }
}