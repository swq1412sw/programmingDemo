<template>
  <div class="sss">
    <div>
      <el-input
        placeholder="请输入角色名称"
        v-model="queryInfo.rolename"
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
      <el-button @click="findPage2" type="primary" :disabled="!pre.select">查找</el-button>
    </div>

    <div style="margin-top: 10px; margin-bottom: 5px">
      <el-button size="small" :disabled="!pre.insert" @click="toadd" type="primary" plain
        >添加</el-button
      >
      <el-button size="small" type="danger"  :disabled="!pre.delete" @click="detelist"
        >批量删除</el-button
      >
    </div>

    <el-table
      ref="multipleTable"
      :data="rolelist"
      tooltip-effect="dark"
      border
      height="390"
      size="small"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column label="角色编码" width="120" prop="rolecode">
      </el-table-column>
      <el-table-column prop="rolename" label="角色名称" width="120">
      </el-table-column>
      <el-table-column prop="effectivedate" label="生效日期" width="150">
      </el-table-column>
      <el-table-column prop="expirydate" label="失效日期" width="150">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="70">
        <template slot-scope="scope">
          　　　　
          <el-switch
            v-model="scope.row.state"
            active-value="0"
            inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="stateChange(scope.row)"
            :disabled="!pre.update"
          ></el-switch>
          　
        </template>
      </el-table-column>
      <!-- <el-table-column
      prop="creatdate"
      label="创建时间"
      width="120"
      >
    </el-table-column>
      <el-table-column
      prop="updatetime"
      label="修改时间"
      width="120"
      > -->
      <!-- </el-table-column> -->
      <el-table-column fixed="right" label="操作" width="340">
        <template slot-scope="scope">
          <el-button
            size="mini"
            :disabled="!pre.update"
            type="text"
            @click="toupdate(scope.row)"
            round
            >修改</el-button
          >
          <el-button
            size="mini"
            :disabled="!pre.delete"
            type="text"
            @click="dele(scope.row)"
            round
            >删除</el-button
          >
          <!-- <el-button size="mini" type="text" v-if="scope.row.state=='0'" @click="stateoff(scope.row)" round>无效</el-button>
        <el-button size="mini" type="text" v-if="scope.row.state=='1'" @click="stateon(scope.row)" round>有效</el-button> -->
          <el-button
            size="mini"
            :disabled="!pre.menuupdate"
            type="text"
            @click="szcd(scope.row)"
            round
            >设置角色权限</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="querydata.page"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="querydata.pagetotal"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      :disabled="!pre.select"
    ></el-pagination>

    <el-dialog
      :title="roleform"
      :visible.sync="outerVisible"
      @opened="upopen"
      :fullscreen="true"
      :show-close="false"
    >
      <el-form :model="now_role" :rules="rules" ref="now_role">
        <el-form-item label="角色编码" prop="rolecode">
          <el-input
            style="width: 70%"
            v-model="now_role.rolecode"
            placeholder="角色名长度为6~20个英文字母或汉字组成，必填"
          ></el-input>
        </el-form-item>
        <el-form-item label="角色名" prop="rolename">
          <el-input
            style="width: 70%"
            v-model="now_role.rolename"
            placeholder="长度为2~50个英文字母或汉字组成，必填。"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item label="状态" prop="state">
          <el-select
            style="width: 30%"
            v-model="now_role.state"
            text-align="left"
            placeholder="请选择"
          >
            <el-option label="有效" value="0"></el-option>
            <el-option label="无效" value="1"></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="生效日期" prop="effectivedate">
          <el-date-picker
            style="width: 40%"
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="now_role.effectivedate"
            type="datetime"
            placeholder="生效日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="失效日期" prop="expirydate">
          <el-date-picker
            style="width: 40%"
            v-model="now_role.expirydate"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="失效日期"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button @click="qx('now_role')">取消</el-button>
          <el-button v-if="flag" @click="add('now_role')">添加</el-button>
          <el-button v-if="!flag" @click="update('now_role')"
            >修改保存</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog
      title="设置菜单"
      :visible.sync="outerVisible1"
      @opened="opendhk"
      :fullscreen="true"
      :show-close="false"
    >
      角色名<el-input
        v-model="now_role.rolename"
        :readonly="true"
        style="width: 50%"
      ></el-input>
      <div style="margin: 20px">
        功能
        <el-select
          v-model="newlist"
          clearable
          placeholder="请选择"
          multiple
          @remove-tag="removetag"
          @clear="handleClear"
          value-key="id"
          ref="selectUpid"
          style="width: 50%"
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
            :data="menulist"
            :props="defaultProps"
            ref="treedata"
            :expand-on-click-node="false"
            show-checkbox
            default-expand-all
            node-key="id"
            :check-on-click-node="true"
            :check-strictly="flag2"
            @node-click="handleNodeClick"
            @check="handleNodeClick"
          >
            <span slot-scope="{ data }">
              <span>{{ data.name }}</span>
              <el-tag size="mini" style="margin: 0 10px">{{
                data.menutype == "0"
                  ? "目录"
                  : data.menutype == "1"
                  ? "菜单"
                  : "功能"
              }}</el-tag>
            </span>
          </el-tree>
        </el-select>
      </div>
      <el-button @click="qxmenu()">取消</el-button>

      <el-button @click="upmenu()">修改保存</el-button>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "role",
  computed: {
    ...mapState(["user"]),
  },
  data() {
    var checkrolecode = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("角色编码不能为空"));
      }
      if (!value.match("^[\u4e00-\u9fa5a-zA-Z]{6,20}$")) {
        return callback(new Error("角色编码不符合规范"));
      }
      callback();
    };
    var checkrolename = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("角色名称不能为空"));
      }
      if (!value.match("^[\u4e00-\u9fa5a-zA-Z]{2,50}$")) {
        return callback(new Error("角色名称不符合规范"));
      }
      callback();
    };
    // var checkexpirydate = (rule, value, callback) => {
    //   if (!value && this.now_role.state == "1") {
    //     return callback(new Error("失效时间不能为空"));
    //   }

    //   callback();
    // };

    return {
      rules: {
        rolecode: [
          { required: true, validator: checkrolecode, trigger: "blur" },
        ],
        rolename: [
          { required: true, validator: checkrolename, trigger: "blur" },
        ],
        // expirydate: [{ validator: checkexpirydate, trigger: "blur" }],
      },
      flag: true,
      outerVisible: false,
      roleform: "",
      now_role: {
        id: 0,
        rolecode: "",
        rolename: "",
        effectivedate: "",
        expirydate: "",
      },
      multipleSelection: [],
      rolelist: [],
      queryInfo: {
        rolename: "",
        state: "",
      },
      querydata: {
        rolename: "",
        state: "",
        page: 1,
        pagetotal: 20,
      },
      total: 0,
      selelist: [],
      saveForm: {
        upids: [{ id: "", name: "", tag: "" }],
      },
      newlist: [],
      outerVisible1: false,
      defaultProps: {
        children: "childlist",
        label: "name",
      },
      menulist: [],
      list: [],
      flag2: true,
      pre: {
        select: false,
        insert: false,
        update: false,
        delete: false,
        menuupdate: false,
      },
    };
  },
  created() {
    this.$axios
      .post("/api/user/preallmenu", {
        name: this.user.username,
        path: this.$route.path,
      })
      .then((rep) => {
        var list = rep.data.data;
        console.log(this.pre);
        this.pre.select = list.indexOf("role:select") == -1 ? false : true;
        this.pre.insert = list.indexOf("role:insert") == -1 ? false : true;
        this.pre.delete = list.indexOf("role:delete") == -1 ? false : true;
        this.pre.update = list.indexOf("role:update") == -1 ? false : true;
        this.pre.menuupdate =
          list.indexOf("role:updatemenu") == -1 ? false : true;
        this.findPage();
      });
  },

  methods: {
    stateChange(val) {
      console.log(val);
      if (val.state == "0") {
        this.stateon(val);
      } else {
        this.stateoff(val);
      }
    },
    qxmenu() {
      this.outerVisible1 = false;
    },
    upmenu() {
      var list__ = [];
      list__.push(this.now_role.id);
      this.$axios
        .post("/api/role/upmenu", {
          id: list__,
          list: this.list,
          newlist: this.newlist,
        })
        .then((rep) => {
          console.log(rep);

          this.$message.success(rep.data.message);
          this.outerVisible1 = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    szcd(role) {
      this.now_role = {
        rolename: role.rolename,
        id: role.id,
      };
      this.$axios
        .post("/api/role/tomenu", this.now_role)
        .then((resp) => {
          this.flag2 = true;
          this.menulist = resp.data.data.menufor;
          this.outerVisible1 = true;
          this.list = resp.data.data.menulist;

          //
        })
        .catch((err) => {
          console.log(err);
        });
    },
    update(formname) {
      this.$refs[formname].validate((valid) => {
        if (valid) {
          if (this.now_role.state == "0") {
            this.now_role.expirydate = "";
          }
          this.$axios
            .post("/api/role/updaterole", this.now_role)
            .then((resp) => {
              console.log(resp);
              this.$message.success(resp.data.message);
              this.findPage();
              this.outerVisible = !this.outerVisible;
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
    add(formname) {
      this.$refs[formname].validate((valid) => {
        if (valid) {
          if (this.now_role.state == "0") {
            this.now_role.expirydate = "";
          }
          this.$axios
            .post("/api/role/addrole", this.now_role)
            .then((resp) => {
              this.$message.success(resp.data.message);
              this.findPage();
              this.outerVisible = !this.outerVisible;
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
    toadd() {
      this.now_role = {
        id: 0,
        rolecode: "",
        rolename: "",
        effectivedate: "",
        expirydate: "",
      };
      this.roleform = "添加角色";
      this.flag = true;
      this.outerVisible = !this.outerVisible;
      
      
    },
    findPage2() {
      this.querydata.rolename = this.queryInfo.rolename;

      this.querydata.state = this.queryInfo.state;
      this.querydata.page = 1;

      this.findPage();
    },
    findPage() {
      if (!this.pre.select) {
        return;
      }

      this.$axios
        .post("/api/role/findall", this.querydata)
        .then((data) => {
          console.log(data);
          this.rolelist = data.data.pageData;
          this.total = data.data.totalpage;
          if (this.rolelist.length == 0 && this.querydata.page > 1) {
            this.querydata.page = this.querydata.page - 1;
            this.findPage();
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    toupdate(data) {
      this.$confirm("您的操作将对该角色进行修改, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.now_role = {
            id: data.id,
            rolecode: data.rolecode,
            rolename: data.rolename,
            effectivedate: data.effectivedate,
            expirydate: data.expirydate,
          };
          this.roleform = "修改角色";
          this.flag = false;
          this.outerVisible = !this.outerVisible;
          
          
        })
        .catch(() => {
          this.$message.info("操作已取消!");
        });
    },
    dele(data) {
      this.$confirm("您的操作将对该角色进行删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          var list = [];
          list.push(data);
          this.$axios
            .post("/api/role/delerole", list)
            .then((rep) => {
              this.$message.success(rep.data.message);
              this.findPage();
            })
            .catch((err) => {
              console.log(err);
            });
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
    detelist(data) {
      this.$confirm("您的操作将对所选角色进行批量删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios
            .post("/api/role/delerole", this.multipleSelection)
            .then((rep) => {
              this.$message.success(rep.data.message);
              this.findPage();
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch(() => {
          this.$message.info("操作已取消!");
        });
    },
    stateoff(data) {
      var list = [];
      list.push(data);
      this.$axios
        .post("/api/role/stateoff", list)
        .then((rep) => {
          this.$message.success(rep.data.message);
          setTimeout(this.findPage(), 500);
          
        })
        .catch((err) => {
          console.log(err);
         setTimeout(this.findPage(), 500);
        });
    },
    stateon(data) {
      var list = [];
      list.push(data);
      this.$axios
        .post("/api/role/stateon", list)
        .then((rep) => {
          this.$message.success(rep.data.message);
          this.findPage();
        })
        .catch((err) => {
          console.log(err);
        });
    },

    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    handleSizeChange(newPageSize) {
      this.querydata.pagetotal = newPageSize;

      this.findPage();
    },
    handleCurrentChange(newPageNumber) {
      this.querydata.page = newPageNumber;
      this.findPage();
    },
    handleNodeClick() {
      // 这里主要配置树形组件点击节点后，设置选择器的值；自己配置的数据，仅供参考
      this.saveForm.upids = this.$refs.treedata.getCheckedNodes(false, true);
      console.log(this.saveForm.upids);
      this.newlist = this.$refs.treedata
        .getCheckedKeys()
        .concat(this.$refs.treedata.getHalfCheckedKeys());
      // 选择器执行完成后，使其失去焦点隐藏下拉框的效果
      if (this.saveForm.upids.length == 0) {
        this.saveForm.upids = [{ id: "", name: "", tag: "" }];
      }
      console.log(this.value1);
    },
    // 选择器配置可以清空选项，用户点击清空按钮时触发
    handleClear() {
      // 将选择器的值置空

      this.saveForm.upids = [{ id: "", name: "" }];
      this.newlist = [];
      this.$refs.treedata.setCheckedKeys([]);
    },
    removetag(val) {
      console.log(val);
      this.$refs.treedata.setChecked(val, false, true);
      this.handleNodeClick();
    },
    opendhk() {
      this.settreedateselect(this.list);
    },
    settreedateselect(val) {
      console.log(val);
      this.$refs.treedata.setCheckedKeys(val, false);
      this.flag2 = false;
      this.handleNodeClick();
    },
    upopen(){
      console.log(111);
      this.$refs["now_role"].clearValidate;
    }
  },
};
</script>

<style>
.sss {
  width: 100%;
}
</style>