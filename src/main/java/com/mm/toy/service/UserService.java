package com.mm.toy.service;

import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserDto;
import com.mm.toy.domain.user.UserRepository;
import com.mm.toy.domain.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
    public Long updateUserInfo(UserUpdateDto userUpdateDto, Long user_id){
        User user = userRepository.findById(user_id).get();
        user.updateUserInfo(userUpdateDto);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserInfoById(Long member_id){
        return userRepository.findById(member_id).get();
    }

    @Transactional(readOnly = true)
    public User getUserInfoByUsername(String username){
        return userRepository.findByUsername(username).get();
    }


}
