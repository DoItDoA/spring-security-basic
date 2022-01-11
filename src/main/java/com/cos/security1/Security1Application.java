package com.cos.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Security1Application {

	public static void main(String[] args) {
		SpringApplication.run(Security1Application.class, args);
	}

	// security를 의존성 추가하면 처음 실행시 localhost:8080/login으로 이동하게 된다
	// localhost:8080/login 에서 아이디는 'user' 이다
	// 하단의 실행창에 Using generated security password 의 값을 복사하여 로그인 창에서 비밀번호를 입력한다
}
