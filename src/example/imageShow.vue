<script>
import axios from "axios";

export default {
  data(){
    return {
      imageUrl: null,
      processedImageUrl: null
    }
  },
  methods: {
    previewImage(e){
      const file = e.target.files[0];
      if (!file || !file.type.includes('image/')) return;

      const reader = new FileReader();
      reader.onload = (e) => {
        this.imageUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    async getImage(){
      const fileInput = this.$refs.fileInput;
      const file = fileInput.files[0];
      if (!file) {
        alert('请先选择图片');
        return;
      }
      try {
        const formData = new FormData();
        formData.append('file', file);

        const response = await axios.post('http://localhost:8080/module/opencv/basic/image/Canny', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          },
          responseType: 'arraybuffer'
        });
        const contentType = response.headers['content-type'];
        if (!contentType?.startsWith('image/')) {
          throw new Error('返回数据不是图片类型');
        }

        const uint8Array = new Uint8Array(response.data);
        const blob = new Blob([uint8Array], { type: 'image/jpeg' });
        this.processedImageUrl = URL.createObjectURL(blob);
      } catch (error) {
        console.error('上传失败:', error);
        alert('图片处理失败，请检查控制台');
      }
    },
    downloadImage(){
      if (!this.processedImageUrl) {
        alert('请先处理图片');
        return;
      }
      const link = document.createElement('a');
      link.href = this.processedImageUrl;
      link.download = 'processed_image.jpg';  // 设置下载文件名
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }
}
</script>

<template>
 <div class="imageShow">
  <h1>示例图片</h1>
   <button @click="$refs.fileInput.click()">选择图片</button>
   <button @click="imageUrl = null">清空图片</button>
   <button @click="getImage()">上传并显示图片</button>
   <div class="imageShow-container">

     <input
         type="file"
         ref="fileInput"
         @change="previewImage"
         accept="image/*"
         style="display: none;"
     >

     <img v-if="imageUrl" :src="imageUrl" alt="预览图片" class="preview-image">
   </div>
   <h3>处理结果</h3>
   <button @click="processedImageUrl = null">清空处理结果</button>
   <button @click="downloadImage()">下载图片</button>
   <div v-if="processedImageUrl" class="imageShow-container">

     <img :src="processedImageUrl" alt="处理后的图片" class="preview-image">
   </div>
 </div>
</template>

<style>
@import "css/imageShow.css";
</style>