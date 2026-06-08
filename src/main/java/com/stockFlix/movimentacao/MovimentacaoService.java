package com.stockFlix.movimentacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stockFlix.excecoes.DisabledEntityException;
import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.produto.Produto;
import com.stockFlix.produto.ProdutoRepository;
import com.stockFlix.usuario.Usuario;
import com.stockFlix.usuario.UsuarioRepository;

@Service
public class MovimentacaoService {
    
    private final MovimentacaoRepository movimentacaoRepo;
    private final ProdutoRepository produtoRepo;
    private final UsuarioRepository usuarioRepo;

    public MovimentacaoService(
        MovimentacaoRepository movimentacaoRepo,
        ProdutoRepository produtoRepo,
        UsuarioRepository usuarioRepo
    ) {
        this.movimentacaoRepo = movimentacaoRepo;
        this.produtoRepo = produtoRepo;
        this.usuarioRepo= usuarioRepo;
    }

    public MovimentacaoDTO createMovimentacao(MovimentacaoDTO movimentDTO) {
        Movimentacao movimentEntity = new Movimentacao(movimentDTO);
        Produto produtoEntity = produtoRepo.findById(movimentDTO.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
        if (!produtoEntity.getAtivo()) throw new DisabledEntityException("Produto inativo!!");

        Usuario usuarioEntity = usuarioRepo.findById(movimentDTO.usuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado!"));
        if (!usuarioEntity.getAtivo()) throw new DisabledEntityException("Usuario inativo!!");

        movimentEntity.setProduto(produtoEntity);
        movimentEntity.setUsuario(usuarioEntity);
        
        //Se for true é adição
        if (movimentEntity.getTipoMovimentacao()) {
            produtoEntity.adicionarQuantidade(movimentDTO.qtdMovimentada());
        }else {
        	produtoEntity.removerQuantidade(movimentDTO.qtdMovimentada());
        }

        produtoRepo.save(produtoEntity);
        return new MovimentacaoDTO(movimentacaoRepo.save(movimentEntity));
    }
    
    public MovimentacaoDTO getMovimentacaoById(long id) {
    	return new MovimentacaoDTO(movimentacaoRepo.findById(id)
    			.orElseThrow(() -> new NotFoundException("Movimentação não encontrada!")));
    }
    
    public List<MovimentacaoDTO> getAllMovimentacoes() {
    	return movimentacaoRepo
    			.findAll()
    			.stream()
    			.map(movi -> new MovimentacaoDTO(movi))
    			.toList();
    }
    
    public List<MovimentacaoDTO> getAllMovimentacaoByProdutoId(long id) {
    	return movimentacaoRepo
    			.findAllByProdutoId(id)
    			.stream()
    			.map(movi -> new MovimentacaoDTO(movi))
    			.toList();
    }

    public List<MovimentacaoDTO> getAllMovimentacaoByUsuarioId(long id) {
    	return movimentacaoRepo
    			.findAllByUsuarioId(id)
    			.stream()
    			.map(movi -> new MovimentacaoDTO(movi))
    			.toList();        
    }
    
    public List<MovimentacaoDTO> getAllMovimentacaoByData(String dataInicio, String dataFim) {
    	return movimentacaoRepo
    			.findByDataBetween(LocalDate.parse(dataInicio), LocalDate.parse(dataFim))
    			.stream()
    			.map(movi -> new MovimentacaoDTO(movi))
    			.toList();
    }
    
    public void deleteMovimentacao(long id) {
    	Movimentacao movimentEntity = movimentacaoRepo.findById(id)
    			.orElseThrow(() -> new NotFoundException("Movimentação não encontrada!"));
        Produto produtoEntity = produtoRepo.findById(movimentEntity.getProduto().getId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));

        //Se for uma movimentação de adição, remove o que foi adicionado ou vise versa
        if (movimentEntity.getTipoMovimentacao()) {
            produtoEntity.removerQuantidade(movimentEntity.getQtdMovimentada());
        }else {
        	produtoEntity.adicionarQuantidade(movimentEntity.getQtdMovimentada());
        }
    	
        produtoRepo.save(produtoEntity);
    	movimentacaoRepo.delete(movimentEntity);
	
    }
}
