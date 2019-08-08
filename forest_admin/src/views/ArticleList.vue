<template>
  <div style="margin:20px 0">
    <el-table :data="tableData" style="width: 100%;">
      <el-table-column prop="articleTitle" label="标题">
      </el-table-column>
      <el-table-column label="所属分类">
        <template slot-scope="scope">
          <span :key="category.categoryId" v-for="category in scope.row.categoryList">{{category.categoryName+"  "}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="articleStatus" label="状态">
      </el-table-column>
      <el-table-column label="发布时间">
        <template slot-scope="scope">{{scope.row.articleCreateTime | formatDateTime}}
        </template>
      </el-table-column>
      <el-table-column prop="articleId" label="id">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index,scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.$index,scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="table_footer">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        layout="total, prev, pager, next, jumper"
        :total="pageTotal">
      </el-pagination>
    </div>

    <el-dialog title="提示" :visible.sync="dialogVisible" width="20%">
      <span>您确定要删除这篇文章吗？</span>
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
      tableData: [],
      pageIndex: 1,
      pageSize: 10,
      pageTotal: 0,
      dialogVisible: false,
      handleIndex: null
    }
  },
  mounted: function () {
    this.articleSearch()    
  },
  methods: {
    articleSearch() {
      var _this = this
      this.$http.post("/article/search", {
        pageIndex: this.pageIndex,
        pageSize: this.pageSize
      }).then(res => {
        if (res.data.code == 200) {
          console.log("article search:",res.data.data);
          _this.pageTotal = res.data.data.total
          _this.tableData = res.data.data.list

          for (var i = 0; i < _this.tableData.length; i++) {
            var article = _this.tableData[i]
            if (article.articleStatus == 0) {
              article.articleStatus = "草稿"
            } else if (article.articleStatus == 1) {
              article.articleStatus = "已发布"    
            }
          }
        } 
      }).catch(err => {
        console.error(err)
      })    
    },
    handleEdit(index,row) {
      this.$router.push({path: '/articleEdit', query: {id:row.articleId}});
    },
    handleDelete(index,row) {
      this.dialogVisible = true
      this.handleIndex = index
    },
    onDeleteConfirm() {
      this.dialogVisible = false
      var article = this.tableData[this.handleIndex]
      var _this = this

      this.$http.post("/article/delete", {
        id: article.articleId,
      }).then(res => {
        if (res.data.code == 200) {
          _this.tableData.splice(this.handleIndex,1)
          _this.pageTotal--
        } 
      }).catch(err => {
        console.error(err)
      }) 
    },
    handleCurrentChange(val) {
      this.pageIndex = val
      this.articleSearch()
    },
  }
}
</script>

<style>
  .table_footer {
    padding-top: 10px;
    padding-bottom: 0px;
    margin-bottom: 0px;
  }
</style>
