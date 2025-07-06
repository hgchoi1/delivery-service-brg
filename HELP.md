# 프로젝트 설명


바로고 서비스는 매장(비즈니스 고객), 라이더(파트너), 그리고 일반 사용자(엔드 유저)까지 다양한 도메인으로 구성되어 있습니다.
배달 주문이 생성되려면 이들 모두가 연결되는 구조이기 때문에, 개발 과정에서 실제로 누가 이 서비스를 사용하는지를 먼저 고민했습니다.

이번 과제에서는 엔드 유저(회원)가 제가 고려해야할 주요 대상이라고 판단했고, 이에 맞춰 발생가능한 예외케이스를 고려하여 필요한 API 기능을 개발하기 위해 노력하였습니다.


### 구성
라스트 마일 서비스를 고려하여 멀티 모듈 프로젝트로 구성하였으며, 그 중 엔드 유저인 회원을 위한 서버를 준비하였습니다.

엔티티(Entity)와 유틸리티(Util) 등은 다른 서버들과 공유할 수 있도록 독립된 모듈로 분리하였습니다.

* barogo-user-api : 회원을 위한 API 서버
* common-model : 프로젝트에서 사용하는 jpa entity
* common-util : 프로젝트에서 사용하는 util 성 class (정규식)
* infra : 프로젝트의 도커 컴포즈와 초기 세팅에 필요한 init.sql 포함

배달 도메인 특성상 라이더와 매장 등 다양한 연관 엔터티가 존재하지만, 서비스 구성을 단순화하기 위해 고민하며 설계하였습니다.
필수적인 엔터티를 중심으로 도메인을 구성하였으며, 요구사항을 만족하기 위해 일부 관련 API는 회원 API 서버에 포함되어 있습니다.

# 사용 기술
1. 언어/프레임워크 : java 21 / springboot 3
2. 데이터베이스 : mysql 8.0.33
3. 기타 프레임워크 및 라이브러리
    4. JPA / QueryDSL
    5. JWT


# 실행방법
프로젝트 루트폴더에서 수행
```
docker build --build-arg DEPENDENCY=build/dependency -t barogo-user-api:latest ./barogo-user-api/.
docker compose --file ./infra/docker-compose-barogo.yml up -d
```

# API 명세서
postman : https://documenter.getpostman.com/view/46498056/2sB34coNVg

# 참고) 회원 정보

* loginId: guest
* password: guest1234567!
* accessToken: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsImlhdCI6MTc1MTc3NjQzOCwiZXhwIjoxNzUyMzgxMjM4fQ.FtkGN7awUq042hSIBdZEcBaASH9M-kEvPCLBWVjx33g

감사합니다.