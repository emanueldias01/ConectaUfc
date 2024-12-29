package br.com.pet.conectaufc.service.usuario;

import br.com.pet.conectaufc.model.usuario.Usuario;
import br.com.pet.conectaufc.model.usuario.UsuarioRole;
import br.com.pet.conectaufc.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public void salvaUsuario(String username, String password, UsuarioRole role){
        String senhaEncriptada = new BCryptPasswordEncoder().encode(password);

        usuarioRepository.save(new Usuario(username, senhaEncriptada, role));
    }
}
