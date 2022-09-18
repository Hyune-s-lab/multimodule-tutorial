# multimodule-tutorial

kotlin 베이스의 multi module 을 구현합니다.

## Environment

- kotlin
    - multi module
- kotest
    - behavior spec
    - test double - stub, fixture

## Feature

### common

- [x] test 세팅
    - [x] kotest with behavior spec
- [ ] 모듈 최적화
    - [ ] 공통 dependencies 를 root 로 이동

### api-module

> 표현 영역으로 사용자 요청을 처리하는 책임이 있습니다.

- [ ] dependencies
    - [ ] spring-boot-starter-web

### account-module

> 도메인 영역으로 계좌의 로직을 처리하는 책임이 있습니다.  
> 기술 의존성을 최소화 합니다. spring X

- [ ] 기본 usecase 구현
    - [x] 계좌 생성, 조회, 입금, 출금
    - [x] 계좌 기록 생성, 조회
    - [x] 시나리오 테스트

### infrastructure-module

> 영속성 영역으로 데이터 정합성에 대한 책임이 있습니다.

- [ ] dependencies
    - [ ] spring-boot-starter-data-jpa
    - [ ] mysql:mysql-connector-java

## Pain Point

### 모듈간 연동을 위한 adapter 에 대한 고민

> 220918

- spring jpa 는 느슨한 결합을 위해 adapter 를 제공합니다. (JpaRepository)
    - 그렇기에 구현체인 hibernate 가 변경될 지라도 사용하는 상위 레이어에는 수정이 없습니다.
- 도메인 모듈의 입장에서 영속성 모듈은 변경될 수 있습니다.
    - ex) datasource 의 변경 - read only 인 history 는 nosql 로 변경.
- 변경되어도 도메인 모듈에서는 변경이 없도록 adapter 를 활용할 수 있습니다.
    - repository 패키지를 활용합니다. 구현체는 dao 의 네이밍을 사용합니다.
- 하지만 adapter 가 상위 레이어인 도메인 모듈에 있다면 변경 여파가 전파되는 것 아닌가?
    - 그렇다면 이것도 분리해야 되나? 근데 분리하면 어디로..

### 경계 간 매핑하기

> 220918

- 도메인 모듈을 기준으로 `완전 매핑 전략` 을 활용합니다.
- 모듈 간 매핑에 대한 것은 조금 더 구현 후 생각해봅니다.

## Reference

- 만들면서 배우는 클린 아키텍처 - 톰 홈버그 
