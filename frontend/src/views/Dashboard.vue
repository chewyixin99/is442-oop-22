<template>
    <div id="dashboard">
        <h1 class="pt-4 mb-4 flex-column d-flex align-items-center">Dashboard</h1>
        <div class="row justify-content-center">
          <hr />
          <div id="selectHolder" class="d-flex">
            <select v-model="timeRange" class="form-select" aria-label="Default select example" @change="refreshCharts()">
              <option value=0 selected disabled>Filter by Time Range</option>
              <option value=1>Every 2 Weeks</option>
              <option value=2>Every Month</option>
              <option value=3>Every 6 Months</option>
            </select>
          </div>
          <input id="exportButton" type="button" class="btn btn-info" value='Export' @click="exportStatistics()"/>

        </div>
        <div class="row mt-5">
          <div class="col-lg-6">
            <div id="donutHolder" class="mb-5 chart-container"><canvas id="donutChart"></canvas></div>
            <div id="barHolder" class="my-5 chart-container"><canvas id="barChart"></canvas></div>
          </div>
          <div class="col-lg-6">
            <div id="polarHolder" class="mb-5 chart-container"><canvas id="polarChart"></canvas></div>
            <div id="lineHolder" class="my-5 chart-container"><canvas id="lineChart"></canvas></div>
          </div>
        </div>
      </div>
</template>
<script>
import Chart from 'chart.js'
import DashboardService from "@/api/services/DashboardService";
import * as XLSX from 'xlsx/xlsx.mjs';
import LoanService from '@/api/services/LoanService';

export default {
    name: 'Dashboard',
    components: {
    },
    data(){
        return {
          allLoans: [],
          charts: ["donut", "bar", "polar", "line"],
          timeRange: 0,
          fullDates: [],
          ratio: 1.5,
          donut: {
            type: 'doughnut',
            data: {
              labels: ['Available','On Loan', 'Overdue'],
              datasets: [{
                backgroundColor: ['rgb(255, 205, 86)', 'rgb(75, 192, 192)', 'rgb(255, 99, 132)'],
                hoverOffset: 1,
                borderWidth: 2
              }],
            },
            options: {
              title: { display: true, text: "Percentage of Passes Overdued", fontSize: 18 }  
            }
          },
          bar: {
            type: 'bar',
            data: {
              labels: [],
              datasets: [{
                data: [0, 0, 0, 0, 0, 0],
                backgroundColor: [
                  'rgba(255, 99, 132, 0.2)',
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(255, 205, 86, 0.2)',
                  'rgba(75, 192, 192, 0.2)',
                  'rgba(54, 162, 235, 0.2)',
                  'rgba(153, 102, 255, 0.2)',
                  'rgba(201, 203, 207, 0.2)'
                ],
                borderColor: [
                  'rgb(255, 99, 132)',
                  'rgb(255, 159, 64)',
                  'rgb(255, 205, 86)',
                  'rgb(75, 192, 192)',
                  'rgb(54, 162, 235)',
                  'rgb(153, 102, 255)',
                  'rgb(201, 203, 207)'
                ],
                borderWidth: 1
              }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            //https://stackoverflow.com/questions/37922518/how-to-set-start-value-as-0-in-chartjs
                            beginAtZero: true,
                            stepSize: 1
                        }
                    }]
                },
                legend : { display: false },
                title: { display: true, text: "No. of Past Loans by Time Range", fontSize: 18}   
            }
          },
          polar: {
            type: 'polarArea',
            data: {
              labels: ["Gardens By The Bay", "Singapore Zoo", "Sea Aquarium"],
              datasets: [{
                data: [0, 0, 0],
                backgroundColor: ['rgba(102, 51, 255, 0.5)', 'rgb(75, 192, 255)', 'rgb(255, 153, 51)'],
                borderColor: 'rgba(255 , 255, 255, 0)'
              }]
            },
            options: {
                title: { display: true, text: "No. of Loans by Attractions", fontSize: 18, fontStyle: 'bold' },
                startAngle: -0.75 * Math.PI
            }
          },
          line : {
            type: 'line',
            data: {
              labels: [],
              datasets: [{
                label: 'Gardens By The Bay',
                data: [0, 0, 0, 0, 0, 0],
                fill: false,
                borderColor: 'rgb(102, 51, 255)',
                tension: 0.1
              },
              {
                label: 'Singapore Zoo',
                data: [0, 0, 0, 0, 0, 0],
                fill: false,
                borderColor: 'rgb(75, 192, 255)',
                tension: 0.1
              },
              {
                label: 'Sea Aquarium',
                data: [0, 0, 0, 0, 0, 0],
                fill: false,
                borderColor: 'rgb(255, 153, 51)',
                tension: 0.1
              },]
            },
            options: {
                title: { display: true, text: "Type of Passes Loaned by Time Range", fontSize: 18 },
                scales: {
                    yAxes: [{
                        ticks: { stepSize: 1 }
                    }]
                },   
            }
          }
        }
    },
    mounted(){
      this.initCharts();

    },
    methods: {
      myEventHandler() {
        if (window.innerWidth - 7 >= 428){ 
          //https://www.chartjs.org/docs/2.9.4/general/responsive.html
          for (let c of this.charts){ this[c].options.maintainAspectRatio = true; }
          this.ratio = 1.5;
        } else {
          for (let c of this.charts){ this[c].options.maintainAspectRatio = false; }
          this.ratio = 1.4;
        }
      },
      refreshCharts(){
        for (let c of this.charts){
          document.getElementById(c + 'Chart').remove();
          document.getElementById(c + 'Holder').innerHTML += `<canvas id="${c}Chart"></canvas>`;
        }

        this.bar.data.labels = [];
        this.line.data.labels = [];
        this.fullDates = [];
        this.bar.data.datasets[0].data = [0,0,0,0,0,0];
        this.polar.data.datasets[0].data = []  
        for (let type of this.line.data.datasets){ type.data = [0, 0, 0, 0, 0, 0]; }

        this.initCharts();
      },  
      async initCharts() {
        this.initChartLabels();
        let loans = await LoanService.getAllLoans();
        let poi = await DashboardService.getPoiBreakDown();
        let passBreakdown = await DashboardService.getPassBreakDown();
        this.allLoans = loans;

        await this.donutChartCalculation(passBreakdown);
        this.lineChartCalculation(loans);
        this.polarChartCalculation(poi);
        this.barChartCalculation(loans);

        this.myEventHandler();
        for (let c of this.charts){ new Chart(document.getElementById(c + "Chart"), this[c]) }
      },
      initChartLabels(){
        let timeRangeOptions = [7, 14, 1, 6]; //7days (default), 14days, 1 month, 6 months
        let today = new Date();
        let newDate;
        let fullDate;
        let localeNewDate;

        let increment = timeRangeOptions[this.timeRange];
        Date.prototype.addDays = function(days) {
          let date = new Date(this.valueOf());
          date.setDate(date.getDate() - days);
          return date;
        }

        for (let i=0; i < 6; i++){
          if (this.timeRange == 0 || this.timeRange == 1){
            //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/toLocaleString
            newDate = today.addDays(increment); 
            localeNewDate = newDate.toLocaleString('en-GB', { day: 'numeric', month: 'numeric' });
            increment += timeRangeOptions[this.timeRange];

          } else if (this.timeRange == 2 || this.timeRange == 3) {
            let year = today.getFullYear();
            let month = today.getMonth();

            if (i){ //don't minus first month/6months from current month if filter by month/6monthly
              newDate = new Date(year, month - increment, 1)
              increment += timeRangeOptions[this.timeRange];
            } else {
              newDate = new Date(year, month, 1);
            }
            localeNewDate = newDate.toLocaleString('default', { year: 'numeric', month: 'short' });

          } else {
            throw Error("Error with getting time range option");
          } 
          fullDate = newDate.toLocaleString('en-GB', {day: 'numeric', month: 'numeric' , year: 'numeric'});

          this.bar.data.labels.unshift(localeNewDate);
          this.line.data.labels.unshift(localeNewDate);
          this.fullDates.unshift(fullDate);
        }
      },
      donutChartCalculation(passBreakDown){
        var that = this;

        that.donut.data.datasets[0].data = passBreakDown ? Object.values(passBreakDown) : [1, 0, 0];
        this.donut.plugins = [{
          beforeDraw: function(chart) {
            var width = chart.chart.width,
                height = chart.chart.height,
                ctx = chart.chart.ctx;

            ctx.restore();
            var fontSize = (height / 114).toFixed(2);
            ctx.font = fontSize + "em sans-serif";
            ctx.textBaseline = "middle";

            let donutData = that.donut.data.datasets[0].data;
            let sum = donutData.reduce((partialSum, a) => partialSum + a, 0);
            var text = (donutData[0] / sum * 100).toFixed(0) + '%';
            
            var textX = Math.round((width - ctx.measureText(text).width) / 2);
            var textY = height / that.ratio;

            ctx.fillText(text, textX, textY);
            ctx.save();
          }
        }];
      },
      barChartCalculation(loans){
        for (let l of loans){ this.insertIntoCorrectTimeFrame(l, this.bar.data.datasets[0]); }
      },
      polarChartCalculation(poi){
        poi.sort((a, b) => a.passId - b.passId);
      
        let polarChartData = {};
        for (let p of poi){ polarChartData[p.poi] = p.numLoans}

        let polarChartKeys = Object.keys(polarChartData);
        let polarChartValues = polarChartKeys.map((key) => { return polarChartData[key]; });
        this.polar.data.datasets[0].data = polarChartValues.length > 0 ? polarChartValues : this.polar.data.datasets[0].data;
      },
      lineChartCalculation(loans){
        for (let l of loans){
          var location = this.line.data.datasets.filter(x => x.label == l.pass.poi)[0];
          this.insertIntoCorrectTimeFrame(l, location);
        }
      },
      insertIntoCorrectTimeFrame(l, input){
        const [year, month, day] = l.startDate.split('-');
        let record = new Date(+year, +month - 1, +day);

        for (let d = this.fullDates.length - 1; d >= 0; d--){
          const [day1, month1, year1] = this.fullDates[d].split('/');
          let chartJsDate = new Date(+year1, +month1 - 1, +day1);
          if (record >= chartJsDate){ 
            input.data[d] += 1;
            break;
          }
        } 
      },
      exportStatistics(){ //https://stackoverflow.com/questions/39825069/node-xlsx-for-write-excel-file
        //Can be customized accordingly when more requirements are needed
        let workBook = XLSX.utils.book_new();

        let loansData = [];
        for (var loans of this.allLoans){
          loansData.push({
            "Loan Id": loans.loanId,
            "Pass Id": loans.pass.passId,
            "GOP Id": loans.gopId,
            "User Id": loans.user.userId,
            "Email": loans.user.email,
            "Contact No.": loans.user.contactNumber,
            "Guests": loans.pass.numGuests,
            "Pass Number": loans.pass.passNumber,
            "Place of Interest": loans.pass.poi,
            "Start Date": loans.startDate,
            "End Date": loans.endDate,
            "URL": loans.pass.poiUrl,
          });
        }

        const allLoans = XLSX.utils.json_to_sheet(loansData);
        allLoans['!cols'] = [ {wch:6}, {wch:6}, {wch:6}, {wch:6}, {wch:15}, {wch:8}, {wch:12}, {wch:12}, {wch:20}, {wch:10}, {wch:10}, {wch:50}]; //https://stackoverflow.com/questions/24395693/how-to-set-cell-width-when-export-xlsx-files-with-js-xlsx
        XLSX.utils.book_append_sheet(workBook, allLoans, `All Loans`);

        let poiData = [
          this.polar.data.labels, this.polar.data.datasets[0].data,  //row 1
          [], ["No. of past loans by time range"].concat(this.bar.data.labels), [].concat(this.bar.data.datasets[0].data), //row 2
          [], ["Type of passes loaned by time range"].concat(this.line.data.labels), ["Gardens By The Bay"].concat(this.line.data.datasets[0].data), ["Singapore Zoo"].concat(this.line.data.datasets[1].data) , ["Sea Aquarium"].concat(this.line.data.datasets[2].data)  //row 3
        ]
        const poiBreakdown = XLSX.utils.aoa_to_sheet(poiData);
        poiBreakdown['!cols'] = this.fitToColumn(poiData);
        XLSX.utils.book_append_sheet(workBook, poiBreakdown, `POI Breakdown`);

        let passData = [this.donut.data.labels, this.donut.data.datasets[0].data];
        const passBreakdown = XLSX.utils.aoa_to_sheet(passData);
        passBreakdown['!cols'] = this.fitToColumn(passData);
        XLSX.utils.book_append_sheet(workBook, passBreakdown, `Pass Breakdown`);

        let exportFileName = `Dashboard Analytics.xlsx`;
        XLSX.writeFile(workBook, exportFileName);
      },
      fitToColumn(arrayOfArray) {
          // get maximum character of each column
          return arrayOfArray[0].map((a, i) => ({ wch: Math.max(...arrayOfArray.map(a2 => a2[i] ? a2[i].toString().length : 0)) }));
      }
    }
}
</script>
<style>
  #dashboard{
      padding: 0 5%;
  }

  #selectHolder{
    width: 45%;
  }

  #exportButton{
    width:75px;
  }

  .chart-container {
    position: relative;
    margin: auto;
    height: 250px;
  }

  @media only screen and (min-width: 767px){
    #selectHolder{
      width: 250px;
    }

    .chart-container {
      height: auto;
    }
  }


</style>
