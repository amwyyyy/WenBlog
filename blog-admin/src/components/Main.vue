<template>
    <section>
        <el-row>
            <el-col :span="10" :offset="0">
                <div id="main" :style="{width:'600px',height:'400px'}"></div>
            </el-col>

            <el-col :span="10" :offset="0">
                <div id="main2" :style="{width:'600px',height:'400px'}"></div>
            </el-col>
        </el-row>
        <el-row>
            
        </el-row>
    </section>
</template>

<script>
    import echarts from 'echarts/lib/echarts';
    import 'echarts/lib/chart/bar';
    import 'echarts/lib/chart/pie';
    import 'echarts/lib/component/tooltip';
    import 'echarts/lib/component/title';

    export default {
        data() {
            return {}
        },
        methods: {},
        mounted() {
            var myChart = echarts.init(document.getElementById('main'));
            myChart.setOption({
                title: {text: '销售统计'},
                tooltip: {},
                xAxis: {
                    data: ["华为", "小米", "苹果", "联想", "魅族", "中兴"]
                },
                yAxis: {},
                series: [{
                    name: '销量',
                    type: 'bar',
                    data: [5, 20, 36, 10, 10, 20],
                    itemStyle: {
                        normal: {
                            color: function (params) {
                                var colorList = ['#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B', '#FE8463'];
                                return colorList[params.dataIndex];
                            }
                        }
                    }
                }]
            });

            var myChart2 = echarts.init(document.getElementById('main2'));
            myChart2.setOption({
                title: {text: '来源统计'},
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        data: [
                            {value: 400, name: '搜索引擎'},
                            {value: 335, name: '直接访问'},
                            {value: 310, name: '邮件营销'},
                            {value: 274, name: '联盟广告'},
                            {value: 235, name: '视频广告'}
                        ]
                    }
                ]
            })
        }
    }
</script>

<style>
    .text {
        font-size: 14px;
    }

    .item {
        padding: 10px 20px;
    }

    .box-card {
        width: 400px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }
</style>