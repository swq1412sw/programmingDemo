<template>
  <div class="hello">
   页面不存在或是你没有对应的权限
   <el-button @click="to">返回首页</el-button>
  </div>
</template>

<script>
export default {
  name: "notfind",
  data(){
    return{

    }
  },
  methods:{
    to(){
        this.$router.push('/index')
    }
  }
  }
  ;
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
