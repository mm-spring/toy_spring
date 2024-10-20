package com.mm.toy.service;

import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserDto;
import com.mm.toy.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public Long registerUser(UserDto UserRegisterDTO){
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

    @Transactional
    public Long updateUserInfo(UserDto UserUpdateDto, Long user_id){
        User user = User.builder()
                .email(UserUpdateDto.getEmail())
                .name(UserUpdateDto.getName())
                .build();


    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserInfoById(Long member_id){
        return userRepository.findById(member_id).get();
    }


}
