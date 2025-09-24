package com.example.security_service.service;

import com.example.security_service.dto.EmployeeDto;
import com.example.security_service.dto.PasswordResetDto;
import com.example.security_service.dto.UserDto;
import com.example.security_service.entity.Employee;
import com.example.security_service.entity.Roles;
import com.example.security_service.entity.Users;
import com.example.security_service.exception.InvalidCredentialsException;
import com.example.security_service.exception.InvalidRolesException;
import com.example.security_service.exception.InvalidTokenException;
import com.example.security_service.exception.UserAlreadyPresent;
import com.example.security_service.feignclients.ActionServiceFeignClient;
import com.example.security_service.model.LogIdPassword;
import com.example.security_service.repository.RoleRepository;
import com.example.security_service.repository.UserRepository;
import com.example.security_service.response.JwtResponse;
import com.example.security_service.util.JwtUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private ActionServiceFeignClient actionServiceFeignClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ObjectMapper objectMapper;

    public UserDto registerNewUser(UserDto userDto) {

        Optional<Users> dbUser = userRepository.findByUserName(userDto.getUserName());

        if (dbUser.isPresent()) {
            throw new UserAlreadyPresent("User is already registered");
        }

        Users users = new Users();
        users.setUserName(userDto.getUserName());
        users.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Set<String> roles = userDto.getRoles();
        Set<Roles> rolesInDb = roleRepository.findByRoleIn(roles);

        if (rolesInDb.size() != roles.size()) {
            throw new InvalidRolesException("Given role is invalid");
        }

        users.setRoles(rolesInDb);

        EmployeeDto employeeDto = userDto.getEmployeeDto();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Employee employee1 = objectMapper.convertValue(employeeDto, Employee.class);
        users.setEmployee(employee1);
        Users savedUsers = userRepository.save(users);

        Employee employee = savedUsers.getEmployee();

        EmployeeDto employeeDto2 = objectMapper.convertValue(employee, EmployeeDto.class);

        return new UserDto(savedUsers.getUserName(), savedUsers.getPassword(), savedUsers.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toSet()), employeeDto2);
    }

    public JwtResponse login(@RequestBody LogIdPassword logIdPassword) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logIdPassword.getUserId(), logIdPassword.getPassword()));
        if (!authenticate.isAuthenticated()) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        jwtUtil.setType("access");
        String accessToken = jwtUtil.generateJwtAccessToken(logIdPassword.getUserId());
        String refreshToken = jwtUtil.generateJwtRefreshToken(logIdPassword.getUserId());

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refreshToken(String refreshToken) {

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        String userId = jwtUtil.extractUsername(refreshToken);
        String accessToken = jwtUtil.generateJwtAccessToken(userId);

        return new JwtResponse(accessToken, refreshToken);
    }

    public String forgotPassword(String userName) {

        Users user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("invalid user name"));
        String email = user.getEmployee().getEmail();
        jwtUtil.setType("reset");
        String passwordResetToken = jwtUtil.generatePasswordResetToken(userName);

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setResetToken(passwordResetToken);
        passwordResetDto.setEmail(email);
        passwordResetDto.setUserName(userName);
        ResponseEntity<String> stringResponseEntity = actionServiceFeignClient.sendResetPasswordMail(passwordResetDto);

        return stringResponseEntity.getBody();
    }

    public String resetPassword(String passwordResetToken, String newPassword) {

        jwtUtil.setType("reset");
        boolean isValidResetToken = jwtUtil.validateToken(passwordResetToken);
        String userName = jwtUtil.extractUsername(passwordResetToken);

        if (isValidResetToken){
            Users user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        return "Password reset successfully";
    }
}
