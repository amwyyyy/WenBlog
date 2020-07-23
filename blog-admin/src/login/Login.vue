<template>
    <el-form :model="loginForm" :rules="rules" ref="loginForm" label-position="left" label-width="0px" class="card-box loginform">
        <h3 class="title">系统登录</h3>
        <el-form-item prop="username">
            <el-input type="text" v-model="loginForm.username" placeholder="账号" @keyup.enter.native.prevent="submit" tabindex=1>
                <el-button slot="append" tabindex=99><i class="fa fa-user-circle fa-fw"/></el-button>
            </el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="密码" @keyup.enter.native.prevent="submit" tabindex=2>
                <el-button slot="append" tabindex=99><i class="fa fa-keyboard-o fa-fw"/></el-button>
            </el-input>
        </el-form-item>
        <el-form-item>
            <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 35px 0px;" tabindex=3>记住我</el-checkbox>
        </el-form-item>
        <el-form-item style="width:100%;">
            <el-button type="primary" style="width:100%;" @click.native.prevent="submit" tabindex=4>登录</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import util from '../common/util'

    export default {
        data() {
            return {
                loginForm: {
                    username: '',
                    password: '',
                    rememberMe: false
                },
                rules: {
                    username: [{
                        required: true,
                        message: '请输入账号',
                        trigger: 'blur'
                    }],
                    password: [{
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur'
                    }]
                }
            };
        },
        methods: {
            submit() {
                var _this = this;
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        util.post("/index/login", _this.loginForm, (result) => {
                            if (result.errCode == 0) {
                                // window.location.href = "./index.html#/main";
                                window.location.href = "./#/main";
                            } else {
                                _this.$message.error(result.msg);
                            }
                        });
                    } else {
                        console.log('参数校验失败');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .card-box {
        padding: 20px;
        -webkit-border-radius: 5px;
        border-radius: 5px;
        -moz-border-radius: 5px;
        background-clip: padding-box;
        margin-bottom: 20px;
        background-color: #F9FAFC;
        margin: 120px auto;
        width: 400px;
        border: 2px solid #8492A6;
    }

    .title {
        margin: 0px auto 40px auto;
        text-align: center;
        color: #505458;
    }

    .loginform {
        width: 350px;
        padding: 35px 35px 15px 35px;
    }
</style>