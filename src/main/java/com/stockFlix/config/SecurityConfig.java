package com.stockFlix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * Classe de configuração de segurança da aplicação.
 * 
 * <p>Define as regras de autenticação, autorização e filtros de segurança
 * utilizando o Spring Security.</p>
 * 
 * <p>Configura a aplicação para utilizar autenticação baseada em JWT,
 * sem uso de sessões (stateless).</p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;


    /**
     * Configura a cadeia de filtros de segurança.
     * 
     * <p>Define:</p>
     * <ul>
     *   <li>Desabilitação do CSRF</li>
     *   <li>Política de sessão stateless</li>
     *   <li>Regras de autorização por rota</li>
     *   <li>Adição do filtro JWT</li>
     * </ul>
     * 
     * @param http objeto de configuração de segurança HTTP
     * @return cadeia de filtros configurada
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http

        .csrf(csrf -> csrf.disable())

        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        
        .authorizeHttpRequests(auth -> auth
        	.requestMatchers("/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/usuarios/**").authenticated()//Só vou colocar as rotas exclusivas do ADMIM, o resto vai ficar authenticated, para o admim conseguir acessar as rotas tbm
            .requestMatchers(HttpMethod.POST, "/estoques").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/estoques/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/estoques/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/estoques/**").authenticated()
            .requestMatchers(HttpMethod.POST, "/setores").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/setores/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/setores/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/setores/**").authenticated())         
        .authenticationProvider(authenticationProvider())
        
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        
        .build();
    }


    /**
     * Define o codificador de senha utilizado pela aplicação.
     * 
     * <p>Utiliza o algoritmo BCrypt para armazenar senhas de forma segura.</p>
     * 
     * @return codificador de senha
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define o gerenciador de autenticação do Spring.
     * 
     * @param authenticationConfiguration configuração de autenticação
     * @return gerenciador de autenticação
     * @throws Exception em caso de erro
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /**
     * Define o provedor de autenticação baseado em banco de dados.
     * 
     * <p>Utiliza o {@link DaoAuthenticationProvider} para buscar usuários
     * e validar credenciais.</p>
     * 
     * @return provedor de autenticação configurado
     * @throws Exception em caso de erro
     */
    @Bean
    public AuthenticationProvider authenticationProvider() throws Exception {
    	
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
    	
    	authProvider.setPasswordEncoder(passwordEncoder());
    	
    	return authProvider;	
    }
}