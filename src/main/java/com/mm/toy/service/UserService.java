package com.mm.toy.service;

import com.mm.toy.domain.User;
import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.repository.BoardRepository;
import com.mm.toy.repository.CommentRepository;
import com.mm.toy.repository.LikeRepository;
import com.mm.toy.repository.UserRepository;
import com.mm.toy.Dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Long registerUser(UserRegisterDto UserRegisterDTO){

        User user = toUser(UserRegisterDTO);

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    @Transactional
    public Long updateUserInfo(UserUpdateDto userUpdateDto, Long user_id){
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.updateUserInfo(userUpdateDto);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUser(){
        return userRepository.findAll();
    }


    @Transactional(readOnly = true)
    public User getUserInfoById(Long member_id){
        return userRepository.findById(member_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional(readOnly = true)
    public User getUserInfoByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Transactional(readOnly = true)
    public User getUserByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    //TODO deleteUserById(Long user_id) -> deleteUser(String username)
    //TODO controller에 이를 반영
    @Transactional
    public Boolean deleteUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //TODO 삭제 순서 고려하기
        //삭제 안되는 경우가 존재함
        boardRepository.deleteAll(user.getBoards());
        commentRepository.deleteAll(user.getComments());
        likeRepository.deleteAll(user.getLikes());

        userRepository.deleteByUsername(username);
        return true;
    }

    //TODO private 메서드로 아래 builder 한줄로 표현하기
    /**
     * method name : of
     * return : User
     * arguments : UserRegisterDto(only)
     */
    private User toUser(UserRegisterDto UserRegisterDTO){
        Random random = new Random();

        User user = User.builder()
                .username(Integer.toString(random.nextInt()))
                .name(UserRegisterDTO.getName())
                .password(UserRegisterDTO.getPassword())
                .email(UserRegisterDTO.getEmail())
                .build();

        return user;
    }

}
