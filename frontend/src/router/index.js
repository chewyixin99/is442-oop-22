import { createRouter, createWebHistory } from 'vue-router'

// import Home from '../views/Home.vue'
import ViewBooking from '../views/booking/ViewBooking.vue'
import ViewBookingAdmin from '../views/booking/ViewBookingAdmin.vue'

// import Booking from '../views/booking/Booking.vue'
import ManageBooking from '../views/booking/ManageBooking.vue'
import Dashboard from '../views/Dashboard.vue'
import Employee from '../views/Employee.vue'
import Pass from '../views/Pass.vue'
import PassUser from '../views/PassUser.vue'
import Email from '../views/Email.vue'
import Login from '../views/Login.vue'
import NotFound from '../views/NotFound.vue'
import SidebarAdmin from '../components/SidebarAdmin.vue'

import Sidebar from '../components/Sidebar.vue'
// import AdminOutlet from '../components/AdminOutlet.vue'

// https://router.vuejs.org/guide/advanced/composition-api.html

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { requiredAuthorization: false }
  },
  {
    path: '',
    component: Sidebar,
    children: [ // ALL ROUTES AFTER LOGIN
      // {
      //   path: '/',
      //   name: 'Home',
      //   component: Home,
      //   meta: {
      //     requiredAuthorization: true,
      //     roles: ['GOP', 'BORROWER']
      //   }
      // },
      {
        path: '/booking/view',
        name: 'ViewBooking',
        component: ViewBooking,
        meta: {
          requiredAuthorization: true,
          roles: ['GOP', 'BORROWER']
        }
      },
      {
        path: '/passUser',
        name: 'PassUser',
        component: PassUser,
        meta: { 
          requiredAuthorization: true, 
          roles: ['GOP','BORROWER'] 
        }
      },
    ]
  },
  {
    path: '/admin',
    component: SidebarAdmin,
    // meta: {
    //   requiredAuthorization: true,
    //   roles: ['ADMIN']
    // },
    children: [ // ALL ROUTES AFTER LOGIN
      {
        path: '',
        name: 'Dashboard',
        component: Dashboard,
        meta: {
          requiredAuthorization: true,
          roles: ['ADMIN']
        }
      },
      {
        path: '/admin/booking/view',
        name: 'ViewBookingAdmin',
        component: ViewBookingAdmin,
        meta: {
          requiredAuthorization: true,
          roles: ['ADMIN']
        }
      },
      {
        path: '/admin/booking/manage',
        name: 'ManageBooking',
        component: ManageBooking,
        meta: {
          requiredAuthorization: true,
          roles: ['ADMIN']
        }
      },
      {
        path: '/admin/employee',
        name: 'Employee',
        component: Employee,
        meta: {
          requiredAuthorization: true,
          roles: ['ADMIN']
        }
      },
      {
        path: '/admin/pass',
        name: 'Pass',
        component: Pass,
        meta: {
          requiredAuthorization: true,
          roles: ['ADMIN']
        }
      },
      {
        path: '/admin/email',
        name: 'Email',
        component: Email,
        meta: {
          requiredAuthorization: true,
          roles: ['ADMIN']
        }
      },
    ]
  },
  {
    path: '/:catchAll(.*)',
    name: 'NotFound',
    component: NotFound,
    meta: { requiredAuthorization: false }
  }



]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => { // This way, you don't need to write hook for each route
  // get where user being stored ex:
  // const user = store.getter('user') // assume user have a role with `user.role`
  // const user = {role: 'ADMIN'}
  const user = localStorage.getItem('userType')
  if (to.meta.requiredAuthorization) {
    if (to.meta?.roles?.includes(user)) {
      next()
    } else {
      next('/login')
    }
  } else {
    next()
  }
})


export default router