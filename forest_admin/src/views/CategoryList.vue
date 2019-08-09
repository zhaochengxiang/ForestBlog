<template>
  <div style="margin:20px 0">
    <el-row :gutter="20">
      <el-col :span="8">
        <h4 v-if="currentCategory.categoryId==null">添加分类</h4>
        <h4 v-else>修改分类</h4>
        <el-form :model="currentCategory" :rules="rules" label-width="100px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="currentCategory.categoryName" placeholder="请输入分类名称"></el-input>
          </el-form-item>
          <el-form-item label="父节点" prop="parentNode">
            <el-select v-model="currentCategory.categoryPid" placeholder="父节点">
              <el-option label="无" :value="0">
              </el-option>
              <el-option v-for="category in categoryParentList" :key="category.categoryId" :label="category.categoryName" :value="category.categoryId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分类描述">
            <el-input v-model="currentCategory.categoryDescription" placeholder="请输入分类描述"></el-input>
          </el-form-item>
          <el-form-item label="图标样式">
            <el-input v-model="currentCategory.categoryIcon" placeholder="请输入图标样式，如fa fa-coffee"></el-input>
          </el-form-item>
          <el-form-item>
            <el-col :span="2">
              <el-button type="primary" @click="onSave">
                <span v-if="currentCategory.categoryId==null">添加</span>
                <span v-else>保存</span>
              </el-button>
            </el-col>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="16">
        <el-table border :data="tableData" style="width: 100%;">
          <el-table-column prop="categoryName" label="名称">
          </el-table-column>
          <el-table-column prop="articleCount" label="文章数">
          </el-table-column>
          <el-table-column prop="categoryId" label="ID">
          </el-table-column>
          <el-table-column prop="categoryPid" label="PID">
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)">编辑</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(scope.$index,scope.row)" v-if="scope.row.articleCount==0">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <h4>温馨提示：</h4>
        <h4>分类最多只有两级，一级分类pid=0，二级分类pid=其父节点id</h4>
        <h4>如果该分类包含文章，将不可删除</h4>
      </el-col>
    </el-row>

    <el-dialog title="提示" :visible.sync="dialogVisible" width="20%">
      <span>您确定要删除该分类吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onDeleteConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
        ],
        parentNode: [
          { required: true, message: '请输入父节点', trigger: 'blur' },
        ],
      },
      dialogVisible: false,
      deleteIndex: null,
      tableData: [],
      categoryParentList: [],
      currentCategory: {
        categoryId: null,
        categoryPid: 0,
        categoryName: "",
        categoryDescription: "",
        categoryIcon: "",
      }
    }
  },
  mounted: function () {
    this.categoryGet()    
  },
  methods: {
    categoryGet() {
      var _this = this
      this.$http.post("/category/getAllWithArticleCount", {
     
      }).then(res => {
        if (res.data.code == 200) {
          console.log("category get:",res.data.data);
          _this.tableData = res.data.data

          _this.categoryParentList.splice(0,_this.categoryParentList.length)
          for (var i=0; i<_this.tableData.length; i++) {
            var category = _this.tableData[i]
            if (category.categoryPid == 0) {
              _this.categoryParentList.push(category)
            }
          }
        } 
      }).catch(err => {
        console.error(err)
      }) 
    },
    handleEdit(index,row) {
      this.currentCategory = row
    },
    handleDelete(index,row) {
      this.dialogVisible = true
      this.deleteIndex = index
    },
    onDeleteConfirm() {
      this.dialogVisible = false
      var category = this.tableData[this.deleteIndex]
      var _this = this

      this.$http.post("/category/delete", {
        id: category.categoryId,
      }).then(res => {
        if (res.data.code == 200) {
          _this.tableData.splice(this.deleteIndex,1)
          this.$message({
            message: '删除分类成功',
            type: 'success'
          });
        } 
      }).catch(err => {
        console.error(err)
      }) 
    },
    onSave() {
      if (this.currentCategory.categoryName.length == 0) {
        this.$message("名称不能为空")
        return
      }
      
      if (this.currentCategory.categoryId == null) {
        var _this = this
        this.$http.post("/category/insert", {
          categoryPid: this.currentCategory.categoryPid,
          categoryName: this.currentCategory.categoryName,
          categoryDescription: this.currentCategory.categoryDescription,
          categoryIcon: this.currentCategory.categoryIcon,
        }).then(res => {
          if (res.data.code == 200) {
            _this.currentCategory = {
              categoryId: null,
              categoryPid: 0,
              categoryName: "",
              categoryDescription: "",
              categoryIcon: "",
            }
            _this.categoryGet()
            this.$message({
              message: '添加分类成功',
              type: 'success'
            });
          } 
        }).catch(err => {
          console.error(err)
        }) 
      } else {
        var _this = this
        this.$http.post("/category/update", {
          categoryId: this.currentCategory.categoryId,
          categoryPid: this.currentCategory.categoryPid,
          categoryName: this.currentCategory.categoryName,
          categoryDescription: this.currentCategory.categoryDescription,
          categoryIcon: this.currentCategory.categoryIcon,
        }).then(res => {
          if (res.data.code == 200) {
            _this.currentCategory = {
              categoryId: null,
              categoryPid: 0,
              categoryName: "",
              categoryDescription: "",
              categoryIcon: "",
            }

            _this.categoryGet()
            this.$message({
              message: '修改分类成功',
              type: 'success'
            });
          } 
        }).catch(err => {
          console.error(err)
        }) 
      }
    }
  }
}
</script>

<style>

</style>