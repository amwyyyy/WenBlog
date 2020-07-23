<template>
    <div id="loginApp">
        <router-view></router-view>
    </div>
</template>
<script>
    export default {
        name: 'loginApp',
        components: {}
    }
</script>
<style>
    @import "../../node_modules/font-awesome/css/font-awesome.min.css";

    body {
        margin: 0px;
        padding: 0px;
        background: #1F2D3D;
        font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
        font-weight: 400;
        -webkit-font-smoothing: antialiased;
    }

    #loginApp {
        position: absolute;
        top: 0px;
        bottom: 0px;
        width: 100%;
        background: url(../assets/bg2.jpg) center no-repeat;
        background-size: cover;
    }
</style>