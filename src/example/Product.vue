<script xmlns="http://www.w3.org/1999/html">
import axios from "axios";

export default {
  data(){
    return {
      searchString: '',
      product: null,
      putProduct: {
        id: null,
        name: null,
        price: null,
        category: null,
        description: null,
      }
    }
  },
  mounted(){
    this.getProduct()
  },
  methods: {
    clearInput(){
      this.putProduct.id = null
      this.putProduct.name = null
      this.putProduct.price = null
      this.putProduct.category = null
      this.putProduct.description = null
    },
    async getProduct() {
      try{
        const response = await axios.get(`http://localhost:8080/example/api/products/getProducts`)
        this.product = response.data
      }catch (error){
        console.log(error)
        alert()
      }
    },
    async PutProduct(item) {
      try{

        const url1=`http://localhost:8080/example/api/products/updateProduct`
        const url2='http://localhost:8080/example/api/products/T_updateProduct'
        const response = await axios.put(url2, item )
        console.log(item)

      }catch (error){
        console.log(error)
        alert()
      }
    },
    async deleProduct(id) {
      try{
        const url="http://localhost:8080/example/api/products/deleteProduct"
        const response = await axios.delete(url,{
          params: {
            id: id
          }
        })
      }catch (error){
        console.log(error)
      }
    },
    async addProduct() {
      try{
        const url="http://localhost:8080/example/api/products/createProduct"
        const response = await axios.post(url, this.putProduct)
      }catch (error){
        console.log(error)
      }
    },
    async SearchProduct() {
      try{
        const url="http://localhost:8080/example/api/products/getProductsByCategory"
        const response = await axios.get(url,{
          params: {
            category: this.searchString
          }
        })
        console.log(response.data)
        console.log(this.searchString)

        console.log(response.data.message)
        this.product = response.data.data

      }catch (error){
        console.log(error)
      }
    }
  }
}
</script>

<template>
 <div class="product">
   <h1>Product</h1>
   <p></p>
   <button @click="getProduct">点击获取产品</button>
   <input type="text" v-model=searchString />
   <button @click="SearchProduct()" >SEA</button>

   <div class="product-item">
      <p class="id">产品ID</p>
      <p class="name">产品名称</p>
      <p class="price">产品价格</p>
      <p class="category">产品分类</p>
      <p class="description">产品描述</p>
    </div>
   <div v-for="item in product" :key="item.id" class="product-item">
      <input class="id" v-model="item.id"  />
      <input class="name" v-model="item.name" />
      <input class="price" v-model="item.price"/>
      <input class="category" v-model="item.category"/>
      <input class="description" v-model="item.description" />
      <button @click="deleProduct(item.id)">DEL</button>
      <button @click="PutProduct(item)">UPD</button>
   </div>
   <div class="product-item">
     <input class="id" v-model="putProduct.id"  />
     <input class="name" v-model="putProduct.name" />
     <input class="price" v-model="putProduct.price"/>
     <input class="category" v-model="putProduct.category"/>
     <input class="description" v-model="putProduct.description" />
     <button @click="addProduct()">ADD</button>
     <button @click="clearInput()">CLE</button>
   </div>


 </div>
</template>

<style>
.product-item{
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  margin-left: 10px;
  border-bottom: 1px solid #ccc ;
  input{
    margin: 10px;
    background-color: transparent;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border: none;

    font-size: 20px;
    color: honeydew;
  }
  .id{
     width: 100px;
     margin-left: 10px;
  }
  .name{
    width: 100px;
    margin-left: 10px;
  }
  .price{
    width: 100px;
    margin-left: 10px;
  }
  .category{
    width: 100px;
    margin-left: 10px;
  }
  .description{
    width: 100px;
    margin-left: 10px;
  }
}
</style>