import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import ViewBooking from '../views/booking/ViewBooking.vue'
import Booking from '../views/booking/Booking.vue'
import ManageBooking from '../views/booking/ManageBooking.vue'
import Dashboard from '../views/Dashboard.vue'
import Employee from '../views/Employee.vue'
import Pass from '../views/Pass.vue'
import PassUser from '../views/PassUser.vue'
import Email from '../views/Email.vue'

const routes = [
    {
      path: '/',
      name: 'Home',
      component: Home,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/booking',
      name: 'Booking',
      component: Booking,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/booking/view',
      name: 'ViewBooking',
      component: ViewBooking,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/booking/manage',
      name: 'ManageBooking',
      component: ManageBooking,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/employee',
      name: 'Employee',
      component: Employee,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/pass',
      name: 'Pass',
      component: Pass,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/passUser',
      name: 'PassUser',
      component: PassUser,
    //   meta: { requiresAuth: true }
    },
    {
      path: '/email',
      name: 'Email',
      component: Email,
    //   meta: { requiresAuth: true }
    },
  
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router