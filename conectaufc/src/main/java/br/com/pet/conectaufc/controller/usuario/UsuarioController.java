package br.com.pet.conectaufc.controller.usuario;

import br.com.pet.conectaufc.dto.usuario.LoginDTO;
import br.com.pet.conectaufc.dto.usuario.RegisterDTO;
import br.com.pet.conectaufc.infra.security.TokenService;
import br.com.pet.conectaufc.model.usuario.Usuario;
import br.com.pet.conectaufc.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

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

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO dto){
        if(usuarioService.loadUserByUsername(dto.username()) != null){
            throw new RuntimeException("Já existe um usuario com esse username");
        }

        usuarioService.salvaUsuario(dto.username(), dto.password(), dto.role());

        return ResponseEntity.ok().build();
    }
}
