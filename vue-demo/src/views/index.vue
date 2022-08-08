<template>
  <div>
    <!-- <el-select
      v-model="value1"
      clearable
      placeholder="请选择"
      multiple
      @remove-tag="removetag"
      @clear="handleClear"
      value-key="id"
      ref="selectUpid"
    >
      <el-option
        hidden
        v-for="(item, index_) in saveForm.upids"
        :key="index_"
        :value="item.id"
        :label="item.name"
      >
      </el-option>

      <el-tree
        :data="data"
        :props="defaultProps"
        ref="treedata"
        :expand-on-click-node="false"
        show-checkbox
        default-expand-all
        node-key="id"
        :check-on-click-node="true"
        @node-click="handleNodeClick"
        @check="handleNodeClick"
      >
        <span slot-scope="{ data }">
          <span>{{ data.name }}</span>
        </span>
      </el-tree>
    </el-select> -->
   <span style="color:blue">欢迎来到用户权限管理系统,你可以在这里修改自己的用户信息</span> <el-button @click="toupdate(user)">修改信息</el-button>
  <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
    <el-tab-pane label="角色查看" name="first">
 <div>
      <el-input
        placeholder="请输入角色名称"
        v-model="queryInfo.name"
        style="width: 200px"
        clearable
      >
      </el-input>
      <el-select
        v-model="queryInfo.state"
        style="width: 120px"
        placeholder="选择状态"
      >
        <el-option label="请选择状态" value=""></el-option>
        <el-option label="正常" value="0"></el-option>
        <el-option label="禁用" value="1"></el-option>
      </el-select>
      <el-button @click="findRole" type="primary" >查找</el-button>
    </div>
<el-table
      ref="multipleTable"
      :data="rolelist"
      tooltip-effect="dark"
      border
      height="390"
      size="small"
      
    >
    <el-table-column prop="rolename" label="角色名称" width="120">
      </el-table-column>
      <el-table-column prop="effectivedate" label="生效日期" width="150">
      </el-table-column>
      <el-table-column prop="expirydate" label="失效日期" width="150">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="80">
        <template slot-scope="scope">
        <span>{{scope.row.state=='0'?'有效':'无效'}}　</span>
        </template>
      </el-table-column>
    </el-table>
    </el-tab-pane>
    <el-tab-pane label="权限查看" name="second">
<div>
      <el-input
        placeholder="请输入菜单名称"
        v-model="queryInfo.name"
        style="width: 200px"
        clearable
      >
      </el-input>
      <el-select
        v-model="queryInfo.state"
        style="width: 120px"
        placeholder="选择状态"
      >
        <el-option label="请选择状态" value=""></el-option>
        <el-option label="正常" value="0"></el-option>
        <el-option label="禁用" value="1"></el-option>
      </el-select>
      <el-select
        v-model="queryInfo.menutype"
        style="width: 160px"
        placeholder="选择菜单类型"
      >
        <el-option label="请选择菜单类型" value=""></el-option>
        <el-option label="目录" value="0"></el-option>
        <el-option label="菜单" value="1"></el-option>
        <el-option label="功能" value="2"></el-option>
      </el-select>
      <el-button @click="findMenu" type="primary"  >查找</el-button>
    </div>
 <el-table
      ref="multipleTable"
      :data="menulist"
      height="390"
      tooltip-effect="dark"
      row-key="id"
      default-expand-all
      border
      size="small"
      :tree-props="{ children: 'childlist', hasChildren: 'hasChildren' }"
      
    >
      
      <el-table-column
        label="菜单名称"
        show-overflow-tooltip
        prop="name"
        width="200"
      >
      </el-table-column>
      <el-table-column prop="pname" label="上级菜单" width="70">
      </el-table-column>
      <el-table-column prop="menutype" label="菜单类型" width="70">
        <template slot-scope="scope">
          　　　　 <span v-if="scope.row.menutype == '0'">目录</span> 　　　　
          <span v-if="scope.row.menutype == '1'">菜单</span>
          <span v-if="scope.row.menutype == '2'">功能</span>
          　
        </template>
      </el-table-column>

      
      <el-table-column prop="orderno" label="排序号" width="70">
      </el-table-column>
       <el-table-column prop="effectivedate" label="生效日期" width="150">
      </el-table-column>
      <el-table-column prop="expirydate" label="失效日期" width="150">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="80">
        <template slot-scope="scope">
        <span>{{scope.row.state=='0'?'有效':'无效'}}　</span>
        </template>
      </el-table-column>
   
    </el-table>
    </el-tab-pane>
   
  </el-tabs>
    <el-dialog
      title="修改个人信息"
      :visible.sync="outerVisible"
      @opened="upopen"
      :fullscreen="true"
      :show-close="false"
    >
      <el-form :model="now_user" :rules="rules" ref="now_user">
        <el-form-item label="用户名" prop="username">
          <el-input
            style="width: 70%"
            v-model="now_user.username"
            :readonly="true"
            placeholder="用户名长度为6~20个英文字母或汉字组成，必填"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            style="width: 70%"
            v-model="now_user.password"
            placeholder="密码长度为6~20个数字或英文字母组成，必填"
          ></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="nickname">
          <el-input
            style="width: 70%"
            v-model="now_user.nickname"
            placeholder="长度为2~50个英文字母或汉字组成，必填。"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select
            style="width: 30%"
            v-model="now_user.gender"
            text-align="left"
            placeholder="请选择"
          >
            <el-option label="请选择" value=""></el-option>
            <el-option label="女" value="0"></el-option>
            <el-option label="男" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select
            style="width: 30%"
            v-model="now_user.state"
            text-align="left"
            placeholder="请选择"
          >
            <el-option label="有效" value="0"></el-option>
            <el-option label="无效" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input
            style="width: 70%"
            v-model="now_user.phone"
            placeholder="请输入电话"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
            style="width: 70%"
            v-model="now_user.email"
            placeholder="请输入邮箱"
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input
            style="width: 70%"
            v-model="now_user.note"
            placeholder="请输入备注"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="qx('now_user')">取消</el-button>
          
          <el-button  @click="update('now_user')"
            >修改保存</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import aes from "@/util/aes";
import { mapState } from "vuex";
export default {
  name: "index",
   computed: {
    ...mapState(["user"]),
  },
  data() {
    var checkUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名不能为空"));
      }
      if (!value.match("^[\u4e00-\u9fa5a-zA-Z]{6,20}$")) {
        return callback(new Error("用户名不符合规范"));
      }
      callback();
    };
    var checknickname = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名称不能为空"));
      }
      if (!value.match("^[\u4e00-\u9fa5a-zA-Z]{2,50}$")) {
        return callback(new Error("用户名称不符合规范"));
      }
      callback();
    };
    var checkpassword = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("密码不能为空"));
      }
      if (!value.match("^[A-Za-z0-9]{6,20}$")) {
        return callback(new Error("密码不符合规范"));
      }
      callback();
    };
    var checkemail = (rule, value, callback) => {
      if (
        value != "" &&
        !value.match("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$")
      ) {
        return callback(new Error("邮箱不符合规范"));
      }
      callback();
    };
    var checkphone = (rule, value, callback) => {
      if (value != "" && !(value.length == 11)) {
        return callback(new Error("电话不符合规范"));
      }
      callback();
    };
    return {
      rules: {
        password: [
          { required: true, validator: checkpassword, trigger: "blur" },
        ],
        username: [
          { required: true, validator: checkUserName, trigger: "blur" },
        ],
        nickname: [
          { required: true, validator: checknickname, trigger: "blur" },
        ],
        email: [{ validator: checkemail, trigger: "blur" }],
        phone: [{ validator: checkphone, trigger: "blur" }],
      },
      saveForm: {
        upids: [{ id: "", name: "" }],
      },
      upResName: "",
      value1: [],
      data: [
        {
          id: 1,
          name: "一级 1",
          childlist: [
            {
              id: 11,
              name: "二级 1-1",
              childlist: [
                {
                  id: 28,
                  name: "三级 1-1-1",
                },
              ],
            },
          ],
        },
        {
          id: 2,
          name: "一级 2",
          childlist: [
            {
              id: 22,
              name: "二级 2-1",
              childlist: [
                {
                  id: 221,
                  name: "三级 2-1-1",
                },
              ],
            },
            {
              id: 23,
              name: "二级 2-2",
              childlist: [
                {
                  id: 224,
                  name: "三级 2-2-1",
                },
              ],
            },
          ],
        },
      ],
      defaultProps: {
        children: "childlist",
        label: "name",
      },
      list: [1, 2, 22],
     outerVisible: false,
      userform: "",
      now_user: {
        username: "",
        nickname: "",
        password: "",
        gender: "",
        phone: "",
        email: "",
        state: "",
        note: "",
      },
      queryInfo:{
        name:'',
        state:'',
        menutype:'',
      },
      rolelist:[],
      menulist:[],
      activeName:'first'
    };
  },
  created() {
    this.findRole()
  },
  methods: {
    handleClick(tab, event){
      this.queryInfo={
        name:'',
        state:'',
        menutype:'',
      }
        if(tab.name=='first'){
          this.findRole();
        }else{
          this.findMenu()
        }
    },
    findMenu(){
      this.$axios.post('/api/user/checkmenus',this.queryInfo).then(resp=>{
        this.menulist=resp.data.data
      }).catch(err=>{console.log(err)})
    },
    findRole(){
      this.$axios.post('/api/user/checkroles',this.queryInfo).then(resp=>{
        this.rolelist=resp.data.data
      }).catch(err=>{console.log(err)})
    },
    update(formname) {
      this.$refs[formname].validate((valid) => {
        if (valid) {
          this.now_user.password = aes.encrypt(this.now_user.password);
          this.$axios
            .post("/api/user/updateuserself", this.now_user)
            .then((resp) => {
              this.$message.success(resp.data.message);
              this.outerVisible = !this.outerVisible;
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
            .catch((errot) => {
              console.log(errot);
            });
          //最终验证成功，需要在这里编写请求逻辑
        } else {
          return false;
        }
      });
    },
     toupdate(data) {
      console.log(data);
      this.$confirm("您的操作将对该用户进行修改, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.now_user = {
            id:data.id,
            username: data.username,
            nickname: data.nickname,
            password: "",
            gender: data.gender,
            phone: data.phone,
            email: data.email,
            state: data.state,
            note: data.note,
          };
         
         
          this.outerVisible = !this.outerVisible;
          
          
        })
        .catch(() => {
          this.$message.info("操作已取消!");
        });
    },
    qx(formname) {
      this.$confirm("您的操作将取消正在进行的操作, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$refs[formname].resetFields();
          this.outerVisible = !this.outerVisible;
        })
        .catch((err) => {
          console.log(err);
          this.$message.info("操作已取消!");
        });
    },
     upopen(){
      this.$refs["now_user"].clearValidate;
    },
    handleNodeClick(data) {
      // 这里主要配置树形组件点击节点后，设置选择器的值；自己配置的数据，仅供参考
      this.saveForm.upids = this.$refs.treedata.getCheckedNodes(false, true);
      console.log(this.saveForm.upids);
      this.value1 = this.$refs.treedata
        .getCheckedKeys()
        .concat(this.$refs.treedata.getHalfCheckedKeys());
      // 选择器执行完成后，使其失去焦点隐藏下拉框的效果
      if (this.saveForm.upids.length == 0) {
        this.saveForm.upids = [{ id: "", name: "" }];
      }
      console.log(this.value1);
    },
    // 选择器配置可以清空选项，用户点击清空按钮时触发
    handleClear() {
      // 将选择器的值置空

      this.saveForm.upids = [{ id: "", name: "" }];
      this.value1 = [];
      this.$refs.treedata.setCheckedKeys([]);
    },
    removetag(val) {
      console.log(val);
      this.$refs.treedata.setChecked(val, false, true);
      this.handleNodeClick(null);
    },
  },
};
</script>

<style scoped>
</style>
