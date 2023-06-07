# gukmo

## 😁 프로젝트 소개
국비지원 학원 정보가 모여있는 커뮤니티 사이트

## 🎮 기술스택
### Backend
- Java 11
- Spring boot 2.7.8
- Spring Data JPA
- QueryDsl
- Oracle

### Frontend
- Bootstrap 4.6.0
- jQuery
- Ajax

## 📂 BackEnd 패키지 구조
```
📦gukmo
┣ 📂admin
┃ ┣ 📂api
┃ ┣ 📂controller
┃ ┣ 📂dto
┃ ┣ 📂repository
┃ ┗ 📂service
┣ 📂user
┃ ┣ 📂api
┃ ┣ 📂controller
┃ ┣ 📂dto
┃ ┣ 📂repository
┃ ┗ 📂service
┣ 📂config
┣ 📂entity
┣ 📂advice
┣ 📂exception
┣ 📂interceptor
┣ 📂utils
``` 
## 핵심 패키지

### `admin, user`
- 추후 아키텍처 변경이 유리하도록 사용자 관련 패키지와 관리자 관련 패키지를 나누었습니다.

### `config`
- 프로젝트 설정 파일들이 들어있습니다.

### `entity`
- 프로젝트 도메인 모델링 파일들이 들어있습니다.

### `exception`
- exception 파일들이 들어있습니다.

### `interceptor`
- interceptor 파일들이 들어있습니다.

### `utils`
- Util 메소드들을 모아놓은 파일들이 들어있습니다. 
- ex) NumberUtil, DateUtil

## ✏️ Tools
- IntelliJ IDE
- Visual Studio Code
- Sqldeveloper
- Postman
