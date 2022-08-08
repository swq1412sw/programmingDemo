<template>
  <div class="sss">
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
        v-model="queryInfo.type"
        style="width: 160px"
        placeholder="选择菜单类型"
      >
        <el-option label="请选择菜单类型" value=""></el-option>
        <el-option label="目录" value="0"></el-option>
        <el-option label="菜单" value="1"></el-option>
        <el-option label="功能" value="2"></el-option>
      </el-select>
      <el-button @click="findPage2" type="primary"  :disabled="!pre.select">查找</el-button>
    </div>

    <div style="margin-top: 10px; margin-bottom: 5px">
      <el-button size="small" :disabled="!pre.insert" @click="toadd" type="primary" plain
        >添加</el-button
      >
      <el-button size="small" :disabled="!pre.delete" @click="detelist" type="danger" 
        >批量删除</el-button
      >
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
      @select="handleSelection"
      @selection-change="handleSelectionChange"
      @select-all="handleSelectionAll"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
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

      <el-table-column prop="logo" label="菜单标识" width="120">
      </el-table-column>
      <el-table-column prop="path" label="菜单地址" width="100">
      </el-table-column>
      <el-table-column prop="orderno" label="排序号" width="70">
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
            :disabled="true"
          >
          </el-switch>
          　
        </template>
        <!-- </el-table-column>
      <el-table-column
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
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150px">
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
        <el-button size="mini" type="danger" v-if="scope.row.state=='1'" @click="stateon(scope.row)" round>有效</el-button> -->
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="menuform"
      :visible.sync="outerVisible"
       @opened="upopen"
      :fullscreen="true"
     :show-close="false"
    >
      <el-form :model="now_menu" :rules="rules" ref="now_menu">
        <el-form-item label="菜单类型" prop="menutype">
          <el-select
            style="width: 30%"
            v-model="now_menu.menutype"
            text-align="left"
            placeholder="请选择"
            :disabled="!flag"
          >
            <el-option label="目录" value="0"></el-option>
            <el-option label="菜单" value="1"></el-option>
            <el-option label="功能" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input
            style="width: 70%"
            v-model="now_menu.name"
            placeholder="请输入"
          ></el-input>
        </el-form-item>

        <el-form-item label="上级菜单" prop="pid">
          <el-select
            v-model="now_menu.pid"
            clearable
            placeholder="请选择"
            @clear="handleClear"
            ref="selectUpResId"
          >
            <el-option
              hidden
              key="id"
              :value="now_menu.pid"
              :label="now_menu.pname"
            >
            </el-option>

            <el-tree
              :data="selelist"
              :props="defaultProps"
              :expand-on-click-node="false"
              :check-on-click-node="true"
              @node-click="handleNodeClick"
              node-key="id"
              ref="treeData"
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
        </el-form-item>
        <el-form-item label="地址" prop="path">
          <el-input
            style="width: 70%"
            v-model="now_menu.path"
            placeholder="当类型为菜单时，必填，其他不填"
          ></el-input>
        </el-form-item>
        <el-form-item label="菜单标识" prop="logo">
          <el-input
            style="width: 70%"
            v-model="now_menu.logo"
            placeholder="当类型为功能时，必填，其他不填"
          ></el-input>
        </el-form-item>
<el-form-item  prop="ico">
  <e-icon-picker v-model="now_menu.ico" />
</el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select
            style="width: 30%"
            v-model="now_menu.state"
            text-align="left"
            placeholder="请选择"
          >
            <el-option label="有效" value="0"></el-option>
            <el-option label="无效" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="describe">
          <el-input
            style="width: 70%"
            v-model="now_menu.describe"
            placeholder="请输入"
          ></el-input>
        </el-form-item>
        <el-form-item label="排序号" prop="orderno">
          <el-input
            style="width: 70%"
            v-model="now_menu.orderno"
            placeholder="请输入"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="qx('now_menu')">取消</el-button>
          <el-button v-if="flag" @click="add('now_menu')">添加</el-button>
          <el-button v-if="!flag" @click="update('now_menu')"
            >修改保存</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "menus",
  computed: {
    ...mapState(["user"]),
  },
  data() {
    var checkmenutype = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("菜单类型不能为空"));
      }

      callback();
    };
    var checkmenuname = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("菜单名称不能为空"));
      }

      callback();
    };
    var checkpath = (rule, value, callback) => {
      console.log(value != "" && this.now_menu.menutype == "1");
      console.log(value != "");
      console.log(this.now_menu.menutype == "1");
      if (value == "" && this.now_menu.menutype == "1") {
        return callback(new Error("类型为菜单时，地址不能为空"));
      }

      callback();
    };
    var checklogo = (rule, value, callback) => {
      if (value == "" && this.now_menu.menutype == "2") {
        return callback(new Error("类型为功能时，标识不能为空"));
      }

      callback();
    };
    var checkpid = (rule, value, callback) => {
      console.log(value != "" && this.now_menu.menutype > this.select_type);
      console.log(value != "");
      console.log(this.select_type > this.now_menu.menutype);
      if (value != "" && this.select_type >= this.now_menu.menutype) {
        if (
          this.select_type == this.now_menu.menutype &&
          this.select_type != "0"
        ) {
          return callback(new Error("上级菜单必须选择大一级的类型"));
        }
      }
      if (this.select_type > this.now_menu.menutype) {
        return callback(new Error("上级菜单必须选择大一级的类型"));
      }

      callback();
    };

    return {
      rules: {
        menutype: [{ validator: checkmenutype, trigger: "blur" }],
        name: [{ validator: checkmenuname, trigger: "blur" }],
        path: [{ validator: checkpath, trigger: "blur" }],
        logo: [{ validator: checklogo, trigger: "blur" }],
        pid: [{ validator: checkpid, trigger: "blur" }],
      },
      flag: true,
      outerVisible: false,
      menuform: "",
      now_menu: {
        id: 0,
        menutype: "",
        pid: -1,
        logo: "",
        name: "",
        path: "",
        state: "",
        describe: "",
        orderno: "",
        pname: "",
        ico:''
      },
      select_type: "",
      multipleSelection: [],
      menulist: [],
      queryInfo: {
        name: "",
        state: "",
        type: "",
      },
      querydata: {
        name: "",
        state: "",
        type: "",
        page: 1,
        pagetotal: 20,
      },
      total: 0,
      selelist: [],
      defaultProps: {
        children: "childlist",
        label: "name",
      },
      totalpage:0,
      pre: {
        select: false,
        insert: false,
        update: false,
        delete: false,
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
        this.pre.select = list.indexOf("menu:select") == -1 ? false : true;
        this.pre.insert = list.indexOf("menu:insert") == -1 ? false : true;
        this.pre.delete = list.indexOf("menu:delete") == -1 ? false : true;
        this.pre.update = list.indexOf("menu:update") == -1 ? false : true;

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
    update(formname) {
      this.$refs[formname].validate((valid) => {
        if (valid) {
          if (this.now_menu.pid == "") {
            this.now_menu.pid = -1;
          }
          this.$axios
            .post("/api/menu/updatemenu", this.now_menu)
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
    upopen(){
      console.log(this.$refs)
//  let menutype_= this.$refs.treeData.getNode(this.now_menu.pid)
           
              
              this.$refs.now_menu.clearValidate();
              //  this.select_type=menutype_.data.menutype
    },
    add(formname) {
      this.$refs[formname].validate((valid) => {
        if (valid) {
          if (this.now_menu.pid == "") {
            this.now_menu.pid = -1;
          }
          this.$axios
            .post("/api/menu/addmenu", this.now_menu)
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
      this.now_menu = {
        id: 0,
        menutype: "",
        pid: -1,
        logo: "",
        name: "",
        path: "",
        state: "",
        describe: "",
        orderno: "",
        pname: "无上级菜单",
        ico:'',
      };

      this.menuform = "添加菜单";
      this.select_type = "0";
      var list = [{ id: -1, name: "无上级菜单", menutype: "0", childlist: [] }];
      this.$axios
        .post("/api/menu/findall", {})
        .then((rep) => {
          this.selelist = list.concat(rep.data.data);
           this.flag = true;
          this.outerVisible = !this.outerVisible;
          
         
        })
        .catch((err) => {
          console.log(err);
        });
    },
    findPage2() {
      this.querydata.name = this.queryInfo.name;
      this.querydata.type = this.queryInfo.type;
      this.querydata.state = this.queryInfo.state;

      this.findPage();
    },
    findPage() {
      if (!this.pre.select) {
        return;
      }

      this.$axios
        .post("/api/menu/findall", this.querydata)
        .then((data) => {
          console.log(data);
          this.menulist = data.data.pageData;
          this.totalpage=data.data.totalpage
        })
        .catch((err) => {
          console.log(err);
        });
    },
    toupdate(data) {
      this.$confirm("您的操作将对该菜单进行修改, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          console.log(data);
          this.now_menu = {
            id: data.id,
            menutype: data.menutype,
            pid: data.pid,
            logo: data.logo,
            name: data.name,
            path: data.path,
            state: data.state,
            describe: data.describe,
            orderno: data.orderno,
            pname: data.pname,
            ico:data.ico
          };
          if (this.now_menu.pid == -1) {
            this.now_menu.pname = "无上级菜单";
          }
          this.select_type = data.menutype=='2'?'1':'0';
          this.menuform = "修改菜单";
          var list = [
            { id: -1, name: "无上级菜单", menutype: "0", childlist: [] },
          ];
          this.$axios
            .post("/api/menu/findall", {})
            .then((rep) => {
              this.selelist = list.concat(rep.data.data);
              console.log(this.selelist)
              
              this.flag = false;
              this.outerVisible = !this.outerVisible;
              
            
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch(() => {
          this.$message.info("操作已取消!");
        });
    },
    dele(data) {
      this.$confirm("您的操作将对该菜单进行删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          var list = [];
          list.push(data);
          this.$axios
            .post("/api/menu/delemenu", list)
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
      this.$confirm("您的操作将对所选菜单进行批量删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios
            .post("/api/menu/delemenu", this.multipleSelection)
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
      console.log(data);
      var list = [];
      list.push(data);
      this.$axios
        .post("/api/menu/stateoff", list)
        .then((rep) => {
          this.$message.success(rep.data.message);
          this.findPage();
        })
        .catch((err) => {
          this.findPage();
        });
    },
    stateon(data) {
      console.log(data);
      var list = [];
      list.push(data);
      this.$axios
        .post("/api/menu/stateon", list)
        .then((rep) => {
          this.$message.success(rep.data.message);
          this.findPage();
        })
        .catch((err) => {
          this.findPage();
        });
    },
 handleSelection(val,vall) {
    console.log(val,vall)
    let flag_select=false;
    val.forEach(selection=>{
      if(selection.id==vall.id){
        flag_select=true
        return
      }
    })

    this.selectchild([vall],flag_select)
   
   
    },
    handleSelectionAll(val){
      let flag=false;
    let list= val.map((item=>{
      return item.id
     }))
     this.menulist.forEach(data=>{
      if(list.indexOf(data.id)!=-1){
        flag=true
      }
     })
     console.log(list)
        if(val.length!=this.totalpage-this.menulist.length || flag){
          this.selectchild(this.menulist,true)
        }else{
          this.selectchild(this.menulist,false)
        }
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = this.$refs.multipleTable.selection;
      console.log(this.multipleSelection);
     
    
    },
    
        selectchild(data,flag){
data.forEach((row=>{
 
  this.$refs.multipleTable.toggleRowSelection(row,flag)
 
  if(row.childlist.length>0){
    this.selectchild(row.childlist,flag)
  }
  
}))
    },
    handleSizeChange(newPageSize) {
      this.querydata.pagetotal = newPageSize;

      this.findPage();
    },
    handleCurrentChange(newPageNumber) {
      this.querydata.page = newPageNumber;
      this.findPage();
    },
    handleNodeClick(data) {
      // 这里主要配置树形组件点击节点后，设置选择器的值；自己配置的数据，仅供参考
      this.now_menu.pname = data.name;
      this.now_menu.pid = data.id;
      this.select_type = data.menutype;
      // 选择器执行完成后，使其失去焦点隐藏下拉框的效果
      this.$refs.selectUpResId.blur();
      console.log(this.now_menu);
      console.log(this.select_type);
      console.log(this.now_menu.menutype > this.select_type);
    },
    // 选择器配置可以清空选项，用户点击清空按钮时触发
    handleClear() {
      // 将选择器的值置空
      this.now_menu.pid = -1;
      this.now_menu.pname = "无上级菜单";
      this.select_type = "0";
    },
  },
};
</script>

<style>
.sss {
  width: 100%;
}
</style>