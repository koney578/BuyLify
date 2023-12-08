package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.RegisterDto;
import com.buylify.buylifyapp.authentication.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(RegisterDto registerDto);

    UserDto userToUserDto(User user);
}
