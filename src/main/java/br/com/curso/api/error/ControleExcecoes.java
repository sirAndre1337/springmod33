package br.com.curso.api.error;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler{

	// Tratamento geral (maioria dos erros)
	@ExceptionHandler({Exception.class , RuntimeException.class , Throwable.class , MaxUploadSizeExceededException.class})
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String msg = "";
		
		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> lista = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for (ObjectError objectError : lista) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else {
			msg = ex.getMessage();
		}
		
		ObjetoError objetoError = new ObjetoError();
		objetoError.setError(msg);
		objetoError.setCode(status.value() + " ==>" + status.getReasonPhrase());
		
		return new ResponseEntity<>(objetoError , headers , status);
	}
}
