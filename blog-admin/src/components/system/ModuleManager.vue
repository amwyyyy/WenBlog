<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="queryMod">
				<el-form-item>
					<el-input v-model="queryMod.name" placeholder="菜单名" icon="search" :on-icon-click="search"/>
				</el-form-item>

				<el-form-item class="toolBtn">
					<el-button @click="addMenu()" type="primary" icon="plus">新增菜单</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="tableData" stripe v-loading="listLoading" style="width: 100%;" @expand="loadSubMenu" @row-dblclick="edit" border>
				<el-table-column type="expand" width="50">
					<template scope="props">
						<el-table :data="subData[props.row.id]" stripe v-loading="listLoading" style="width: 100%" @expand="loadFunction" @row-dblclick="edit" :show-header="false">
							<el-table-column type="expand" width="50">
								<template scope="fun">
									<el-table :data="funData[fun.row.id]" stripe v-loading="listLoading" style="width: 100%" @row-dblclick="edit" :show-header="false">
										<el-table-column label="菜单名" width="200">
											<template scope="fun1">
												<span style="margin-left: 25px">{{ fun1.row.name }}</span>			
											</template>
										</el-table-column>
										<el-table-column prop="type" label="类型" :formatter="typeFormatter" width="100"/>
										<el-table-column prop="component" label="资源路径" width="250"/>
										<el-table-column prop="path" label="URL" width="150"/>
										<el-table-column prop="orderNum" label="排序" width="80"/>
										<el-table-column prop="remark" label="备注" width="250"/>

										<el-table-column inline-template :context="_self" label="操作" min-width="250">
											<span>
												<el-button :plain="true" type="info" size="small" @click="edit(row)">编辑</el-button>
												<el-button :plain="true" type="danger" size="small" @click="del(row)">删除</el-button>
											</span>
										</el-table-column>
									</el-table>			
								</template>
							</el-table-column>
							<el-table-column label="菜单名" width="200">
								<template scope="sub">
									<i :class="sub.row.iconCls"/>
									<span style="margin-left: 10px">{{ sub.row.name }}</span>			
								</template>
							</el-table-column>
							<el-table-column prop="type" label="类型" :formatter="typeFormatter" width="100"/>
							<el-table-column prop="component" label="资源路径" width="250"/>
							<el-table-column prop="path" label="URL" width="150"/>
							<el-table-column prop="orderNum" label="排序" width="80"/>
							<el-table-column prop="remark" label="备注" width="250"/>

							<el-table-column inline-template :context="_self" label="操作" min-width="250">
								<span>
									<el-button :plain="true" type="info" size="small" @click="edit(row)">编辑</el-button>
									<el-button :plain="true" type="danger" size="small" @click="del(row)">删除</el-button>
									<el-button :plain="true" type="info" size="small" @click="addFunction(row)">新增功能</el-button>
								</span>
							</el-table-column>
						</el-table>
					</template>
				</el-table-column>
				<el-table-column label="菜单名" width="250">
					<template scope="scope">
						<i :class="scope.row.iconCls"/>
						<span style="margin-left: 10px">{{ scope.row.name }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="type" label="类型" :formatter="typeFormatter" width="100"/>
				<el-table-column prop="component" label="资源路径" width="250"/>
				<el-table-column prop="path" label="URL" width="150"/>
				<el-table-column prop="orderNum" label="排序" width="80"/>
				<el-table-column prop="remark" label="备注" width="250"/>

				<el-table-column inline-template :context="_self" label="操作" min-width="250">
					<span>
						<el-button :plain="true" type="info" size="small" @click="edit(row)">编辑</el-button>
						<el-button :plain="true" type="danger" size="small" @click="del(row)">删除</el-button>
						<el-button :plain="true" type="info" size="small" @click="addSubMenu(row)">新增子菜单</el-button>
					</span>
				</el-table-column>
			</el-table>
		</template>

		<!--编辑弹框-->
		<el-dialog :title="editForm.id ? '编辑' : '新增'" v-model="editVisible" size="tiny">
			<el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
				<el-form-item label="菜单名" prop="name">
					<el-input v-model="editForm.name"/>
				</el-form-item>
				<el-form-item label="类型">
					<el-select v-model="editForm.type" placeholder="请选择" disabled>
						<el-option v-for="item in options" :label="item.label" :value="item.value"/>
					</el-select>
				</el-form-item>
				<el-form-item label="资源路径" prop="component" v-if="editForm.type == 2">
					<el-input v-model="editForm.component"/>
				</el-form-item>
				<el-form-item label="URL" prop="path" v-if="editForm.type != 1">
					<el-input v-model="editForm.path"/>
				</el-form-item>
				<el-form-item label="排序" prop="orderNum">
					<el-input v-model.number="editForm.orderNum"/>
				</el-form-item>
				<el-form-item label="图标" v-if="editForm.type != 3">
					<icon-select v-model="editForm.iconCls"/>
				</el-form-item>
				<el-form-item label="备注" prop="remark">
					<el-input v-model="editForm.remark"/>
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
		util.get('/module/load/menu', _this.queryMod, result => {
			if (result.errCode == 0) {
				_this.tableData = result.data;
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
					type: 1,
					parentId: 0
                },
				tableData: [],
				subData: [],
				funData: [],

                editVisible: false, //编辑界面显是否显示
                //编辑界面数据,(里面的变量要先定义，不然会有不好的效果)
                editForm: {
					id: null,
					name: '',
					type: 1,
					component: '',
					path: '',
					orderNum: null,
					iconCls: '',
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
                },
				options: [{label: '一级菜单', value: 1}, {label: '二级菜单', value: 2}, {label: '功能', value: 3}]
			}
        },
		mounted() {
			loadData(this);
        },
		components: {
			IconSelect
		},
        methods: {
			search(params) {
				loadData(this);
			},
			loadSubMenu(row, expanded) {
				var _this = this;

				if (expanded) {
					_this.listLoading = true;
					util.get('/module/load/menu', {type: 2, parentId: row.id}, result => {
						if (result.errCode == 0) {
							_this.subData[row.id] = result.data;
						} else {
							_this.$message.error(result.msg);
						}
						_this.listLoading = false;
					});
				}
			},
			loadFunction(row, expanded) {
				var _this = this;

				if (expanded) {
					_this.listLoading = true;
					util.get('/module/load/menu', {type: 3, parentId: row.id}, result => {
						if (result.errCode == 0) {
							_this.funData[row.id] = result.data;
						} else {
							_this.$message.error(result.msg);
						}
						_this.listLoading = false;
					});
				}
			},
            //删除记录
            del(row) {
                var _this = this;
                this.$confirm('确认删除该记录吗?', '提示', { type: 'warning' }).then(() => {
                    _this.listLoading = true;
                    NProgress.start();
                    
					util.post('/module/delete', {moduleId: row.id}, result=>{
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
			typeFormatter(row, column) {
				if (row.type == 1) {
					return "一级菜单";
				} else if (row.type == 2) {
					return "二级菜单";
				} else if (row.type == 3) {
					return '功能';
				}
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

				this.editForm.type = 1;
				this.editForm.pid = 0;

				this.editVisible = true;
			},
			addSubMenu(row) {
				util.clearProperties(this.editForm);

				this.editForm.pid = row.id;
				this.editForm.type = 2;

				this.editVisible = true;
			},
            //编辑提交
            submit() {
                var _this = this;

                _this.$refs.editForm.validate((valid) => {
                    if (valid) {
						_this.editLoading = true;
						NProgress.start();

						var url = '/module/add';
						if (_this.editForm.id) {
							url = '/module/update';
						}

						util.post(url, _this.editForm, result => {
							_this.editLoading = false;
							NProgress.done();

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
			addFunction(row) {
				util.clearProperties(this.editForm);

				this.editForm.pid = row.id;
				this.editForm.type = 3;

				this.editVisible = true;
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