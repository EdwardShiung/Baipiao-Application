import Vue from 'vue'
import VueRouter from 'vue-router'
import DashboardLayout from '../views/DashboardLayout.vue'
import Dashboard from '../views/Dashboard.vue'
import NotFound from '../views/404.vue'
import LoginAndRegister from '../views/LoginAndRegister.vue'
import BaipiaoMap from '../views/BaipiaoMap.vue'
import BaipiaoList from '../views/BaipiaoList.vue'



Vue.use(VueRouter)

const routes = [

  {
    path: '/login',
    name: 'login',
    component: LoginAndRegister,
  },
  {
    path: '/',
    component: DashboardLayout,
    redirect: 'dashboard',
    children:[
      {
        path: "dashboard",
        name: "dashboard",
        component: Dashboard,
      },
      {
        path: "baipiaolist",
        name: "baipiaolist",
        component: BaipiaoList,
      },
      {
        path: "baipiaomap",
        name: "baipiaomap",
        component: BaipiaoMap,
      },
    ],
  },
  {
    path:'*',
    name:'404',
    component: NotFound,
  },
]

const router = new VueRouter({
  routes
})

// Router Guard
router.beforeEach((to, from, next) => {
  const isLogin = localStorage.eleToken ? true : false;
   if (to.path == "/login" || to.path == "/register") {
     next();
   } else {
     isLogin ? next() : next("/login");
   }
 })




export default router
