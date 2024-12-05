package br.com.pet.conectaufc.controller.cadeira;

import br.com.pet.conectaufc.dto.cadeira.*;
import br.com.pet.conectaufc.service.cadeira.CadeiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cadeira")
public class CadeiraController {

    @Autowired
    CadeiraService cadeiraService;

    @GetMapping("/all")
    public ResponseEntity<Page<CadeiraResponseDTO>> buscaTodasAsCadeiras(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(cadeiraService.listaTodasAsCadeiras(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CadeiraResponseDTO> buscaCadeiraPorId(@PathVariable Long id){
        return ResponseEntity.ok(cadeiraService.buscaCadeiraPeloId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CadeiraResponseDTO> criaCadeira(@RequestBody CadeiraRequestDTO dto, UriComponentsBuilder uriComponentsBuilder){
        CadeiraResponseDTO cadeira = cadeiraService.criaCadeira(dto);
        var uri = uriComponentsBuilder.path("{id}").buildAndExpand(cadeira.id()).toUri();

        return ResponseEntity.created(uri).body(cadeira);
    }

    @PutMapping("/updateCadeira")
    public ResponseEntity<CadeiraResponseDTO> atualizaCadeira(@RequestBody CadeiraUpdateDTO dto){
        return ResponseEntity.ok(cadeiraService.atualizaNomeDaCadeira(dto));
    }

    @PutMapping("/addProfessor")
    public ResponseEntity<CadeiraProfessorResponseDTO> adicionaProfessorAUmaCadeira(@RequestBody CadeiraProfessorRequestDTO dto){
        return ResponseEntity.ok(cadeiraService.adicionaProfessorAUmaCadeira(dto));
    }

    @PutMapping("/removeProfessor")
    public ResponseEntity<CadeiraProfessorResponseDTO> removeProfessorDaCadeira(@RequestBody CadeiraProfessorRequestDTO dto){
        return ResponseEntity.ok(cadeiraService.removeProfessorDaCadeira(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletaCadeira(@PathVariable Long id){
        cadeiraService.deletaCadeira(id);
        return ResponseEntity.noContent().build();
    }
}
