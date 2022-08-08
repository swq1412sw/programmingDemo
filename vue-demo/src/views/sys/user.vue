<template>
  <div class="sss">
    <div>
      <el-input
        placeholder="请输入用户名"
        v-model="queryInfo.username"
        style="width: 150px"
        clearable
      >
      </el-input>
      <el-input
        placeholder="请输入用户名称"
        v-model="queryInfo.nickname"
        style="width: 150px"
        clearable
      ></el-input>
      <el-select
        v-model="queryInfo.gender"
        style="width: 90px"
        placeholder="选择性别"
      >
        <el-option label="请选择" value=""></el-option>
        <el-option label="女" value="0"></el-option>
        <el-option label="男" value="1"></el-option>
      </el-select>
      <el-select
        v-model="queryInfo.state"
        style="width: 90px"
        placeholder="选择状态"
      >
        <el-option label="请选择状态" value=""></el-option>
        <el-option label="正常" value="0"></el-option>
        <el-option label="禁用" value="1"></el-option>
      </el-select>
      <el-date-picker
        style="width: 150px"
        v-model="queryInfo.start"
        type="date"
        placeholder="开始时间"
      >
      </el-date-picker>
      <el-date-picker
        style="width: 150px"
        v-model="queryInfo.end"
        type="date"
        placeholder="结束时间"
      >
      </el-date-picker>
      <el-button @click="findPage2" type="primary" :disabled="!pre.select">查找</el-button>
    </div>

    <div style="margin-top: 10px; margin-bottom: 5px">
      <el-button size="small" @click="toadd" type="primary" plain :disabled="!pre.insert"
        >添加</el-button
      >
      <el-button size="small" @click="detelist" type="danger" :disabled="!pre.delete"
        >批量删除</el-button
      >

      <el-button size="small" type="primary" plain @click="topladd" :disabled="!pre.insert"
        >批量导入</el-button
      >
      <el-button size="small" type="primary" plain @click="muban">导入模板</el-button>
      <el-button size="small" type="primary" plain @click="exportData">导出所选</el-button>
    </div>

    <el-table
      ref="multipleTable"
      :data="userlist"
      tooltip-effect="dark"
      border
      height="390"
      size="small"
      @selection-change="handleSelectionChange"
    >
      <el-table-column  type="selection" width="55">
      </el-table-column>
      <el-table-column label="用户名" width="120" prop="username">
      </el-table-column>
      <el-table-column prop="nickname" label="用户名称" width="120">
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="55">
        <template slot-scope="scope">
          　　　　　<span v-if="scope.row.gender == ''"> </span> 　　　　
          <span v-if="scope.row.gender == '0'">女</span> 　　　　
          <span v-if="scope.row.gender == '1'">男</span> 　
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="120"> </el-table-column>
      <el-table-column prop="email" label="邮箱" width="120"> </el-table-column>
      <el-table-column prop="state" label="状态" width="70">
        <template slot-scope="scope">
          　　　　
          <el-switch
            v-model="scope.row.state"
            active-value="0"
            inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="stateChange(scope.row)"
            :disabled="scope.row.username == user.username || !pre.update"
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
      >
    </el-table-column> -->
      <el-table-column fixed="right" label="操作" width="300px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            :disabled="!pre.update"
            @click="toupdate(scope.row)"
            round
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            :disabled="scope.row.username == user.username || !pre.delete"
            @click="dele(scope.row)"
            round
            >删除</el-button
          >
          <!-- <el-button type="danger" v-if="scope.row.state=='0'" @click="stateoff(scope.row)" round>无效</el-button>
        <el-button type="danger" v-if="scope.row.state=='1'" @click="stateon(scope.row)" round>有效</el-button> -->
          <el-button
            size="mini"
            type="text"
            :disabled="!pre.roleupdate"
            @click="szjs(scope.row)"
            round
            >设置角色</el-button
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
      :title="userform"
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
            :readonly="!flag"
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
          <el-button v-if="flag" @click="add('now_user')">添加</el-button>
          <el-button v-if="!flag" @click="update('now_user')"
            >修改保存</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog
      title="批量添加导入"
      :visible.sync="outerVisible1"
      :fullscreen="true"
      :show-close="false"
    >
      <upload-excel :on-success="success"></upload-excel>
    </el-dialog>
    <el-dialog
      title="设置角色"
      :visible.sync="outerVisible2"
      :fullscreen="true"
    >
      用户名：<el-input
        v-model="now_user.username"
        :readonly="true"
        style="width: 60%"
      ></el-input>
      <div style="margin: 20px">
        角色：<el-select
          v-model="newlist"
          multiple
          placeholder="请选择"
          style="width: 50%"
        >
          <el-option
            v-for="item in rolelist"
            :key="item.value"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <el-button @click="qxrole()">取消</el-button>

      <el-button @click="uprole()">修改保存</el-button>
    </el-dialog>
  </div>
</template>

<script>
import UploadExcel from "@/components/excelhelp.vue";
import aes from "@/util/aes";
import { mapState } from "vuex";
export default {
  name: "user",
  computed: {
    ...mapState(["user"]),
  },
  components: {
    UploadExcel,
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
      flag: true,
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
      multipleSelection: [],
      userlist: [],
      queryInfo: {
        username: "",
        gender: "",
        state: "",
        end: "",
        nickname: "",
        start: "",
      },
      querydata: {
        username: "",
        gender: "",
        state: "",
        start: "",
        end: "",
        nickname: "",
        page: 1,
        pagetotal: 20,
      },
      total: 0,
      selelist: [],
      outerVisible1: false,
      list: [],
      rolelist: [],
      newlist: [],
      outerVisible2: false,
      pre: {
        select: false,
        insert: false,
        update: false,
        delete: false,
        roleupdate: false,
      },
    };
  },
  watch:{
    outerVisible2(newvalue,oldvalue){
      if(oldvalue){
        this.newlist=[]
        this.rolelist=[]
      }
    }
  },
  created() {
    console.log(this.pre);
    this.$axios
      .post("/api/user/preallmenu", {
        name: this.user.username,
        path: this.$route.path,
      })
      .then((rep) => {
        var list = rep.data.data;
        console.log(this.pre);
        this.pre.select = list.indexOf("user:select") == -1 ? false : true;
        this.pre.insert = list.indexOf("user:insert") == -1 ? false : true;
        this.pre.delete = list.indexOf("user:delete") == -1 ? false : true;
        this.pre.update = list.indexOf("user:update") == -1 ? false : true;
        this.pre.roleupdate =
          list.indexOf("user:updaterole") == -1 ? false : true;
        this.findPage();
      });
  },

  methods: {
    ableselect(val) {
      return val.username != this.user.username;
    },
    stateChange(val) {
      console.log(val);
      if (val.state == "0") {
        this.stateon(val);
      } else {
        this.stateoff(val);
      }
    },
    uprole() {
      var list__ = [];
      list__.push(this.now_user.id);
      this.$axios
        .post("/api/user/uprole", {
          id: list__,
          list: this.list,
          newlist: this.newlist,
        })
        .then((rep) => {
          console.log(rep);
          this.$message.success(rep.data.message);
          this.outerVisible2 = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    qxrole() {
      this.outerVisible2 = false;
    },
    szjs(user) {
      this.now_user = {
        username: user.username,
        id: user.id,
      };
      this.$axios
        .post("/api/user/torole", this.now_user)
        .then((resp) => {
          this.list = resp.data.data.rolefor;
          var list_ = [];

          this.newlist = list_.concat(this.list);
          this.rolelist = resp.data.data.rolelist;
          this.outerVisible2 = true;
          console.log(this.list);
          console.log(this.newlist);
          console.log(this.rolelist);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    topladd() {
      this.outerVisible1 = !this.outerVisible1;
    },
    update(formname) {
      this.$refs[formname].validate((valid) => {
        if (valid) {
          this.now_user.password = aes.encrypt(this.now_user.password);
          this.$axios
            .post("/api/user/updateuser", this.now_user)
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
    add(formname) {
      this.$refs[formname].validate((valid) => {
        if (valid) {
          this.now_user.password = aes.encrypt(this.now_user.password);
          var list = [];
          list.push(this.now_user);
          this.$axios
            .post("/api/user/adduser", list)
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
      this.now_user = {
        username: "",
        nickname: "",
        password: "",
        gender: "",
        phone: "",
        email: "",
        state: "0",
        note: "",
      };
      this.userform = "添加用户";
       this.flag = true;
      this.outerVisible = !this.outerVisible;
      
     
    },
    findPage2() {
      this.querydata.username = this.queryInfo.username;
      this.querydata.gender = this.queryInfo.gender;
      this.querydata.state = this.queryInfo.state;
      this.querydata.nickname = this.queryInfo.nickname;

      this.querydata.start = this.queryInfo.start;
      this.querydata.end = this.queryInfo.end;
      this.querydata.page = 1;
      this.findPage();
    },
    findPage() {
      console.log(!this.pre.select);
      if (!this.pre.select) {
        return;
      }
      this.$axios
        .post("/api/user/findall", this.querydata)
        .then((data) => {
          console.log(data);
          this.userlist = data.data.pageData;
          this.total = data.data.totalpage;
          if (this.userlist.length == 0 && this.querydata.page > 1) {
            this.querydata.page = this.querydata.page - 1;
            this.findPage();
          }
        })
        .catch((err) => {
          console.log(err);
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
          this.userform = "修改用户";
          this.flag = false;
          this.outerVisible = !this.outerVisible;
         
          
        })
        .catch(() => {
          this.$message.info("操作已取消!");
        });
    },
    dele(data) {
      this.$confirm("您的操作将对该用户进行删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          var list = [];
          list.push(data);
          this.$axios
            .post("/api/user/deteuser", list)
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
      this.$confirm("您的操作将对所选用户进行批量删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios
            .post("/api/user/deteuser", this.multipleSelection)
            .then((rep) => {
              this.$message.success(rep.message);
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
        .post("/api/user/stateoff", list)
        .then((rep) => {
          this.$message.success(rep.data.message);
          this.findPage();
        })
        .catch((err) => {
          console.log(err);
          this.findPage();
        });
    },
    stateon(data) {
      var list = [];
      list.push(data);
      this.$axios
        .post("/api/user/stateon", list)
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

    success({ header, results }) {
      // 拿到的数据是如下这种，我们要将它转化成我们请求接口中所需要的数据
      console.log(header);
      // ["手机号", "姓名", "入职日期", "转正日期", "工号", ...]
      console.log(results);
      // [{入职日期: 'xxx', 姓名: 'xxx', 工号: 'xxx', ...}]

      // 1.定义我们需要的表头的映射关系（表头的字段其实就是我们接口中所需要的参数）
      const userRelations = {
        用户名: "username",
        密码: "password",
        用户名称: "nickname",
        性别: "gender",
        状态: "state",
        备注: "note",
        邮箱: "email",
        电话: "phone",
      };
      // 2.进行数据转换，实现业务需求
      var newArr = results.map((item) => {
        const userInfo = {};
        Object.keys(item).forEach((k) => {
          // key是当前的中文名 找到对应的英文名
          if (
            userRelations[k] == "creatdate" ||
            userRelations[k] === "updatetime"
          ) {
            userInfo[userRelations[k]] = new Date(
              this.formatDate(item[k], "/")
            ); // 只有这样, 才能入库
            return;
          }
          if (userRelations[k] == "gender") {
            userInfo[userRelations[k]] = item[k] == "女" ? "0" : "1"; // 只有这样, 才能入库
            return;
          }
          if (userRelations[k] == "state") {
            userInfo[userRelations[k]] = item[k] == "无效" ? "1" : "0"; // 只有这样, 才能入库
            return;
          }
          if (userRelations[k] == "is_delete") {
            userInfo[userRelations[k]] = item[k] == "已删除" ? "1" : "0"; // 只有这样, 才能入库
            return;
          }
            if (userRelations[k] == "password") {
            userInfo[userRelations[k]] = aes.encrypt(item[k])
            return;
          }
          userInfo[userRelations[k]] = item[k];
        });
        return userInfo;
      });
      var list = newArr;
      this.$axios
        .post("/api/user/adduser", list)
        .then((resp) => {
          this.$message.success(resp.data.message);
          this.findPage();
          this.outerVisible1 = !this.outerVisible1;
        })
        .catch((errot) => {
          console.log(errot);
        });
    },
    formatDate(numb, format) {
      const time = new Date((numb - 1) * 24 * 3600000 + 1);
      time.setYear(time.getFullYear() - 70);
      const year = time.getFullYear() + "";
      const month = time.getMonth() + 1 + "";
      const date = time.getDate() - 1 + "";
      if (format && format.length === 1) {
        return year + format + month + format + date;
      }
      return (
        year +
        (month < 10 ? "0" + month : month) +
        (date < 10 ? "0" + date : date)
      );
    },

    muban() {
      const headers = {
        用户名: "username",
        密码: "password",
        用户名称: "nickname",
        性别: "gender",
        状态: "state",
        备注: "note",
        邮箱: "email",
        电话: "phone",
      };
      // 懒加载
      import("@/vendor/Export2Excel").then((excel) => {
        // excel是引入文件（也就是@/ventor/Export2Excel）的导出对象
        // 获取所有的数据
        var rows = [
          {
            username: "用户名长度为6~20个英文字母或汉字组成",
            password: "6~20个数字或英文字母组成",
            nickname: "长度为2~50个英文字母或汉字组成",
            gender: "男，女，或者不填",
            state: "有效，无效，默认有效",
            note: "可不填",
            email: "可不填",
            phone: "可不填",
          },
        ];
        const data = this.formatJson2(headers, rows); // 返回的data就是我们转化后的数据
        excel.export_json_to_excel({
          // 要求转出的表头是中文
          header: Object.keys(headers), // 表头 必填
          data, // //具体数据 必填
          filename: "用户导入模板", // 非必填
          autoWidth: true, // 非必填
          bookType: "xlsx", // 非必填
        });
        // 拿到的数据是这种
        //  [{ username: 'xxx', mobile: 'xxx', ... }, { username: 'xxx', mobile: 'xxx', ... }]
        // 要转化的是如下这种
        // header: ['姓名', '手机号', '入职日期', ...]
        // data: [['张三', '138xxxxxxxx', '1992-08-04', ...], ['李四', '135xxxxxxxx', '1992-08-04', ...]]
      });
    },
    // 导出数据
    exportData() {
      // 1.定义表头对应关系
      const headers = {
        用户名: "username",
        用户名称: "nickname",
        性别: "gender",
        状态: "state",
        备注: "note",
        邮箱: "email",
        电话: "phone",
        创建时间: "creatdate",
        修改时间: "updatetime",
      };
      // 懒加载
      import("@/vendor/Export2Excel").then((excel) => {
        // excel是引入文件（也就是@/ventor/Export2Excel）的导出对象
        // 获取所有的数据
        var rows = this.multipleSelection;
        const data = this.formatJson(headers, rows); // 返回的data就是我们转化后的数据
        excel.export_json_to_excel({
          // 要求转出的表头是中文
          header: Object.keys(headers), // 表头 必填
          data, // //具体数据 必填
          filename: "用户数据导出", // 非必填
          autoWidth: true, // 非必填
          bookType: "xlsx", // 非必填
        });
        // 拿到的数据是这种
        //  [{ username: 'xxx', mobile: 'xxx', ... }, { username: 'xxx', mobile: 'xxx', ... }]
        // 要转化的是如下这种
        // header: ['姓名', '手机号', '入职日期', ...]
        // data: [['张三', '138xxxxxxxx', '1992-08-04', ...], ['李四', '135xxxxxxxx', '1992-08-04', ...]]
      });
    },
    formatJson(headers, rows) {
      return rows.map((item) => {
        console.log(item);
        return Object.keys(headers).map((k) => {
          if (headers[k] == "gender" && item[headers[k]] != "") {
            return item[headers[k]] == "0" ? "女" : "男";
          } else if (headers[k] == "state") {
            return item[headers[k]] == "0" ? "有效" : "无效";
          } else if (headers[k] === "is_delete") {
            return item[headers[k]] == "0" ? "未删除" : "已删除";
          }
          return item[headers[k]];
        });
      });
    },
    formatJson2(headers, rows) {
      return rows.map((item) => {
        console.log(item);
        return Object.keys(headers).map((k) => {
          return item[headers[k]];
        });
      });
    },
    upopen(){
      this.$refs["now_user"].clearValidate;
    }
  },
};
</script>

<style>
.sss {
  width: 100%;
}
</style>