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
            host: 'http://localhost:8080/gauth',
            contentType: 'application/json',
            cacheControl: 'no-cache'
        },
        init: function(opt){
            if(opt !== undefined){
                if(opt.type !== undefined){
                    this.server_info.type = opt.type;
                }
                if(opt.host !== undefined){
                    this.server_info.host = opt.host;
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
        api: function (data, callback){
            console.log("gauth api call",data, callback);

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
                if(response.gauth_result !== undefined){
                    gauth_result = JSON.parse(response.gauth_result);
                }

                var result = {
                    code : response.code,
                    result : gauth_result
                };

                console.log("요청 결과", this);

                callback(result, this);
              }
            });

            xhr.open(gauth.server_info.type, gauth.server_info.host);
            xhr.setRequestHeader("content-type", gauth.server_info.contentType);
            xhr.setRequestHeader("cache-control", gauth.server_info.cacheControl);
            xhr.send(data);
        },
        dataFormat: function(url, type, body, header){
            body   = body === undefined ? {} : body;
            header = header === undefined ? {} : header;

            return JSON.stringify({
                        "url": url,
                        "type": type,
                        "body": body,
                        "header": header
                    });
        }
    }

    $.gauth = gauth;

})(jQuery);
