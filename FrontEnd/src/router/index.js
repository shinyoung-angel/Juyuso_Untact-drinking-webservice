import Vue from 'vue'
import VueRouter from 'vue-router'

// accounts
import Login from '@/views/accounts/login.vue'
import MyData from '@/views/accounts/mydata.vue'
import MyPage from '@/views/accounts/mypage.vue'
import Signup from '@/views/accounts/signup.vue'

//tables
import TableList from '@/views/tables/table-list.vue'
import Table from '@/views/tables/table.vue'

//main
import Main from '@/views/main.vue'

//store
import store from '@/store/modules/accounts.js'
Vue.use(VueRouter)


const rejectAuthUser = (to, from, next) => {
  if (localStorage.getItem('jwt')) {
    console.log('로그인 접근을 reject함')
    console.log('router_page')
    console.log(store.state.isLogin)
    alert('이미 로그인을 하셨습니다')
    next('/')
  }else {
    next()
  }
}


const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/login',
    name: 'Login',
    beforeEnter: rejectAuthUser,
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/tables',
    name: 'TableList',
    component: TableList
  },
  {
    path: '/mypage/:userId',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/mydata/:userId',
    name: 'MyData',
    component: MyData,
    props: true
  },
  {
    path: '/table/:roomId',
    name: 'Table',
    component: Table,
    props: true,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})



export default router
