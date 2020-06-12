// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import store from './store'
import 'jQuery/tmp/jquery'
import '../static/js/rm'
//设置反向代理请求默认发送到8843/api
var axios=require('axios')
//http://121.37.153.17:8843/api
axios.defaults.baseURL='http://localhost:8843/api'

//全局注册，之后在其他组件中使用this.$axios 发送数据
Vue.prototype.$axios=axios

Vue.config.productionTip = false

Vue.use(mavonEditor)
Vue.use(Element)

axios.defaults.withCredentials=true //跨域可以携带cookie
router.beforeEach((to,from,next)=>{
  if(to.meta.requireAuth){ //判断是否要登录
    if(store.state.user.username){//store是否存在user信息
      var username=store.state.user.username
      var password=store.state.user.password
      axios.post('/authentication',{username:username,password:password}).then(resp=>{
        if(resp.data.message=='身份认证正确'){
          next()

        }else{
          next({
            path:'login',
            query:{redirect:to.fullPath} //存储访问路径
          })
        }
      })
    }else{
      next({
        path:'login',
        query:{redirect:to.fullPath} //存储访问路径
      })
    }
  }else{
    next()
  }
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  render:h=>h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
