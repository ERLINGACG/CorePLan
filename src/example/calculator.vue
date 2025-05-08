<script >
import axios from 'axios'

export default {


  data(){

    return {
      DataModel: {
         LoanAmount: null, // 贷款金额
         AgeLimit: null, // 年限,
         InterestRate: null, // 利率
      },
      ResultModel: {
         LoanAmount: null, // 贷款金额
         AgeLimit: null, // 年限,
         InterestRate: null, // 利率
      }
    }
  },
  methods: {
     async getCalculator() {
        const response = await axios.get('http://localhost:8080/example/calculate2',
            {
               params: {
                  LoanAmount: this.DataModel.LoanAmount,
                  AgeLimit: this.DataModel.AgeLimit,
                  InterestRate: this.DataModel.InterestRate
               }
            }
        )
        this.ResultModel = response.data
        console.log(this.ResultModel)
        console.log(response.data)
     }
  },
}
</script>

<template>
  <div>
    <h1>简易房贷计算器（先息后本）</h1>
    <div>
      <a>贷款金额:</a><input type="text" v-model="DataModel.LoanAmount">
    </div>

    <div>
     <a>年限:</a> <input type="text" v-model="DataModel.AgeLimit">
    </div>

    <div>
      <a>利率:</a> <input type="text" v-model="DataModel.InterestRate">
    </div>

    <div>
      <button @click="getCalculator">Get Calculator</button>
    </div>

    <div>
      <h2>结果:</h2>
      <div v-for="(index, item) in ResultModel">
        <div>
          <a>{{item}}</a>
          <a>{{index}}</a>
        </div>
      </div>

    </div>

  </div>
</template>

<style scoped>

</style>