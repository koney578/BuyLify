package com.buylify.buylifyapp.authentication;

import com.buylify.buylifyapp.mappers.UserMapper;
import com.buylify.buylifyapp.userType.UserType;
import com.buylify.buylifyapp.userType.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void registerUser(RegisterDto registerDto){

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            // exceptio

        }
        UserType userType = userTypeRepository.getReferenceById(1L);

        User user = userMapper.userDtoToUser(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setActive(true);
        user.setUserType(userType);

        User savedUser = userRepository.save(user);
    }

    public UserDto updateUser(Long userId, EditUserDto editUserDto) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setUsername(editUserDto.getUsername());
        user.setName(editUserDto.getName());
        user.setSurname(editUserDto.getSurname());
        user.setEmail(editUserDto.getEmail());
        user.setPhoneNumber(editUserDto.getPhoneNumber());

        return userMapper.userToUserDto(userRepository.save(user));
    }
}
