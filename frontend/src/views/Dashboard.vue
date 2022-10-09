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
        <div class="row">
          <div class="col-lg-6">
            <div class="mb-5"><canvas id="barChart"></canvas></div>
            <div class="my-5"><canvas id="donutChart"></canvas></div>
          </div>
          <div class="col-lg-6">
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
          ratio: 1.75,
          bar: {
            type: 'bar',
            data: {
              labels: ['1','2','3','4','5','6','7'],
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
              labels: ['1','2','3','4','5','6','7'],
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
    methods: {
      myEventHandler() {
        window.innerWidth - 7 > 390 ? this.ratio = 1.75 : this.ratio = 1.5;
      },
    }
}
</script>
<style>
  #dashboard{
      padding: 0 5%;
  }
</style>
