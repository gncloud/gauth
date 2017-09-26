-- Gauth
Gauth는 통합인증 서버이다.


1. 배포 방법
    A. 소스를 다운받는다.
    B. sample 프로젝트는 별도의 스프링 프로젝트이다.


2. 실행 방법

    기본 경로
    http://localhost:7979/v1

    URL 경로
    POST
    /tokens
    create token / login action

    GET
    /tokens
    Token All List (admin Only)

    DELETE
    /tokens
    remove Token

    GET
    /tokens/{tokenId}
    read token info (admin only)

    DELETE
    /tokens/{tokenId}
    delete token (admin Only)

    GET
    /user
    my info

    DELETE
    /user
    remove client inner user

    POST
    /users
    user sign up

    GET
    /users
    user info keyword search (admin Only)

    GET
    /users/{userId}
    user info (admin only)

    DELETE
    /users/{userId}
    delete user (admin only)

    HEAD
    /validateToken
    token validate check

    POST
    /activate
    create activate pending user

    GET
    /activate
    pending user active

    POST
    /clients
    create client (admin Only)

    GET
    /clients
    client list (admin Only)

    GET
    /clients/{clientId}
    client info

    DELETE
    /clients/{clientId}
    delete client

    PUT
    /clients/{clientId}
    update client

    POST
    /scopes
    create scope (admin Only)

    GET
    /scopes
    scope info list (admin Only)

    GET
    /scopes/{scopeId}
    scope info

    DELETE
    /scopes/{scopeId}
    delete scope

    PUT
    /scopes/{scopeId}
    update scope


    어드민 페이지
    URL 경로
    http://localhost:7979/v1/admin

3. 샘플 로그인 확인
    gauth 프로젝트 하위의 sample 프로젝트를 별도로 실행한다.

    URL 경로
    http://localhost:8080/sample/login.html

