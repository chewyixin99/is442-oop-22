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
          <div class="w-25">
            <select v-model="timeRange" class="form-select" aria-label="Default select example" @change="refreshCharts()">
              <option value=0 selected disabled>Filter by Time Range</option>
              <option value=1>Every 2 Weeks</option>
              <option value=2>Every Month</option>
              <option value=3>Every 6 Months</option>
            </select>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-6">
            <div id="barHolder" class="mb-5"><canvas id="barChart"></canvas></div>
            <div id="donutHolder" class="my-5"><canvas id="donutChart"></canvas></div>
          </div>
          <div id="lineHolder" class="col-lg-6">
            <canvas id="lineChart"></canvas>
          </div>
        </div>
    </div>
</template>
<script>
import Chart from 'chart.js'

export default {
    name: 'Dashboard',
    components: {

    },
    data(){
        return {
          timeRange: 0,
          ratio: 1.75,
          bar: {
            type: 'bar',
            data: {
              labels: [],
              datasets: [{
                label: 'Progress',
                data: [65, 59, 80, 81, 56, 55, 40],
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
                }
            }
          },
          donut: {
            type: 'doughnut',
            data: {
              labels: [
                'Overdue',
                'Booked',
                'Available'
              ],
              datasets: [{
                label: 'My First Dataset',
                data: [300, 50, 100],
                backgroundColor: [
                  'rgb(255, 99, 132)',
                  'rgb(75, 192, 192)',
                  'rgb(255, 205, 86)'
                ],
                hoverOffset: 1,
                borderWidth: 2
              }],
            }
          },
          line : {
            type: 'line',
            data: {
              labels: [],
              datasets: [{
                label: 'My First Dataset',
                data: [65, 59, 80, 81, 56, 55, 40],
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
              }]
            }
          }
        }
    },
    mounted(){
      this.initCharts();
      // let month = date.getMonth();

      // console.log(month);
      // console.log(newDate);

    },
    methods: {
      myEventHandler() {
        window.innerWidth - 7 > 390 ? this.ratio = 1.75 : this.ratio = 1.5;
      },
      refreshCharts(){
        document.getElementById('barChart').remove();
        document.getElementById('donutChart').remove();
        document.getElementById('lineChart').remove();

        document.getElementById('barHolder').innerHTML += '<canvas id="barChart"></canvas>';
        document.getElementById('donutHolder').innerHTML += '<canvas id="donutChart"></canvas>';
        document.getElementById('lineHolder').innerHTML += '<canvas id="lineChart"></canvas>';

        this.bar.data.labels = [];
        this.line.data.labels = [];

        this.initCharts();
      },  
      initCharts() {
        this.initChartLabels();
        var that = this;
        this.donut.plugins = [{
          beforeDraw: function(chart) {
            var width = chart.chart.width,
                height = chart.chart.height,
                ctx = chart.chart.ctx;

            ctx.restore();
            var fontSize = (height / 114).toFixed(2);
            ctx.font = fontSize + "em sans-serif";
            ctx.textBaseline = "middle";

            // var text = (that.complete / that.total * 100).toFixed(0);
            // text = that.total == 0 ? 0 + '%': text + '%';
            var text = "0%";
            var textX = Math.round((width - ctx.measureText(text).width) / 2);
            var textY = height / that.ratio;

            ctx.fillText(text, textX, textY);
            ctx.save();
          }
        }];
        this.myEventHandler();
        new Chart(document.getElementById("barChart"), this.bar);
        new Chart(document.getElementById("donutChart"), this.donut);
        new Chart(document.getElementById("lineChart"), this.line);
      },
      initChartLabels(){
        let timeRangeOptions = [7, 14, 1, 6]; //7days (default), 14days, 1 month, 6 months
        let today = new Date();
        let newDate;

        let daysToIncrement = timeRangeOptions[this.timeRange];
        Date.prototype.addDays = function(days) {
          let date = new Date(this.valueOf());
          date.setDate(date.getDate() + days);
          return date;
        }

        for (let i=0; i <= 6; i++){
          if (this.timeRange == 0 || this.timeRange == 1){
            //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/toLocaleString
            newDate = today.addDays(daysToIncrement).toLocaleString('en-GB', { day: 'numeric', month: 'numeric' }); 
            daysToIncrement += timeRangeOptions[this.timeRange];
          } else if ((this.timeRange == 2 || this.timeRange == 3)) {
            //https://stackoverflow.com/questions/5645058/how-to-add-months-to-a-date-in-javascript
            newDate = new Date(today.setMonth(today.getMonth() + timeRangeOptions[this.timeRange])).toLocaleString('default', { year: 'numeric', month: 'short' });
          } else {
            throw Error("Error with getting time range option");
          }

          this.bar.data.labels.push(newDate);
          this.line.data.labels.push(newDate);
        }
      }
    }
}
</script>
<style>
  #dashboard{
      padding: 0 5%;
  }
</style>
