<script>
export default {
  data() {
    return {
      receivedChunks: [], // 存储所有接收到的数据块
      ws: null,
      isDownloadReady: false,
    };
  },
  mounted() {
    this.connectWebSocket();
  },
  beforeUnmount() {
    if (this.ws) this.ws.close();
  },
  methods: {
    connectWebSocket() {
      this.ws = new WebSocket("ws://localhost:8080/video-stream");
      this.ws.binaryType = "arraybuffer"; // 接收二进制数据

      this.ws.onopen = () => {
        const startCommand = new TextEncoder().encode("start");
        this.ws.send(startCommand);
        console.log("WebSocket连接已建立，开始接收数据...");
      };

      this.ws.onmessage = (event) => {
        const chunk = new Uint8Array(event.data);
        this.receivedChunks.push(chunk); // 累积数据块
        console.log(`已接收数据块：${chunk.length}字节`);
      };

      this.ws.onclose = (event) => {
        if (event.code === 1000) {
          console.log("传输完成，准备生成MP4文件");
          this.isDownloadReady = true;
        } else {
          console.error("连接异常关闭:", event.reason);
        }
      };

      this.ws.onerror = (error) => {
        console.error("WebSocket错误:", error);
      };
    },
    downloadFullVideo() {
      // 合并所有数据块
      const fullData = new Uint8Array(
          this.receivedChunks.reduce((acc, chunk) => acc + chunk.length, 0)
      );
      let offset = 0;
      this.receivedChunks.forEach((chunk) => {
        fullData.set(chunk, offset);
        offset += chunk.length;
      });

      // 生成Blob并下载
      const blob = new Blob([fullData], { type: "video/mp4" });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "完整视频.mp4";
      a.click();
      URL.revokeObjectURL(url);
      console.log("文件已下载");
    },
  },
};
</script>

<template>
  <div>
    <h1>视频下载</h1>
    <button
        @click="downloadFullVideo"
        :disabled="!isDownloadReady"
        style="padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px;"
    >
      {{ isDownloadReady ? "下载完整视频" : "接收中..." }}
    </button>
  </div>
</template>