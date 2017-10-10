(function ($) {

    'gncloud gauth'


// data format
//var data = JSON.stringify({
//    "url": "",
//    "type": "",
//    "body": {
//        "clientId": "test",
//        "email": "test@gncloud.kr"
//    },
//    "header": {"name","content"}
//});


    var gauth = {
        server_info: {
            type: 'POST',
            path: 'gauth',
            contentType: 'application/json',
            cacheControl: 'no-cache'
        },
        init: function(opt){
            if(opt !== undefined){
                if(opt.type !== undefined){
                    this.server_info.type = opt.type;
                }
                if(opt.host !== undefined){
                    this.server_info.path = opt.host;
                }
                if(opt.contentType !== undefined){
                    this.server_info.contentType = opt.contentType;
                }
                if(opt.cacheControl !== undefined){
                    this.server_info.cacheControl = opt.cacheControl;
                }
            }
            console.log(this.server_info);
        },
        api: function (data, successCallback, errorCallback){
            console.log("요청", data);

            var xhr;
            //var xhr = new XMLHttpRequest();
            if (window.XMLHttpRequest) { // 모질라, 사파리등 그외 브라우저, ...
                xhr = new XMLHttpRequest();
            } else if (window.ActiveXObject) { // IE 8 이상
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }

            xhr.withCredentials = true;

            xhr.addEventListener("readystatechange", function () {

            if (this.readyState === 4) {
                var response = JSON.parse(this.response);
                var gauth_result = {};

                try {
                  gauth_result = JSON.parse(response.gauth_result);
                }catch (e) {
                  gauth_result = response.gauth_result;
                }

                var result = {
                    code: response.code,
                    result: gauth_result,
                };

                console.log("요청 결과", this);
                if(successCallback !== undefined
                        && successCallback !== null
                        && result.code !== undefined
                        && result.code.startsWith('2')){
                    successCallback(result, this);
                }else if(errorCallback !== undefined
                            && errorCallback !== null){
                    errorCallback(result, this);
                }
            }
            });
            xhr.open(gauth.server_info.type, gauth.server_info.path);
            xhr.setRequestHeader("content-type", gauth.server_info.contentType);
            xhr.setRequestHeader("cache-control", gauth.server_info.cacheControl);
            xhr.send(data);
        },
        getReq: function(url, type, body, header){
            body   = body === undefined ? {} : body;
            header = header === undefined ? {} : header;
            return JSON.stringify({
                        "url": url,
                        "type": type,
                        "body": body,
                        "header": header
                    });
        },
        setCookie: function(data){
            var gauth = 'gauth=';
            var tokenId = data.result.tokenId;
            cookies = gauth + escape(tokenId) + ';expires=;';
            document.cookie = cookies;
        },
        getCookie: function(){
            var gauth = 'gauth=';
            var cookieData = document.cookie;
            var start = cookieData.indexOf(gauth);
            var cValue = '';
            if(start != -1){
                start += gauth.length;
                var end = cookieData.indexOf(';', start);
                if(end == -1){
                    end = cookieData.length;
                }
                cValue = cookieData.substring(start, end);
            }
            return unescape(cValue);
        },
        removeCookie: function(){
            var gauth = 'gauth=';
            cookies = gauth + ';expires=-1;';
            document.cookie = cookies;
        },
        isLogin: function(loginUrl, nextUrl){
            var tokenId = this.getCookie();
            if(tokenId !== undefined && tokenId != ''){
                var data = this.getReq('/tokens/' + tokenId, 'get', {});
                this.api(data, function(response,obj){
                    var nowDate = new Date();
                    var expireDate = new Date(response.result.expireDate);
                    if(nowDate >= expireDate){
                        location.href = loginUrl;
                    }else if(nextUrl !== undefined){
                        location.href = nextUrl;
                    }
                }, function(){
                    location.href = loginUrl;
                });
            }
        }
    }
    $.gauth = gauth;
})(jQuery);
