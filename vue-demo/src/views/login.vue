<template>
  <div style="width: 100%; heingh: 100%" class="log">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm formclass"
    >
      <div>
        <el-card id="sw">
          <el-form-item label="用户名" prop="username">
            <el-input style="width: 70%" v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              style="width: 70%"
              type="password"
              v-model="ruleForm.password"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="确认密码" prop="checkPass">
    <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
  </el-form-item> -->

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')"
              >登录</el-button
            >
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-card>
      </div>
    </el-form>
  </div>
</template>

<script>
import aes from "@/util/aes";

export default {
  name: "login",
  data() {
    var checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名不能为空"));
      }
      callback();
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    return {
      ruleForm: {
        password: "",
        username: "",
      },
      rules: {
        password: [{ validator: validatePass, trigger: "blur" }],
        username: [{ validator: checkName, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.password = aes.encrypt(this.ruleForm.password);
          this.$axios
            .post("/api/log/login", this.ruleForm)
            .then((resp) => {
              const tokenBody = resp.data.data;
              let tokenHead = tokenBody.tokenHead;
              let token = tokenBody.token;
              this.$store.commit("setToken", tokenHead + token);
              this.$nextTick(() => {
                this.$router.push(
                  { name: "_index", path: "/index" },
                  () => {},
                  () => {}
                );
              });
            })
            .catch((errot) => {
              console.log(errot);
            });
          //最终验证成功，需要在这里编写请求逻辑
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  created() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
#sw {
  background-color: #ebeef5;
  width: 42%;

  margin: 10% 30% auto;
}
</style>
