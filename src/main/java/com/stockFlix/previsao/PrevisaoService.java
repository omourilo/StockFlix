package com.stockFlix.previsao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.movimentacao.MovimentacaoRepository;
import com.stockFlix.produto.Produto;
import com.stockFlix.produto.ProdutoRepository;

@Service
public class PrevisaoService {
 
 private final PrevisaoRepository previsaoRepo;
 private final MovimentacaoRepository movimentacaoRepo;
 private final ProdutoRepository produtoRepo;
 
 public PrevisaoService(
    PrevisaoRepository previsaoRepo, 
    MovimentacaoRepository movimentacaoRepo,
    ProdutoRepository produtoRepo
) {
    this.previsaoRepo = previsaoRepo;
    this.movimentacaoRepo = movimentacaoRepo;
    this.produtoRepo = produtoRepo;
 }

 public PrevisaoDTO createPrevisao(PrevisaoDTO previsaoDTO) {
    Previsao previsaoEntity = new Previsao(previsaoDTO);

    Produto produtoEntity = produtoRepo.findById(previsaoDTO.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));

    previsaoEntity.setProduto(produtoEntity);

    previsaoEntity.setQtdPrevista(this.calculoPrevisao(
                                            previsaoEntity.getProduto().getId(),
                                            previsaoEntity.getInicioPeriodo(), 
                                            previsaoEntity.getFimPeriodo()
                                        ));

    return new PrevisaoDTO(previsaoRepo.save(previsaoEntity));
 }

 public PrevisaoDTO getPrevisaoById(long id) {
    return new PrevisaoDTO(previsaoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Previsão não encontrada!")));
 }

 public List<PrevisaoDTO> getAllPrevisoes() {
    return previsaoRepo
    			.findAll()
    			.stream()
    			.map(previ -> new PrevisaoDTO(previ))
    			.toList();

 }

public List<PrevisaoDTO> getAllPrevisoesByProdutoId(long id) {
    return previsaoRepo
    			.findAllByProdutoId(id)
    			.stream()
    			.map(previ -> new PrevisaoDTO(previ))
    			.toList(); 
}

public void deletePrevisao(long id) {
    Previsao previsaoEntity = previsaoRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Previsão não encontrada!"));

    previsaoRepo.delete(previsaoEntity);
        	
}

public int calculoPrevisao(long produtoId, LocalDate dataInicio, LocalDate dataFim) {
    ArrayList<Integer> qtdsMovimentadas = new ArrayList<>();
    movimentacaoRepo.findByProdutoIdAndDataBetween(produtoId, dataInicio, dataFim)
            .stream()
            .forEach(
                m -> qtdsMovimentadas.add(m.getQtdMovimentada()));
    
    int soma = 0;
    int somaPonderada = 0;
    if(qtdsMovimentadas.isEmpty()) {
        throw new NotFoundException("Não encontrado movimentações para o período");
    }
    for(int i = 0; i < qtdsMovimentadas.size(); i++) {
        soma += qtdsMovimentadas.get(i) * (i+1);
        somaPonderada += i+1;
    }

    Double resultado = (double) soma  / somaPonderada;

    return (int) Math.round(resultado);
 }
}
