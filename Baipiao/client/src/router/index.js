import Vue from 'vue'
import VueRouter from 'vue-router'
import DashboardLayout from '../views/DashboardLayout.vue'
import Dashboard from '../views/Dashboard.vue'
import NotFound from '../views/404.vue'
import LoginAndRegister from '../views/LoginAndRegister.vue'
import BaipiaoUser from '../views/BaipiaoUser.vue'
import BaipiaoList from '../views/BaipiaoList.vue'
import BaipiaoVenue from '@/views/BaipiaoVenue.vue'
import BaipiaoCategory from '@/views/BaipiaoCategory.vue'
import BaipiaoOrganization from '@/views/BaipiaoOrganization.vue'
import BaipiaoEvent from '@/views/BaipiaoEvent.vue'



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
        // Login Auth
        meta: {requiresAuth: true},
      },
      {
        path: "baipiaoEvents",
        name: "baipiaoEvents",
        component: BaipiaoEvent,
        meta: {requiresAuth: true, requiredUserTypes: ['admin', 'organization', 'user']}
      },
      {
        path: "baipiaolist",
        name: "baipiaolist",
        component: BaipiaoList,
        meta: {requiresAuth: true, requiredUserTypes: ['admin']}
      },
      {
        path: "baipiaouser",
        name: "baipiaouser",
        component: BaipiaoUser,
        meta: {requiresAuth: true, requiredUserTypes: ['admin']}
      },
      {
        path: "baipiaoVenue",
        name: "baipiaoVenue",
        component: BaipiaoVenue,
        meta: {requiresAuth: true, requiredUserTypes: ['admin', 'organization']}
      },
      {
        path: "baipiaoCategory",
        name: "baipiaoCategory",
        component: BaipiaoCategory,
        meta: {requiresAuth: true, requiredUserTypes: ['admin']}
      },

      {
        path: "baipiaoOrganization",
        name: "baipiaoOrganization",
        component: BaipiaoOrganization,
        meta: {requiresAuth: true, requiredUserTypes: ['admin']}
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


// Helper function to get user type from localStorage
function getUserType() {
  const userType = localStorage.getItem("token");
  return userType ? JSON.parse(userType) : null;
}

// Router Guard
router.beforeEach((to, from, next) => {
  const userType = getUserType(); // 直接從 localStorage 獲取 userType

  // Verify Credential
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // if not login
    if (!userType) {
      next(to.name === 'login' ? undefined : { name: 'login' });
      return;
    }

    //if user has already login, still enter login page
    if (to.name === 'login') {
      // redirect to dashboard
      next({ name: 'dashboard' }); 
      return;
    }

    // Verify the userType (admin, organization, user)
    if (to.matched.some(record => record.meta.requiredUserTypes)) {
      const requiredUserTypes = to.meta.requiredUserTypes;
      if (!requiredUserTypes.some(type => userType === type)) {
        next({ name: '404' }); 
        return;
      }
    }
  }
  next();
});



export default router
