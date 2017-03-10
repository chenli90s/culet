# culet
0.1


### 检查username是否存在
  - url: /user/checkAccount.go
  - 输入参数: username
  - 输出参数: 
    - errcode: false为不存在   true为存在
    - msg: 
    
### 注册账户
  - url：/user/registUser.go
  - 输入参数：username,password,eMail
  - 输出参数: 
    - errcode: 注册false为成功   true为失败
    - msg: 错误信息
    
### 登陆 
  - url：/user/loginUser.go
  - 输入参数: username,password
  - 输出参数: 
    - errcode: false为登陆成功   true为失败
    - msg: 
    
### 获取用户信息
  - url:/user/getUserInfo.go
  - 输入参数: 
  - 输出参数: 
    - errcode: false设置成功   true为失败
    - msg: 失败信息
    
### 上传头像图片
  - url:/user/updataHeadImg.go
  - 输入参数: file
  - 输出参数: 
    - errcode: false上传成功   true为失败
    - msg: 失败信息
    
### 设置用户信息
  - url：/user/setUserInfo.go
  - 输入参数: niclname(昵称),gender(性别),mobile(手机号),birthdy(生日),declaration（描述）
  - 输出参数: 
    - errcode: false上传成功   true为失败
    - msg: 失败信息
    
### 登出
   - url:/user/loginUserOut.go
    

## 发言功能

 ### 发言
	
- url： /announce/addAnnouce.go
	
	- 输入参数：	 
			
		``` {"aid":"",  ---->发言id
  	        "status":"yum", ----->转发者
            "content":"天", -----> 正文
    	    "date":,     ----> 日期
            "hot":59,    -----> 热度
            "attribute":"teacher",  ----->转发域
            "userid":"",   -----> 用户id
            "comments":null}  ---->  不管```

    

	- 输出参数：

- 获取一些发言
 
 - 参数