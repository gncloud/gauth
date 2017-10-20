$(function(){

    $('.form input').on('keyup', function(e){
        if(e.keyCode == 13){
            var userId = $('#userId').val();
            var password = $('#password').val();
            loginRequest(userId, password);
        }
    });
    $('#submit').on('click', function(){
        var userId = $('#userId').val();
        var password = $('#password').val();
        loginRequest(userId, password);
    });

});
var loginRequest = function(userId, password){

    if(userId === undefined || userId == ''){
        alert('아이디를 입력하세요.');
        return false;
    }
    if(password === undefined || password == ''){
        alert('비밀번호를 입력하세요.');
        return false;
    }
    $.ajax({
        url:'tokens',
        type:'post',
        data: JSON.stringify({
                 'userId': userId,
                 'password': password,
                 'clientId': 'gauth'
             }),
        dataType:'json',
        contentType:'application/json',
        success:function(data){
            var tokenId = data.tokenId;
            setCookie('gauth',tokenId);
            location.href = "/v1/admin/client";
        },
        error: function(x,h,r){
            console.log(x);
            var errorData = JSON.parse(x.responseText).message;
            if(errorData == 'user userClientScope empty'){
                alert('접근 권한이 없습니다.');
                $('input').val('').eq(0).focus();
                return false;
            }else if(errorData == 'invalid userId'){
                alert('회원 정보가 없습니다.');
                $('input').val('').eq(0).focus();
                return false;
            }else if(errorData == 'userClientScope empty'){
                alert('접근 권한이 없습니다.');
                $('input').val('').eq(0).focus();
                return false;
            }else {
                 alert(x.responseText);
                 return false;
             }
        }
    });

};

// 쿠키 생성
function setCookie(name, value, expiredays) {
    var cookie = name + "=" + escape(value) + "; path=/;"
    if (typeof expiredays != 'undefined') {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        cookie += "expires=" + todayDate.toGMTString() + ";"
    }
    document.cookie = cookie;
}
function getCookie(name) {
    name += "=";
    var cookie = document.cookie;
    var startIdx = cookie.indexOf(name);
    if (startIdx != -1) {
        startIdx += name.length;
        var endIdx = cookie.indexOf(";", startIdx);
        if (endIdx == -1) {
            endIdx = cookie.length;
            return unescape(cookie.substring(startIdx, endIdx));
        }
    }
    return '';
}
// 쿠키 삭제
var deleteCookie = function(name) {
    setCookie(name, "", -1);
}