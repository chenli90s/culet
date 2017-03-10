const routers = {
    '/index': {
        name:'index',
        component(resolve) {
            require(['./views/index.vue'], resolve);
        },
        auth: false
    },
    '/home': {
        name:'home',
        component(resolve) {
            require(['./views/home.vue'], resolve);
        },
        auth: true
    }    
};
export default routers;