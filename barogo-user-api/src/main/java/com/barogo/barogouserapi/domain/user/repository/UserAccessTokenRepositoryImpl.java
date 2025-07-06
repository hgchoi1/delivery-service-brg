package com.barogo.barogouserapi.domain.user.repository;

import com.barogo.model.domain.user.entity.QUserAccessToken;
import com.barogo.model.domain.user.entity.UserAccessToken;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAccessTokenRepositoryImpl implements UserAccessTokenRepositoryCustom {

    private final JPAQueryFactory query;

    QUserAccessToken accessToken = QUserAccessToken.userAccessToken;

    @Override
    public List<UserAccessToken> findAccessTokensByUserSeq(Long userSeq) {
        return query.selectFrom(accessToken)
                    .where(
                        eqUserSeq(userSeq),
                        validTokenOnly()
                    )
                    .fetch();
    }

    private BooleanExpression eqUserSeq(Long userSeq) {
        return accessToken.userSeq.eq(userSeq);
    }

    private BooleanExpression validTokenOnly() {
        return accessToken.expired.isFalse()
                                  .and(accessToken.revoked.isFalse());
    }
}
