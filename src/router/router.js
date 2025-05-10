import { createRouter, createWebHistory } from 'vue-router'
import Register from '@/view/user/login/register.vue'
import Login from '@/view/user/login/login.vue'
import Calculator from "@/example/calculator.vue";
import Module from "@/view/modelNavigation/model.vue"
import Home from "@/view/home/home.vue"
const routes = [
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/calculator',
        name: 'calculator',
        component: Calculator
    },
    {
        path:'/Home',
        name:'home',
        component: Home

    },
    {
      path: '/module',
      name: 'Module',
      component:   Module,
    },
    {
        path: '/',
        redirect: '/login',
    }
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router
