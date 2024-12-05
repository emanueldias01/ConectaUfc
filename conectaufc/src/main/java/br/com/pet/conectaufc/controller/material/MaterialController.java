package br.com.pet.conectaufc.controller.material;

import br.com.pet.conectaufc.dto.material.MaterialRequestDTO;
import br.com.pet.conectaufc.dto.material.MaterialResponseDTO;
import br.com.pet.conectaufc.dto.material.MaterialUpdateDTO;
import br.com.pet.conectaufc.service.material.MaterialService;
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
    public ResponseEntity<Page<MaterialResponseDTO>> buscaTodosOsMateriais(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(materialService.listarTodosOsMateirais(PageRequest.of(page, size)));
    }

    @GetMapping("/cadeira-prof/{idCadeira}/{idProfessor}")
    public ResponseEntity<Page<MaterialResponseDTO>> buscaMateiralPorCadeiraEProfessor(@PathVariable Long idCadeira,
                                                                                       @PathVariable Long idProfessor,
                                                                                       @RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(materialService.listaTodosOsMateriaisDeCertaCadeiraEDeCertoProfessor(idCadeira, idProfessor, PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> buscaMaterialPorId(@PathVariable Long id){
        return ResponseEntity.ok(materialService.buscaMaterialPorId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MaterialResponseDTO> criaMaterial(@RequestBody MaterialRequestDTO dto, UriComponentsBuilder uriComponentsBuilder){
        MaterialResponseDTO material = materialService.criaMaterial(dto);
        var uri = uriComponentsBuilder.path("{id}").buildAndExpand(material.id()).toUri();

        return ResponseEntity.created(uri).body(material);
    }

    @PutMapping("/update")
    public ResponseEntity<MaterialResponseDTO> editaMaterial(@RequestBody MaterialUpdateDTO dto){
        return ResponseEntity.ok(materialService.editaMaterial(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletaMaterial(@RequestParam Long id){
        materialService.deletaMaterial(id);
        return ResponseEntity.noContent().build();
    }
}