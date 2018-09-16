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
			title="Input Area"
			:visible.sync="dialogVisible"
			size="tiny"
			>
			<el-input placeholder="Nama" v-model="form.nama"></el-input>
			<el-input type="textarea" placeholder="Alamat" v-model="form.alamat"></el-input>
			<el-input placeholder="No HP" v-model="form.custphone"></el-input>
			<el-input placeholder="No. KTP" v-model="form.ktpno"></el-input>
			<el-input placeholder="No Telepon" v-model="form.phone"></el-input>
			<el-input placeholder="No Internet" v-model="form.inetnumber"></el-input>
			<p></p>
			<!-- <el-input placeholder="Role" v-model="form.role"></el-input> -->
			<!-- <el-form-item label="Category"> -->
				<el-select v-model="form.area" filterable placeholder="Pilih Area" value-key="id">
					<el-option v-for="item in areas" :key="item.id" :label="item.nama" :value="item">
					</el-option>
				</el-select>
			<!-- </el-form-item> -->


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
					{fieldname : 'alamat', caption : 'Alamat', width: null},
					{fieldname : 'custphone', caption : 'No HP', width: 120},
					{fieldname : 'phone', caption : 'No Telp', width: 120},
					{fieldname : 'inetnumber', caption : 'No Internet', width: 140},
					{fieldname : 'area', caption : 'Area', width: 140},
				],
				selectedfield : 'nama',
				form : {
					id: 0,
					nama : '',
					alamat : '',
					custphone : '',
					phone : '',
					inetnumber : '',
					ktpno : '',
					role : '',
					area : null
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
				areas : []
		  	}

		},
		beforeMount(){
			this.refreshData(true);
			var vm=this;
			axios.get(this.$rest_url + '/area').then(function(response) {
				if (response.data)	vm.areas = response.data;
			})
		},
		methods:{
			refreshData(reset){
				if (reset) {
					this.currentpage = 1;
				}
				var url = this.$rest_url + '/customer/' + this.pagesize + '/' + this.currentpage + '/';
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
					this.form.alamat = '';
					this.form.custphone = '';
					this.form.phone = '';
					this.form.inetnumber = '';
					this.form.ktpno = '';
					this.form.area = null;
					this.dialogVisible = true;
					return;
				}
				var vm = this;
				axios.get(this.$rest_url + '/customer/' + id)
				.then(function(response) {
					vm.form = response.data;
					vm.dialogVisible = true;
				})
				.catch(function(error) {
					vm.showErrorMessage(error);
				});
			},
			saveData(){
				if (!this.form.area){
					this.showErrorMessage('Area belum dipilih');
					return;
				}

				this.form.area_id = this.form.area.id;

				var vm = this;
				axios.post(this.$rest_url + '/customer', vm.form)
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
				// axios.delete(this.$rest_url + '/customer/' + id) //del call blocked by hosting
				axios.post(this.$rest_url + '/delete_customer/' + id)
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
