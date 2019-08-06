<template>
    <div>
        <el-form ref="form" :model="form">
            <el-form-item label="用户名">
                <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">登录</el-button>
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
            }
        }
    },
    methods: {
      onSubmit() {
        this.$http.post("/user/login", {
            username: this.form.username,
            password: this.form.password
        }).then(res => {

        }).catch(err => {
            console.error(err);
        })
      }
    }
}
</script>

