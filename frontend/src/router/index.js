import { createRouter, createWebHistory } from 'vue-router'
import jwt_decode from "jwt-decode";

import ViewBooking from '../views/booking/ViewBooking.vue'
import ViewBookingAdmin from '../views/booking/ViewBookingAdmin.vue'
import ViewBookingGOP from '../views/booking/ViewBookingGOP.vue'
import ManageBooking from '../views/booking/ManageBooking.vue'
import ManageBookingGOP from '../views/booking/ManageBookingGOP.vue'

import Dashboard from '../views/Dashboard.vue'
import Employee from '../views/Employee.vue'
import Pass from '../views/Pass.vue'
import PassUser from '../views/PassUser.vue'
import Email from '../views/Email.vue'
import Login from '../views/Login.vue'
import ResetPassword from '../views/ResetPassword.vue'
import page404 from '../views/page404.vue'

import SidebarAdmin from '../components/admin/SidebarAdmin.vue'
import SidebarGOP from '../components/gop/SidebarGOP.vue'
import Sidebar from '../components/borrower/Sidebar.vue'


const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: { requiredAuthorization: false }
  },
  {
    path: '/resetPassword',
    name: 'ResetPassword',
    component: ResetPassword,
    meta: { requiredAuthorization: false }
  },
  {
    path: '/staff',
    component: Sidebar,
    children: [ 
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
          roles: ['BORROWER']
        }
      },
    ]
  },
  {
    path: '/admin',
    component: SidebarAdmin,
    children: [ 
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
    path: '/gop',
    component: SidebarGOP,
    children: [ 
      {
        path: '/gop/booking/view',
        name: 'ViewBookingGOP',
        component: ViewBookingGOP,
        meta: {
          requiredAuthorization: true,
          roles: ['GOP']
        }
      },
      {
        path: '/gop/booking/manage',
        name: 'ManageBookingGOP',
        component: ManageBookingGOP,
        meta: {
          requiredAuthorization: true,
          roles: ['GOP']
        }
      },
      {
        path: '/gop/passes',
        name: 'Passes',
        component: PassUser,
        meta: {
          requiredAuthorization: true,
          roles: ['GOP']
        }
      },
    ]
  },
  {
    path: '/:catchAll(.*)',
    name: 'page404',
    component: page404,
    meta: {
      requiredAuthorization: true,
      roles: ['GOP', 'BORROWER', 'NULL']
    }
  }



]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => { 

  // check if the route requires authorization, if yes check if the user is logged in, else redirect to login page
  if (to.meta.requiredAuthorization) {
    const token = localStorage.getItem('token') ? localStorage.getItem('token') : 'NULL'
    if (token != 'NULL'){
      const decoded = jwt_decode(token);
      if (to.meta?.roles?.includes(decoded.scope)) {
        next()
      } else {
        next('/')
      }
    }
    else {
      next('/')
    }
   
  } else {
    next()
  }
})


export default router