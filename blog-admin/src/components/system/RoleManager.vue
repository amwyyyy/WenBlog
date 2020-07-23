<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="queryMod">
				<el-form-item>
					<el-input v-model="queryMod.name" placeholder="角色名" icon="search" :on-icon-click="search"/>
				</el-form-item>
				<el-form-item class="toolBtn">
					<el-button @click="roleAdd()" type="primary" icon="plus">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="tableData" stripe v-loading="listLoading" style="width: 100%;" @sort-change="handleSort" @row-dblclick="handleEdit">
				<el-table-column type="index" width="100"/>
				<el-table-column prop="name" label="角色名" width="200"/>
				<el-table-column prop="remark" label="备注" width="250"/>
				<el-table-column prop="createTime" label="创建时间" sortable width="200"/>
				<el-table-column inline-template :context="_self" label="操作" align='center'>
					<span>
						<el-button :plain="true" type="info" size="small" @click="openAssignDlg(row)">分配权限</el-button>
						<el-button :plain="true" type="info" size="small" @click="handleEdit(row)">编辑</el-button>
						<el-button :plain="true" type="danger" size="small" @click="handleDel(row)">删除</el-button>
					</span>
				</el-table-column>
			</el-table>
		</template>

		<!--分页-->
		<page :query-mod="queryMod" :page-size="pageSize" :total="pageTotal" @pagination="loadData"></page>

		<!--编辑弹框-->
		<el-dialog :title="editForm.id ? '编辑' : '新增'" v-model="editVisible" :close-on-click-modal="false" size="tiny">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="角色名" prop="name">
					<el-input v-model="editForm.name"/>
				</el-form-item>
				<el-form-item label="备注" prop="remark">
					<el-input v-model="editForm.remark"/>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editVisible=false;editLoading=false;btnEditText='提交'">取 消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
			</div>
		</el-dialog>

		<!-- 权限分配框 -->
		<el-dialog title="权限分配" v-model="moduleVisible" size="tiny">
			<el-tree :data="modules" :props="props" node-key="id" ref="moduleTree" :default-checked-keys="hasModules" 
				:default-expand-all="true" show-checkbox :render-content="renderContent" />
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="moduleVisible=false;editLoading=false;">取 消</el-button>
				<el-button type="primary" @click.native="moduleSubmit" :loading="editLoading">提 交</el-button>
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
		util.get('/role/list', _this.queryMod, result => {
			if (result.errCode == 0) {
				_this.tableData = result.data;
				_this.pageTotal = result.pageTotal;
			} else {
				_this.$message.error(result.msg);
			}

			_this.listLoading = false;
		});
	}

	//加载菜单
	function loadModule(pid, datas) {
		var menus = [];
		datas.forEach(data => {
			if (data.pid == pid) {
				var menu = {};
				menu.id = data.id;
				menu.name = data.name;
				menu.iconCls = data.iconCls;
				menu.children = loadModule(data.id, datas);
				menus.push(menu);
			}
		});
		return menus;
	}

    export default {
        data() {
            return {
				listLoading: false,

                queryMod: {
					name: '',
					pageSize: 10,
					pageNum: 1,
					sortName: '',
					sortType: ''
                },
				tableData: [],

                editVisible: false, //编辑界面显是否显示
                //编辑界面数据
                editForm: {
                    id: null,
                    name: '',
                    remark: ''
                },
                btnEditText: '提 交',
                editFormRules: {
                    name: [{
                        required: true,
                        message: '请输入角色名',
                        trigger: 'blur'
                    }]
                },

				moduleVisible: false,
				modules: [],
				hasModules: [],
				props: {
					label: 'name',
          			children: 'children'
				},
				roleId: null
            }
        },
		mounted() {
			loadData(this);
        },
		components: {
			Page
		},
        methods: {
			loadData(params) {
				this.queryMod = params || this.queryMod;
				loadData(this);
			},
			search() {
				loadData(this);
			},
			handleSort(column) {
				this.queryMod.sortName = column.prop;
				this.queryMod.sortType = column.order == "ascending" ? 'asc' : 'desc';

				loadData(this);
			},
            //删除记录
            handleDel(row) {
                var _this = this;
                this.$confirm('确认删除该记录吗?', '提示', { type: 'warning' }).then(() => {
                    _this.listLoading = true;
                    NProgress.start();
                    
					util.post('/role/delete', {roleId: row.id}, result=>{
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
            roleAdd() {
                this.editVisible = true;

				util.clearProperties(this.editForm);
            },
            //显示编辑界面
            handleEdit(row) {
                this.editVisible = true;

                util.copyProperties(this.editForm, row);
            },
            //编辑 or 新增
            editSubmit() {
                var _this = this;

                _this.$refs.editForm.validate((valid) => {
                    if (valid) {
						_this.editLoading = true;
						NProgress.start();
						_this.btnEditText = '提交中';

						var url = '/role/add';
						if (_this.editForm.id) {
							url = '/role/update';
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
								_this.editVisible = false;

								loadData(this);
							} else {
								_this.$message.error(result.msg);
							}
						});
                    }
                });
            },
			openAssignDlg(row) {
				this.moduleVisible = true;
				this.roleId = row.id;
				NProgress.start();

				var _this = this;
				util.get('/module/load/all', {roleId: row.id}, result => {
					NProgress.done();
					if (result.errCode == 0) {
						_this.modules = loadModule(0, result.data.modules);
						_this.hasModules = result.data.hasModules;
					} else {
						_this.$message.error(result.msg);
					}
				});
			},
			moduleSubmit() {
				NProgress.start();
				var checkModules = this.$refs.moduleTree.getCheckedKeys();

				var _this = this;
				util.post('/module/assign', {roleId: _this.roleId, moduleIds: checkModules.join(',')}, result => {
					NProgress.done();
					if (result.errCode == 0) {
						_this.$notify({
							title: '成功',
							message: '分配权限成功',
							type: 'success'
						});
						_this.moduleVisible = false;
					} else {
						_this.$message.error(result.msg);
					}
				});
			},
			renderContent(createElement, node) {
				// 使用vue Render方式生成dom
				return createElement('span', [
					createElement('i', {class: node.data.iconCls}), 
					createElement('span', {style: 'margin-left: 5px'}, node.data.name)
				]);
			}
        }
    }
</script>

<style scoped>
    .toolbar .el-form-item {
        margin-bottom: 10px;
    }
    
    .toolbar {
        background: #fff;
        padding: 10px 10px 0px 10px;
    }

	.toolBtn {
		float: right;
	}
</style>