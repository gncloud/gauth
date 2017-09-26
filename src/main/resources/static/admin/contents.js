$(function(){

    // login check
    isLogin();

    // tab event init function call
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var focusTabId = e.target.href.substring(e.target.href.lastIndexOf('#') + 1, e.target.href.length);
        if(focusTabId == 'client'){
            fnClientList();
        }else if(focusTabId == 'user'){
            userList();
        }else if(focusTabId == 'waitUser'){
            waitUserList();
        }
    });


    // 처음엔 클라이언트 목록 조회
    fnClientList();

    $('#logout').on('click',logout);
    $('#createClient').on('click',createClient);
    $('#updateClientSubmit').on('click', updateClient);
    $('#searchUserBtn').on('click',function(){
        var search = $('#searchUserInput').val() === undefined ? '' : $('#searchUserInput').val();
        userList(search);
    });



});
// 회원 (인증) 조회
var userList = function(search){
    $('#userList tbody').empty();
    fnAjax({
        url: '/v1/users?search=' + (search === undefined ? '' : search),
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success: function(data){
            $(data).each(function(i,o){
                var t = '<tr>';
                t += '  <td class="td-center">';
                t +=        o.userId;
                t += '  </td>';
                t += '  <td class="td-center">';
                t +=        o.email;
                t += '  </td>';
                t += '  <td class="td-center">';
                t +=        o.name;
                t += '  </td>';
                t += '  <td class="td-center">';
                t +=        o.phone;
                t += '  </td>';
                t += '  <td>';
                t +=        o.address;
                t += '  </td>';
                t += '  <td class="td-center">';
                t +=        o.company;
                t += '  </td>';
                t += '  <td class="td-center">';
                t +=        o.register_date.substring(0, o.register_date.indexOf(' '));
                t += '  </td>';
                t += '  <td class="td-center">';
                t += '      <a href="javascript:void(0);" onclick="updateUserClientModal(\'' + o.userId + '\');">조회</a>';
                t += '  </td>';
                t += '</tr>';
                $('#userList tbody').append(t);
            });
        },
        error:fnError
    });
};

var waitUserList = function(){


};
//유저의 클라이언트 권한 조회 모달
var updateUserClientModal = function(userId){
    $('#userClientList tbody').empty();
    $('#userClientList select').empty();

    fnAjax({
        url: '/v1/users/' + userId,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success: function(data){
            var userInfo = data.userInfo;
            var userClientList = data.userClientScopes;

            $(userClientList).each(function(i, o){
                var t = '<tr>';
                t += '<td>';
                t += o.clientId;
                t += '</td>';
                t += '<td>';
                $(userClientList).each(function(si, so){
                    if(so.clientId == o.clientId){
                        t +=    o.scopeId === null ? '' : o.scopeId;
                    }
                });


                t += '</td>';
                t += '<td>';

                t += '<td>';

                t += '</td>';
                t += '<td>';

                t += '</td>';
                t += '</tr>';
                $('#userClientList tbody').append(t);
            });
        },
        error:fnError
    });
    $('#updateUserClientModal').modal('show');
};

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
                var t = '<tr>';
                t += '   <td>';
                t +=        o.clientId
                t += '   </td>';
                t += '   <td>';
                t +=        o.clientSecret
                t += '   </td>';
                t += '   <td>';
                t +=        o.domain
                t += '   </td>';
                t += '   <td>';
                t +=        o.description
                t += '   </td>';
                t += '   <td align="center">';
                t +=        '<a href="javascript:void(0);" onclick="findScopeList(\'' + o.clientId + '\');">조회</a>';
                t += '   </td>'
                t += '   <td align="center">';
                t +=        '<a href="javascript:void(0);" onclick="updateClientModal(\'' + o.clientId + '\');">수정</a>';
                t += '   </td>'
                t += '   <td align="center">';
                t +=        '<a href="javascript:void(0);" onclick="removeClient(\'' + o.clientId + '\');">삭제</a>';
                t += '   </td>'
                t += '</tr>';
                $('#clientList tbody').append(t);
            });
        },
        error:fnError
    });
};


var findScopeList = function(clientId){
    $('#scopeList tbody').empty();
    fnAjax({
        url:'/v1/scopes?client=' + clientId,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){
            $(data).each(function(i,o){
                var t = '<tr>';
                t += '<th>';
                t +=    o.scopeId;
                t += '</th>';
                t += '<th>';
                t +=    o.description;
                t += '</th>';
                t += '</tr>';
                $('#scopeList tbody').append(t);
            });
            if(data === undefined || data.length == 0){
                $('#scopeList tbody').html('<tr><td colspan="5" align="center">등록된 권한이 없습니다.</td></tr>');
            }
        }
    });
};

var updateClientModal = function(clientId){
    fnAjax({
        url:'/v1/clients/' + clientId,
        type:'get',
        head:{'Authorization':getCookie('gauth')},
        success:function(data){
            $('#u_clientId').val(data.clientId);
            $('#u_secretKey').val(data.clientSecret);
            $('#u_domain').val(data.domain);
            $('#u_desc').val(data.description);
        }
    });
    $('#updateClientModal').modal('show');
};

var updateClient = function(){
    if(!confirm('수정 하시겠습니까?')){
        return false;
    }

    var clientId = $('#u_clientId').val();
    var secretKey = $('#u_secretKey').val();
    var domain = $('#u_domain').val();
    var desc = $('#u_desc').val();

    fnAjax({
        url:'/v1/clients/' + clientId,
        type:'put',
        head:{'Authorization':getCookie('gauth')},
        data: {
            clientId: clientId,
            clientSecret:secretKey,
            domain:domain,
            description:desc
        },
        success:function(data){
            fnClientList();
            $('#updateClientModal').modal('hide');
        },
        error: fnError
    });
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