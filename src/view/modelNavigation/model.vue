<script setup>

import router from "@/router/router.js";
</script>

<template>
  <div class="model-navigation">

      <div class="model-navigation-header">
          <h2>Model Navigation</h2>
      </div>
      <div class="model-navigation-body">
          <div class="model-navigation-body-item">
              <div class="opencv-model">
                <h3 @click="router.push('/model/opencv')">OpencvPage Model</h3>
              </div>
              <div class="AI-model">
                <h3>AI Model</h3>
              </div>
              <div class="user-model">
                <h3 >User Model</h3>
              </div>
              <div class="fileUpload-model">
                <h3>FileUpload Model</h3>
              </div>
          </div>
      </div>
  </div>
</template>

<style scoped>
@import "model.css";
</style>