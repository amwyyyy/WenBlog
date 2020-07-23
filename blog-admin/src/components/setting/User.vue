<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar">
            <el-form :inline="true" :model="queryMod">
                <el-form-item>
                    <el-input v-model="queryMod.username" placeholder="姓名" icon="search" :on-icon-click="search"/>
                </el-form-item>
                <el-form-item class="toolBtn">
                    <el-button @click="add()" type="primary" icon="plus">新增</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表-->
        <el-table :data="tableData" stripe v-loading="listLoading" style="width: 100%;" @sort-change="handleSort" @row-dblclick="handleEdit">
            <el-table-column type="index" width="60"/>
            <el-table-column prop="username" width="100" label="用户名"/>
            <el-table-column prop="email" label="邮箱"/>
            <el-table-column prop="createTime" label="创建时间" sortable/>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button :plain="true" type="success" size="small" @click="role(scope.row)">分配角色</el-button>
					<el-button :plain="true" type="info" size="small" @click="edit(scope.row)">编辑</el-button>
					<el-button :plain="true" type="danger" size="small" @click="del(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <page :query-mod="queryMod" :page-size="pageSize" :total="pageTotal" @pagination="loadData"></page>

        <!--编辑弹框-->
        <el-dialog :title="editFormIsEdit ? '编辑' : '新增'" v-model="editFormVisible" :close-on-click-modal="false" size="tiny">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="editForm.username"/>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="editForm.email"/>
                </el-form-item>
                <el-form-item label="密码" prop="password" v-if="!editFormIsEdit">
                    <el-input type="password" v-model="editForm.password" icon="information"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false;editLoading = false;btnEditText = '提交'">取 消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
            </div>
        </el-dialog>

        <!-- 分配角色 -->
        <el-dialog title="分配角色" v-model="roleAssign" close-on-click-model="false" size="tiny" @close="roleClose">
            <el-tree :data="roleData" :props="props" node-key="id" ref="roleTree" :default-checked-keys="checkRole" show-checkbox/>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="roleAssign=false">取 消</el-button>
                <el-button type="primary" @click.native="roleSubmit" :loading="editLoading">确 定</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>
    import util from '../../common/util'
    import NProgress from 'nprogress'
    import Page from '../common/Page.vue'

    function loadData(vue) {
        vue.listLoading = true;

        var _this = vue;
        util.get('/user/list', _this.queryMod, result => {
            if (result.errCode === 0) {
                _this.tableData = result.data;
                _this.pageTotal = result.pageTotal;
            } else {
                _this.$message.error(result.msg);
            }

            _this.listLoading = false;
        });
    }

    export default {
        data() {
            return {
                listLoading: false,

                queryMod: {
                    username: '',
                    pageSize: 10,
                    pageNum: 1,
                    sortName: '',
                    sortType: ''
                },
                tableData: [],

                editFormVisible: false, //编辑界面显是否显示
                editFormIsEdit: true, //编辑界面标题
                //编辑界面数据
                editForm: {
                    id: null,
                    username: '',
                    email: '',
                    password: ''
                },
                editLoading: false,
                btnEditText: '提 交',
                editFormRules: {
                    username: [{
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    }],
                    email: [{
                        required: true,
                        message: '请输入邮箱地址',
                        trigger: 'blur'
                    }, {
                        type: 'email',
                        message: '请输入正确的邮箱地址',
                        trigger: 'blur,change'
                    }],
                    password: [{
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur'
                    }]
                },
                roleAssign: false,
                checkRole:[],
                roleData: [],
                assignUserId: null,
                props: {
                    label: 'name'
                }
            }
        },
        mounted() {
            loadData(this);
        },
        components: {
            Page //分页插件
        },
        methods: {
            //分页插件要调用的方法
            loadData(params) {
                this.queryMod = params || this.queryMod;
                loadData(this);
            },
            search() {
                loadData(this);
            },
            sort(column) {
                this.queryMod.sortName = column.prop;
                this.queryMod.sortType = column.order == "ascending" ? 'asc' : 'desc';

                loadData(this);
            },
            //删除记录
            del(row) {
                var _this = this;
                this.$confirm('确认删除该记录吗?', '提示', { type: 'warning' }).then(() => {
                    _this.listLoading = true;
                    NProgress.start();

                    util.post('/user/del', {userId: row.id}, result=>{
                        _this.listLoading = false;
                        NProgress.done();

                        if (result.errCode == 0) {
                            _this.$notify({
                                title: '成功',
                                message: '删除成功',
                                type: 'success'
                            });
                            loadData(this);
                        } else {
                            _this.$message.error(result.msg);
                        }
                    });
                }).catch(() => {
                    _this.$message('已取消删除');
                });
            },
            //显示新增界面
            add() {
                this.editFormVisible = true;
                this.editFormIsEdit = false;

                this.editForm.id = null;
                this.editForm.username = '';
                this.editForm.email = '';
                this.editForm.password = '';
            },
            //显示编辑界面
            edit(row) {
                this.editFormVisible = true;
                this.editFormIsEdit = true;

                util.copyProperties(this.editForm, row);
            },
            //显示角色添加框
            role(row) {
                NProgress.start();

                var _this = this;
                util.get('/role/assign/list', {userId: row.id}, result => {
                    NProgress.done();
                    if (result.errCode == 0) {
                        _this.roleData = result.data;

                        result.data.forEach(role => {
                            if (role.checked) {
                                _this.checkRole.push(role.id);
                            }
                        });
                    } else {
                        _this.$message.error(result.msg);
                    }
                });

                this.roleAssign = true;
                this.assignUserId = row.id;
            },
            roleSubmit() {
                this.editLoading = true;

                var roleIds = this.$refs.roleTree.getCheckedKeys(true).join(',');

                var _this = this;
                util.post('/role/assign', {userId: _this.assignUserId, roleIds: roleIds}, result => {
                    this.editLoading = false;
                    if (result.errCode == 0) {
                        this.roleAssign = false;
                        this.roleClose();

                        _this.$notify({
                            title: '成功',
                            message: '分配角色成功',
                            type: 'success'
                        });
                    } else {
                        _this.$message.error(result.msg);
                    }
                });
            },
            roleClose() {
                this.checkRole = [];
                this.assignUserId = null;
            },
            //编辑 or 新增
            editSubmit() {
                var _this = this;

                _this.$refs.editForm.validate((valid) => {
                    if (valid) {
                        _this.editLoading = true;
                        NProgress.start();
                        _this.btnEditText = '提交中';

                        var url = '/user/add';
                        if (_this.editFormIsEdit) {
                            url = '/user/update';
                        }

                        util.post(url, _this.editForm, result => {
                            _this.editLoading = false;
                            NProgress.done();
                            _this.btnEditText = '提 交';

                            if (result.errCode == 0) {
                                _this.$notify({
                                    title: '成功',
                                    message: '提交成功',
                                    type: 'success'
                                });
                                _this.editFormVisible = false;

                                loadData(this);
                            } else {
                                _this.$message.error(result.msg);
                            }
                        });
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>