## GoOnThat

### Repositories   [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FGoOnThat%2FGoOnThat&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

---
# MataMate (MataverseAcademy + SoulMate)
> **MTVS-SERVER-2ND-GoOnThat** <br/> **개발기간: 2023.06.29 ~ 2023.07.31**

---
## Team Member Introduction
|권은지|김태현|남효정|
|-------------------------------------------------|-------------------------------------------------|--------------------------------------------------|
|<img width="160px" src="https://avatars.githubusercontent.com/u/139085498?v=4" />|<img width="160px" src="https://avatars.githubusercontent.com/u/136583226?v=4" />|<img width="160px" src="https://avatars.githubusercontent.com/u/122511826?v=4" />|
|<center>[@kej2971](https://github.com/kej2971)| <center>[@taedyyyyy](https://github.com/taedyyyyy)|<center>[@namhyojeong](https://github.com/namhyojeong)|

|정민호|정재민|조평훈|
|-------------------------------------------------|-------------------------------------------------|--------------------------------------------------|
|<img width="160px" src="https://avatars.githubusercontent.com/u/134987020?v=4" />|<img width="160px" src="https://avatars.githubusercontent.com/u/125876896?v=4" />|<img width="160px" src="https://avatars.githubusercontent.com/u/122511815?v=4" />|
|<center>[@jeongmino1](https://github.com/jeongmino1)|<center>[@devJaem](https://github.com/devJaem)|<center>[@pyunghun](https://github.com/pyunghun)|

---
## Project Introduction

### MetaMate는 학생들에게 다음의 기능을 제공합니다.
메타버스 아카데미의 커리큘럼 특성상, 다른 전공의 학생들과 소통하기가 쉽지 않습니다. 
그래서,  프로젝트를 같이할 팀을 짜기 어려운 현실을 극복해 보고자 커뮤니티 기능을 가진 게시판을
만들었습니다. 평소에 접점이 서로 없었던 학생들이 잡담, 각 반의 프로젝트 진행시 궁금점들을 공유하면서 서로 소통하고
그런 행위들을 바탕으로, 최종프로젝트 팀빌딩에 도움이 되고자 합니다.

---

## Getting Started Guide
### 요구사항
For building and running the application you need:

- [MySQL8.0.33](https://dev.mysql.com/downloads/mysql/)
- [JAVA11.0.15](https://github.com/ojdkbuild/ojdkbuild)

---

## Stacks 🐈

### 개발환경
<img src="https://img.shields.io/badge/Intelii J-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
<img src="https://img.shields.io/badge/GitHub-000000?style=for-the-badge&logo=github&logoColor=white">

### 구성
![npm](https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)

### 사용언어
![JS](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=Javascript&logoColor=white)
![springboot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MYSQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=Bootstrap&logoColor=white)

### 소통수단
<img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">

---
## Screen Layout 📺

### [LayOut](https://github.com/GoOnThat/GoOnThat/wiki/Screen-Layout)
|                                                                                                                                             메인 페이지 |                                                                                                                         로그인 페이지 |
|---------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| <img width="329" src="https://github-production-user-asset-6210df.s3.amazonaws.com/125876896/257041304-ea431078-05b9-482d-8901-fe05c5128f90.png"/> | <img width="329" src="https://user-images.githubusercontent.com/125876896/257041747-ef8adc64-89df-4772-a751-f1a5ff9f00c8.png"/> |  
|                                                                                                                                           회원가입 페이지 |                                                                                                                         게시판 페이지 |  
|                    <img width="329" src="https://user-images.githubusercontent.com/125876896/257041757-e8a6e2c0-e137-4a7a-80e6-b1c4ac5a6435.png"/> |             <img width="329" src="https://github.com/GoOnThat/GoOnThat/assets/125876896/abbde490-87fc-4463-9f8b-e3bcd7199365"/> 

---
# Main Function 📦

---
### ⭐️ 게시판 -정재민
- 게시판 에 카테고리 지정하여 글 작성기능
- 키워드를 활용한 검색 가능 (제목, 작성자 닉네임, 내용)
- 카테고리를 선택하여 몰아보기 가능
- 본인이 올린 글만 삭제하거나, 수정 가능
### ⭐️ 댓글 -남효정
- 댓글 기능 제공
- 본인이 올린 댓글만 삭제하거나, 수정이 가능
- 댓글 사용자 선택시 쪽지 보내기 기능
### ⭐️ 좋아요 - 정민호
- 좋아요로, 공감 표시 가능
- 좋아요 및 좋아요 취소 가능
- 한 게시물에 하나의 좋아요만 가능
- 좋아요는 회원 개개인이 따로 적용됨
---
### ⭐️ 1:1 쪽지 -김태현
- 회원 닉네임을 클릭하여 , 1:1 쪽지 전송기능
- 추후 게시판 리스트에서 적용 예정
---
### ⭐️ 스프링 시큐리티 & 회원정보수정 -김태현
- 회원 정보를 수정 가능
---
### ⭐️ 관리자페이지 -김태현
- 관리자는 회원가입 승인, 가입 유저 정보 확인 및 삭제, 유저 권한 수정 및 삭제가 가능합니다.
---
## Architecture
[Layerd Architecture](https://velog.io/@hanblueblue/%EB%B2%88%EC%97%AD-Layered-Architecture)

---
## directory tree
```bash
└─src
    └─main
        ├─java
        │  └─com
        │      └─ohgiraffers
        │          └─goonthatbackend
        │              └─metamate
        │                  ├─admin
        │                  │  └─advice
        │                  ├─auth
        │                  ├─comment
        │                  │  ├─command
        │                  │  │  ├─application
        │                  │  │  │  ├─controller
        │                  │  │  │  ├─dto
        │                  │  │  │  └─service
        │                  │  │  ├─domain
        │                  │  │  │  ├─aggregate
        │                  │  │  │  │  └─entity
        │                  │  │  │  ├─repository
        │                  │  │  │  └─service
        │                  │  │  └─infra
        │                  │  │      ├─repository
        │                  │  │      └─service
        │                  │  └─query
        │                  │      └─query
        │                  │          ├─application
        │                  │          ├─domain
        │                  │          └─infra
        │                  ├─common
        │                  ├─config
        │                  ├─declaration
        │                  │  ├─command
        │                  │  │  ├─application
        │                  │  │  │  ├─controller
        │                  │  │  │  ├─dto
        │                  │  │  │  └─service
        │                  │  │  ├─domain
        │                  │  │  │  ├─aggregate
        │                  │  │  │  │  ├─entity
        │                  │  │  │  │  └─vo
        │                  │  │  │  └─service
        │                  │  │  └─infra
        │                  │  │      ├─repository
        │                  │  │      └─service
        │                  │  └─query
        │                  │      ├─application
        │                  │      ├─domain
        │                  │      └─infra
        │                  ├─domain
        │                  │  └─user
        │                  ├─exception
        │                  ├─freeboard
        │                  │  ├─command
        │                  │  │  ├─application
        │                  │  │  │  ├─controller
        │                  │  │  │  ├─dto
        │                  │  │  │  └─service
        │                  │  │  ├─domain
        │                  │  │  │  ├─aggregate
        │                  │  │  │  │  └─entity
        │                  │  │  │  ├─repository
        │                  │  │  │  └─service
        │                  │  │  └─infra
        │                  │  │      ├─repository
        │                  │  │      └─service
        │                  │  └─query
        │                  │      ├─application
        │                  │      ├─domain
        │                  │      └─infra
        │                  ├─like
        │                  │  ├─command
        │                  │  │  ├─application
        │                  │  │  │  ├─controller
        │                  │  │  │  ├─dto
        │                  │  │  │  └─service
        │                  │  │  ├─domain
        │                  │  │  │  ├─aggregate
        │                  │  │  │  │  ├─entity
        │                  │  │  │  │  └─vo
        │                  │  │  │  └─service
        │                  │  │  └─infra
        │                  │  │      ├─repository
        │                  │  │      └─service
        │                  │  └─query
        │                  │      ├─application
        │                  │      ├─domain
        │                  │      └─infra
        │                  ├─message
        │                  ├─multifile
        │                  │  ├─command
        │                  │  │  ├─application
        │                  │  │  │  ├─controller
        │                  │  │  │  ├─dto
        │                  │  │  │  └─service
        │                  │  │  ├─domain
        │                  │  │  │  └─aggregate
        │                  │  │  │      └─entity
        │                  │  │  └─infra
        │                  │  │      ├─repository
        │                  │  │      └─service
        │                  │  └─query
        │                  │      ├─application
        │                  │      ├─domain
        │                  │      └─infra
        │                  ├─service
        │                  └─web
        │                      └─dto
        │                          └─user
           
```
