<template>
  <div style="margin:20px 0">
    <el-row :gutter="20">
      <el-col :span="8">
        <h4 v-if="currentTag.tagId==null">添加标签</h4>
        <h4 v-else>修改标签</h4>
        <el-form :model="currentTag" :rules="rules" label-width="100px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="currentTag.tagName" placeholder="请输入标签名称"></el-input>
          </el-form-item>
          <el-form-item label="标签描述">
            <el-input v-model="currentTag.tagDescription" placeholder="请输入标签描述"></el-input>
          </el-form-item>
          <el-form-item>
            <el-col :span="2">
              <el-button type="primary" @click="onSave">
                <span v-if="currentTag.tagId==null">添加</span>
                <span v-else>保存</span>
              </el-button>
            </el-col>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="16">
        <el-table border :data="tableData" style="width: 100%;">
          <el-table-column prop="tagName" label="名称">
          </el-table-column>
          <el-table-column prop="articleCount" label="文章数">
          </el-table-column>
          <el-table-column prop="tagId" label="ID">
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)">编辑</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(scope.$index,scope.row)" v-if="scope.row.articleCount==0">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <h4>温馨提示：</h4>
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
      },
      dialogVisible: false,
      deleteIndex: null,
      tableData: [],
      currentTag: {
        tagId: null,
        tagName: "",
        tagDescription: "",
        articleCount: 0
      }
    }
  },
  mounted: function () {
    this.tagGet()    
  },
  methods: {
    tagGet() {
      var _this = this
      this.$http.post("/tag/getAllWithArticleCount", {
     
      }).then(res => {
        if (res.data.code == 200) {
          console.log("tag get:",res.data.data);
          _this.tableData = res.data.data
        } 
      }).catch(err => {
        console.error(err)
      }) 
    },
    handleEdit(index,row) {
      this.currentTag = row
    },
    handleDelete(index,row) {
      this.dialogVisible = true
      this.deleteIndex = index
    },
    onDeleteConfirm() {
      this.dialogVisible = false
      var tag = this.tableData[this.deleteIndex]
      var _this = this

      this.$http.post("/tag/delete", {
        id: tag.tagId,
      }).then(res => {
        if (res.data.code == 200) {
          _this.tableData.splice(this.deleteIndex,1)
          this.$message({
            message: '删除标签成功',
            type: 'success'
          });
        } 
      }).catch(err => {
        console.error(err)
      }) 
    },
    onSave() {
      if (this.currentTag.tagName.length == 0) {
        this.$message("名称不能为空")
        return
      }
      
      if (this.currentTag.tagId == null) {
        var _this = this
        this.$http.post("/tag/insert", {
          tagName: this.currentTag.tagName,
          tagDescription: this.currentTag.tagDescription,
        }).then(res => {
          if (res.data.code == 200) {
            _this.currentTag = {
              tagId: null,
              tagName: "",
              tagDescription: "",
              articleCount: 0
            }
            _this.tagGet()
            this.$message({
              message: '添加标签成功',
              type: 'success'
            });
          } 
        }).catch(err => {
          console.error(err)
        }) 
      } else {
        var _this = this
        this.$http.post("/tag/update", {
          tagId: this.currentTag.tagId,
          tagName: this.currentTag.tagName,
          tagDescription: this.currentTag.tagDescription,
        }).then(res => {
          if (res.data.code == 200) {
            _this.currentTag = {
              tagId: null,
              tagName: "",
              tagDescription: "",
              articleCount: 0
            }

            _this.tagGet()
            this.$message({
              message: '修改标签成功',
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