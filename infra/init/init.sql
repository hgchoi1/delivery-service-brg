CREATE DATABASE IF NOT EXISTS barogo;
use barogo;

create table tb_user
(
    created_at       datetime(6)                 null,
    created_by       bigint                      null,
    delete_at        datetime(6)                 null comment '계정 삭제',
    last_modified_at datetime(6)                 null,
    last_modified_by bigint                      null,
    user_seq         bigint auto_increment
        primary key,
    user_id          binary(16)                  not null comment '외부 노출 키',
    login_id         varchar(255)                not null comment '로그인 아이디',
    name             varchar(255)                not null comment '이름',
    password         varchar(255)                not null comment '비밀번호',
    phone            varchar(255)                null comment '핸드폰 번호',
    role             enum ('USER', 'ADMIN')      not null comment '권한',
    status           enum ('INACTIVE', 'ACTIVE') not null comment '유저 상태',
    constraint UK_eqthhn3k7f8u694ue2emhspkd
        unique (user_id)
);

create index idx_user_seq
    on tb_user (user_seq);

INSERT INTO barogo.tb_user (created_at, created_by, delete_at, last_modified_at, last_modified_by, user_seq, user_id, login_id, name, password, phone, role, status) VALUES ('2025-07-06 13:33:36.943833', null, null, '2025-07-06 13:33:36.943833', null, 1, 0x56881ED8A8164A98B0B641CD714C75C3, 'guest', '게스트', '$2a$10$xHV187TVayz2sNTvLrlPMuSkJ36RRtdidyxVMpGDh.iJMojLhE5oS', '01012345678', 'USER', 'ACTIVE');
INSERT INTO barogo.tb_user (created_at, created_by, delete_at, last_modified_at, last_modified_by, user_seq, user_id, login_id, name, password, phone, role, status) VALUES ('2025-07-06 13:33:39.329046', null, null, '2025-07-06 13:33:39.329046', null, 2, 0x60A3967139D04AB0A65DAAC7DC68F794, 'guest2', '홍길동', '$2a$10$Go/HksIBrQQhJQ8fXT47MexvML1aSlO398Jp4sZpj3Nk7zkCgM2kq', '01012345679', 'USER', 'ACTIVE');

create table tb_rider
(
    created_at       datetime(6)                            null,
    created_by       bigint                                 null,
    last_modified_at datetime(6)                            null,
    last_modified_by bigint                                 null,
    rider_seq        bigint auto_increment
        primary key,
    rider_id         binary(16)                             not null comment '외부 노출 키',
    login_id         varchar(255)                           not null comment '로그인 아이디',
    name             varchar(255)                           not null comment '이름',
    password         varchar(255)                           not null comment '비밀번호',
    phone            varchar(255)                           null comment '핸드폰 번호',
    status           enum ('INACTIVE', 'ACTIVE', 'BLOCKED') not null,
    constraint UK_hym8f2p3m77n9q4xem05cmix9
        unique (rider_id)
);

create index idx_rider_seq
    on tb_rider (rider_seq);

INSERT INTO barogo.tb_rider (created_at, created_by, last_modified_at, last_modified_by, rider_seq, rider_id, login_id, name, password, phone, status) VALUES ('2025-07-06 13:33:50.614640', null, '2025-07-06 13:33:50.614640', null, 1, 0xE755D84ED93643CD8F1BC52F5B041232, 'rider', '라이더', '$2a$10$jRQrzcR7m8fUNlFwNgGJZOzYdsBnPZmHjT4jqWB8ugL2No1LcwfFa', '01099990000', 'ACTIVE');
INSERT INTO barogo.tb_rider (created_at, created_by, last_modified_at, last_modified_by, rider_seq, rider_id, login_id, name, password, phone, status) VALUES ('2025-07-06 13:33:52.299457', null, '2025-07-06 13:33:52.299457', null, 2, 0xBF12766D6F704EB2B8532EB3A8DCC407, 'riders', '홍길숙', '$2a$10$6FIOGBM/1CoWvRPjEy1oNuIIZ650IaYtC4F/sUx8/E.6WKwMbSXu6', '01088880000', 'ACTIVE');

create table tb_user_access_token
(
    expired          bit          not null comment '만료 여부',
    revoked          bit          not null comment '재발급 여부',
    access_token_seq bigint auto_increment
        primary key,
    created_at       datetime(6)  null,
    created_by       bigint       null,
    last_modified_at datetime(6)  null,
    last_modified_by bigint       null,
    user_seq         bigint       not null,
    refresh_token    varchar(255) not null comment '리프레시 토큰',
    token            varchar(255) not null comment '엑세스 토큰',
    constraint UK_jg90he00dtyxwjm30jrqyvltf
        unique (token),
    constraint UK_qtidpcr2hbbaer22vbpgort2f
        unique (refresh_token)
);

INSERT INTO barogo.tb_user_access_token (expired, revoked, access_token_seq, created_at, created_by, last_modified_at, last_modified_by, user_seq, refresh_token, token) VALUES (false, false, 1, '2025-07-06 13:33:59.073031', null, '2025-07-06 13:33:59.073031', null, 1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsImlhdCI6MTc1MTc3NjQzOCwiZXhwIjoxNzUyMzgxMjM4fQ.FtkGN7awUq042hSIBdZEcBaASH9M-kEvPCLBWVjx33g', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsImlhdCI6MTc1MTc3NjQzOCwiZXhwIjoxNzUyMzgxMjM4fQ.FtkGN7awUq042hSIBdZEcBaASH9M-kEvPCLBWVjx33g');

create table tb_delivery
(
    amount           bigint                                                                                       null comment '주문 금액',
    created_at       datetime(6)                                                                                  null,
    created_by       bigint                                                                                       null,
    delivery_seq     bigint auto_increment
        primary key,
    last_modified_at datetime(6)                                                                                  null,
    last_modified_by bigint                                                                                       null,
    rider_seq        bigint                                                                                       null,
    user_seq         bigint                                                                                       not null,
    delivery_id      binary(16)                                                                                   not null comment '외부 노출 키',
    memo             varchar(50)                                                                                  null comment '요청사항',
    destination_addr varchar(255)                                                                                 not null comment '도착지',
    pickup_addr      varchar(255)                                                                                 not null comment '픽업지',
    store_name       varchar(255)                                                                                 null comment '가게명',
    status           enum ('REQUESTED', 'ASSIGNED', 'PICKED_UP', 'DELIVERING', 'DELIVERED', 'CANCELED', 'FAILED') not null comment '상태',
    type             enum ('STANDARD', 'EXPRESS')                                                                 not null comment '배달타입',
    constraint UK_7wus6pubhj3kgoj5q4ulqmti3
        unique (delivery_id)
);

create index idx_delivery_seq
    on tb_delivery (delivery_seq);

INSERT INTO barogo.tb_delivery (amount, created_at, created_by, delivery_seq, last_modified_at, last_modified_by, rider_seq, user_seq, delivery_id, memo, destination_addr, pickup_addr, store_name, status, type) VALUES (11000, '2025-07-06 13:34:20.196611', 1, 1, '2025-07-06 13:35:23.869328', 1, null, 1, 0xBAF00ADFBC294E52AB6280EC05D3D754, '1층에서 연락주세요', '서울 강남구 선릉로115길 42 학동초등학교', '서울 강남구 선릉로 667 성우빌딩', '맥도날드 강남구청점', 'REQUESTED', 'STANDARD');
INSERT INTO barogo.tb_delivery (amount, created_at, created_by, delivery_seq, last_modified_at, last_modified_by, rider_seq, user_seq, delivery_id, memo, destination_addr, pickup_addr, store_name, status, type) VALUES (16000, '2025-07-06 13:34:20.204952', 1, 2, '2025-07-06 13:34:20.204952', 1, 2, 1, 0x42B31E7D1CA94580B0777D6324B5CFD0, '도착 전 연락주세요', '서울 강남구 언주로134길 32', '서울 강남구 봉은사로 328 써브웨이 선정릉역점', '써브웨이 선정릉역점', 'ASSIGNED', 'STANDARD');
