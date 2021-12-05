// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Area Chart Example
var ctx = document.getElementById("myAreaChart");
let data=[10, 13, 15, 18, 21, 25, 29, 31, 35, 38];
let labels = ["2021.11.25", "2021.11.26", "2021.11.27", "2021.11.28", "2021.11.29", "2021.11.30", "2021.12.01", "2021.12.02", "2021.12.03", "2021.12.04"];
let title = "날짜 별 농작물 분석 정보";

var myLineChart = new Chart(ctx, {  
  type: 'line',
  data: {
    label: lables,  
    datasets: [{
      backgroundColor: "rgb(240, 255, 238)",
      borderColor: "rgb(240, 255, 238)",
      pointRadius: 5,
      pointBackgroundColor: "rgb(178, 195, 152)",
      pointBorderColor: "rgb(178, 195, 152)",
      pointHoverRadius: 5,
      pointHoverBackgroundColor: "rgb(124, 175, 82)",
      pointHitRadius: 50,
      pointBorderWidth: 2,
      data: data,
      label:"날짜"
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'date'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: 10
        }
      }],
      yAxes: [{
        ticks: {
          text:"생장률(%)",
          min: 0,
          max: 100,
          maxTicksLimit: 11
        },
        gridLines: {
          color: "rgb(178, 195, 152)",
        }
      }],
    },
    legend: {
      display: false
    }
  }
});
