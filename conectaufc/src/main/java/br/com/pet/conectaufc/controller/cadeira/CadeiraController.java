package br.com.pet.conectaufc.controller.cadeira;

import br.com.pet.conectaufc.dto.cadeira.*;
import br.com.pet.conectaufc.service.cadeira.CadeiraService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "lista todas as cadeiras em ordem alfabetica")
    public ResponseEntity<Page<CadeiraResponseDTO>> buscaTodasAsCadeiras(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(cadeiraService.listaTodasAsCadeiras(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(description = "busca cadeira pelo id")
    public ResponseEntity<CadeiraResponseDTO> buscaCadeiraPorId(@PathVariable Long id){
        return ResponseEntity.ok(cadeiraService.buscaCadeiraPeloId(id));
    }

    @PostMapping("/create")
    @Operation(description = "cria cadeira")
    public ResponseEntity<CadeiraResponseDTO> criaCadeira(@RequestBody CadeiraRequestDTO dto, UriComponentsBuilder uriComponentsBuilder){
        CadeiraResponseDTO cadeira = cadeiraService.criaCadeira(dto);
        var uri = uriComponentsBuilder.path("{id}").buildAndExpand(cadeira.id()).toUri();

        return ResponseEntity.created(uri).body(cadeira);
    }

    @PutMapping("/update")
    @Operation(description = "atualiza nome da cadeira")
    public ResponseEntity<CadeiraResponseDTO> atualizaCadeira(@RequestBody CadeiraUpdateDTO dto){
        return ResponseEntity.ok(cadeiraService.atualizaNomeDaCadeira(dto));
    }

    @PutMapping("/addProfessor")
    @Operation(description = "adiciona professor existente a cadeira")
    public ResponseEntity<CadeiraProfessorResponseDTO> adicionaProfessorAUmaCadeira(@RequestBody CadeiraProfessorRequestDTO dto){
        return ResponseEntity.ok(cadeiraService.adicionaProfessorAUmaCadeira(dto));
    }

    @PutMapping("/removeProfessor")
    @Operation(description = "remove professor da cadeira")
    public ResponseEntity<CadeiraProfessorResponseDTO> removeProfessorDaCadeira(@RequestBody CadeiraProfessorRequestDTO dto){
        return ResponseEntity.ok(cadeiraService.removeProfessorDaCadeira(dto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "deleta cadeira e todo seu material")
    public ResponseEntity deletaCadeira(@PathVariable Long id){
        cadeiraService.deletaCadeira(id);
        return ResponseEntity.noContent().build();
    }
}
