<template>
	<div id ="aa">
		<el-alert v-if="error.status"
			v-bind:title="error.title"
			type="error"
			v-bind:description="error.description"
			show-icon
			style = "margin-bottom:10px"
			@close="error.status = false"
			>
		</el-alert>
		<el-row>
			<h4>Pilih Periode Report</h4>
		</el-row>
		<el-row type="flex" :gutter="20">

			<el-col :span="0">
				<el-date-picker
					v-model="startDate"
					type="date"
					placeholder="Pilih Tanggal Awal"
				>
				</el-date-picker>
			</el-col>
			<el-col :span="0">
				<p>s/d</p>
			</el-col>
			<el-col :span="0">
				<el-date-picker
					v-model="endDate"
					type="date"
					placeholder="Pilih Tanggal Awal"
				>
				</el-date-picker>
			</el-col>
			<el-col :span="0">
				<el-button size="large" type="primary" @click="refreshData">Download PDF</el-button>
			</el-col>
			<!-- <el-col :span="0">
				<el-button size="large" type="primary" @click="printData">Download PDF</el-button>
			</el-col> -->
		</el-row>

		<!-- <div id ="tablereport">

			<table v-bind:style="{fontSize: '10px' }" >
				<thead>
					<tr>
						<th v-for="col in fields">
							{{col.caption}}
						</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="row in items">
						<td v-for="col in fields">
							{{ row[col.fieldname] }}
						</td>
					</tr>
				</tbody>


			</table>
		</div> -->

	</div>
</template>

<script>
	import axios from 'axios';

	var jsPDF = require('jspdf');
	require('jspdf-autotable');

	var CONFIG = require('../../config.json');
	// import jsPDF from 'jspdf';
	// import autoTable from 'jspdf-autotable';

	export default {
		data () {
			return {
				dialogVisible : false,
				startDate :null,
				endDate : null,
				rows : [],
				columns : [
					{dataKey : 'orderno', title : 'Order No', width: 120},
					{dataKey : 'tanggal', title : 'Tanggal', width: 120},
					{dataKey : 'product', title : 'Layanan', width: 120},
					{dataKey : 'jenis_order', title : 'Jenis', width: 140},
					{dataKey : 'status', title : 'Status', width: 120},
					{dataKey : 'customer', title : 'Customer', width: 200},
					{dataKey : 'alamat', title : 'Alamat', width: 200},
					{dataKey : 'phone', title : 'No Telp', width: 120},
					{dataKey : 'inetnumber', title : 'No Internet', width: 120},
					{dataKey : 'teknisi', title : 'Teknisi', width: 120},
					{dataKey : 'area', title : 'Area', width: 120},
					{dataKey : 'list_bahan', title : 'List Bahan', width: 120},
				],
				error : {
					status : false,
					title : 'Error Occured',
					description : 'Error description'
				},
				textdata : ''
		  	}

		},
		beforeMount(){
			var date = new Date();
			this.startDate =new Date(date.getFullYear()-1, date.getMonth(), 1);
			this.endDate =new Date(date.getFullYear(), date.getMonth() + 1, 0);
			// this.refreshData(true);
		},
		methods:{
			refreshData(){
				var date1 = this.startDate.toISOString().slice(0,10);
				var date2 = this.endDate.toISOString().slice(0,10);

				var url = this.$rest_url + '/reportorder/'
						+ date1 + '/' + date2 ;
				var vm = this;

				axios.get(url)
				.then(function(response) {
					vm.rows = response.data;
					vm.printData();
				})
				.catch(function(error) {
					console.log(error);
					vm.showErrorMessage(error);
				});
			},
			printData(){
				var header = 'Report Order Periode ' + this.formatedStartDate + ' s/d ' + this.formatedEndDate;

				var doc = new jsPDF('p', 'pt');
				// console.log(this.columns);
				doc.autoTable(this.columns, this.rows,{
				    // styles: {fillColor: [100, 255, 255]},
					styles : {
						cellPadding: 5, // a number, array or object (see margin below)
					    fontSize: 5,
					    font: "helvetica", // helvetica, times, courier
					    // lineColor: 200,
					    // lineWidth: 0,
					    fontStyle: 'normal', // normal, bold, italic, bolditalic
					    overflow: 'ellipsize', // visible, hidden, ellipsize or linebreak
					    // fillColor: false, // false for transparent or a color as described below
					    // textColor: 20,
					    // halign: 'left', // left, center, right
					    // valign: 'middle', // top, middle, bottom
					    columnWidth: 'auto' // 'auto', 'wrap' or a number
					},
				    margin: {left: 10, top: 50},
				    addPageContent: function(data) {
						doc.setFont("helvetica");
						doc.setFontSize(9);
				    	doc.text(header, 10, 30);
				    }
				});
				doc.save(header + '.pdf');
			},
		},
		computed: {
		    formatedStartDate: function () {
		    	return this.startDate.getDate() + "/" + this.startDate.getMonth()+1 +  "/"  + this.startDate.getFullYear();
		  	},
			formatedEndDate: function () {
  				return this.endDate.getDate() + "/" + this.endDate.getMonth()+1 +  "/"  + this.endDate.getFullYear();
	  		},
	  	}
	}
</script>

<style scoped>
	.el-row {
		margin-bottom: 5px;
	}
	.el-input {
		margin-bottom: 10px;
	}
	.el-table{
		font-size: 12px;
	}

	/* .el-table__header{
		font-size: 22px !important;
		background-color: #bdbdbd !important;
	} */

</style>
