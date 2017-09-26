# Gauth

## Gauth는 통합인증 서버

### 배포 방법

  - 소스를 다운받는다.
  - Maven으로 package한다 (명령어 : man package)
  - Gauth spring boot 프로젝트를 실행한다. (java -jar gauth.jar)
  - http://localhost:7979/v1/admin  어드민의 로그인 페이지
  - 샘플 로그인 프로젝트는 gauth안의 별도의 sample 프로젝트가 있다.
  - Sample 디렉터리 에서 Maven으로 package한다 (명령어 : man package)
  - Gauth-sample spring boot 프로젝트를 실행한다. (java -jar gauth-sample.jar)
  - http://localhost:8080/sample/login.html 기본 경로

### API 요청 방법
- 기본 경로 : http://localhost:7979/v1

| TYPE | URI | description |
| ------ | ------ | ------ |
| POST | /tokens | create token / login action |
| GET | /tokens | Token All List (admin Only) |
| DELETE | /tokens | remove Token |
| GET | /tokens/{tokenId} | read token info (admin only) |
| DELETE | /tokens/{tokenId} | delete token (admin Only) |
| GET | /user | my info |
| DELETE | /user | remove client inner user |
| POST | /users | user sign up |
| GET | /users | user info keyword search (admin Only) |
| GET | /users/{userId} | user info (admin only) |
| DELETE | /users/{userId} | delete user (admin only) |
| HEAD | /validateToken | token validate check |
| POST | /activate | create activate pending user |
| GET | /activate | pending user active |
| POST | /clients | create client (admin Only) |
| GET | /clients | client list (admin Only) |
| GET | /clients/{clientId} | client info |
| DELETE | /clients/{clientId} | delete client |
| PUT | /clients/{clientId} | update client |
| POST | /scopes | create scope (admin Only) |
| GET | /scopes | scope info list (admin Only) |
| GET | /scopes/{scopeId} | scope info |
| DELETE | /scopes/{scopeId} | delete scope |
| PUT | /scopes/{scopeId} | update scope |
