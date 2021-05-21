package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Erro erro = new Erro();
		erro.setStatus(status.value());
		erro.setDataHora(LocalDateTime.now());
		erro.setTitulo("Foram informados campos invalidos!");
		List<Campo> campos = new ArrayList<Campo>();
		for (ObjectError campo : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) campo).getField();
			String message = messageSource.getMessage(campo, LocaleContextHolder.getLocale());
			campos.add(new Campo(nome, message));
		}
		erro.setCampo(campos);
		return super.handleExceptionInternal(ex, erro, headers, status, request);
	}

	@Data
	public class Erro {
		private Integer status;
		private LocalDateTime dataHora;
		private String titulo;
		private List<Campo> campo;
	}

	@Data
	@AllArgsConstructor
	public class Campo {
		private String nome;
		private String message;
	}
}