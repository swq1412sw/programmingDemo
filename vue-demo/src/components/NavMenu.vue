<template>
  <div class="ddd">
    <!-- 根据实际情况做修改 -->
    <div v-for="(item, index) in navlist" :key="index + item.name + 'b'">
      <el-menu-item v-if="item.menutype == '1'" @click="toplace(item.path)">
      <e-icon v-if="item.ico" :icon-name="item.ico"></e-icon>
        <span>{{ item.name }}</span>
      </el-menu-item>

      <el-submenu v-if="item.menutype == '0'" :index="item.id + 'a'">
        <template slot="title">
            <e-icon v-if="item.ico" :icon-name="item.ico"></e-icon>
          <span>{{ item.name }}</span>
        </template>
        <NavMenu :navlist="item.childlist"></NavMenu>
      </el-submenu>
    </div>
  </div>
</template>
 
<script>
export default {
  name: "NavMenu",
  props: ["navlist"],
  data() {
    return {};
  },
  methods: {
    toplace(data) {
      console.log(data);

      var database = { name: data.replace("/", "_"), path: data };
      this.$router.push(
        database,
        () => {},
        (err) => {}
      );
    },
  },
};
</script>
<style  >
.ddd{
  background-color: black;
  
}
</style>
