<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="queryMod">
				<el-form-item>
					<el-input v-model="queryMod.name" placeholder="菜单名">
						<el-button slot="append" icon="el-icon-search" @click="search"></el-button>
					</el-input>
				</el-form-item>

				<el-form-item class="toolBtn">
					<el-button @click="addMenu()" type="primary" icon="el-icon-plus">新增菜单</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
        <template>
            <el-table :data="tableData" stripe v-loading="listLoading" @cell-click="loadSubMenu">
                <el-table-column width="50">
                    <template scope="scope">
                        <i v-if="scope.row.hasSubMenu && !scope.row.subMenu" class="el-icon-arrow-right"></i>
                        <i v-if="scope.row.hasSubMenu && scope.row.subMenu > 0" class="el-icon-arrow-down"></i>
                    </template>
                </el-table-column>
                <el-table-column label="菜单名" width="150">
                    <template scope="scope">
                        <i :class="scope.row.iconCls"></i>
                        <span>{{ scope.row.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="page" label="页面文件" width="150"/>
                <el-table-column prop="path" label="URL" />
                <el-table-column prop="orderNum" label="排序" align="center" width="50"/>
                <el-table-column label="是否隐藏" align="center">
                    <template scope="scope">
						<span>{{ scope.row.hidden ? '是' : '否' }}</span>
                    </template>
                </el-table-column>
				<el-table-column prop="remark" label="备注"/>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button :plain="true" type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
                        <el-button :plain="true" type="danger" size="small" @click="del(scope.row)">删除</el-button>
                        <el-button :plain="true" type="primary" size="small" @click="addSubMenu(scope.row)"
								   v-if="scope.row.level===1 && (scope.row.path=='' || scope.row.path==undefined)">新增子菜单</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </template>

		<!--编辑弹框-->
		<el-dialog :title="editForm.id ? '编辑' : '新增'" :visible.sync="editVisible" width="40%">
			<el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
				<el-form-item label="菜单名" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<el-form-item label="页面文件" prop="page">
					<el-input v-model="editForm.page"></el-input>
				</el-form-item>
				<el-form-item label="URL" prop="path">
					<el-input v-model="editForm.path"></el-input>
				</el-form-item>
				<el-form-item label="排序" prop="orderNum">
					<el-input v-model.number="editForm.orderNum"></el-input>
				</el-form-item>
				<el-form-item label="图标" v-if="editForm.level != 3">
					<icon-select v-model="editForm.iconCls"></icon-select>
				</el-form-item>
                <el-form-item label="是否隐藏">
                    <el-switch v-model="editForm.hidden"></el-switch>
                </el-form-item>
				<el-form-item label="备注" prop="remark">
					<el-input type="textarea" v-model="editForm.remark"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editVisible=false;editLoading=false;">取 消</el-button>
				<el-button type="primary" @click.native="submit" :loading="editLoading">提 交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
    import util from '../../common/util'
    import NProgress from 'nprogress'
    import IconSelect from '../common/IconSelect.vue'

    function loadData(vue) {
		vue.listLoading = true;

		var _this = vue;
		util.get('/menu/load_menu', _this.queryMod, result => {
			if (result.errCode === 0) {
				_this.tableData = result.data;
			} else {
				_this.$message.error(result.msg);
			}
			_this.listLoading = false;
		});
	}

	function getIndex(tableData, id) {
        for (let i in tableData) {
            if (tableData[i].id === id) {
                return parseInt(i);
            }
        }
    }

    export default {
        data() {
            return {
				listLoading: false,

                queryMod: {
					level: 1,
					parentId: 0
                },
				tableData: [],

                editVisible: false, //编辑界面显是否显示
                //编辑界面数据,(里面的变量要先定义，不然会有不好的效果)
                editForm: {
					id: null,
                    level: null,
					name: '',
					page: '',
					path: '',
					orderNum: null,
					pId: null,
					iconCls: '',
                    hidden: null,
					remark: ''
				},
                editFormRules: {
                    name: [{
                        required: true,
                        message: '请输入菜单名',
                        trigger: 'blur'
                    }],
					component: [{
						required: true,
                        message: '请输入资源路径',
                        trigger: 'blur'
					}],
					path: [{
						required: true,
                        message: '请输入url',
                        trigger: 'blur'
					}],
					orderNum: [{
                        type: 'number', 
						message: '排序必须为数字值',
						trigger: 'blur'
					}],
					remark: [{
						max: 100,
						message: '备注不能超过100个字符',
						trigger: 'blur'
					}]
                }
			}
        },
		mounted() {
			loadData(this);
        },
		components: {
            IconSelect
		},
        methods: {
			search() {
			    this.queryMod.level = 1;
			    this.queryMod.parentId = 0;
				loadData(this);
			},
            loadSubMenu(row, col) {
			    // 箭头那行没有label
                let flag = col.label === undefined || col.label === '';

			    if (flag && (row.subMenu === undefined || row.subMenu === 0)) {
                    let _this = this;
                    util.get('/menu/load_menu', {level: row.level + 1, parentId: row.id}, result => {
                        if (result.errCode === 0) {
                            let index = getIndex(_this.tableData, row.id) + 1;
                            result.data.forEach(value => {
                                _this.tableData.splice(index++, 0, value);
                            });
                            row.subMenu = result.data.length;
                        } else {
                            _this.$message.error(result.msg);
                        }
                    });
                } else {
                    let index = getIndex(this.tableData, row.id);
                    this.tableData.splice(index + 1, row.subMenu);
                    row.subMenu = 0;
                }
            },
            //删除记录
            del(row) {
                let _this = this;
                this.$confirm('确认删除该记录吗?', '提示', { type: 'warning' }).then(() => {
                    _this.listLoading = true;
                    NProgress.start();
                    
					util.post('/menu/delete', {menuId: row.id}, result=>{
						_this.listLoading = false;
						NProgress.done();

						if (result.errCode === 0) {
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
            //显示编辑界面
            edit(row) {
                this.editVisible = true;
				
				// 直接把row赋给editForm会和表格数据绑定
                util.copyProperties(this.editForm, row);
            },
			//添加菜单
			addMenu() {
				util.clearProperties(this.editForm);

				this.editForm.level = 1;
				this.editForm.pid = 0;

				this.editVisible = true;
			},
			addSubMenu(row) {
				util.clearProperties(this.editForm);

				this.editForm.pid = row.id;
				this.editForm.level = 2;

				this.editVisible = true;
			},
            //编辑提交
            submit() {
                var _this = this;

                _this.$refs.editForm.validate((valid) => {
                    if (valid) {
						_this.editLoading = true;
						NProgress.start();

						util.post('/menu/save', _this.editForm, result => {
							_this.editLoading = false;
							NProgress.done();

							if (result.errCode === 0) {
								_this.$notify({
									title: '成功',
									message: '提交成功',
									type: 'success'
								});
								_this.editVisible = false;

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

<style>

</style>