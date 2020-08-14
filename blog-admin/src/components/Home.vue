<template>
    <el-container>
        <el-aside>
            <el-menu class="el-menu-vertical" default-active="/main" unique-opened router background-color="#2C3F53"
                     text-color="#F7F7F7" :collapse="isCollapse">
                <el-menu-item index="/">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">您好<span style="color:#20a0ff;font-size:20px">{{ user.username }}</span></span>
                </el-menu-item>
                <template v-for="(item, index) in $router.options.routes" v-if="!item.hidden">
                    <el-submenu :index="index+''" v-if="!item.leaf && item.children.length > 0">
                        <i :class="item.iconCls"></i>
                        <span slot="title">{{ item.name }}</span>
                        <el-menu-item v-for="child in item.children" :index="child.path" v-if="!child.hidden">
                            <i :class="child.iconCls"></i>
                            <span slot="title">{{ child.name }}</span>
                        </el-menu-item>
                    </el-submenu>
                    <el-menu-item v-if="item.leaf && !item.hidden" :index="item.children[0].path">
                        <i :class="item.children[0].iconCls"></i>
                        <span slot="title">{{ item.children[0].name }}</span>
                    </el-menu-item>
                </template>
            </el-menu>
        </el-aside>

        <el-container style="width: 100px">
            <el-header>
                <el-col :span="1">
                    <i class="fa fa-bars fa-lg" style="cursor: pointer;" @click="setCollapse"></i>
                </el-col>

                <el-col :span="20">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/main' }">控制台</el-breadcrumb-item>
                        <el-breadcrumb-item v-if="currentPathNameParent!='' && $route.fullPath!='/main'">
                            {{ currentPathNameParent }}
                        </el-breadcrumb-item>
                        <el-breadcrumb-item v-if="currentPathName!='' && $route.fullPath!='/main'">
                            {{ currentPathName }}
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>

                <el-col :span="3" style="text-align: right;">
                    <el-tooltip class="tip" effect="dark" content="消息通知" placement="bottom">
                        <i class="fa fa-envelope-o" aria-hidden="true" @click="userInfo"></i>
                    </el-tooltip>

                    <el-dropdown>
                        <span class="el-dropdown-link tip">
                            <i class="fa fa-user" aria-hidden="true"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item><span @click="userInfo">个人信息</span></el-dropdown-item>
                            <el-dropdown-item><span @click="updatePwd">更改密码</span></el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>

                    <el-tooltip class="tip" effect="dark" content="退出" placement="bottom">
                        <i class="fa fa-sign-out" aria-hidden="true" @click="logout"></i>
                    </el-tooltip>
                </el-col>
            </el-header>

            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import util from "../common/util";

    export default {
        data() {
            return {
                currentPathName: this.$route.name,
                currentPathNameParent: this.$route.matched[0].name,
                user: {
                    username: "",
                    email: "",
                    header: ""
                },
                isCollapse: false
            };
        },
        watch: {
            $route(to) {
                //监听路由改变
                this.currentPathName = to.name;
                this.currentPathNameParent = to.matched[0].name;
            }
        },
        mounted() {
            let _this = this;
            util.get("/index/user", null, result => {
                if (result.errCode === 0) {
                    _this.user = result.data;
                } else {
                    _this.$message.error(result.msg);
                }
            });
        },
        methods: {
            //退出登录
            logout() {
                this.$confirm("确认退出吗?", "提示", {})
                    .then(() => {
                        util.post("/index/logout", null, () => {
                            window.location.href = "./login.html";
                        });
                    }).catch(() => {});
            },
            // 查看用户信息
            userInfo() {
                window.location.href = "./#/user_info";
            },
            setCollapse() {
                this.isCollapse = !this.isCollapse;
            },
            updatePwd() {
                window.location.href = "./#/update_pwd";
            }
        }
    };
</script>
<style>
    .el-menu-vertical:not(.el-menu--collapse) {
        border-right: 1px solid #475669;
        border-top: 1px solid #475669;
        width: 220px;
    }

    .el-menu--collapse {
        border-right: 1px solid #475669;
        border-top: 1px solid #475669;
    }

    .tip {
        margin-left: 20px;
        cursor: pointer;
    }

    .admin {
        color: #c0ccda;
        text-align: center;
        font-size: 15px;
        margin-top: 20px;
        margin-bottom: 0px;
        height: 40px;
    }

    .el-header {
        color: #333;
        text-align: left;
        font-size: 15px;
        background-color: #F7F7F7;
        height: 60px;
        padding-top: 20px
    }

    .el-container {
        height: 100%;
        background-color: #EDEDED;
    }

    .el-aside {
        background-color: #2C3F53;
        width: auto !important;
        overflow: visible;
    }

    .el-col {
        box-sizing: border-box;
    }
</style>