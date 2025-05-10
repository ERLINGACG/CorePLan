<template>
  <div class="opencv-container"  ref="opencvContainer">
     <div class="opencv-content-top-box" ref="contentTopBox">

         <div class="top-title-box">
           <h3>OpenCV模块</h3>
         </div>
         <div class="top-content-box">
           <div class="doc-box">
             <a>文档</a>
           </div>
           <div class="return-box">
             <a @click="$router.push('/Home' )">返回首页</a>
           </div>
           <div class="user-box">
             <a>个人中心</a>
           </div>
         </div>
     </div>

    <div class="opencv-content-left-box" ref="contentLeftBox">
       <div class="left-item-content-box">
          <div class="left-item-box">
             <div class="left-item-title-box">
                <a href="/model/opencv/home">模块首页</a>
             </div>

             <div class="left-item-title-box"
                  @mouseover="isFormVisible = true" >
                <a>图像处理</a>
             </div>
              <div class="form-box" v-show="isFormVisible">
                 <router-link :to="{ name: 'Surf'}" >图像处理</router-link>
              </div>
              <div class="form-box" v-show="isFormVisible">
                 <a>图像显示</a>
              </div>

            <div class="left-item-title-box">
               <a>视频处理</a>
            </div>

          </div>
       </div>
    </div>

    <div class="opencv-content-center-box" ref="contentCenterBox">
       <router-view></router-view>
    </div>

  </div>
</template>
<style >
@import "opencv.css";
</style>

<script>
import OpenCVStyle from "@/view/opencv/OpenCVStyle.js";
import router from "@/router/router.js";
export default {
  data() {
    return {
      isFormVisible: false
    }
  },
   mounted() {
      OpenCVStyle.SetLeftBarStyle(this.$refs.contentLeftBox)
     this.$refs.contentLeftBox.addEventListener('mouseleave', () => {
       this.isFormVisible = false;
     });
     router.push("/model/opencv/home")
   }
}

</script>

