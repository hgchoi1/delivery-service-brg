package com.barogo.barogouserapi.domain.delivery.repository;

import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListReq;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListRes;
import com.barogo.model.domain.delivery.entity.QDelivery;
import com.barogo.model.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements QDeliveryRepository {

  private final JPAQueryFactory query;
  QDelivery delivery = QDelivery.delivery;
  QUser user = QUser.user;

  @Override
  public Page<DeliveryListRes> searchPage(Long userSeq, DeliveryListReq deliveryListReq, Pageable pageable) {
    List<DeliveryListRes> content = query
        .select(Projections.constructor(DeliveryListRes.class,
            delivery.deliveryId,
            delivery.createdAt,
            delivery.status,
            delivery.type,
            delivery.storeName,
            delivery.amount
        ))
        .from(delivery)
        .join(user).on(delivery.user.userSeq.eq(user.userSeq))
        .where(
            delivery.createdAt.goe(deliveryListReq.startDt().atStartOfDay()),
            delivery.createdAt.loe(deliveryListReq.endDt().atTime(LocalTime.MAX)),
            delivery.user.userSeq.eq(userSeq)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = query
        .select(delivery.count())
        .from(delivery)
        .join(user).on(delivery.user.userSeq.eq(user.userSeq))
        .where(
            delivery.createdAt.goe(deliveryListReq.startDt().atStartOfDay()),
            delivery.createdAt.loe(deliveryListReq.endDt().atTime(LocalTime.MAX)),
            delivery.user.userSeq.eq(userSeq)
        );
    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

}
