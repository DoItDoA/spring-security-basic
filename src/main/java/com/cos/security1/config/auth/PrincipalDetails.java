package com.cos.security1.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어준다 (Security ContextHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User정보가 있어야 됨
// User오브젝트타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());
        /*
         new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        }
        * */
        return collect;
    }

    @Override
    public String getPassword() { // 계정의 패스워드를 반환
        return user.getPassword();
    }

    @Override
    public String getUsername() { // 계정의 이름을 반환
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료되었는지를 판단(true 반환하면 만료되지 않았음)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겨있지 않은지를 판단(true 반환하면 잠겨있지 않음)
        return true;
    }

    @Override 
    public boolean isCredentialsNonExpired() { // 계정 비밀번호가 만료되었는지를 판단(true 반환하면 만료되지 않았음)
        return true;
    }

    @Override 
    public boolean isEnabled() { // 계정이 사용가능한지 판단(true 반환하면 사용가능)

        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함
        // 현재시간 - 로그인시간 => 1년을 초과하면 return false

        return true;
    }
}
