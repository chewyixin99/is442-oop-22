<template>
    <div id="dashboard">
        <h1>Dashboard</h1>
        ------------------------------------
        <p>Admin functionalities:</p>
        ------------------------------------
        <p>1. Chart to show number of loans per month</p>
        <p>2. Chart to show number of borrowers per month</p>
        <p>3. Search function to show a specific employee's loan history - monthly, 6monthly, anually</p>
        <p>4. Chart to show  number of loans per pass per month - optional</p>
        <div class="row justify-content-center ">
          <div id="selectHolder">
            <select v-model="timeRange" class="form-select" aria-label="Default select example" @change="refreshCharts()">
              <option value=0 selected disabled>Filter by Time Range</option>
              <option value=1>Every 2 Weeks</option>
              <option value=2>Every Month</option>
              <option value=3>Every 6 Months</option>
            </select>
          </div>
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

export default {
    name: 'Dashboard',
    components: {

    },
    data(){
        return {
          charts: ["donut", "bar", "polar", "line"],
          timeRange: 0,
          fullDates: [],
          ratio: 1.5,
          donut: {
            type: 'doughnut',
            data: {
              labels: ['Available','On Loan', 'Overdue' ],
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
                            beginAtZero: true
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
                label: 'Fort Canning',
                data: [0, 0, 0, 0, 0, 0],
                fill: false,
                borderColor: 'rgb(102, 51, 255)',
                tension: 0.1
              },
              {
                label: 'Jurong Bird Park',
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
        if (window.innerWidth - 7 > 390){ 
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
        let loans = await DashboardService.getLoansForMonth();
        let passBreakdown = await DashboardService.getPassBreakDown();

        await this.donutChartCalculation(passBreakdown);
        this.lineChartCalculation(loans);
        this.polarChartCalculation(loans);
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
      polarChartCalculation(loans){
        let polarChartData = {};
        for (let l of loans){ l.poi in polarChartData ? polarChartData[l.poi] += 1 : polarChartData[l.poi] = 1;}

        let polarChartKeys = Object.keys(polarChartData);
        this.polar.data.labels = polarChartKeys;
        this.polar.data.datasets[0].data = polarChartKeys.map((key) => { return polarChartData[key]; });
      },
      lineChartCalculation(loans){
        for (let l of loans){
          var location = this.line.data.datasets.filter(x => x.label == l.poi)[0];
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
      }
    }
}
</script>
<style>
  #dashboard{
      padding: 0 5%;
  }

  #selectHolder{
    width: 100%;
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
