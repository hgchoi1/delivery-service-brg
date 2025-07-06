package com.barogo.barogouserapi.domain.user.repository;

import com.barogo.model.domain.user.entity.UserAccessToken;
import java.util.List;

public interface UserAccessTokenRepositoryCustom {
    List<UserAccessToken> findAccessTokensByUserSeq(Long userSeq);

}
