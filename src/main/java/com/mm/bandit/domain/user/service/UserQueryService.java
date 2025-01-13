package com.mm.bandit.domain.user.service;

import com.mm.toy.domain.User;

import java.lang.reflect.Member;

public interface UserQueryService {
    User getById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

}
