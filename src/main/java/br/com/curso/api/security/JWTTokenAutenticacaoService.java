package br.com.curso.api.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.curso.api.model.Usuario;
import br.com.curso.api.repository.UsuarioRepository;
import br.com.curso.api.rest.ApplicationContextLoad;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*Tempo de validade do token 2 dias*/
	private static final long EXPIRATION_TIME = 172800000;
	
	/*Uma senha unica para compor a autenticaçao*/
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	/*Prefixo padrão de TOKEN*/
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	/*Gerando TOKEN  de autenticação e adicionando ao cabeçalho e respota HTTP*/
	public void addAuthentication(HttpServletResponse response , String username) throws IOException {
		
		/*Montagem do TOKEN*/
		String JWT = Jwts.builder() /*Chama o gerador de TOKEN*/
				.setSubject(username) /*Adiciona o usuario que esta fazendo o login*/
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))/*Tempo de expiração*/
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();/*Compactação e algorito de geração de senha*/
		
		/*Junta o TOKEN com o prefixo (Bearer)*/
		String token = TOKEN_PREFIX + " " + JWT; // Ex: Bearer  8732kasdiuaxusahdsad__@328dsnja
		
		/*Adiciona no cabeçalho HTTP*/
		response.addHeader(HEADER_STRING, token); // Ex: Authorization: Bearer  8732kasdiuaxusahdsad__@328dsnja
		
		/*Escreve TOKEN como resposta no corpo do HTTP*/
		response.getWriter().write("{\"Authorization\": \""+token+"\"}"); // como e json precisa escapar as ASPAS
	}
	
	/*Retorna o usuário validado com o TOKEN  ou caso não seja validado retorna NULL*/
	public Authentication getAuthentication(HttpServletRequest request) {
		/*Pega o TOKEN enviado no cabeçalho HTTP*/
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
			/*Faz a validação do TOKEN do usuário na requisição*/
			String user = Jwts.parser().setSigningKey(SECRET)// nesse momento o token esta : Bearer  8732kasdiuaxusahdsad__@328dsnja
					.parseClaimsJws(tokenLimpo) // aqui ele retira o prefixo : 8732kasdiuaxusahdsad__@328dsnja
					.getBody().getSubject(); // aqui descompacta usuario. Ex : André Luis
			
			if (user != null) {
				 /*Metodo que pega o contexto em memoria do spring , assim podendo pega o usuarioRepository*/
				 Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).findUserByLogin(user);
				 
				 /*Retorna o usuário logado*/
				 if (usuario != null) {
					 
					 if (tokenLimpo.equals(usuario.getToken())) {
						 
					 return new UsernamePasswordAuthenticationToken(
							 usuario.getLogin(),
							 usuario.getSenha() ,
							 usuario.getAuthorities());
					 }
				 }
			}
		}
			return null; /*Não Autorizado*/
	}
}