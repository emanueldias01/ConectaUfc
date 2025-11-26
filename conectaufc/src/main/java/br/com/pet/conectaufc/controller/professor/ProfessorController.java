package br.com.pet.conectaufc.controller.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorRequestDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorResponseDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorUpdateDTO;
import br.com.pet.conectaufc.service.professor.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/professores")
@SecurityRequirement(name = "bearerAuth")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @GetMapping("/")
    @Operation(description = "busca todos os professores")
    public ResponseEntity<Page<ProfessorResponseDTO>> buscarTodosOsProfessores(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(professorService.listaTodosOsProfessores(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(description = "busca professor pelo id")
    public ResponseEntity<ProfessorResponseDTO> buscaProfessorPeloId(@PathVariable Long id){
        return ResponseEntity.ok(professorService.buscaProfessorPorId(id));
    }

    @GetMapping("/cadeira/{id}")
    @Operation(description = "busca professores que lecionam determinada cadeira")
    public ResponseEntity<Page<ProfessorResponseDTO>> buscaProfessoresDeDeterminadaCadeira(@PathVariable Long id,
                                                                                     @RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(professorService.listaProfessoresDeUmaDeterminadaCadeira(id, PageRequest.of(page, size)));
    }

    @PostMapping("/")
    @Operation(description = "cria professor")
    public ResponseEntity<ProfessorResponseDTO> criaProfessor(@RequestBody ProfessorRequestDTO dto){
        ProfessorResponseDTO professor = professorService.criaProfessor(dto);

        URI location = URI.create("/professores/" + dto.nome());

        return ResponseEntity.created(location).body(professor);
    }

    @PutMapping("/")
    @Operation(description = "atualiza nome de professor")
    public ResponseEntity<ProfessorResponseDTO> atualizaProfessor(@RequestBody ProfessorUpdateDTO dto){
        return ResponseEntity.ok(professorService.atualizaNomeDoProfessor(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "deleta professor e seu material")
    public ResponseEntity<Void> deletaProfessor(@PathVariable("id") Long id){
        professorService.deletaProfessor(id);
        return ResponseEntity.noContent().build();
    }

}
