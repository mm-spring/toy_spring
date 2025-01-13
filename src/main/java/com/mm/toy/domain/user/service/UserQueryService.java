package com.mm.toy.domain.user.service;

import com.mm.toy.domain.user.entity.User;

public interface UserQueryService {
    User getById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

}
