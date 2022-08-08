import Vue from 'vue'
import Router from 'vue-router'
import login from '../views/login.vue'
import home from '../views/home.vue'
import sieaxios from '../util/axios.js'
import store from '@/store'
import notfind from "@/views/notfound.vue"
Vue.use(Router)
// const VueRouterPush = Router.prototype.push
// Router.prototype.push = function push (to) {
//   return VueRouterPush.call(this, to).catch(err => err)
// }
const routes = [

  {
    path: '/login',
    name: 'login',
    component: login
  }, {
    path: '/home',
    name: 'home',
    component: home,

  // },{
  //   path: '/index',
  //   name: '_index',
  //   component: home,
  },
  {
    path: '/',
    name: 'login',
    component: login
  }
]
const router = new Router({
  mode: "hash",
  routes,
});

function loadView(view) {
  return resolve => require([`@/views${view}` + '.vue'], resolve)
}
let pathlist=['/','/login','/home','/notfind']
function loadlist(list) {
  // console.log(list)
  for (let index = 0; index < list.length; index++) {
    const element = list[index];
    if (element.menutype == '1') {
      var name = element.path.replace('/', '_')
      router.addRoute('home', { name: name, path: element.path, component: loadView(element.path) })
      pathlist.push(element.path)
    }
    if (element.childlist.length != 0) {
      loadlist(element.childlist)
    }
  }
}

let flage = true
function hasrouter(path){
for (let index = 0; index < pathlist.length; index++) {
  const element = pathlist[index];
  if(element===path){
    return true
  }
}
return false
}

function proissame(val,val1) {
  for (let index = 0; index <val.length; index++) {
    const element = val[index];
    const element1=val1[index]
   if(element!=element1){
    return false
   }
    
   }
   return true
}
router.beforeEach((to, from, next) => {
  console.log(to, from)
  //判断用户是否登录，登录成功了才能使用路由
  const token = sessionStorage.getItem('token');
  // console.log(token)
  //如果没有登录
  if (!token) {
   
    //如果你接下来要去的页面是登录页面，直接next放行
    if (to.path === '/login') {
      next();
    } else {
      //如果不是 我手动让你去login登录页面
      next('/login');
    }
  } else {
    //发送一个获取用户信息的请求
  
    // if(store.state.roles || store.state.name){
    //向后端发送请求，获取用户信息
  console.log("router",router)
    sieaxios.post("/api/log/info").then(resp => {
      const user = resp.data.data.user;
      const token_ = resp.data.data.token;
      const tokenhead = resp.data.data.tokenhead
console.log(resp);
      store.commit('setUser', user);
      store.commit("setToken", tokenhead + token_)
      store.commit('setMenuList', resp.data.data.list);
      if (flage) {
   
        var routerlist = resp.data.data.list
        loadlist(routerlist)
        router.addRoute({name:'_notfind',path:'/notfind',component:notfind})
        flage = !flage
        
        
        store.commit('setUserPro',user.authors)
        store.commit('setMenuList', resp.data.data.list);
        next({name:to.path.replace('/','_'),path:to.path})
        return false
      }
      console.log(store.state.userPro,resp.data.data.user.authors);
     console.log(store.state.userPro==resp.data.data.user.authors);
     if(store.state.userPro.length!=resp.data.data.user.authors.length){
      
      store.commit('setUserPro',resp.data.data.user.authors)
     }
    if(!proissame(store.state.userPro,resp.data.data.user.authors)){
     
      store.commit('setUserPro',resp.data.data.user.authors)
    }
      if (!flage) {
        if (to.path === '/login'||to.path==='/') {
          next({ name: "_index", path: "/index" })
        } else if(to.name===null){
         
          if(to.path==='/index'){
           
            next()
            return false
          }
         
         console.log(to)
          next({name:to.path.replace('/','_'),path:to.path})
       
        
        }else{
          if(hasrouter(to.path)){
            next()
          }else{
            console.log(to)
            next({name:'_notfind',path:'/notfind'})
            return false
          }
         
        }
        return false
      }
    }).catch(err => {
      console.log(err)
    })
    // }
    //已经登录,直接放行

    // setTimeout(function (){
    //  console.log(12)
  }
  //     , 100);
  // }
})

export default router
