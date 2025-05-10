<script>
import axios from "axios";

export default {
  data(){
    return {
      cookie:{
        username:'',
        token:[]
      }
    }
  },
  mounted() {

      this.checkCookieInit();
  },

  methods: {
    async checkCookieInit() {
      try {
        const url='http://localhost:8080/test/cookie/parse2'
        const response = await axios.get(url, {
          withCredentials: true
        })
        console.log('登录状态:', response.data)
      } catch (error) {
        // 添加详细的错误处理
        if (error.response) {
          console.error('服务器响应错误:', error.response.data)
          if (error.response.status === 401 || error.response.status === 400) {
            alert('请先登录')
          } else {
            alert(`请先登录`)
          }
        } else if (error.request) {
          console.error('请求未收到响应:', error.request)
          alert('网络连接异常，请检查网络')
        } else {
          console.error('请求配置错误:', error.message)
        }
      }
    },
    async setCookie() {
      try {
        const url = 'http://localhost:8080/test/cookie';
        const response = await axios.get(url, {
          params: { username: this.cookie.username },
          withCredentials: true,
          // 添加超时配置
          headers: {
            'Cross-Origin-Opener-Policy': 'same-origin-allow-popups',
            'Cross-Origin-Embedder-Policy': 'unsafe-none'
          },
          timeout: 5000
        });
        console.log('Cookie设置响应头:', response.headers);
        console.log('Cookie设置响应数据:', response.data);
      } catch (error) {
        console.error('完整错误信息:', error.toJSON());
        if (error.code === 'ECONNABORTED') {
          alert('请求超时，请检查网络连接');
        } else if (!error.response) {
          alert(`跨域错误: ${error.message}`);
        }
      }
    },
    async checkCookie(){
      const url='http://localhost:8080/test/cookie/parse'
      const response=await axios.get(url,{
        params:{
          username:this.cookie.username,
        },
        withCredentials: true
      })
      console.log(response.data);
    },
    async getCookie(){

    }
  }

}
</script>

<template>
    <div class="test-cookie">
      <a>用户名</a>
      <input class="username" v-model="cookie.username">
    </div>
     <div>
       <a>密码</a>
       <input class="password" >
     </div>


  <button class="login" @click="setCookie()">登录</button>
  <button class="isCookie" @click="checkCookie()">校验cookie</button>
  <button class="getCookie" @click="getCookie()">获取本地cookie</button>
</template>

<style scoped>

</style>