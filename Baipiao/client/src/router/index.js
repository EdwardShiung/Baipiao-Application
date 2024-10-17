import Vue from 'vue'
import VueRouter from 'vue-router'
import DashboardLayout from '../views/DashboardLayout.vue'
import Dashboard from '../views/Dashboard.vue'
import NotFound from '../views/404.vue'
import LoginAndRegister from '../views/LoginAndRegister.vue'
import BaipiaoUser from '../views/BaipiaoUser.vue'
// import BaipiaoList from '../views/BaipiaoList.vue'
import BaipiaoVenue from '@/views/BaipiaoVenue.vue'
import BaipiaoCategory from '@/views/BaipiaoCategory.vue'
import BaipiaoOrganization from '@/views/BaipiaoOrganization.vue'


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
      // Don't Delete!!!!
      // {
      //   path: "baipiaolist",
      //   name: "baipiaolist",
      //   component: BaipiaoList,
      // },
      {
        path: "baipiaouser",
        name: "baipiaouser",
        component: BaipiaoUser,
      },
      {
        path: "baipiaoVenue",
        name: "baipiaoVenue",
        component: BaipiaoVenue,
      },
      {
        path: "baipiaoCategory",
        name: "baipiaoCategory",
        component: BaipiaoCategory,
      },

      {
        path: "baipiaoOrganization",
        name: "baipiaoOrganization",
        component: BaipiaoOrganization,
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
    next();
    //  isLogin ? next() : next("/login");
   }

 })




export default router
