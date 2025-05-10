import { createRouter, createWebHistory } from 'vue-router'
import Register from '@/view/user/login/register.vue'
import Login from '@/view/user/login/login.vue'
import Calculator from "@/example/calculator.vue";
import Module from "@/view/modelNavigation/model.vue"
import Home from "@/view/home/home.vue"
import opencv from "@/view/opencv/opencv.vue"
import imageShow from "@/example/imageShow.vue";
import Product from "@/example/Product.vue";
import Surf from "@/view/opencv/opencvModel/surf/surf.vue";
import TestCookie from "@/view/test/testCookie.vue";
import opencvhome from "@/view/opencv/opencvModel/opencvhome/opencvhome.vue";
import Esp8266E from "@/example/Esp8266E.vue";
import VideoShow from "@/example/VideoShow.vue";
function configRouter(path, name, component) {
    return {
        path:path,
        name:name,
        component:component
    }
}
function configSetChild(path,component,children) {
    return {
        path:path,
        component:component,
        children:children
    }
}

const routes =[
    configRouter('/register','Register',Register),
    configRouter('/login','Login',Login),
    configRouter('/calculator','calculator',Calculator),
    configRouter('/Home','home',Home),
    configRouter('/module','module',Module),
    configRouter('/model/opencv','opencv',opencv),
    configRouter('/example/imageShow','imageShow',imageShow),
    configRouter('/example/Esp8266E','Esp8266E',Esp8266E),
    configRouter('/example/Product','Product',Product),
    configRouter('/test/testCookie','testCookie',TestCookie),
    configRouter('/example/VideoShow','videoShow',VideoShow),
    {
        path:'/model/opencv',
        component: opencv,
        children:[
            configRouter('/model/opencv/Surf','Surf',Surf)
        ]
    },
    configSetChild('/model/opencv/home',opencv,[
        configRouter('/model/opencv/home','opencv-home',opencvhome)
    ]),
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
