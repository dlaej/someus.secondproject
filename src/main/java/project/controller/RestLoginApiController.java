package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.dto.MemberDto;
import project.service.LoginService;

@RestController
public class RestLoginApiController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/api/regist")
	public ResponseEntity<Object> regist(@RequestBody MemberDto memberDto) throws Exception {
		int registedCount = loginService.registUser(memberDto);
		if (registedCount > 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body(registedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(registedCount);
		}
	}
	
}
