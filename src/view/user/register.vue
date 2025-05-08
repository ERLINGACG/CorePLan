<script>

import registerData from "@/model/user-data/registerData.js"
import RegisterViewModel from "@/viewmodel/user/register.js";

export default {
  data(){
    return {
      userData: registerData,
      registerViewModel: new RegisterViewModel(),
      captchaUrl: "http://localhost:8080/user/get-captcha"
    }

  },
  methods: {
    async register() {

      console.log("userdata", this.userData);
      await this.registerViewModel.register(
          this.userData.username,
          this.userData.password,
          this.userData.confirmPassword,
          this.userData.captchaCode);

    },
    async refreshCaptcha() {
      this.captchaUrl = `http://localhost:8080/user/get-captcha?t=${Date.now()}`
    }
  }
}
</script>



<template>
  <div class="register-box-main">
    <div class="register-top">

    </div>

    <div class="register-box">
      <div class="register-nom">

      </div>

      <div class="register-left">
        <div class="register-left-img">
          <img src="./image/OIP-D.png" height=200px width="300px" alt="">
        </div>
      </div>

      <div class="register-right">
        <div>
          <h2 style="font-size: 24px;">用户注册</h2>
        </div>

        <div class="register-input-item">
          <input type="username" placeholder="请输入用户名" v-model="userData.username">
        </div>

        <div class="register-input-item">
          <input type="password" class="input1" placeholder="请输入密码" v-model="userData.password">
        </div>

        <div class="register-input-item">
          <input type="password" class="input2" placeholder="请再次输入密码" v-model="userData.confirmPassword">
        </div>

        <div class="register-input-item-code">
          <input type="password" class="code-input" placeholder="请输入验证码" v-model="userData.captchaCode">
          <img
              :src="captchaUrl"
              alt="验证码"
              class="code-img"
              @click="refreshCaptcha()">
        </div>

        <div class="register-btn">
          <button class="r-submit-btn" @click="$router.push('/login')">返回登录？</button>
          <button class="r-register-btn" @click="register()">注册</button>
        </div>


      </div>

      <div class="register-nom">

      </div>


    </div>

    <div class="copyright">
      © 2024 公司名称 保留所有权利
      <div>
        <a href="#">隐私政策</a>
        <a href="#">服务条款</a>
      </div>
      <div>
         <a>注册即表示同意服务条款</a>
      </div>
    </div>

  </div>

</template>

<style >

@import "register.css";
</style>
