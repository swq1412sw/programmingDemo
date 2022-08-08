import Vue from "vue";
import axios from "axios";
import { Message } from "element-ui";
import router from "../router"
//创建一个axios对象全局注册到Vue对象中管理使用
const sieaxios= axios.create({
     //baseURL:"http://localhost:8887",
    timeout: 10000
});



// axios.defaults.baseURL = 'http://localhost:8080';

//处理所有请求的拦截器
sieaxios.interceptors.request.use((config) =>{
    
    //需要在发送请求前携带token令牌
    const token = sessionStorage.getItem('token');  
    if(token){
        //给所有的请求头，绑定一个token令牌，访问后端
        config.headers['Authorization'] = token;
    }
   
    return config;
},(err) => {
  
})



//拦截器
sieaxios.interceptors.response.use((resp)=>{
    
    if(!resp.data.flag){
Message.error(resp.data.message)
    }else{return resp;}
   
},(err)=>{
    
    if(err.response.status===400){
        Message.error(err.response.data.message);
        
    }else if(err.response.status===401){
        Message.error("你还没有登录呢或账号已过期,请重新登录")
       
                    sessionStorage.clear();
                    //跳转页面
                    
                   router.replace({ path: '/login' });
                    //刷新vuex中的数据
                    window.location.reload();
    }else if(err.response.status===403){
        Message.error("没有相对应的权限")
    }else if(err.response.status===404){
        Message.error("后端找不到对应的接口")
    }else if(err.response.status===500){
        Message.error(err.response.data.message)
    }else{
        Message.error("未知错误")
    }
})
Vue.prototype.$axios=sieaxios;
export default sieaxios;
