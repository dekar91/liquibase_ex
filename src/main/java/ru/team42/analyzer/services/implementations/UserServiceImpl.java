package ru.team42.analyzer.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team42.analyzer.dto.response.RoleDto;
import ru.team42.analyzer.dto.response.UserDto;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.repositories.UserRepository;
import ru.team42.analyzer.services.interfaces.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAll(Long id) {
        return userRepository.findAll().parallelStream()
                .map(UserServiceImpl::wrapEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return wrapEntity(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserDto save(UserDto dto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(dto, userEntity);

        userRepository.save(userEntity);

        return null;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

    }

    private static UserDto wrapEntity(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UserDto(entity.getId(),
                entity.getUsername(),
                entity.getRoles().stream()
                        .map(role -> new RoleDto(role.getId(), role.getName()))
                        .collect(Collectors.toSet()),
                entity.getChat2DeskId()
        );
    }

}
