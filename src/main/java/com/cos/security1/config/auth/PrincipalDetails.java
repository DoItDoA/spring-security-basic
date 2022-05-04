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

    
    @Override // 계정이 갖고있는 권한 목록을 리턴
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

    @Override // 계정의 패스워드를 반환
    public String getPassword() {
        return user.getPassword();
    }

    @Override // 계정의 이름을 반환
    public String getUsername() {
        return user.getUsername();
    }

    @Override // 계정이 만료되었는지를 판단(true 반환하면 만료되지 않았음)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정이 잠겨있지 않은지를 판단(true 반환하면 잠겨있지 않음)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 계정 비밀번호가 만료되었는지를 판단(true 반환하면 만료되지 않았음)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정이 사용가능한지 판단(true 반환하면 사용가능)
    public boolean isEnabled() {

        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함
        // 현재시간 - 로그인시간 => 1년을 초과하면 return false

        return true;
    }
}
