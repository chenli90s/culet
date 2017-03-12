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

    ```  
    {"aid":"",  ---->发言id
  	"status":"yum", ----->转发者
    "content":"天", -----> 正文
    "date":,     ----> 日期
     "hot":59,    -----> 热度
     "attribute":"teacher",  ----->转发域
     "userid":"",   -----> 用户id
    "comments":null }  ---->  不管 
    ```
    
- 输出参数：成功或失败

### 获取一些发言
 
 - url: /announce/addAnnounce.go
 
 - 参数: 获取一些发言
            
       @param string {一次请求多少个(requestNum：，当前分页数(currentPage}
       @return {当前分页内容(currentContent)，当前分页数(currentPage)，分页总数(totalPage)}
       
### 添加评论

- url: /announce/addComments.go
- 参数：
    ```
     @param commets  {"cid":"", ---->null
         *           "statues":"15", ----->备注状态
         *            "target":"哈哈",------>@的人
         *            "comment":"这个",------>内容
         *             "condate":1488813050000, --------->日期
         *            "acid":"", ------------>发言id（必填）
         *            "announce":null}
     ```  

### 获取当前发言评论

-  url:/announce/getAnnounceComments.go
-  参数：
    ````
    /**
         * 获取当前发言的评论
         * @param currentAnnounce {currentAnnounceId}
         * @return
         */
    ````
    
### 点赞

- url: /announce/likeAnnounce.go
- 参数：直接传送当前评论的aid

### 转发

- url：/announce/dispacherAnnounce.go
- 参数：
    ````
    转发
    * 
    * @param announce  {"aid":"",  ---->发言id（转发的id必填）
    *                  "status":"yum", ----->转发者（不管）
    *                  "content":"天", -----> 正文 （转发评论）
    *                  "date":,     ----> 日期
    *                  "hot":59,    -----> 热度 (填写)
    *                  "attribute":"teacher",  ----->转发域 (填写)
    *                  "userid":"",   -----> 用户id
    *                  "comments":null}  ---->  不管
    ````
    