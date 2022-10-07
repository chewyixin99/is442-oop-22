import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import Booking from '../views/Booking.vue'
import Dashboard from '../views/Dashboard.vue'
import Employee from '../views/Employee.vue'
import Pass from '../views/Pass.vue'
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