<script>

import SurfViewModel from "@/viewmodel/opencv/image/surf/SurfViewModel.js";
import SurfData from "@/model/opencv-data/image/surfData/SurfData.js";
import uploadImage from "@/model/opencv-data/image/upLoadImage.js";

export default {
  data(){
    return {
      imageUrl: SurfData,
      processedImageUrl:  uploadImage,
      rawFile: null,
      viewModel: new SurfViewModel()
    }
  },
  methods:{
    previewImage(e){
      const file = e.target.files[0];
      if (!file || !file.type.includes('image/')) return;

      const reader = new FileReader();
      reader.onload = (e) => {
        this.imageUrl = e.target.result;
      };
      this.rawFile = file; // 保存原始文件引用
      reader.readAsDataURL(file);
    },

    async getProcessedImage(){
      if(this.imageUrl!=null){
        this.processedImageUrl =  await this.viewModel.getViewModelData(this.rawFile);
      }

    },
  }

}

</script>

<template>
  <div class="surf-container">
      <div class="title">
          <h2>SURF-特征点检测</h2>
      </div>
      <div class="content-upload">
        <div class="upload-title">
          <h3>上传图片</h3>
        </div>
        <div class="upload-input-btn">
          <button class="upload-btn" @click="$refs.fileInput.click()">打开图片</button>
          <button class="upload-btn" @click="imageUrl = null">清空图片</button>
          <button class="upload-btn" @click="getProcessedImage()">上传图片</button>
        </div>
        <div class="upload-container">
          <input
              type="file"
              ref="fileInput"
              @change="previewImage"
              accept="image/*"
              style="display: none;"
          >
          <img v-if="imageUrl" class="upload-img" :src="imageUrl" alt="上传图片" />
        </div>
        <div class="upload-title">
          <h3>结果展示</h3>
        </div>
        <div class="upload-input-btn">
          <button class="upload-btn">保存图片</button>
          <button class="upload-btn" @click="processedImageUrl = null">清空图片</button>
        </div>
        <div class="upload-container">
          <img v-if="this.processedImageUrl" class="upload-img" :src="this.processedImageUrl" alt="上传图片" />
        </div>
      </div>
  </div>
</template>

<style>
@import "surf.css";
</style>