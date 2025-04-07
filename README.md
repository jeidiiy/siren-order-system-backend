# 사이렌 오더 시스템
스타벅스의 사이렌 오더를 보고 만드는 백엔드 포트폴리오입니다. 기획 및 도메인 요소를 차용하여 직접 개발해보는 프로젝트입니다.

## Skill Stack

### Back-end
- Java 17 (Amazon Corretto 17)
- Spring Boot 3.4.3 (latest)
- Spring Security 6.4.3
- Spring Data JPA 3.4.3
  - Hibernate 6.6.8
- Gradle 8.12.1
- MySQL 8.0.41
- H2 Database
- Lombok

### Version Control
- Git
- GitHub

### Tool
- IntelliJ IDEA
- Fork
- Notion

## 주요 기능

### 회원 기능
- 회원 가입
- 로그인 / 로그아웃 (JWT 인증)
- 내 정보 조회 및 수정

### 매장 관리
- 매장 목록 조회
- 특정 매장 상세 조회
- 매장별 주문 가능 여부 확인
- 매장별 픽업 옵션

### 상품 & 옵션 관리
- 상품 조회
- 특정 상품 상세 조회
- 음료, 음식, 물건

### 주문 기능
- 주문 요청
  - 매장 선택, 픽업 옵션, 주문 상품 및 개수
- 주문 상태 확인
  - 대기 중, 만드는 중, 완료

## ERD

![siren_order_system.png](./images/siren_order_system.png)

## 커밋 규칙

### 커밋 메시지 규칙

```plain
#{issue number} - type: subject

body
```

### 커밋 유형

| 키워드      | 설명                                |
|----------|-----------------------------------|
| feat     | 새로운 기능 추가                         |
| fix      | 버그 수정                             |
| docs     | 문서 수정                             |
| build    | 빌드 관련 파일 수정, 외부 라이브러리 추가 및 삭제 등   |
| chore    | 코드 포맷팅, 세미콜론 누락 등 기능과 관련 없는 변경 사항 |
| refactor | 코드 리팩토링                           |
| test     | 테스트 코드, 리팩토링 테스트 코드 추가            |

### 제목

- 제목은 50자를 넘기지 않고, 소문자로 작성하며 마침표를 붙이지 않는다.
- 프리픽스로 이슈 번호를 작성하여 깃허브에서 관련 사항들을 추적하기 쉽도록 한다.
- 명령어로 작성한다.
- 가능한 제목으로 커밋 의도를 알 수 있도록 간결하고 명확하게 작성한다.

### 내용

- 선택사항이므로 모든 커밋에 본문 내용을 작성할 필요는 없다.
- 추가 설명이 필요한 경우 작성한다.
- 72자를 넘기지 않고 제목과 구분을 위해 한 줄을 띄우고 작성한다.

## 코드 컨벤션
- google-java-format
