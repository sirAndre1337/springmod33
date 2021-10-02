package br.com.curso.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.api.model.Usuario;
import br.com.curso.api.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/index" , produces = "application/json")
	public String init () {
		
		return "Olá Spring Boot";
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<Usuario> salvarUsuario(@RequestParam("nome") String nome , @RequestParam("login") String login , @RequestParam("senha") String senha) {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		
		Usuario usuSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuSalvo , HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/findAll")
	public ResponseEntity<List<Usuario>> findAll(){
		
		List<Usuario> lista = usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/")
	public void salvaUsuario(@RequestBody Usuario usuario) {
		
		for (int pos = 0; pos < usuario.getCarros().size(); pos++) {
			usuario.getCarros().get(pos).setUsuario(usuario);
		}
		
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		
		usuarioRepository.save(usuario);
	}
	
	@PutMapping(value = "/")
	public ResponseEntity<Usuario> atualizaUsuario(@RequestBody Usuario usuario){
		
		for (int pos = 0; pos < usuario.getCarros().size(); pos++) {
			usuario.getCarros().get(pos).setUsuario(usuario);
		}
		
		Usuario usuarioTemp = usuarioRepository.findById(usuario.getId()).get();
		
		/*Verifica se o usuário quer atualizar a senha, e criptografa a nova senha*/
		if (!usuarioTemp.getSenha().equals(usuario.getSenha())) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		}
		
		Usuario save = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(save , HttpStatus.OK);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public String deletarUsuario(@PathVariable Long id) {
		usuarioRepository.deleteById(id);
		return "Usuario Excluido com sucesso";
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> buscaPorId(@PathVariable Long id){
		Usuario usuario = usuarioRepository.findById(id).get();
		return new ResponseEntity<Usuario>(usuario , HttpStatus.OK);
	}
	
}
