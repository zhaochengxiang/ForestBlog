<template>
  <div style="margin: 20px 0">
    <el-form :model="article" :rules="rules" label-width="100px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="article.articleTitle"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <mavon-editor :subfield = "false" :defaultOpen = "editDefaultOpen" v-model="article.articleContent" class="editor" />
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-col :span="4">
          <el-select v-model="categoryPrimaryId" placeholder="一级分类">
            <el-option-group label="一级分类">
              <el-option v-for="category in categoryPrimaryList" :key="category.categoryId" :label="category.categoryName" :value="category.categoryId">
              </el-option>
            </el-option-group>
          </el-select>
        </el-col>
        <el-col :span="1">-</el-col>
        <el-col :span="4">
          <el-select v-model="categorySecondaryId" placeholder="二级分类">
            <el-option-group label="二级分类">
              <el-option v-for="category in categorySecondaryList" :key="category.categoryId" :label="category.categoryName" :value="category.categoryId">
              </el-option>
            </el-option-group>
          </el-select>
        </el-col>
      </el-form-item>
      <el-form-item label="order">
        <el-col :span="5">
          <el-input v-model="article.articleOrder"></el-input>
        </el-col>
        <el-col :span="6">
          <span>输入1-10的数字,order越大排序越前</span>
        </el-col>
      </el-form-item>
      <el-form-item label="状态">
        <el-col :span="4">
        <el-radio-group v-model="article.articleStatus">
          <el-radio :label="1">已发布</el-radio>
          <el-radio :label="0">草稿</el-radio>
        </el-radio-group>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-col :span="2">
          <el-button type="primary" @click="onSave">保存</el-button>
        </el-col>
        <el-col :span="2">
          <el-button @click="onReset">重置</el-button>
        </el-col>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
        ],
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' },
        ],
        category: [
          { required: true, message: '请选择分类', trigger: 'blur' },
        ],
      },
      editDefaultOpen: null,
      article: {
        articleId: null,
        articleTitle: '',
        articleContent: '',
        categoryList: [],
        tagList: [],
        articleOrder: 1,
        articleStatus: 1,
      },
      categoryList: [],
      categoryPrimaryList: [],
      categorySecondaryList: [],
      prevCategoryPrimaryId: null,
      categoryPrimaryId: null,
      prevCategorySecondaryId: null,
      categorySecondaryId: null,
      tagList: [],
    }
  },
  watch: {
    categoryPrimaryId: function() {
      if (this.prevCategoryPrimaryId != null && this.categoryPrimaryId == this.prevCategoryPrimaryId) {
        this.categorySecondaryId = this.prevCategorySecondaryId
      } else {
        this.categorySecondaryId = null
      }

      this.categorySecondaryList.splice(0,this.categorySecondaryList.length)
      for (var i = 0; i < this.categoryList.length; i++) {
        var category = this.categoryList[i]
        if (this.categoryPrimaryId == category.categoryPid) {
          this.categorySecondaryList.push(category)
        }
      }
    }
  },
  mounted: function () {
    var id = this.$route.query.id;
    if (id != null && id != '' && id != undefined) {
      console.log("article id:",id)
      this.article.articleId = id
      this.editDefaultOpen = "preview"
      this.articleGet()
    } else {
      this.editDefaultOpen = "edit"
      this.categoryAll()
    }
  },
  methods: {
    articleGet() {
      var _this = this
      this.$http.post("/article/get", {
        id: this.article.articleId,
      }).then(res => {
        if (res.data.code == 200) {
          console.log("article get:",res.data.data);
          _this.article = res.data.data
          for (var i = 0; i < _this.article.categoryList.length; i++) {
            var category = _this.article.categoryList[i]
            if (category.categoryPid == 0) {
              _this.categoryPrimaryId = _this.prevCategoryPrimaryId = category.categoryId
            } else {
              _this.categorySecondaryId = _this.prevCategorySecondaryId = category.categoryId
            }
          }
          _this.categoryAll()
        } 
      }).catch(err => {
        console.error(err)
      })    
    },
    categoryAll() {
      var _this = this
      this.$http.post("/category/getAll", {
        
      }).then(res => {
        if (res.data.code == 200) {
          console.log("category all:",res.data.data)
          _this.categoryList = res.data.data

          for (var i = 0; i < _this.categoryList.length; i++) {
            var category = _this.categoryList[i]
            if (category.categoryPid == 0) {
              _this.categoryPrimaryList.push(category)
            }
            if (_this.categoryPrimaryId == category.categoryPid) {
              _this.categorySecondaryList.push(category)
            }
          }
        } 
      }).catch(err => {
        console.error(err)
      }) 
    },
    onSave() {
      if (this.article.articleTitle==null || this.article.articleTitle.length==0) {
        this.$message('标题不能为空')
        return
      }
      if (this.article.articleContent==null || this.article.articleContent.length==0) {
        this.$message('内容不能为空')
        return
      }
      if (this.categoryPrimaryId == null) {
        this.$message('必须选择一级分类')
        return
      }

      if (this.article.articleId == null) {
      
        var params = {
          articleTitle: this.article.articleTitle,
          articleContent: this.article.articleContent,
          articleParentCategoryId: this.categoryPrimaryId,
          articleStatus: this.article.articleStatus
        };
        if (this.categorySecondaryId) {
          params.articleChildCategoryId = this.categorySecondaryId
        }
        if (this.article.articleOrder) {
          params.articleOrder = this.article.articleOrder
        }

        var _this = this
        this.$http.post("/article/insert", params).then(res => {
          if (res.data.code == 200) {
            this.$router.push({path: '/articleList'});
          } 
        }).catch(err => {
          console.error(err)
        }) 

      } else {
        
        var params = {
          articleId: this.article.articleId,
          articleTitle: this.article.articleTitle,
          articleContent: this.article.articleContent,
          articleParentCategoryId: this.categoryPrimaryId,
          articleStatus: this.article.articleStatus
        };
        if (this.categorySecondaryId) {
          params.articleChildCategoryId = this.categorySecondaryId
        }
        if (this.article.articleOrder) {
          params.articleOrder = this.article.articleOrder
        }

        var _this = this
        this.$http.post("/article/update", params).then(res => {
          if (res.data.code == 200) {
            this.$router.push({path: '/articleList'});
          } 
        }).catch(err => {
          console.error(err)
        }) 
      }
    },
    onReset() {

    }
  }
}
</script>

<style scoped>
  .editor {
    width: 100%;
    height: 400px;
  }

</style>
