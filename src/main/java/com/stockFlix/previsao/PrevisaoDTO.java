package com.stockFlix.previsao;

public record PrevisaoDTO(
		Long id,
		Integer qtdPrevista,
		String inicioPeriodo,
		String fimPeriodo,
		String criadoEm,
		Long produtoId,
		String produtoNome
		) {
	public PrevisaoDTO(Previsao previsao) {
		this(
				previsao.getId(),
				previsao.getQtdPrevista(),
				previsao.getInicioPeriodo().toString(),
				previsao.getFimPeriodo().toString(),
				previsao.getCriadoEm().toString(),
				previsao.getProduto() != null ? previsao.getProduto().getId() : null,
				previsao.getProduto() != null ? previsao.getProduto().getNome() : null
				);
	}

}
