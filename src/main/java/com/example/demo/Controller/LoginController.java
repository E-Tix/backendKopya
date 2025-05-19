package com.example.demo.Controller;

import com.example.demo.Dto.Request.AdminLoginDto;
import com.example.demo.Dto.Request.LoginDto;
import com.example.demo.Dto.Request.OrgLoginDto;
import com.example.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //JWT token döndür
    @PostMapping("/user")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto)
    {
        String token = loginService.login(loginDto);
        if (token != null){
            return ResponseEntity.ok(token);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/organizator")
    public ResponseEntity<String> login(@RequestBody OrgLoginDto orgLoginDto){
        String token = loginService.login(orgLoginDto);
        if (token != null){
            return ResponseEntity.ok(token);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<String> login(@RequestBody AdminLoginDto adminLoginDto){
        String token = loginService.login(adminLoginDto);
        if (token != null){
            return ResponseEntity.ok(token);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
