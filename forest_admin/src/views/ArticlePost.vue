<template>
  <div style="margin: 20px 0">
    <el-form :model="article" :rules="rules" label-width="100px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="article.articleTitle"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <!-- <mavon-editor :subfield = "false" :defaultOpen = "editDefaultOpen" v-model="article.articleContent" class="editor" /> -->
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-col :span="4">
          <el-select v-model="categoryParentId" placeholder="一级分类">
            <el-option-group label="一级分类">
              <el-option v-for="category in categoryParentList" :key="category.categoryId" :label="category.categoryName" :value="category.categoryId">
              </el-option>
            </el-option-group>
          </el-select>
        </el-col>
        <el-col :span="1">-</el-col>
        <el-col :span="4">
          <el-select v-model="categoryChildId" placeholder="二级分类">
            <el-option-group label="二级分类">
              <el-option v-for="category in categoryChildList" :key="category.categoryId" :label="category.categoryName" :value="category.categoryId">
              </el-option>
            </el-option-group>
          </el-select>
        </el-col>
      </el-form-item>
      <el-form-item label="标签">
        <el-checkbox-group v-model="articleTagList">
          <el-checkbox v-for="tag in tagList" :key="tag.tagId" :label="tag.tagId">{{tag.tagName}}</el-checkbox>
        </el-checkbox-group>
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
      categoryParentList: [],
      categoryChildList: [],
      prevCategoryParentId: null,
      prevCategoryChildId: null,
      categoryParentId: null,
      categoryChildId: null,
      tagList: [],
      articleTagList:[]
    }
  },
  watch: {
    categoryParentId: function() {
      if (this.prevCategoryParentId != null && this.categoryParentId == this.prevCategoryParentId) {
        this.categoryChildId = this.prevCategoryChildId
      } else {
        this.categoryChildId = null
      }

      this.categoryChildList.splice(0,this.categoryChildList.length)
      for (var i = 0; i < this.categoryList.length; i++) {
        var category = this.categoryList[i]
        if (this.categoryParentId == category.categoryPid) {
          this.categoryChildList.push(category)
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
      this.tagAll()
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
              _this.categoryParentId = _this.prevCategoryParentId = category.categoryId
            } else {
              _this.categoryChildId = _this.prevCategoryChildId = category.categoryId
            }
          }

          for (var i = 0; i< _this.article.tagList.length; i++) {
            var tag = _this.article.tagList[i]
            _this.articleTagList.push(tag.tagId)
          }

          _this.categoryAll()
          _this.tagAll()
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
              _this.categoryParentList.push(category)
            }
            if (_this.categoryParentId == category.categoryPid) {
              _this.categoryChildList.push(category)
            }
          }
        } 
      }).catch(err => {
        console.error(err)
      }) 
    },
    tagAll() {
      var _this = this
      this.$http.post("/tag/getAll", {
        
      }).then(res => {
        if (res.data.code == 200) {
          console.log("tag all:",res.data.data)
          _this.tagList = res.data.data
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
      if (this.categoryParentId == null) {
        this.$message('必须选择一级分类')
        return
      }

      if (this.article.articleId == null) {

        var params = {
          articleTitle: this.article.articleTitle,
          articleContent: this.article.articleContent,
          articleParentCategoryId: this.categoryParentId,
          articleStatus: this.article.articleStatus,
          articleTagIds: this.articleTagList
        };
        if (this.categoryChildId) {
          params.articleChildCategoryId = this.categoryChildId
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
          articleParentCategoryId: this.categoryParentId,
          articleStatus: this.article.articleStatus,
          articleTagIds: this.articleTagList
        };
        if (this.categoryChildId) {
          params.articleChildCategoryId = this.categoryChildId
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
  }
}
</script>

<style scoped>
  .editor {
    width: 100%;
    height: 400px;
  }

</style>
