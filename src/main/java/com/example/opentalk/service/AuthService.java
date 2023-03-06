package com.example.opentalk.service;


import com.example.opentalk.Security.JwtToken;
import com.example.opentalk.dto.CreateEmployeeRequest;
import com.example.opentalk.dto.GetEmployeeDTO;
import com.example.opentalk.dto.LoginRequest;
import com.example.opentalk.entity.*;
import com.example.opentalk.mapper.EmployeeMapper;
import com.example.opentalk.model.KeyModel;
import com.example.opentalk.model.MailEvent;
import com.example.opentalk.model.ResponseLogin;
import com.example.opentalk.model.Status;
import com.example.opentalk.repository.EmployeeRepository;
import com.example.opentalk.repository.IpBlockRepository;
import com.example.opentalk.repository.RefreshTokenRepository;
import com.example.opentalk.repository.TokenToConfirmEmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthService {

    private final Environment env;
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final JwtToken jwtToken;
    private final EmployeeMapper employeeMapper;
    private final TokenToConfirmEmailRepository tokenToConfirmEmailRepository;
    private final IpBlockRepository ipBlockRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MailService mailService;

    @PostConstruct
    public void testRun() {
        System.out.println("Init AuthService");
    }


    private boolean isIpBlocked(String ipAddress) {
        Date now = new Date();
        IpBlock ipBlock = ipBlockRepository.findIpBlockByID(ipAddress);
        long justSendRequestOn = TimeUnit.MINUTES.toMillis(10),
                timeBlock = TimeUnit.MINUTES.toMillis(5);

        if(ipBlock != null){
            Boolean HigherThan3 = ipBlock.getCount() >= 3;
            if(ipBlock.isBlocked() && ipBlock.getTimeBlock().getTime() + timeBlock <= now.getTime()){
                ipBlock.setBlocked(false);
                ipBlock.setStartTimeSendRequest(now);
                ipBlock.setCount(1);
            }else if (ipBlock.getStartTimeSendRequest().getTime() + justSendRequestOn > now.getTime() && HigherThan3) {
                ipBlock.setBlocked(true);
                ipBlock.setTimeBlock(now);
            }else if(ipBlock.getStartTimeSendRequest().getTime() + justSendRequestOn < now.getTime() && !HigherThan3) {
                ipBlock.setStartTimeSendRequest(now);
                ipBlock.setCount(1);
            } else {
                ipBlock.setCount(ipBlock.getCount() + 1);
            }
        }else {
            ipBlock = new IpBlock(ipAddress, 1, now, false);
        }
        ipBlockRepository.save(ipBlock);
        return ipBlock.isBlocked();
    }

    @Transactional
    public void saveTokenConfirmEmail(CreateEmployeeRequest c) throws Status {
        Employee employee = employeeRepository.findEmployeeByName(c.getName());
        TokenToConfirmEmail tokenToConfirmEmail = new TokenToConfirmEmail();
        String token = UUID.randomUUID().toString();
        tokenToConfirmEmail.setToken_id(token);
        tokenToConfirmEmail.setEmployee(employee);
        tokenToConfirmEmailRepository.save(tokenToConfirmEmail);
        String mailContent = "http://localhost:8082/auth/confirm/" + token;
        MailEvent mailEvent = new MailEvent(this, "Thư xác nhận email", mailContent, c.getEmail());
        mailService.sendMailWelcome(mailEvent.getMailName(), mailEvent.getContent(), mailEvent.getName());
//        applicationEventPublisher.publishEvent(mailEvent);
    }


    public Status register(CreateEmployeeRequest createEmployeeRequest) throws Exception {
        if (employeeRepository.findEmployeeByName(createEmployeeRequest.getName()) != null)
            throw new Status(HttpStatus.BAD_REQUEST, "tên tài khoản người dùng đã tồn tại");
        if (employeeRepository.findEmployeeByEmail(createEmployeeRequest.getEmail()) != null)
            throw new Status(HttpStatus.BAD_REQUEST, "Email người dùng đã tồn tại");
        try {
            Employee e = employeeMapper.MapDtoToEmployee(createEmployeeRequest);
            employeeRepository.save(e);
            saveTokenConfirmEmail(createEmployeeRequest);
            return new Status(HttpStatus.OK, "Đăng ký thành công, xác nhận email của bn");
        }catch (Exception e){
            System.out.println(e);
            throw new Status(HttpStatus.INTERNAL_SERVER_ERROR, "lỗi");
        }
    }

    public ResponseLogin login(LoginRequest loginRequest) throws Status {
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }catch (Exception e){
            System.out.println(e);
            throw new Status(HttpStatus.BAD_REQUEST, "Tài khoản hoặc mật khẩu không chính xác");
        }
        try {
            GetEmployeeDTO employee= employeeRepository.findEmployeeNotDecript(loginRequest.getUsername());
            String token = jwtToken.generateToken(employee.getName());
            RefreshToken refreshToken = jwtToken.generateRefreshToken(employee.getName());
            return ResponseLogin.builder()
                    .status(HttpStatus.OK)
                    .authenticationToken(token)
                    .refreshToken(refreshToken.getToken())
                    .username(employee.getName())
                    .role(employee.getRole())
                    .build();
        }catch (Exception e){
            System.out.println(e);
            throw new Status(HttpStatus.INTERNAL_SERVER_ERROR, "lỗi server");
        }
    }

    public Employee getCurrentUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return employeeRepository.findEmployeeByName(principal.getUsername());
    }

    public Status confirmEmail(String token) throws Status{
        try {
            TokenToConfirmEmail tokenToConfirmEmail = tokenToConfirmEmailRepository.findTokenToConfirmEmailByToken_id(token);
            Employee employee = employeeRepository.findEmployeeByName(tokenToConfirmEmail.getEmployee().getName());
            if(tokenToConfirmEmail != null){
                tokenToConfirmEmail.getEmployee().setEnable(true);
                tokenToConfirmEmailRepository.delete(tokenToConfirmEmail);
                System.out.println(employee.getTokenToConfirmEmail().getToken_id());
                return new Status(HttpStatus.OK, "Xác nhận email thành công");
            }else {
                return new Status(HttpStatus.BAD_REQUEST, "Không thành công");
            }
        }catch (Exception e){
            System.out.println(e);
            throw new Status(HttpStatus.INTERNAL_SERVER_ERROR,"lỗi");
        }
    }

    public String refreshToken(String token) throws Status {
        String rfToken = refreshTokenRepository.findRefreshTokenByToken(token).getToken();
        jwtToken.validateToken(rfToken);
        String username = jwtToken.getUsernameFromJWT(rfToken);
        return jwtToken.generateToken(username);
    }
}