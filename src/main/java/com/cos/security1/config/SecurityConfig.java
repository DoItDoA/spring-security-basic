package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다. 스프링 시큐리티 필터는 아래에 내가 작성한 내용이다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 , preAuthorize or postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    } // 패스워드 암호화 설정

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // rest api에서는 csrf 공격으로부터 안전하고 매번 api 요청으로부터 csrf 토큰을 받지 않아도 되어 이 기능을 disable() 하는 것
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // user이하 경로는 들어가려면 인증이 필요하다
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // hosRole을 이용하여 해당 권한이 있는 것만 들어갈 수 있다
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 나머지 경로는 인증이 없어도 된다
                .and()
                .formLogin() // formLogin 쓰고 하위 설정하기
                .loginPage("/loginForm") // 권한, 인증없이 페이지로 이동하면 자동으로 loginForm 이동
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다. PrincipalDetailsService로 이동
                .defaultSuccessUrl("/"); // 로그인이 완료되면 이 경로로 이동
    }
}
