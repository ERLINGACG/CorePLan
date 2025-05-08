import { ElMessage } from 'element-plus'
import axios from "axios";
class RegisterViewModel {

    async register(username, password,confirmPassword,captchaCode) {
        if (confirmPassword!== password) {
            alert("两次输入的密码不一致！");
            return;
        }
        const captchaCodeData = new URLSearchParams();
        captchaCodeData.append('captcha', captchaCode);
        const codeResult = await axios.post("http://localhost:8080/user/verify-captcha", captchaCodeData);
        if (codeResult.data!==true) {
            alert("验证码错误！");

        }else{
            const formData = new URLSearchParams(); //创建一个URLSearchParams对象，用于存储表单数据
            formData.append('username', username);
            formData.append('password', password);
            const result = await axios.post("http://localhost:8080/user/register",
                formData)
           console.log(result.data);

            if (result.data.data) {
                alert(result.data.message);

            }else{
                alert(result.data.message);
            }
        }



    }
}

export default RegisterViewModel;

