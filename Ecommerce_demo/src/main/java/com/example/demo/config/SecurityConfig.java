package com.example.demo.config;

import com.example.demo.filter.JwtAuthenticationFilter;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,//@RolesAllowed
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, MyAuthenticationEntryPoint myAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        StringBuffer urlxyz = new StringBuffer();//eta antMatchers e bosiya dibo...
//        List<Map<String, String>> listRoleUrlmap = new ArrayList<>();
//        listRoleUrlmap.forEach(stringStringMap -> {
//            if (stringStringMap.containsKey("xyz")){///xyz is a role
//                urlxyz.append(stringStringMap.get("xyz"));
//            }
//        });
        String allPrefix = "/*";
        http.cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//.and().next
        for (String url : UrlUtil.permitAllUrl) {
            http.authorizeRequests()
                    .antMatchers(url)
                    .permitAll();
        }
        //  http.authorizeRequests().antMatchers(UrlConstraint.ProductManagement.ROOT + UrlConstraint.ProductManagement.CREATE)//
        //.hasRole("ADMIN")//
//                .antMatchers(UrlConstraint.ProductManagement.ROOT + UrlConstraint.ProductManagement.CREATE)//
//                .hasRole("xyz")//chile avabe add korte parbo.
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

