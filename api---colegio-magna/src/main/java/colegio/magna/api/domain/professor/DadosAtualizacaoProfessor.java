package colegio.magna.api.domain.professor;

import colegio.magna.api.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProfessor(
    @NotNull
    Long id,
    String nome,
    String idade,
    DadosEndereco endereco) {
	
}