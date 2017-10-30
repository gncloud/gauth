# Gauth

## Gauth는 통합인증 서버

### 배포 방법

  - 소스를 다운받는다.
  - Maven으로 package한다 (명령어 : man package)
  - Gauth spring boot 프로젝트를 실행한다. (스크립트 사용 권장 -> gauth start)
  - http://localhost:7979/v1/admin  어드민의 로그인 페이지이다.(root 계정은 admin/1111)
  
  - 샘플 로그인 프로젝트는 gauth안의 별도의 sample 프로젝트가 있다.
  - Sample 디렉터리 에서 Maven으로 package한다 (명령어 : man package)
  - Gauth-sample spring boot 프로젝트를 실행한다. (스크립트 사용 권장 -> gauth-sample start)
  - 샘플 기본 경로 는 /sample/login.html 이다.
  
### API 요청 방법
- 기본 경로 : http://localhost:7979/v1
- API요청 정의는 http://localhost:7979/v1/api-docs 에서 확인바람.

| TYPE | URI | description |
| ------ | ------ | ------ |
| POST | /register | create activate pending user |
| POST | /activate | active pending user |
| DELETE | /clients/{clientId} | delete client |
| GET | /clients/{clientId} | client info |
| GET | /clients | client list (admin Only) |
| POST| /clients | create client (admin Only) |
| PUT | /clients/{clientId} | update client |
| POST | /userClientScope | create UserClientScope |
| DELETE | /userClientScope | delete user scope |
| GET | /scopes | scope info list (admin Only) |
| POST | /scopes | create scope (admin Only) |
| DELETE | /scopes/{scopeId} | delete scope |
| GET | /scopes/{scopeId} | scope info |
| PUT | /scopes/{scopeId} | update scope |
| DELETE | /token | remove Token |
| GET | /token | myToken Info |
| POST | /tokens | create token / login action |
| DELETE | /tokens/{tokenId} | delete token (admin Only) |
| GET | /tokens/{tokenId} | read token info |
| GET | /user | my info |
| POST | /users | user sign up |
| PUT | /users/{userCode} | update user |
| GET | /users/{userCode} | user info (admin only) |
| GET | /users?state=pending | list pending users (admin only) |
| DELETE | /users/{userCode} | delete user |
| DELETE | /users?state=pending&activateKey={key} <br> /users?state=pending&email={email} | delete one pending user |
| DELETE | /users?state=pending&truncate=true | delete all pending users |
| GET | /users | user info keyword search (admin Only) |
| HEAD | /validateToken | validate token check |
| PUT | /tokens | refresh expire date |

### 클라이언트 등록
- http://localhost:7979/v1/admin admin으로 로그인한다.
- 클라이언트 메뉴에서 생성버튼을 누른 후 각 입력항목을 등록한다.
- 클라이언트 기본 스코프는 Admin,User 두가지가 기본 생성된다.
- 기본값으로 선택되어 있을 시 사용자가 회원가입시 부여 받게될 권한이다.
- Admin으로 권한을 부여 시 관리자 페이지 접근이 가능하다. 
- * 절대 기본값으로 Admin 스코프를 부여하지 않도록 한다.
- 클라이언트 등록완료.

### 회원가입
- http://localhost:8080/sample/signup.html 접속한다.
- 1단계
- 메일을 입력하고, 등록할 클라이언트 아이디를 입력한다.
- 메일 인증 버튼을 클릭한다. 오른쪽에 응답코드가 200이면 정상이다.
- 2단계
- 회원 정보를 입력한다.
- activateKey는 메일 인증시 발급한 확인 키이다.
- PUT http://localhost:7979/v1/activates 요청으로 메일을 인증 할 수 있다. 
- 또는 회원가입 요청에 QueryString으로 포함하여 인증 할 수 있다.
- 회원가입 요청시 상태코드 200이면 정상이다.





