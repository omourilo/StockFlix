package com.stockFlix.usuario;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockFlix.excecoes.LoginAlreadyExistsException;
import com.stockFlix.excecoes.NotFoundException;

import jakarta.transaction.Transactional;

/**
 * Serviço responsável pelas regras de negócio relacionadas à entidade Usuario.
 *
 * <p>Realiza operações de criação, leitura, atualização e remoção (CRUD),
 * além de gerenciar a conversão entre {@code Usuario} e {@code UsuarioDTO}.</p>
 */
@Service
public class UsuarioService {
	
	//instanciamento do repositorio do usuario no service
	private final UsuarioRepository usuarioRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	
	/**
	 * Construtor responsável pela ligação com o Repository
	 * 
	 * @param usuarioRepository repositório de acesso aos dados de Usuario
	 */
	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * Método para salvamento do usuario no banco de dados.
	 * 
	 * <p>Instância uma entidade usuario através de um DTO do usuario,
	 * fornecido pelo Controller, e faz a persistência dessa entidade
	 * no banco de dados. 
	 * Além de retornar um UsuarioDTO persistido contendo o id gerado.</p>
	 * 
	 * @param usuarioDTO dados do usuario a serem salvos no banco
	 * @return usuarioDTO contendo os dados persistidos
	 */
	public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {

		if(usuarioRepository.findByLogin(usuarioDTO.login()).isPresent() ) {
			throw new LoginAlreadyExistsException("Email já registrado no banco!"); 
		}

		Usuario usuarioEntity = new Usuario(usuarioDTO);
		usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
		return new UsuarioDTO(usuarioRepository.save(usuarioEntity));	
	

	}
	
	
	
	/**
	 * Método para a atualização do usuario no banco de dados.
	 * 
	 * <p>Ele verifica se o usuario que o ID referencia realmente existe, 
	 * se não existe, lança {@code NotFoundException},
	 * se existe, ele cria uma entidade nova, com os dados do usuarioDTO -  
	 * que deve conter os dados atualizados e os dados antigos - 
	 * e altera o ID dessa entidade para o ID passado como parâmetro,
	 * retornando esse usuarioDTO com os dados persistidos no banco.</p>
	 * 
	 * @param id o ID referente ao usuario que será atualizado
	 * @param usuarioDTO os dados do usario, contendo tanto os dados atualizados como os dados antigos
	 * @return usuarioDTO contendo os dados persistidos com o ID alterado
	 * @throws NotFoundException caso a busca não encontre resultado
	 */
	@Transactional 
	public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
		
		/**
		 * Para parar a execução de update, 
		 * ele já faz uma busca com {@code .orElseThrow} 
		 * para lançar a exceção caso não encotre o usuario e parar a execução
		 */
		Usuario usuarioEntity = usuarioRepository.findById(id)
						.orElseThrow(() -> new NotFoundException("Usuario não encontrado!!"));

		usuarioEntity.setLogin(usuarioDTO.login());
		usuarioEntity.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
		usuarioEntity.setAcessoADM(usuarioDTO.acessoADM());
		
		return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
	}
	
	/**
	 * Método para ler todos os usuarios do banco.
	 * 
	 * <p>Cria um novo usuario DTO para cada usuario do banco e retorna como uma lista.</p>
	 * 
	 * @return List de {@code UsuarioDTO} com todos os usuarios do banco
	 */
	public List<UsuarioDTO> readAllUsuarios(){
		return usuarioRepository.findAll()
				.stream()
				.map(user -> new UsuarioDTO(user))
				.toList();
	}
	
	/**
	 * Método para buscar um usuario em especifico.
	 * 
	 * <p>Usa o ID como paramêtro para realizar a busca e retorna-lo como um {@code UsuarioDTO}.
	 * Caso a busca não tenha retorno, lança a {@code NotFoundException}.</p>
	 * 
	 * @param id do usuario a ser buscado
	 * @return UsuarioDTO contendo os dados da busca no banco
	 * @throws NotFoundException caso a busca não encontre resultado
	 */
	public UsuarioDTO findById(Long id) {
		return new UsuarioDTO(usuarioRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuario não encontrado!!")));
	}
	
	/**
	 * Método para deletar um usuario em específico.
	 * 
	 * <p>Deleta um usuario em específico do banco, usando como referencia o ID passado.
	 * Caso a busca não tenha retorno, lança a {@code NotFoundException}.</p>
	 * 
	 * @param id do usuario a ser deletado
	 * @throws NotFoundException caso a busca não encontre resultado
	 */
	public void deleteUsuario(Long id) {
		
		/**
		 * Para parar a execução de delete, 
		 * ele já faz uma busca com {@code .orElseThrow} 
		 * para lançar a exceção caso não encotre o usuario e parar a execução
		 */
		usuarioRepository.findById(id)
						.orElseThrow(() -> new NotFoundException("Usuario não encontrado!!"));
		
		usuarioRepository.deleteById(id);
	}
	
}
