<script setup>


</script>
<script>

import markdownData, {homeContent} from "@/model/home-data/markdownData.js";
import {marked} from "marked";
export default {
    data() {
        return {
            markdownData: markdownData,
            homepageData: homeContent

        }
    },
    methods: {
       renderMarkdown() {
           return marked.parse(this.markdownData);
       },
       renderHomepage() {
           return marked.parse(this.homepageData);
       }

    }
}
</script>
<template>
    <div class="home-main-container">
        <div class="home-content-box">

          <div class="home-content-box-top-bar">
            <div class="item-container-home">
              <h3 class="title">核心计划平台</h3>
            </div>

            <div class="item-container-personal">
              <h3 class="title">个人中心</h3>
            </div>
            <div class="item-container-document">
              <h3 class="title">文档</h3>
            </div>
            <div class="item-container-widget">
              <h3 class="title">控件</h3>
            </div>
          </div>

          <div class="home-content-box-left-bar">
             <div class="tile-container">
               <a class="tile-title">模块介绍</a>
             </div>
            <div class="tile-container">
              <a class="tile-title" href="/module">模块一览</a>
            </div>
            <div class="tile-container">
              <a class="tile-title">版本信息</a>
            </div>

          </div>

          <div class="home-content-box-center">
            <div class="tile-container">
<!--              <h1 class="tile-title">主页</h1>-->
<!--              <h3 class="tile-content">欢迎使用核心计划平台，这里是您核心计划的主页，您可以快速访问您需要的功能，包括模块介绍、快速导航、版本信息等。</h3>-->
<!--              <div class="tile-action">-->
<!--                <ul>-->
<!--                  <li>-->
<!--                    <a href="/module">模块介绍</a>-->
<!--                  </li>-->
<!--                  <li>-->
<!--                    <a href="/quick">快速开始</a>-->
<!--                  </li>-->
<!--                  <li>-->
<!--                    <a href="/version">版本信息</a>-->
<!--                  </li>-->
<!--                </ul>-->
<!--              </div>-->
              <div class="markdown-content" v-html="renderHomepage()"></div>
            </div>
            <div class="tile-container">
<!--              <h1 class="tile-news-title">最近更新</h1>-->
              <div class="markdown-content" v-html="renderMarkdown()"></div>
            </div>
          </div>

          <div class="home-content-box-right-bar">
            <div class="tile-container">
              <h1>最近访问</h1>

            </div>
          </div>







        </div>
    </div>
</template>

<style scoped>

@import "home.css";

</style>
