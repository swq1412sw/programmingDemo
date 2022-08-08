<template>
  <el-container class="home-css">
    <el-aside :width="menuflag?'50px':'200px'" style=" overflow-y:auto;overflow-x:hidden;">
      <el-menu
        style="text-align: left;"
        background-color="#010217"
        :collapse-transition="false"
        :collapse="menuflag"
        :unique-opened="false"
        text-color="#fefefe"
        active-text-color="#fefefe"
      >
        <NavMenu :navlist="menuList"></NavMenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <i v-if="!menuflag" @click="menuflag=!menuflag" class="el-icon-s-fold"></i>
        <i v-if="menuflag" @click="menuflag=!menuflag" class="el-icon-s-unfold"></i>
        {{ user.nickname }}<el-button style="float:right;margin-top: 10px;" type="danger" @click="logout">退出</el-button>
      </el-header>
      <el-main style="overflow-y: auto; overflow-x: auto">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import NavMenu from "../components/NavMenu.vue";
import { mapState } from "vuex";
export default {
  name: "home",
  computed: {
    ...mapState(["user", "menuList","userPro"]),
  },
  components: {
    NavMenu,
  },
  data() {
    return {
      menuflag: false,
    };
  },
watch:{
  userPro(oldvalue,newvalue){
    location.reload();
  },
  menuList(oldvalue,newvalue){
    console.log(oldvalue==newvalue)
     console.log(oldvalue,newvalue)
  }
},
  created() {},
  methods: {
    toplace(date) {
      this.$router.push(date);
    },
    logout() {
      this.$confirm("您的操作将退出此系统, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios.get("/api/log/logout").then((res) => {
            this.$message.success(res.data.message);
            //清空本地缓存
            sessionStorage.clear();
            //跳转页面
            this.$router.push({ path: "/login" });
            //刷新vuex中的数据
            window.location.reload();
          });
        })
        .catch(() => {
          this.$message.info("操作已取消!");
        });
    },
  },
};
</script>

<style >
.el-header {
  background-color: #192536;
  color: rgb(51, 207, 235);

  line-height: 60px;
  height: 100%;
}

.el-aside {
  background-color: #010217;
  

  line-height: 100vh;
}

.el-main {
  background-color: #F2F6FC;
  color: rgb(238, 229, 229);
  
}
.home-css {
  height: 100%;
  width: 100%;
}
</style>