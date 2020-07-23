<template>
	<div>
		<!-- 图标选择插件 -->
		<el-select v-model="iconCls" placeholder="请选择" @change="changeValue">
			<el-option-group v-for="group in iconGroup" :label="group.label">
				<el-option v-for="icon in group.icons" :label="group.prefix + icon + group.suffix" :value="group.prefix + icon + group.suffix">
					<span style="float: left">{{ group.prefix + icon + group.suffix }}</span>
					<span style="float: right"><i :class="group.prefix + icon + group.suffix"/></span>
				</el-option>
			</el-option-group>
		</el-select>
		<i :class="iconCls"/>
	</div>
</template>
<script>
    export default {
		data() {
			return {
				iconCls: this.value,
				iconGroup: [{
					label: 'element',
					prefix: 'el-icon-',
					suffix: '',
					icons: ['upload','error','success','warning','sort-down','sort-up','arrow-left','circle-plus',
							'circle-plus-outline','arrow-down','arrow-right','arrow-up','back','circle-close','date',
							'circle-close-outline','caret-left','caret-bottom','caret-top','caret-right','close',
							'd-arrow-left','check','delete','d-arrow-right','document','d-caret','edit-outline',
							'download','goods','search','info','message','edit','location','loading','location-outline',
							'menu','minus','bell','mobile-phone','news','more','more-outline','phone','phone-outline',
							'picture','picture-outline','plus','printer','rank','refresh','question','remove','share',
							'star-on','setting','circle-check','service','sold-out','remove-outline','star-off',
							'circle-check-outline','tickets','sort','zoom-in','time','view','upload2','zoom-out']
				}, {
					label: 'font-awesome',
					prefix: 'fa ',
					suffix: ' fa-fw',
					icons: ['fa-id-card','fa-calendar','fa-calculator','fa-cogs',
							'fa-desktop','fa-user','fa-windows','fa-th-list','fa-undo','fa-save',
							'fa-spinner','fa-refresh','fa-user-circle-o','fa-trash','fa-envelope',
							'fa-download','fa-code']
				}],
			}
		},
		props: ['value'],
		methods: {
			changeValue(val) {
				this.$emit('input', val);
			}
		},
		watch: {
			value(val) {
				this.iconCls = val;
			}
		}
    }
</script>