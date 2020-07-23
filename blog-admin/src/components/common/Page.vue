<template>
    <!--分页插件-->
    <el-col :span="24" class="toolbar" style="padding-bottom:10px;">
        <el-pagination :current-page="1" :page-sizes="[10, 20, 30, 50]" :page-size="pageSize"
                       :total="total" @size-change="sizeChange" @current-change="currentChange"
                       style="float:right" layout="total, sizes, prev, pager, next"/>
    </el-col>
</template>
<script>
    export default {
        data() {
            return {}
        },
        props: ['queryMod', 'pageSize', 'total'],
        methods: {
            sizeChange(pageSize) {
                this.queryMod.pageSize = pageSize;
                this.queryMod.pageNum = 1;

                this.$emit('pagination', this.queryMod);
            },
            currentChange(pageNum) {
                this.queryMod.pageNum = pageNum;

                this.$emit('pagination', this.queryMod);
            }
        }
    }
</script>