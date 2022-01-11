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
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 비밀번호 사용이 너무 오래됐는지 확인
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정이 활성화됐는지 확인
    public boolean isEnabled() {

        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함
        // 현재시간 - 로그인시간 => 1년을 초과하면 return false

        return true;
    }
}