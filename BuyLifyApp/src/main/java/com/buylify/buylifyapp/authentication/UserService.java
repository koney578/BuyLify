package com.buylify.buylifyapp.authentication;

import com.buylify.buylifyapp.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void registerUser(UserDto userDto){

        if (userRepository.existsByUsername(userDto.getUsername())) {
            // exceptio

        }

        User user = userMapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);

        User savedUser = userRepository.save(user);
    }

}
