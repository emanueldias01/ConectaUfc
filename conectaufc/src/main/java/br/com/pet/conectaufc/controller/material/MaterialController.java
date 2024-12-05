package br.com.pet.conectaufc.controller.material;

import br.com.pet.conectaufc.dto.material.MaterialRequestDTO;
import br.com.pet.conectaufc.dto.material.MaterialResponseDTO;
import br.com.pet.conectaufc.dto.material.MaterialUpdateDTO;
import br.com.pet.conectaufc.service.material.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    MaterialService materialService;

    @GetMapping("/all")
    @Operation(description = "busca todos os materiais")
    public ResponseEntity<Page<MaterialResponseDTO>> buscaTodosOsMateriais(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(materialService.listarTodosOsMateirais(PageRequest.of(page, size)));
    }

    @GetMapping("/cadeira-prof/{idCadeira}/{idProfessor}")
    @Operation(description = "busca todos os materiais de uma certa cadeira de um certo professor")
    public ResponseEntity<Page<MaterialResponseDTO>> buscaMateiralPorCadeiraEProfessor(@PathVariable Long idCadeira,
                                                                                       @PathVariable Long idProfessor,
                                                                                       @RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(materialService.listaTodosOsMateriaisDeCertaCadeiraEDeCertoProfessor(idCadeira, idProfessor, PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(description = "busca material pelo id")
    public ResponseEntity<MaterialResponseDTO> buscaMaterialPorId(@PathVariable Long id){
        return ResponseEntity.ok(materialService.buscaMaterialPorId(id));
    }

    @PostMapping("/create")
    @Operation(description = "cria material de um professor e cadeira existente")
    public ResponseEntity<MaterialResponseDTO> criaMaterial(@RequestBody MaterialRequestDTO dto, UriComponentsBuilder uriComponentsBuilder){
        MaterialResponseDTO material = materialService.criaMaterial(dto);
        var uri = uriComponentsBuilder.path("{id}").buildAndExpand(material.id()).toUri();

        return ResponseEntity.created(uri).body(material);
    }

    @PutMapping("/update")
    @Operation(description = "edita um material")
    public ResponseEntity<MaterialResponseDTO> editaMaterial(@RequestBody MaterialUpdateDTO dto){
        return ResponseEntity.ok(materialService.editaMaterial(dto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "deleta um material")
    public ResponseEntity deletaMaterial(@RequestParam Long id){
        materialService.deletaMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
