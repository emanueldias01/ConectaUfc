package br.com.pet.conectaufc.controller.usuario;

import br.com.pet.conectaufc.dto.usuario.LoginDTO;
import br.com.pet.conectaufc.infra.security.TokenService;
import br.com.pet.conectaufc.model.usuario.Usuario;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = authenticationManager.authenticate(authenticationToken);
        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }
}
