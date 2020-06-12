import Vue from 'vue'
import Router from 'vue-router'
//import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/Login'
import Home from '@/components/Home'
Vue.use(Router)

export default new Router({
  mode:'history',
  routes: [
    {
      path:'/',
      name:'',
      redirect:'/index'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path:'/regist',
      name:'Regist',
      component:()=>import('@/components/Regist')
    },
    {
      path:'/home',
      name:'Home',
      component:Home,
      redirect:'/index',
      children:[
        {
          path:'/index',
          name:'AppIndex',
          component: () =>import('@/components/home/AppIndex'),
          meta:{
            requireAuth:true //是否拦截
          }
        },
        {
          path:'/library',
          name:'Library',
          component:()=>import('@/components/library/LibraryIndex'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/jotter',
          name:'Jotter',
          component:()=>import('@/components/admin/jotter/Articles'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/photos',
          name:'Photos',
          component:()=>import('@/components/photos/Photos'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/music',
          name:'Music',
          component:()=>import('@/components/music/Music'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/admin',
          name:'Admin',
          component:()=>import('@/components/admin/MyHome'),
          meta:{
            requireAuth:true
          }
        }
      ]

    }
  ]
})
