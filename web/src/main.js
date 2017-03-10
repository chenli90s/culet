import Vue from 'vue';
import Vuex from 'vuex'
import VueRouter from 'vue-router';
import axios from 'axios';
import App from 'components/app.vue';
import Routers from './router';
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import VueI18n from 'vue-i18n';
import Locales from './locale';
import zhLocale from 'iview/src/locale/lang/zh-CN';
import enLocale from 'iview/src/locale/lang/en-US';

Vue.prototype.$http = axios;
Vue.prototype.$store = Vuex;
Vue.use(VueRouter);
Vue.use(iView);
Vue.use(VueI18n);
Vue.use(Vuex);

// 开启debug模式
Vue.config.debug = true;
// 自动设置语言
const navLang = navigator.language;
const localLang = (navLang === 'zh-CN' || navLang === 'en-US') ? navLang : false;
const lang = window.localStorage.getItem('language') || localLang || 'zh-CN';

Vue.config.lang = lang;

// 多语言配置
const locales = Locales;
const mergeZH = Object.assign(zhLocale, locales['zh-CN']);
const mergeEN = Object.assign(enLocale, locales['en-US']);
Vue.locale('zh-CN', mergeZH);
Vue.locale('en-US', mergeEN);

// Vuex定义
const store = new Vuex.Store({
	state: {
		isLogin: false,
		user: {},
		access_token: ''
	},
	mutations: {
		setAccessToken(state, access_token) {
			state.access_token = access_token;
		},
		login(state) {
			state.isLogin = true;
		},
		logout(state) {
			state.isLogin = false;
			state.user = {};
			state.access_token = '';
		},
		setUser(state, user) {
			state.user = user;
		}
	}
});

// 路由配置
let router = new VueRouter({
    // 是否开启History模式的路由, 如果生产环境的服务端没有进行相关配置,请慎用
    history: false
});

router.map(Routers);

router.beforeEach(({to, next, redirect}) => {
    window.scrollTo(0, 0);
    // Auth验证
    if (to.auth) {
    	if (!store.state.isLogin) {
    		if (localStorage.access_token) {
    			// 自动登录
    			store.commit('setAccessToken', localStorage.access_token);
    			store.commit('login');
    			// 获取用户信息
    			Vue.$http.post('/user/loginUser.go', JSON.parse(localStorage.access_token)
    			).then((response) => {
    				store.commit('setUser', response.body.data);
    			}, (error) => {
    				redirect({name: 'index'});
    			});
    			return true;
    		}
    		redirect({name: 'index'});
    	}
    }
    return true; 
});

router.afterEach(() => {

});

router.redirect({
    '*': "/index"
});
App.store = store;
router.start(App, '#app');