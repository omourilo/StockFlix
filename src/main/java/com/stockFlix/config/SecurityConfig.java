
import org.springframework.stereotype.Configuration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http

        .csrf(csrf -> csrf.disable())

        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/usuarios/**").authenticated()) //Só vou colocar as rotas exclusivas do ADMIM, o resto vai ficar authenticated, para o admim conseguir acessar as rotas tbm
        
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}