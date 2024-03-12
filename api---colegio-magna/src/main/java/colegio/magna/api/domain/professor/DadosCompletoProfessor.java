package colegio.magna.api.domain.professor;

import colegio.magna.api.domain.endereco.Endereco;

public record DadosCompletoProfessor(Long id, String nome, String cpf, String idade, String email,
		Disciplina disciplina, Endereco endereco) {

	public DadosCompletoProfessor(Professor professor) {
		this(professor.getId(), professor.getNome(), professor.getCpf(), professor.getIdade(), professor.getEmail(), professor.getDisciplina(), professor.getEndereco());
	}

}