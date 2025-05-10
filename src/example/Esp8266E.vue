<script>
import SockJS from 'sockjs-client/dist/sockjs.min.js'
import { Stomp } from '@stomp/stompjs'

export default {
  data() {
    return {
      temperature: '--',   // 当前温度
      humidity: '--',      // 当前湿度
      isConnected: false,  // WebSocket连接状态
      errorMessage: null   // 错误信息
    }
  },
  mounted() {
    this.connectWebSocket()
  },
  methods: {
    connectWebSocket() {
      const socket = new SockJS('http://localhost:8080/ws-sensor')
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect({},
          (frame) => {
            this.isConnected = true
            this.subscribeToData()
          },
          (error) => {
            console.error('连接失败:', error)
            this.errorMessage = '实时数据连接失败，请刷新页面重试'
            this.isConnected = false
          }
      )
    },
    subscribeToData() {
      this.stompClient.subscribe('/topic/sensorData', (message) => {
        const data = JSON.parse(message.body)
        this.temperature = data.temp.toFixed(1)
        this.humidity = data.hump.toFixed(1)
        console.info('<UNK>:', data)
      })
    },
    beforeDestroy() {
      if (this.stompClient) {
        this.stompClient.disconnect()
      }
    }
  }
}
</script>

<template>
  <div>
    <h1>Esp8266E</h1>
    <div>
      <div>
        <a>湿度{{this.humidity}}</a>
        <a>温度{{this.temperature}}</a>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>