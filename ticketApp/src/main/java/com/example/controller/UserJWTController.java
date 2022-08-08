package com.example.controller;

import com.example.dto.request.LoginRequest;
import com.example.dto.response.LoginResponse;
import com.example.dto.response.ODResponse;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.ResponseMessage;
import com.example.security.jwt.JwtUtils;
import com.example.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJWTController {

    private UserService userService;
    private AuthenticationManager authManager;
    private JwtUtils jwtUtils;


    /**
     * Kullanıcı kayıt işlemi
     * @param registerRequest registerRequest bir user dto'dur. Register işlemim için özel bir dto oluşturuldu.
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ODResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Kullanıcı login işlemi
     * @param loginRequest loginRequest bir user dto'dur. Login işlemim için özel bir dto oluşturuldu.
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid  @RequestBody LoginRequest loginRequest){

        Authentication authentication= authManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        String token= jwtUtils.generateJwtToken(authentication);

        LoginResponse response=new LoginResponse();
        response.setToken(token);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
