<style scoped>
    .layout{
        border: 1px solid #d7dde4;
        background: #f5f7f9;
    }
    .layout-logo{
        width: 100px;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        float: left;
        position: relative;
        top: 15px;
        left: 20px;
    }
    .layout-nav{
        width: 420px;
        margin: 0 auto;
    }
    .layout-assistant{
        width: 300px;
        margin: 0 auto;
        height: inherit;
    }
    .layout-breadcrumb{
        padding: 10px 15px 0;
    }
    .layout-content{
        min-height: 200px;
        margin: 15px;
        overflow: hidden;
        background: #fff;
        border-radius: 4px;
    }
    .layout-content-main{
        padding: 10px;
    }
    .layout-copy{
        text-align: center;
        padding: 10px 0 20px;
        color: #9ea7b4;
    }
</style>
<template>
    <div class="layout">
        <Menu mode="horizontal" theme="dark" active-key="1">
            <row> 
                <i-col span="6"><div class="layout-logo"></div></i-col>
                <i-col span="8">
                    <div> 
                        <i-input type="text" placeholder="请输入..." style="width: 40%;"></i-input>
                        <i-button type="primary" shape="circle" icon="ios-search"></i-button>
                    </div>
                </i-col>
                <i-col span="3" offset="7">
                   
                        <i-button type="primary" @click="modal_login = true">登陆</i-button>
                        <i-button type="primary" @click="modal_register = true">注册</i-button>
                  
                   
                </i-col>
                        
                
            </row>    
        </Menu>

      
        <div class="layout-copy">
            2011-2016 &copy; TalkingData
        </div>
    </div>
        <Modal :visible.sync="modal_login" :mask-closable="false" width="360">
            <p slot="header" style="color:#0c6;text-align:center">
                <Icon type="information-circled"></Icon>
                <span>登陆</span>
            </p>
            <div style="text-align:left">
                <i-form v-ref:login-validate :model="loginValidate" :rules="ruleValidate" :label-width="80">
                    <Form-item label="姓名" prop="name">
                        <i-input :value.sync="loginValidate.name" placeholder="请输入用户名/邮箱">
                             <Icon type="ios-person-outline" slot="prepend"></Icon>
                        </i-input>
                    </Form-item>
                    <Form-item label="密码" prop="password">
                        <i-input type="password" :value.sync="loginValidate.password" placeholder="请输入密码">
                             <Icon type="ios-locked-outline" slot="prepend"></Icon>
                        </i-input>

                    </Form-item>
                    <Form-item>
                            <Checkbox-group>
                            <Checkbox :value.sync="loginValidate.auto">记住登陆</Checkbox>
                        </Checkbox-group>
                    </Form-item>
                </i-form>
            </div>
            <div slot="footer">
                <i-button type="success" @click="handleSubmit('loginValidate')" size="large" long :loading="loginButton.loading">{{loginButton.text}}</i-button>
            </div>
        </Modal>


        <Modal :visible.sync="modal_register" :mask-closable="false" width="360">
            <p slot="header" style="color:#0c6;text-align:center">
                <Icon type="information-circled"></Icon>
                <span>注册</span>
            </p>
            <div style="text-align:left">
                <i-form v-ref:register-validate :model="registerValidate" :rules="ruleValidate" :label-width="80">
                    <Form-item label="用户名" prop="name">
                        <i-input :value.sync="registerValidate.name" placeholder="请输入用户名">
                            <Icon type="ios-person-outline" slot="prepend"></Icon>
                        </i-input>
                    </Form-item>
                    <Form-item label="邮箱" prop="mail">
                        <i-input :value.sync="registerValidate.mail" placeholder="请输入邮箱">
                            <Icon type="ios-email-outline" slot="prepend"></Icon>
                        </i-input>
                    </Form-item>
                    <Form-item label="密码" prop="password">
                        <i-input type="password" :value.sync="registerValidate.password" placeholder="请输入密码">
                            <Icon type="ios-locked-outline" slot="prepend"></Icon>
                        </i-input>

                    </Form-item>
                    <Form-item label="重复密码" prop="password_confirmation">
                        <i-input type="password" :value.sync="registerValidate.password_confirmation" placeholder="再次输入密码">
                            <Icon type="ios-locked-outline" slot="prepend"></Icon>
                        </i-input>

                    </Form-item>                    
                </i-form>
            </div>
            <div slot="footer">
                <i-button type="success" @click="handleSubmit('registerValidate')" size="large" long :loading="registerButton.loading">{{registerButton.text}}</i-button>
            </div>
        </Modal>        
</template>
<script>

    export default {
        data () {
            return {
                modal_login: false,
                modal_register: false,
                loginValidate: {
                    name: '',
                    password: '',
                    mail: '',
                    auto: false
                },
                 registerValidate: {
                    password: '',
                    password_confirmation:'',
                    mail: '',
                }, 
                loginButton: {
                    text: '登录',
                    loading: false
			    },
                registerButton: {
                    text: '注册',
                    loading: false
			    },
                ruleValidate: {
                    name: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不能为空', trigger: 'blur' },
                        { type: 'string', min: 6, message: '密码不能少于六个字符', trigger: 'blur' }
                    ],
                    password_confirmation: [    
                        {validator: (rule, value, callback) => {
                            if (!value) {
                                return callback(new Error('请输入密码'));
                            }
                            if (value !== this.registerValidate.password) {
                                return callback(new Error('两次输入密码不一致'));
                            }
                            return callback();
                        }, trigger: 'blur'}
				    ],
                    mail: [
                        { required: true, message: '邮箱不能为空', trigger: 'blur' },
                        { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
                    ]
                }
             
            }
        },
        methods: {
            handleSubmit (name) {
                 this.$refs[name].validate((valid) => {
                    if (valid) {                     
                        if(name === "loginValidate"){
                            this.loginButton.text = "登陆中";
                            this.loginButton.loading = true;
                            this.$http.post('/user/loginUser.go', {
                                'username': this.loginValidate.name,
                                'password': this.loginValidate.password
                            }).then((response) => {
                                if(response.data.errcode == false){
                                    this.$store.commit('setAccessToken', response.data.info);
                                    this.$store.commit('login');
                                    localStorage.access_token = JSON.stringify({'username': this.loginValidate.name,'password': this.loginValidate.password});

                                    this.$Message.destroy();
                                    this.$Message.success('登陆成功!');
                                    //this.$router.go('/home');
                                    this.$router.redirect({name: 'home'});
                                } 
                            },(error) => {
                                console.log(error);
                            });

                        }
                        if(name === "registerValidate"){
                            this.registerButton.text = "注册中";
                            this.registerButton.loading = true;
                             this.$http.post('/user/registUser.go', {
                                'username': this.registerValidate.name,
                                'eMail': this.registerValidate.mail,
                                'password': this.registerValidate.password
                            }).then((response) => {
                                console.log(response)
                                if(response.data.errcode == false){
                                   this.$store.commit('setAccessToken', response.data.info);
                		           this.$store.commit('login');
                                   localStorage.access_token = this.$store.state.access_token;


                                    this.$Message.destroy();
                                    this.$Message.success('注册成功,请登陆!');
                                    this.modal_register = false;
                                    this.modal_login = true;
                                }
                            },(error) => {
                                console.log(error);
                            });                           
                        }

                    } else {
                        this.$Message.error('表单验证失败!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            }
        }       
    }
</script>