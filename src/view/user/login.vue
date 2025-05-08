<script>
import LoginViewModel from '@/viewmodel/user/login.js'
import loginData from "@/model/user-data/loginData.js";
export default {
  data(){
    return {
      userData: loginData,
      loginViewModel: new LoginViewModel()
    }

  },
  methods: {
    async login() {

      console.log("userdata",this.userData);
      await this.loginViewModel.login(this.userData.username, this.userData.password);
    }
  }
}
</script>

<template>
  <div class="login-box-main">
    <div class="login-top">

    </div>

    <div class="login-box">
        <div class="login-nom">

        </div>

        <div class="login-left">
          <div class="login-left-img">
            <img src="./image/OIP-D.png" height=200px width="300px" alt="">
          </div>
        </div>

       <div class="login-right">
          <div>
            <h2 style="font-size: 24px;">用户登录</h2>
          </div>

         <div class="login-input-item">
           <input type="username" placeholder="请输入用户名" v-model="userData.username">
         </div>

         <div class="login-input-item">
           <input type="password" placeholder="请输入密码" v-model="userData.password">
         </div>



         <div class="login-btn">
           <button class="submit-btn" @click="login" >登录</button>
           <button class="register-btn" @click="$router.push('/register')">注册</button>
         </div>

         <div class="login-forget">
           <a href="#">忘记密码?</a>
         </div>


       </div>

      <div class="login-nom">

      </div>


    </div>

    <div class="copyright">
      © 2024 公司名称 保留所有权利
    </div>
  </div>

</template>

<style >

 @import "login.css";
</style>
