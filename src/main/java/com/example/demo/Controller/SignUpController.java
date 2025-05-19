package com.example.demo.Controller;

import com.example.demo.Dto.Request.OrgSignUpDto;
import com.example.demo.Dto.Request.SignUpDto;
import com.example.demo.Service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService)
    {
        this.signUpService = signUpService;
    }


    @PostMapping("/kullanici")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto)
    {
        String token = signUpService.signUp(signUpDto);
        if (token != null){
            return ResponseEntity.ok(token);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/organizator")
    public ResponseEntity<String> signUp(@RequestBody OrgSignUpDto orgSignUpDto)
    {
        String token = signUpService.signUp(orgSignUpDto);
        if (token != null){
            return ResponseEntity.ok(token);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
