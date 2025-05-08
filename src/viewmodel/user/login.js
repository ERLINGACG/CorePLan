import { ElMessage } from 'element-plus'
import axios from "axios";
import router from "@/router/router.js";
class LoginViewModel {

    async login(username, password) {
        if(!username ||!password){
            alert("请输入用户名和密码");
            return;
        }
        const formData = new URLSearchParams(); //创建一个URLSearchParams对象，用于存储表单数据
        formData.append('username', username);
        formData.append('password', password);
        const result = await axios.post("http://localhost:8080/user/login",
            formData)
        if (result.data.data) {
            alert(result.data.message);
            await router.push("/Home");
        }else{
            alert(result.data.message);
        }

    }
}

export default LoginViewModel;

