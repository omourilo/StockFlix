

public class SecurityConfig {

    private final JwtFilter JwtFilter;
    private final UserDetailsService userDetailsService;

    public SecurityFilterChain filterChain(HttpSecutity http) {
        http.csrf().disable();
    }
}