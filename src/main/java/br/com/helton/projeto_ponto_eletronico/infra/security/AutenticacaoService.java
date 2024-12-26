package br.com.helton.projeto_ponto_eletronico.infra.security;

import br.com.helton.projeto_ponto_eletronico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService { //essa classe representa o serviço de autenticação do projeto
    //ao implementar UserDetailsService o Spring, através da classe AuthenticationManager, chama a classe AutenticacaoService automaticamente no momento que o usuário fizer o login (autenticação)
    @Autowired
    private UsuarioRepository usuarioRepository;

    //esse método o spring chama automaticamente sempre que o usuário fizer login
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));
    }
}
