package uz.pdp.appcinemarest.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.appcinemarest.aouth.CustomOauthUser;
import uz.pdp.appcinemarest.aouth.CustomerOAuth2UserService;
import uz.pdp.appcinemarest.security.JwtFilter;
import uz.pdp.appcinemarest.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;


// Zuhridin Bakhriddinov 4/5/2022 11:32 AM

@Configuration
@EnableWebSecurity
/*@EnableOAuth2Sso*/
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthService authService;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    AuthService userService;

    @Autowired
    CustomerOAuth2UserService customerOAuth2UserService;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
  /*      http.authorizeRequests()
                .antMatchers("/", "/login", "/oauth/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .oauth2Login()

                .userInfoEndpoint()
                .userService(customerOAuth2UserService)
                .and().successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        Principal oauthUser = (Principal) authentication.getPrincipal();
                        System.out.println(oauthUser.getName());
                       // System.out.println(oauthUser.getAttributes());
                     //   userService.processOAuthPostLogin(oauthUser.getEmail());
                        response.sendRedirect("/success");

                    };
                });*/

/*        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/movie")
//                    .hasRole("ADMIN")
                .antMatchers("/api/movie", "/")
                .permitAll()
//                    .hasRole("ADMIN")
//                    .authenticated()
//                .antMatchers("/api/**")
//                    .permitAll()
                .anyRequest()
                .authenticated()
                .and()

                .oauth2Login()
                .defaultSuccessUrl("/success-url-oauth", true)
                .failureUrl("/error")
                .loginPage("/login").permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/success-url",true)
                .failureUrl("/error")
                .loginPage("/login").permitAll()
                .and()
                .httpBasic();*/

        http.
                csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest()
              .permitAll();
//                .authenticated();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);




    }


}
