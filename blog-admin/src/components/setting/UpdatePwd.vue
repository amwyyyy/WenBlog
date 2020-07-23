<template>
    <section>
        <el-card class="box-card" style="width: 100%">
            <el-form :model="passwordForm" status-icon :rules="passwordFormRules" ref="passwordForm" label-width="100px">
                <el-form-item label="旧密码" prop="old">
                    <el-input type="password" v-model="passwordForm.old" auto-complete="off"/>
                </el-form-item>
                <el-form-item label="新密码" prop="new1">
                    <el-input type="password" v-model="passwordForm.new1" auto-complete="off"/>
                </el-form-item>
                <el-form-item label="新密码" prop="new2">
                    <el-input type="password" v-model="passwordForm.new2"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('passwordForm')">提交</el-button>
                    <el-button @click="resetForm('passwordForm')">清空</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </section>
</template>

<script>
    import util from '../../common/util'

    export default {
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.passwordForm.new1) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };

            return {
                passwordForm: {
                    old: "",
                    new1: "",
                    new2: ""
                },
                passwordFormRules: {
                    old: [{
                        required: true,
                        message: '请输入旧密码',
                        trigger: 'blur'
                    }],
                    new1: [{
                        required: true,
                        message: '请输入新密码',
                        trigger: 'blur'
                    }],
                    new2: [{
                        validator: validatePass,
                        trigger: 'blur'
                    }]
                },
            }
        },
        mounted() {

        },
        methods: {
            submitForm(formName) {
                var _this = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        util.post("/user/update_pwd", _this.passwordForm, result => {
                            if (result.errCode == 0) {
                                _this.$notify({
                                    title: '成功',
                                    message: '修改密码成功',
                                    type: 'success'
                                });
                            } else {
                                _this.$message.error(result.msg);
                            }
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>