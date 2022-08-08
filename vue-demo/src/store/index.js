import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
state:{
    token: sessionStorage.getItem('token') ,
    user: JSON.parse(sessionStorage.getItem('user')) ,
    menuList:JSON.parse(sessionStorage.getItem("menuList")),
    userPro:JSON.parse(sessionStorage.getItem("userPro")),
},
mutations: {
    setToken(state,data){
         state.token = data;
         sessionStorage.setItem('token',data);
        
    },

    /**
     * 向Vuex中存入角色信息
     * @param {*} state 
     * @param {*} data 
     */
     setUser(state,data){
        state.user = data;
         sessionStorage.setItem('user',JSON.stringify(data)); 
    },
    setMenuList(state,data){
        state.menuList=data;
        sessionStorage.setItem('menuList',JSON.stringify(data))
    },
    setUserPro(state,data){
        state.userPro=data
        sessionStorage.setItem('userPro',JSON.stringify(data))
    }
},
  
   
})
export default store
