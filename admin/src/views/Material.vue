<template>
	<div>
		<el-alert v-if="error.status"
			v-bind:title="error.title"
			type="error"
			v-bind:description="error.description"
			show-icon
			style = "margin-bottom:10px"
			@close="error.status = false"
			>
		</el-alert>
		<el-dialog
			title="Input Material"
			:visible.sync="dialogVisible"
			size="tiny"
			>
			<el-input placeholder="Nama" v-model="form.nama"></el-input>
			<p></p>
			<el-select v-model="form.merk" filterable allow-create placeholder="Pilih Merk">
				<el-option v-for="item in merks" :key="item.merk" :label="item.merk" :value="item.merk">
				</el-option>
			</el-select>
			<p></p>
			<el-radio-group v-model="form.jenis">
				<el-radio-button label="ONT"></el-radio-button>
			    <el-radio-button label="Roset"></el-radio-button>
				<el-radio-button label="Aksesoris"></el-radio-button>
				<el-radio-button label="Other"></el-radio-button>
			</el-radio-group>
			<p></p>
			<el-select v-model="form.satuan" filterable allow-create placeholder="Pilih Satuan">
				<el-option v-for="item in satuans" :key="item.satuan" :label="item.satuan" :value="item.satuan">
				</el-option>
			</el-select>
			<p></p>
			<!-- <el-input placeholder="Satuan" v-model="form.satuan"></el-input> -->
			<el-input placeholder="Template Qty" v-model="form.template_qty"></el-input>

			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="saveData">Confirm</el-button>
				<el-button @click="dialogVisible = false">Cancel</el-button>
			</span>
		</el-dialog>
		<!-- <h1>{{items}}</h1> -->
		<!-- <el-row type="flex"> -->
			<el-input placeholder="Filter Keyword" v-model="keyword">
				<el-select v-model="selectedfield" placeholder="Filter By" slot="prepend" style="width:180px">
					<el-option
						v-for="item in fields"
						:key="item.fieldname"
						:label="item.caption"
						:value="item.fieldname">
					</el-option>
				</el-select>
				<el-button slot="append" icon="search" @click="onSearchClick">Search Data</el-button>
			</el-input>

		<el-table :data="items"	stripe style="width:100%" border>
			<el-table-column type="expand">
				<template scope="scope">
					<el-button size="small" icon="edit" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
					<el-button size="small" icon="delete" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
				</template>
			</el-table-column>

			<el-table-column
				v-for="item in fields"
				sortable
				:key="item.fieldname"
				:prop="item.fieldname"
				:label="item.caption"
				:width="item.width">
			</el-table-column>
		</el-table>
		<el-row type="flex">
				<el-button size="small" icon="plus" type="primary" @click="loadByID(0)" style="margin-top:10px">Tambah</el-button>

				<span style="margin-left:10px">
					<el-pagination
						@size-change="onSizeChanged"
						@current-change="onPageChanged"
						:page-sizes="pagesizes"
						:page-size="pagesize"
						layout="sizes, prev, pager, next, jumper, total"
						:total="totalrecord"
						style = "margin-top:10px">
					</el-pagination>
				</span>
			<!-- </el-col> -->
		</el-row>


	</div>
</template>

<script>
	import axios from 'axios';
	// var CONFIG = require('../../config.json');

	export default {
		data () {
			return {
				dialogVisible : false,
				items : [],
				fields : [
					{fieldname : 'nama', caption : 'Nama', width: 200},
					{fieldname : 'merk', caption : 'Merk', width: 200},
					{fieldname : 'jenis', caption : 'Jenis', width: 200},
					{fieldname : 'satuan', caption : 'Satuan', width: 200}
				],
				selectedfield : 'nama',
				form : {
					id: 0,
					nama : '',
					merk : '',
					jenis : '',
					satuan : '',
					template_qty : ''
				},
				error : {
					status : false,
					title : 'Error Occured',
					description : 'Error description'
				},
				currentpage : 1,
				totalrecord : 1,
				pagesize : 10,
				pagesizes : [10,20,50,100],
				keyword : '',
				merks : [],
				satuans : []
		  	}

		},
		beforeMount(){
			this.refreshData(true);
			var vm=this;
			axios.get(this.$rest_url + '/merk').then(function(response) {
				if (response.data)	vm.merks = response.data;
			})

			axios.get(this.$rest_url + '/satuan').then(function(response) {
				if (response.data)	vm.satuans = response.data;
			})
		},
		methods:{
			refreshData(reset){
				if (reset) {
					this.currentpage = 1;
				}
				var url = this.$rest_url + '/material/' + this.pagesize + '/' + this.currentpage + '/';
				var vm = this;

				if (this.keyword != '') url += this.selectedfield + '/' + this.keyword;

				axios.get(url)
				.then(function(response) {
					vm.items = response.data.data;
					vm.totalrecord = parseInt(response.data.totalrecord);
				})
				.catch(function(error) {
					vm.showErrorMessage(error);
				});
			},
			onPageChanged(val){
				this.currentpage = val;
				this.refreshData(false);
			},
			onSizeChanged(val){
				this.pagesize = val;
				this.refreshData(false);
			},
			onSearchClick(){
				this.refreshData(true);
			},
			loadByID(id){
				if (id == 0){
					this.form.id = 0;
					this.form.nama = '';
					this.form.merk = '';
					this.form.jenis = '';
					this.form.satuan = '';
					this.form.template_qty = '';
					this.dialogVisible = true;
					return;
				}
				var vm = this;
				axios.get(this.$rest_url + '/material/' + id)
				.then(function(response) {
					vm.form = response.data;
					vm.dialogVisible = true;
				})
				.catch(function(error) {
					vm.showErrorMessage(error);
				});
			},
			saveData(){
				// if (!this.form.area){
				// 	this.showErrorMessage('Area belum dipilih');
				// 	return;
				// }

				// this.form.area_id = this.form.area.id;

				var vm = this;
				axios.post(this.$rest_url + '/material', vm.form)
				.then(function(response) {
					vm.$message('Data berhasil diupdate');
					vm.refreshData(false);
				})
				.catch(function(error) {
					vm.showErrorMessage(error);
				});
				vm.dialogVisible = false;
			},
			showErrorMessage(error){
				this.error.status = true;
				this.error.title = error.message;
				if (error.response!=undefined){
					this.error.description = error.response.data;
				}else{
					this.error.description = error;
				}
			},
			handleEdit(index, item){
				this.loadByID(item.id);
			},
			handleDelete(index, item){
				var vm = this;
				this.$confirm('Anda yakin menghapus data ini?', 'Warning', {
		        	confirmButtonText: 'Ya',
			        cancelButtonText: 'Batal',
			        type: 'warning'
		        }).then(()=>{
					this.deleteData(item);
				}).catch(()=>{
				});
			},
			deleteData(item){
				var id = item.id;
				var vm = this;
				// axios.delete(this.$rest_url + '/material/' + id) //del call blocked by hosting
				axios.post(this.$rest_url + '/delete_material/' + id)
				.then(function(response) {
					vm.$message('Data berhasil dihapus');
					vm.refreshData(false);
				})
				.catch(function(error) {
					vm.showErrorMessage(error);
				});
				vm.dialogVisible = false;
			}
		}
	}
</script>

<style scoped>
	.el-row {
		margin-bottom: 5px;
	}
	.el-input{
		margin-bottom: 10px;
	}
	.el-table{
		font-size: 12px;
	}
</style>
