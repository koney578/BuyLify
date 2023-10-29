package com.buylify.buylifyapp.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto){
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserName(userDto.getUserName());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEMail(userDto.getEMail());
        user.setActive(true);
        userRepository.save(user);
    }
}
