# 微信HTTP接口说明

## 获取uuid

- 说明：用于获取显示二维码以及登录所需的uuid，表示扫码和获取二维码是一个人
- 请求方式：GET
- 地址：https://login.wx.qq.com/jslogin
- 请求参数：

| 参数  | 示例               | 说明           |
| ----- | ------------------ | -------------- |
| appid | wx782c26e4c19acffb | 固定值         |
| fun   | new                | 固定值         |
| lang  | zh_CN              | 表示中文字符集 |

- 返回：

```
window.QRLogin.code = 200; window.QRLogin.uuid = "wb7R2kx9dA==";
```

- 说明: 请求参数固定，保存uuid值

## 获取二维码

- 说明：展示一张用于登陆的二维码图片，地址里的**{uuid}**传第一步所获取的**uuid**
- 请求方式：GET
- 地址：https://login.wx.qq.com/qrcode/{uuid}
- 请求参数：无参数
- 返回：二维码的二进制流，浏览器打开会直接显示一张二维码图片

## 监听二维码扫描结果

- **说明**：尝试登录。若此时用户手机已完成扫码并点击登录，则返回一个真正用于登录的**url**地址。否则接口大概10s后返回未扫码或未登录的状态码

- **请求方式**：GET

- **地址**：[https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login](https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login)

- **请求参数**：

  | **参数** | **示例值**   | **说明**            |
  | -------- | ------------ | ------------------- |
  |   loginicon    |   true                       |  固定值；           |
  | tip      | 0\|1          | 0表示等待用户扫码确认；1表示等待用户直接确认，用于push登陆时； |
  | uuid     | wb7R2kx9dA== | 第一步所获取的uuid  |

- **返回**：redirect_uri 的值可以直接用于下一步的“登录并获取公参”请求

```javascript
window.code=200（408为未扫码，201为已扫码但未点击登录，200为成功登录）;
window.redirect_uri="https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=AfrMMbhsnElmA7xc1R9CWUq-@qrticket_0&uuid=4ZnG7WZ0Cg==&lang=zh_CN&scan=1520738372";
```

- 说明：
  - tip = 0
  - code = 408 表示未及时扫码，需再次请求
  - code = 400 二维码失效，重新获取uuid以及二维码
  - code = 201 用户扫码未点击登录，下次请求tip = 1
  - code = 200 用户扫码以及确认，返回登录信息redirect_uri 

## 获取登录参数

- 请求方式：GET
- 地址： redirect_uri + &fun=new&version=v2
- 返回参数：获取并存储该返回值中的四个参数**skey**、**wxsid**、**wxuin**、**pass_ticket**，以及所返回Cookie中的**webwx_data_ticket**，**webwx_auth_ticket**

URL参数分析：

服务器返回数据：

```XML
<error>
    <ret>0</ret>
    <message></message>
    <skey>@crypt_jkde99da_b4xxxxxxxxxxxxxxx76d9yualp</skey>
    <wxsid>8rwxxxxxxxxxHq2P</wxsid>
    <wxuin>26xxx7</wxuin>
    <pass_ticket>AFsdZ7eHxxxxxxxxxxxxxxxxfr1vjHPn=</pass_ticket>
    <isgrayscale>1</isgrayscale>
</error>
```

***（为了个人隐私，以上返回数据有打码）***

- ret表示请求返回状态，0表示成功。
- skey、wxsid和wxuin都是具体微信用户的信息，不会变化，在后续的通讯中需要用到。
- pass_ticket在初始化页面时需要用到。