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

        //TODO private 메서드로 아래 builder 한줄로 표현하기
        /**
         * method name : of
         * return : User
         * arguments : UserRegisterDto(only)
         */
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
        //TODO get()대신 찾지못했을 경우 예외 던지기
        User user = userRepository.findById(user_id).get();
        user.updateUserInfo(userUpdateDto);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //TODO 아래와 마찬가지로 optional 제거
    @Transactional(readOnly = true)
    public Optional<User> getUserInfoById(Long member_id){
        return userRepository.findById(member_id);
    }

    //TODO 위와 마찬가지로 get()메서드 사용 x
    @Transactional(readOnly = true)
    public User getUserInfoByUsername(String username){
        return userRepository.findByUsername(username).get();
    }

    //TODO service에서 optional<Entity> return 하지않기
    //optional 처리는 최대한 service레이어 안에서 처리하겠습니다.
    //다른 레이어에서는 엔티티가 null인 경우 없이 처리할 수 있도록 합니다.
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }

    //TODO deleteUserById(Long user_id) -> deleteUser(String username)
    //TODO controller에 이를 반영
    @Transactional
    public Boolean deleteUserById(Long user_id){
        //TODO 존재하지 않을 경우까지 고려한 메서드 사용으로, optional<User>이 아닌 User리턴하기
        //현재 줄 기준으로 85-88까지 결과를 최대 두줄로 변경 및 User 객체 리턴
        Optional<User> user = userRepository.findById(user_id);
        if (!user.isPresent()) {
            return false;
        }
        //TODO 삭제 순서 고려하기
        //삭제 안되는 경우가 존재함
        boardRepository.deleteAll(user.get().getBoards());
        commentRepository.deleteAll(user.get().getComments());
        likeRepository.deleteAll(user.get().getLikes());

        userRepository.deleteById(user_id);
        return true;
    }

}
