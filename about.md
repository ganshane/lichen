# About

#### Quick Start

| code | description            |
| ---- | ---------------------- |
| 0    | SUCCESS                |
| 1    | INVALID\_PARAMETERS    |
| 2    | INVALID\_TOKEN         |
| 3    | INVALID\_RETURN\_VALUE |
| 4    | EXPIRED\_TOKEN         |
| 5    | ACCESS\_DENIED         |
| 6    | RESOURCE\_NOT\_FOUND   |
| 7    | FAIL\_PARSE\_XML       |
| 9999 | UNKNOWN                |

* \
  If you have any problem,please contact us without any hesitation.

#### 签名

MF-ACCESS-SIGN的请求头是对timestamp + method + requestPath + body字符串（+表示字符串连接），以及SecretKey，使用HMAC SHA256方法加密，通过Base-64编码输出而得到的。

如：sign=CryptoJS.enc.Base64.stringify(CryptoJS.HmacSHA256(timestamp + 'GET' + '/api/config?p1=value', SecretKey))

其中，timestamp的值与MF-ACCESS-TIMESTAMP请求头相同，为ISO格式，如2020-12-08T09:08:57.715Z。

method是请求方法，字母全部大写：GET/POST。

requestPath是请求接口路径,包含query参数。如：/api/config?p1=value1

body是指请求主体的字符串，如果请求没有主体（通常为GET请求）则body可省略。如：{"p1":"v1"}

#### Signature

The MF-ACCESS-SIGN header is generated as follows:

* Create a prehash string of timestamp + method + requestPath + body (where + represents String concatenation).
* Prepare the SecretKey.
* Sign the prehash string with the SecretKey using the HMAC SHA256.
* Encode the signature in the Base64 format.

Example: sign=CryptoJS.enc.Base64.stringify(CryptoJS.HmacSHA256(timestamp + 'GET' + '/api/config?p1=v1', SecretKey))

The timestamp value is the same as the MF-ACCESS-TIMESTAMP header with millisecond ISO format, e.g. 2020-12-08T09:08:57.715Z.

The request method should be in UPPERCASE: e.g. GET and POST.

The requestPath is the path of requesting an endpoint.

Example: /api/config?p1=v1

The body refers to the String of the request body. It can be omitted if there is no request body (frequently the case for GET requests).

Example: {"p1":"v1")
