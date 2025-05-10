import axios from "axios"; // 修正导入方式

class SurfViewModel {

    async getViewModelData(fileInput) { // 参数改为File对象而非字节数组
        const file = fileInput
        if (!file) {
            alert('请先选择图片');
            return;
        }
        try {
            const formData = new FormData();
            formData.append('file', file);

            const response = await axios.post('http://localhost:8080/module/opencv/basic/image/Canny', formData, {
                headers: {

                },
                responseType: 'arraybuffer'
            });
            const contentType = response.headers['content-type'];
            if (!contentType?.startsWith('image/')) {
                Error('响应数据不是图片');
            }

            const uint8Array = new Uint8Array(response.data);
            const blob = new Blob([uint8Array], { type: 'image/jpeg' });
            return URL.createObjectURL(blob);
        } catch (error) {
            console.error('上传失败:', error);
            alert('图片处理失败，请检查控制台');
        }
    }

}

export default SurfViewModel;