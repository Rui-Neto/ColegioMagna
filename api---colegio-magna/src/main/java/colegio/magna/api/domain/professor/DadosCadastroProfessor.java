package colegio.magna.api.domain.professor;

import colegio.magna.api.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroProfessor(
		
		@NotBlank
		String nome,

		@NotBlank
		@Pattern(regexp ="\\d{11}")
		String cpf,
		
		@NotBlank
		@Pattern(regexp ="\\d{2,3}")
		String idade, 
		
		@NotBlank
		@Email
		String email,
		
		@NotNull
		Disciplina disciplina,
		
		@NotNull 
		@Valid
		DadosEndereco endereco) {

}
