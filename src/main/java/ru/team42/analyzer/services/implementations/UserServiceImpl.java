package ru.team42.analyzer.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.dto.response.RoleDto;
import ru.team42.analyzer.dto.response.UserDto;
import ru.team42.analyzer.entities.RoleEntity;
import ru.team42.analyzer.entities.UserEntity;
import ru.team42.analyzer.repositories.RoleRepository;
import ru.team42.analyzer.repositories.UserRepository;
import ru.team42.analyzer.services.interfaces.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {
        return createUser(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
    }

    @Override
    public UserDto createUser(String username, String password, Set<RoleDto> roles) {
        Set<RoleEntity> roleEntities = roles.stream().map(role -> {
            RoleEntity roleEntity = roleRepository.findOneByName(role.getName()).orElse(null);

            if(role == null) {
                var entity = conversionService.convert(role, RoleEntity.class);
                roleEntity = roleRepository.save(entity);
            }

            return roleEntity;
        }).collect(Collectors.toSet());

        UserEntity user = userRepository.findByUsername(username);
        if(user == null) {
            user = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntities)
                .build();
        } else {
            user.setRoles(roleEntities);
        }

       return conversionService.convert(userRepository.save(user), UserDto.class);
    }

    @Autowired
    UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ConversionService conversionService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAll() {
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

        Set<RoleEntity> s = Objects.requireNonNull(dto.getRoles())
                .stream().map(r -> new RoleEntity(r.getId(), r.getName())).collect(Collectors.toSet());

        userEntity.setRoles(s);

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
                entity.getPassword(),
                entity.getRoles().stream()
                        .map(role -> new RoleDto(role.getId(), role.getName()))
                        .collect(Collectors.toSet()),
                entity.getChat2DeskId()
        );
    }

}
