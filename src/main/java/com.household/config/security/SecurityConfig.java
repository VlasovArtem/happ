package com.household.config.security;

import com.household.config.security.authentication.MongoTokenRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Collections;

/**
 * Created by artemvlasov on 21/10/15.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan("com.household.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private HappAuthenticationEntryPoint entryPoint;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public HappUserDetailsService happUserDetailsService () {
        return new HappUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(happUserDetailsService());
        return provider;
    }

    @Bean
    public ProviderManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        MongoTokenRepositoryImpl db = new MongoTokenRepositoryImpl(mongoTemplate);
        return db;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/style/**", "/js/**", "/app/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/rest/user/login", "/rest/user/registration", "/signup", "/rest/user/authentication")
                .permitAll()
                .anyRequest()
                .hasAnyAuthority("ADMIN", "USER")
                .and()
                    .formLogin().loginPage("/").loginProcessingUrl("/rest/user/login").failureHandler(new SimpleUrlAuthenticationFailureHandler())
                    .passwordParameter("password").usernameParameter("loginData").permitAll()
                .and()
                    .logout().logoutUrl("/rest/user/logout").logoutSuccessUrl("/")
                .and()
                    .rememberMe().tokenValiditySeconds(604800).rememberMeParameter("rememberMe")
                    .userDetailsService(happUserDetailsService()).tokenRepository(persistentTokenRepository());
//                .antMatchers("/admin/persons", "/rest/person/admin/**", "/rest/card/delete",
//                        "/rest/tag/custom/admin/**")
//                .hasAuthority("ADMIN")
//                .antMatchers("/account/update", "/account", "/card/add", "/rest/card/**",
//                        "/rest/person/**", "/rest/card/booking/**", "/rest/tag/custom/**")
//                .hasAnyAuthority("USER", "ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin().loginPage("/signin").loginProcessingUrl("/rest/person/login")
//                .passwordParameter("password").failureHandler(new SimpleUrlAuthenticationFailureHandler()).usernameParameter("loginData").permitAll()
//                .and()
//                .logout().logoutUrl("/rest/person/logout").logoutSuccessUrl("/")
//                .and()
//                .rememberMe().tokenValiditySeconds(604800).rememberMeParameter("rememberMe")
//                .userDetailsService(happUserDetailsService()).tokenRepository(persistentTokenRepository());
    }
}
