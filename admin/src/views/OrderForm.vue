<template>
	<div>
		<div style="margin-bottom:30px; padding-bottom:10px; overflow: hidden; border-bottom:1px solid #A9A9A9;">
			<span style="font-size: 18px;color: #8492a6;line-height: 40px">Update Data Order</span>
			<span style="float:right">
			<el-button @click="saveData" type="primary">
				<i class="fa fa-check"/>
				Simpan Data
			</el-button>
			<el-button @click="back()" ><i class="fa fa-times"/> Batal</el-button>
			</span>
		</div>

		<el-alert v-if="error.status"
			v-bind:title="error.title"
			type="error"
			v-bind:description="error.description"
			show-icon
			style = "margin-bottom:10px"
			@close="error.status = false"
			>
		</el-alert>
		<el-form label-position="top" :model="form" ref="form" :rules="rules">

			<el-row>
				<el-col :span="8">
					<el-form-item label="Order No" prop="orderno">
						<el-input v-model="form.orderno" style="width:90%"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="8">
					<el-form-item label="Pilh Area" prop="area">
						<el-select v-model="form.area" filterable placeholder="Select" value-key="id">
							<el-option v-for="item in areas" :key="item.id" :label="item.nama" :value="item">
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="8">
					<el-form-item label="Tanggal" prop="tanggal">
						<el-date-picker
							v-model="form.tanggal"
							placeholder="Pick a Date"
						>
						</el-date-picker>
					</el-form-item>
				</el-col>
			</el-row>


			<el-row>
				<el-col :span="8">
					<el-form-item label="Jenis Order">
						<el-radio-group v-model="form.jenis_order">
							<el-radio-button label="INSTALASI"></el-radio-button>
						    <el-radio-button label="MIGRASI"></el-radio-button>
							<el-radio-button label="SERVICE"></el-radio-button>
						</el-radio-group>
					</el-form-item>
				</el-col>
				<el-col :span="8">
					<el-form-item label="Pilh Product / Layanan" prop="area">
						<el-select v-model="form.product" filterable placeholder="Select" value-key="id">
							<el-option v-for="item in products" :key="item.id" :label="item.nama" :value="item">
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="8">
					<el-form-item label="Buat Customer Baru" prop="area">
						<el-switch	v-model="newcustomer" on-text="" off-text=""></el-switch>
					</el-form-item>
				</el-col>
			</el-row>


			<el-row>
				<el-col :span="8">
					<el-form-item label="Pilh Customer" prop="area" v-if="!newcustomer">
						<el-select v-model="form.customer" filterable placeholder="Select" value-key="id" >
							<el-option v-for="item in customers" :key="item.id" :label="item.nama" :value="item">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="Nama Customer" prop="area" style="width:90%" v-if="newcustomer">
						<el-input placeholder="Nama" v-model="form.customer.nama"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="8">
					<el-form-item label="No HP" prop="area" style="width:90%" >
						<el-input placeholder="No HP" v-model="form.customer.custphone" v-bind:readonly="!newcustomer"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row>
				<el-col :span="8">
					<el-form-item label="Alamat" prop="area" style="width:90%">
						<el-input type="textarea" placeholder="Alamat" v-model="form.customer.alamat" v-bind:readonly="!newcustomer"></el-input>
					</el-form-item>

				</el-col>
				<el-col :span="8">
					<el-form-item label="No KTP" prop="area" style="width:90%">
						<el-input placeholder="No KPTI" v-model="form.customer.ktpno" v-bind:readonly="!newcustomer"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row>
				<el-col :span="8">
					<el-form-item label="No Telepon" prop="phone">
						<el-input v-model="form.customer.phone" style="width:90%"  v-bind:readonly="!newcustomer"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="8">
					<el-form-item label="No Internet" prop="inetnumber">
						<el-input v-model="form.customer.inetnumber" style="width:90%"  v-bind:readonly="!newcustomer"></el-input>
					</el-form-item>
				</el-col>
			</el-row>


			<el-form-item label="Status">
				<el-radio-group v-model="form.status">
					<el-radio-button label="OPEN"></el-radio-button>
				    <el-radio-button label="PROCESS"></el-radio-button>
					<el-radio-button label="CLOSED"></el-radio-button>
					<el-radio-button label="PENDING"></el-radio-button>
					<el-radio-button label="CANCEL"></el-radio-button>
				</el-radio-group>
			</el-form-item>

			<el-form-item label="Teknisi">
				<el-select v-model="form.user" filterable placeholder="Select" value-key="uid" >
					<el-option v-for="item in users" :key="item.nama" :label="item.nama" :value="item">
					</el-option>
				</el-select>
			</el-form-item>

		</el-form>


		<br />


	</div>
</template>

<script>
	import axios from 'axios';

	export default {
		data () {
			return{
				newcustomer : true,
				areas : [],
				products : [],
				users : [],
				customers : [],
				imageUrl: '',
				form : {
					id: 0,
					orderno : '',
					tanggal : '',
					jenis_order : 'INSTALASI',
					status : 'OPEN',
					customer : {
						id : 0,
						nama : '',
						alamat : '',
						custphone : '',
						ktpno : '',
						phone : '',
						inetnumber : ''
					},
					phone : '',
					inetnumber : '',
					product : null,
					area : null,
					user : null
				},
				error : {
					status : false,
					title : 'Error Occured',
					description : 'Error description'
				},
				rules : {
					orderno: [{ required: true, message: 'Please Input Order No' }],
					tanggal: [{ required: true, message: 'Please Input Order Date' }],
				}
			}
		},
		beforeMount(){
			var vm=this;
			axios.get(this.$rest_url + '/area').then(function(response) {
				if (response.data)	vm.areas = response.data;
			})

			axios.get(this.$rest_url + '/product').then(function(response) {
				if (response.data)	vm.products = response.data;
			})

			axios.get(this.$rest_url + '/user').then(function(response) {
				if (response.data)	vm.users = response.data;
			})

			axios.get(this.$rest_url + '/customer').then(function(response) {
				if (response.data)	vm.customers = response.data;
			})

			vm.loadByID(vm.$route.params.id);

		},
		methods:{
			loadByID(id){
				if (id == 0){
					this.newcustomer = true;
					this.form.id = 0;
					this.form.orderno = '';
					this.form.tanggal = new Date();
					this.form.jenis_order = 'INSTALASI';
					this.form.status = 'OPEN';
					this.customer = {
						id : 0,
						nama : '',
						alamat : '',
						custphone : '',
						ktpno : '',
						phone : '',
						inetnumber : ''
					};
					this.phone = '';
					this.inetnumber = '';
					this.product = null;
					this.area = null;
					this.user = null;
					return;
				}

				var vm = this;
				axios.get(this.$rest_url + '/order/' + id).then(function(response) {
					var res = response.data;

					if (!res.customer){
						res.customer = {
							id : 0,
							nama : '',
							alamat : '',
							custphone : '',
							ktpno : '',
							phone : '',
							inetnumber : ''
						};
					}
					if (!res.user){
						res.user = null;
					}

					vm.form = res;

				})
				.catch(function(error) {
					vm.showErrorMessage(error);
				});
			},
			saveData(){
				if (this.form.orderno == ''){
					this.showErrorMessage('No Order kosong');
					return;
				}

				if (!this.form.area){
					this.showErrorMessage('Area belum dipilih');
					return;
				}
				if (!this.form.product){
					this.showErrorMessage('Product belum dipilih');
					return;
				}
				this.form.area_id = this.form.area.id;
				this.form.product_id = this.form.product.id;
				if (this.form.customer) {
					this.form.customer_id = this.form.customer.id;
					this.form.customer.area_id = this.form.area_id;
				}
				if (this.form.user) this.form.user_id = this.form.user.id;

				// this.$refs.form.validate((valid) => {
				// 	if (valid) {
				var vm = this;
				axios.post(this.$rest_url + '/order', vm.form)
				.then(function(response) {
					vm.$message('Data berhasil diupdate');
					vm.back();
				})
				.catch(function(error) {
					vm.showErrorMessage(error);
				});
				// 	}
				// });
			},
			back(){
				this.$router.push({
				    path: '/order'
				})
			},
			showErrorMessage(error){
				this.error.status = true;
				this.error.title = error.message;
				if (error.response!=undefined){
					this.error.description = error.response.data;
				}else{
					this.error.description = error;
				}
			}

		}
	}

</script>

<style scoped>
	/*.el-form {
		width: 650px;
	}*/
	/*.el-form-item {
		margin-bottom: 20px;*/
	/*}*/
	/*.el-form-item{
		margin-bottom: 10px;
	}*/
	/*.productform{
		margin-bottom: 20px;
		margin-left: 150px;
	}*/
</style>


<!-- var vm = new Vue({
  data: {
    a: 1
  }
})
// `vm.a` is now reactive

vm.b = 2
// `vm.b` is NOT reactive -->
