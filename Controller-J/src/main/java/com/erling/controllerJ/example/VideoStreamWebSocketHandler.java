package com.erling.controllerJ.example;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class VideoStreamWebSocketHandler extends BinaryWebSocketHandler {
    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB chunks
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // 接收前端控制指令
        String command = new String(message.getPayload().array());
        if ("start".equals(command)) {
            System.out.println("发送视频流开始指令");
            streamVideo(session, "E:/CorePLAN/Web/WebCore/C/VideoExample.mp4"); // 修改为实际视频路径
        }
    }
    private void streamVideo(WebSocketSession session, String filePath) {
        new Thread(() -> { // 新增异步线程
            try (FileInputStream fis = new FileInputStream(filePath)) {
                byte[] buffer = new byte[CHUNK_SIZE];
                int bytesRead;

                System.out.println("开始传输视频流"); // 新增日志

                while ((bytesRead = fis.read(buffer)) != -1) {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);
                    if (session.isOpen()) { // 新增连接状态检查
                        session.sendMessage(new BinaryMessage(byteBuffer));
                    } else {
                        System.out.println("连接已关闭，停止发送");
                        break;
                    }
                    Thread.sleep(30);
                }
                System.out.println("视频传输完成"); // 新增完成日志
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    session.close(); // 确保关闭连接
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start(); // 启动线程
    }
}
