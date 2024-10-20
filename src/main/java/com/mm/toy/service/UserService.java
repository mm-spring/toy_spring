package com.mm.toy.service;

import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserDTO;
import com.mm.toy.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public Long registerUser(UserDTO UserRegisterDTO){
        Random random = new Random();

        User user = User.builder()
                .username(Integer.toString(random.nextInt()))
                .name(UserRegisterDTO.getName())
                .password(UserRegisterDTO.getPassword())
                .email(UserRegisterDTO.getEmail())
                .build();

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }
}
