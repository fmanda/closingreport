<template>
	<dx-chart
      id="chart"
      palette="Harmony Light"
      title="Rekap Order By Area"
      :data-source="dataSource"
      :common-series-settings="commonSeriesSettings"
      :series="series"
      :legend="legend"
  >
  </dx-chart>
</template>

<script>
import { DxChart } from 'devextreme-vue';
import axios from 'axios';

export default {
  data() {
    return {
			dataSource : [],
      commonSeriesSettings: {
				argumentField: "nama",
					type: "bar",
					hoverMode: "allArgumentPoints",
					selectionMode: "allArgumentPoints",
					label: {
							visible: true,
							format: {
									type: "fixedPoint",
									precision: 0
							}
					}
      },
      series: [
				{ valueField: "total_order", name: "Total Order" },
        { valueField: "total_closed", name: "Total Closed" },
        { valueField: "total_cancel", name: "Total Cancel" }
			],
      legend: {
          verticalAlignment: "bottom",
          horizontalAlignment: "center"
      },
			"export": {
          enabled: true
      },
    }
  },
  components: {
    DxChart
  },
	beforeMount(){
		var vm=this;
		axios.get(this.$rest_url + '/orderdashboard').then(function(response) {
			if (response.data){
				for (item in vm.dataSource){
					item.total_order = parseFloat(item.total_order);
					item.total_closed = parseFloat(item.total_closed);
					item.total_cancel = parseFloat(item.total_cancel);					
				}
				vm.dataSource = response.data;
			}
		})
	}
};
</script>
