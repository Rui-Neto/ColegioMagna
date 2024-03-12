package colegio.magna.api.domain.professor;

public record DadosListagemProfessor(Long id, String nome, String email, Disciplina disciplina) {

    public DadosListagemProfessor(Professor professor) {
        this(professor.getId(), professor.getNome(), professor.getEmail(), professor.getDisciplina());
    }

}