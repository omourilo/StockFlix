package com.stockFlix.previsao;

public record PrevisaoDTO(
		Long id,
		int qtdPrevista,
		String inicioPeriodo,
		String fimPeriodo,
		String criadoEm,
		Long produtoId
		) {
	public PrevisaoDTO(Previsao previsao) {
		this(
				previsao.getId(),
				previsao.getQtdPrevista(),
				previsao.getInicioPeriodo().toString(),
				previsao.getFimPeriodo().toString(),
				previsao.getCriadoEm().toString(),
				previsao.getProduto() != null ? previsao.getProduto().getId() : null
				);
	}

}
