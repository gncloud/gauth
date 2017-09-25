$(function(){
    // login check
    isLogin();

    $('#logout').on('click',logout);
    $('#createClient').on('click',createClient);
    fnClientList();
});
var createClient = function(){
    var clientId = $('#c_clientId').val();
    var domain = $('#c_domain').val();
    var desc = $('#c_desc').val();
    var duCheck = false;

    $('table td:first-child').each(function(i,o){
        if($(o).text == clientId){
            duCheck = true;
            return false;
        }
    });

    if(clientId === undefined || clientId == ''){
        alert('아이디는 필수입니다.');
        return false;
    }
    if(duCheck){
        alert('아이디가 중복입니다.');
        return false;
    }
    if(domain === undefined || domain == ''){
        alert('도메인은 필수입니다.');
        return false;
    }

    fnAjax({
            url:'/v1/clients',
            type:'post',
            head:{'Authorization':getCookie('gauth')},
            data:{clientId:clientId,
                  domain:domain,
                  description:desc
            },
            success:function(data){
                $('#client input').val('');
                fnClientList();

            },
            error:function(r,s,e){
                if(r.status == '400'){
                    alert('요정 데이터가 잘못 되었습니다.');
                }
            }
        });
};
var fnClientList = function(){
    $('#clientList tbody').empty();
    fnAjax({
        url:'/v1/clients',
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){

            $(data).each(function(i,o){
                if(o.clientId != 'gauth'){
                    var t = '<tr>';
                    t += '   <td>';
                    t +=        o.clientId
                    t += '   </td>';
                    t += '   <td>';
                    t +=        o.domain
                    t += '   </td>';
                    t += '   <td>';
                    t +=        o.clientSecret
                    t += '   </td>';
                    t += '   <td>';
                    t +=        o.description
                    t += '   </td>';
                    t += '   <td>';
                    t +=        '<a href="javascript:void(0);" onclick="updateClient(\'' + o.clientId + '\');">수정</a>';
                    t += '   </td>'
                    t += '   <td>';
                    t +=        '<a href="javascript:void(0);" onclick="removeClient(\'' + o.clientId + '\');">삭제</a>';
                    t += '   </td>'
                    t += '</tr>';
                    $('#clientList tbody').append(t);
                }
            });

        },
        error:fnError
    });
};
var updateClient = function(clientId){



    $('#myModal').modal('show');

};

var removeClient = function(clientId){
    if(!confirm(clientId + '를 삭제하시겠습니까?')){
        return false;
    }
    fnAjax({
            url:'/v1/clients/' + clientId,
            type:'delete',
            head:{'Authorization':getCookie('gauth')},
            success:function(data){
                fnClientList();
            },
            error:function(){
                fnClientList();
            }
        });
}
var logout = function(){
    var tokenId = getCookie('gauth');
    deleteCookie('gauth');
    fnAjax({
        url:'/v1/token',
        type:'delete',
        head:{'Authorization':tokenId},
        success:function(){
            loginPage();
        },
        error:function(){
            loginPage();
        }
    });
};

var isLogin = function(){
    var tokenId = getCookie('gauth');
    if(tokenId !== undefined && tokenId != ''){
        fnAjax({
            url:'/v1/validateToken',
            type:'head',
            head:{'Authorization':tokenId},
            error:function(e,x,h){
                loginPage();
            }
        });
    }else{
        loginPage();
    }
};


var fnAjax = function(req){
    var url = req.url;
    var type = req.type;
    var data = req.data;
    var head = req.head;
    var success = req.success;
    var error = req.error;

    $.ajax({
        url: url,
        type: type,
        contentType:'application/json',
        data:JSON.stringify(data),
        dataType:'json',
        beforeSend : function(xhr){
            for (var key in head) {
                console.log(key + ": " + head[key]);
                xhr.setRequestHeader(key, head[key]);
            }
        },
        success:success,
        error: error
    });
};

var loginPage = function(){
    location.href='/v1/admin';
}

// 쿠키 생성
var setCookie = function(name, value, expiredays) {
    var cookie = name + "=" + escape(value) + "; path=/;"
    if (typeof expiredays != 'undefined') {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        cookie += "expires=" + todayDate.toGMTString() + ";"
    }
    document.cookie = cookie;
}

// 쿠키 획득
var getCookie = function(name) {
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
    return null;
}

// 쿠키 삭제
var deleteCookie = function(name) {
    setCookie(name, "", -1);
}
// 에러
var fnError = function(request,status,error){
    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    console.log(request,status,error);
}