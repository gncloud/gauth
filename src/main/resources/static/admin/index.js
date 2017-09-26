$(function(){
    // login check
    isLogin();

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
            location.href="admin/contents.html";
        },
        error: function(x,h,r){
            console.log(x);
            var errorData = JSON.parse(x.responseText).message;
            if(errorData == 'user invalid'){
                alert('회원 정보가 없습니다.');
                $('input').val('').eq(0).focus();
                return false;
            }else if(errorData == 'password invalid'){
                alert('비밀번호가 잘못되었습니다.');
                $('input:eq(1)').val('').focus();
                return false;
            }else{
                alert(x.responseText.message);
                return false;
            }
        }
    });

};
var isLogin = function(){
    var tokenId = getCookie('gauth');
    if(tokenId !== undefined && tokenId != ''){
        $.ajax({
            url: 'validateToken',
            type: 'HEAD',
            contentType:'application/json',
            beforeSend : function(xhr){
                xhr.setRequestHeader("Authentication", tokenId);
            },
            success:function(data){
                location.href="admin/contents.html";
            },
            error: function(){
                deleteCookie('gauth');
            }
        });
    }
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