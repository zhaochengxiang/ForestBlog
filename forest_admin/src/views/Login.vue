<template>
  <div>
    <el-form class="login_container" label-position="right" label-width="80px" v-loading="loading">
      <h3 class="login_title">系统登录</h3>
      <el-form-item label="用户名">
        <el-input type="text" v-model="form.username" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="form.password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click.native.prevent="onSubmit" style="width: 100%">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
          username: '',
          password: '',
      },
      loading: false
    }
  },
  methods: {
    onSubmit() {
      var _this = this
      this.loading = true
      this.$http.post("/user/login", {
        username: this.form.username,
        password: this.form.password
      }).then(res => {
        _this.loading = false;
        if (res.data.code == 200) {
          window.localStorage.setItem("token", res.data.data.token)
          _this.$router.replace({path: '/home'})
        } else {
            _this.$alert('登录失败!', '失败!')
        }
      }).catch(err => {
        console.error(err)
      })
    }
  }
}
</script>

<style>
.login_container {
  border-radius: 15px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
