package com.example.opentalk.controller;


import com.example.opentalk.dto.*;
import com.example.opentalk.model.ResponseLogin;
import com.example.opentalk.model.Status;
import com.example.opentalk.service.AuthService;
import com.example.opentalk.service.EmployeeService;
import com.example.opentalk.service.TestService;
import com.example.opentalk.validate.LoginValidate;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RestTemplate restTemplate;
    private final TestService testService;
    private final EmployeeService employeeService;
    private final Gson gson;


    @PostMapping("/test")
    ResponseEntity<?> Test(@RequestBody TestDTO testDTO){
            testService.stopTask();
            testService.startTask(testDTO.getTime());
            return ResponseEntity.status(HttpStatus.OK).body(testDTO);
    }

    @PostMapping("/login")
    ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) throws InterruptedException {
        try {
            System.out.println(Thread.currentThread().getName());
            ResponseLogin responseLogin = authService.login(loginRequest);
            return ResponseEntity.status(responseLogin.getStatus()).body(gson.toJson(responseLogin));
        }catch (Status e){
            return ResponseEntity.status(e.getStatus()).body(e);
        }
    }
//    @GetMapping("/fakeHRM")
//    public Result getHRM(){
//        HRMtool hrMtool = new HRMtool("khanh", "Tran", "sdfsdfsdf");
//        Result result = new Result();
//        List<HRMtool> hrMtools = Arrays.asList(hrMtool);
//        result.setResult(hrMtools);
//        return result;
//    }

    @GetMapping("/webclient")
    public Mono<Result> callApi() {
        WebClient client = WebClient.create();
        Mono<Result> result1 = client.get()
                .uri("http://hrm-api.nccsoft.vn/api/services/app/CheckIn/GetUserForCheckIn")
                .retrieve()
                .bodyToMono(Result.class);
        System.out.println("adfasdasd");
        return result1;
    }

    @GetMapping("/resttemplate")
    public Result callRestTemplateApi(){
        final String apiUrl = "http://hrm-api.nccsoft.vn/api/services/app/CheckIn/GetUserForCheckIn";

        ResponseEntity<Result> response =
                    restTemplate.getForEntity(
                            apiUrl,
                            Result.class);

        Result employees = response.getBody();

        return employees;
    }

    @PostMapping("/register")
    private ResponseEntity<?> Register(@RequestBody CreateEmployeeRequest createEmployeeRequest) throws Exception {
        try{
            LoginValidate.usernameValidate(createEmployeeRequest.getName());
            LoginValidate.passwordValidate(createEmployeeRequest.getPassword());
//            LoginValidate.emailValidate(createEmployeeRequest.getEmail());
            Status status = authService.register(createEmployeeRequest);
            return new ResponseEntity<>(gson.toJson(status), HttpStatus.OK);
        }catch (Status e){
            return new ResponseEntity<>(e, e.getStatus());
        }


    }

    @GetMapping("/confirm/{token}")
    private ResponseEntity<Status> ConfirmEmail(@PathVariable String token){
        try{
            Status status = authService.confirmEmail(token);
            return ResponseEntity.status(status.getStatus()).body(status);
        }catch (Status e){
            return ResponseEntity.status(e.getStatus()).body(e);
        }
    }

    @PostMapping("/refreshtoken")
    private ResponseEntity<?> RefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        try{
            String token = authService.refreshToken(refreshTokenRequest.getToken());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Status e){
            return new ResponseEntity<>(e, e.getStatus());
        }
    }

}
