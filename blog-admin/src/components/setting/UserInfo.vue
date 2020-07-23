<template>
	<section>
        <el-card class="box-card" style="width: 100%">
            <el-form :model="userForm" status-icon :rules="userFormRules" ref="userForm" label-width="100px">
                <el-form-item label="用户名" prop="username">
                    <el-input type="text" v-model="userForm.username" auto-complete="off"/>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input type="text" v-model="userForm.email" auto-complete="off"/>
                </el-form-item>
                <el-form-item label="头像" prop="header">
                    <el-input type="text" v-model="userForm.header"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('userForm')">提交</el-button>
                    <el-button @click="resetForm('userForm')">清空</el-button>
                </el-form-item>
            </el-form>
        </el-card>
	</section>
</template>

<script>
    import util from '../../common/util'
    import NProgress from 'nprogress'

	function loadData(vue) {
        NProgress.start();

		var _this = vue;
		util.get('/index/user', null, result => {
			if (result.errCode == 0) {
				_this.userForm = result.data;
			} else {
				_this.$message.error(result.msg);
			}
            NProgress.done();
		});
	}

    export default {
        data() {
            return {
                userForm: {
                    username: "",
                    email: "",
                    header: ""
                },
                userFormRules: {
                    username: [{
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    }],
					email: [{
						required: true,
						message: '请输入邮箱',
						trigger: 'blur'
					}]
                },
            }
        },
		mounted() {
			loadData(this);
        },
        methods: {
			submitForm(formName) {
                var _this = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        util.post("/user/update", _this.userForm, result => {
                            if (result.errCode == 0) {
								_this.$notify({
									title: '成功',
									message: '修改用户信息成功',
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