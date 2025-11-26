package br.com.pet.conectaufc.controller.usuario;

import br.com.pet.conectaufc.dto.auth.TokenDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto){

        System.out.println(dto);
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = authenticationManager.authenticate(authenticationToken);
        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDTO dto){
        if(usuarioService.loadUserByUsername(dto.username()) != null){
            throw new RuntimeException("Já existe um usuario com esse username");
        }

        usuarioService.salvaUsuario(dto.username(), dto.password(), dto.matricula(),dto.role());
        URI location = URI.create("/users/" + dto.username());

        return ResponseEntity.created(location).build();
    }
}
