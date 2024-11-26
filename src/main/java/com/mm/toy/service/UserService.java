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

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Transactional
    public Boolean deleteUserById(Long user_id){
        Optional<User> user = userRepository.findById(user_id);
        if (!user.isPresent()) {
            return false;
        }
        boardRepository.deleteAll(user.get().getBoards());
        commentRepository.deleteAll(user.get().getComments());
        likeRepository.deleteAll(user.get().getLikes());

        userRepository.deleteById(user_id);
        return true;
    }

}
