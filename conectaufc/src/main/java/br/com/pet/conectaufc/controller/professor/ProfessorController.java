package br.com.pet.conectaufc.controller.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorRequestDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorResponseDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorUpdateDTO;
import br.com.pet.conectaufc.service.professor.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @GetMapping("/all")
    public ResponseEntity<Page<ProfessorResponseDTO>> buscarTodosOsProfessores(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(professorService.listaTodosOsProfessores(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscaProfessorPeloId(@PathVariable Long id){
        return ResponseEntity.ok(professorService.buscaProfessorPorId(id));
    }

    @GetMapping("/cadeira/{id}")
    public ResponseEntity<Page<ProfessorResponseDTO>> buscaProfessoresDeDeterminadaCadeira(@PathVariable Long id,
                                                                                     @RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(professorService.listaProfessoresDeUmaDeterminadaCadeira(id, PageRequest.of(page, size)));
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessorResponseDTO> criaProfessor(@RequestBody ProfessorRequestDTO dto, UriComponentsBuilder uriComponentsBuilder){
        ProfessorResponseDTO professor = professorService.criaProfessor(dto);
        var uri = uriComponentsBuilder.path("{id}").buildAndExpand(professor.id()).toUri();

        return ResponseEntity.created(uri).body(professor);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfessorResponseDTO> atualizaProfessor(@RequestBody ProfessorUpdateDTO dto){
        return ResponseEntity.ok(professorService.atualizaNomeDoProfessor(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletaProfessor(@RequestParam("id") Long id){
        professorService.deletaProfessor(id);
        return ResponseEntity.noContent().build();
    }

}
