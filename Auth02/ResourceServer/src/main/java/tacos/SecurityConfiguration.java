package tacos;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tacos.data.jdbc.UserRepository;

import java.util.Optional;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username ->
            Optional.ofNullable(userRepository.findByUsername(username))
                    .orElseThrow( () -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers(headers -> headers.frameOptions().disable())
                .authorizeRequests()
                .antMatchers("/design", "/orders").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/data-api/ingredients").hasAuthority("SCOPE_writeIngredients")
                .antMatchers(HttpMethod.DELETE, "/data-api/ingredients/**").hasAuthority("SCOPE_deleteIngredients")
               // .antMatchers(HttpMethod.GET, "/data-api/ingredients/**").hasRole("ADMIN")
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .antMatchers("/","/**", "/**/**").permitAll()
                .and()
                .oauth2ResourceServer(oaut2 -> oaut2.jwt())
                .csrf().ignoringRequestMatchers(PathRequest.toH2Console())
                .ignoringAntMatchers("/data-api/**")
                .and()
               // .and()

                .formLogin()
                .loginPage("/login")
                .and()
                .build();
    }
}
