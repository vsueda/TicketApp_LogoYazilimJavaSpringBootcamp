package com.example.service;

import com.example.dto.UserDTO;
import com.example.dto.mapper.UserMapper;
import com.example.dto.request.RegisterRequest;
import com.example.dto.request.UpdatePasswordRequest;
import com.example.dto.request.UserUpdateRequest;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.message.ErrorMessage;
import com.example.model.Role;
import com.example.model.User;
import com.example.model.enums.RoleType;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    /**
     * Register işlemi
     * @param registerRequest registerRequest, register işlemi için özelleştirilmiş bir User dto'dur
     */
    public void register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST,registerRequest.getEmail()));
        }
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Role role = roleRepository.findByName(RoleType.ROLE_INDIVIDUAL).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_INDIVIDUAL.name())));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setRoles(roles);

        userRepository.save(user);
    }

    /**
     * Bütün Kullanıcıları repo'dan alıp bir listeye koyduk.
     * @return map edilmiş UserDTO'ları döner.
     */
    public List<UserDTO> findAllUsers(){
        List<User> users = userRepository.findAll();
        return userMapper.map(users);
    }

    /**
     * Bir Kullanıcının bilgilerini repo'dan alma.
     * @param id Bilgileri getirilecek kullanıcının id'si (User)
     * @return Map edilmiş UserDTO'yu döner.
     */
    public UserDTO findById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

        return userMapper.userToUserDTO(user);
    }

    /**
     * Kullanıcılar page olarak //TODO
     * @param pageable
     * @return Map edilmiş UserDto döner.
     */
    public Page<UserDTO> findUserPage(Pageable pageable){
        Page<User> users=userRepository.findAll(pageable);

        Page<UserDTO> dtoPage = users.map(new Function<User, UserDTO>() {
            @Override
            public UserDTO apply(User user) {
                return userMapper.userToUserDTO(user);
            }
        });
        return dtoPage;
    }

    /**
     * Request olarak gelen şifreyi hashleyip eski şifreyle eşleştirip sonrasında şifre değiştirme işlemi yapar.
     * @param id hali hazırda giriş yapmış kullanıcı id'si
     * @param passwordRequest bana gelicek olan request'in tipi
     */
    public void updatePassword(Long id, UpdatePasswordRequest passwordRequest){
        Optional<User> userOpt=userRepository.findById(id);
        User user = userOpt.get();

        if (user.getBuiltIn()){
            throw  new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())){
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
        }

        String hashedPassword=passwordEncoder.encode(passwordRequest.getNewPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    /**
     * builtIn = true veya emailExist = true olmayan kullanıcıların bilgilerinin güncellendiği method
     * @param id Hali hazırda giriş yapmış kullanıcının token'indan aldığım id
     * @param userUpdateRequest Update olucak kullanıcı bilgilerini formatladığım User dto sınıfım.
     */
    @Transactional
    public void updateUser(Long id, UserUpdateRequest userUpdateRequest){
        boolean emailExist = userRepository.existsByEmail(userUpdateRequest.getEmail());
        User user = userRepository.findById(id).get();

        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if (emailExist && !userUpdateRequest.getEmail().equals(user.getEmail())){
            throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXIST);
        }
        userRepository.update(id,userUpdateRequest.getFirstName(), userUpdateRequest.getLastName(),
                userUpdateRequest.getEmail(), userUpdateRequest.getPhoneNumber());
    }

    /**
     * builtIn = true hariç olan kullanıcıları silme methodu
     * @param id endpointteki path'ten gelen user id
     */
    public void removeById(Long id){
        User user=userRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        userRepository.deleteById(id);
    }
}
