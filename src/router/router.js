import { createRouter, createWebHistory } from 'vue-router'
import Register from '@/view/user/register.vue'
import Login from '@/view/user/login.vue'
import Calculator from "@/example/calculator.vue";
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
        path: '/',
        redirect: '/login',
    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
