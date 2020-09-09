package ru.team42.analyzer.services.interfaces;


import ru.team42.analyzer.dto.response.RoleDto;
import ru.team42.analyzer.dto.response.UserDto;

import java.util.Set;

public interface UserService extends CrudServiceInterface<UserDto> {

    UserDto createUser(String username, String password, Set<RoleDto> roles);

    UserDto createUser(UserDto userDto);

}
