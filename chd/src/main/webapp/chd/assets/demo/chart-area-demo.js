// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';


// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ["2021.11.23", "2021.11.24", "2021.11.25", "2021.11.26", "2021.11.27", "2021.11.28", "2021.11.29", "2021.11.30", "2021.12.01", "2021.12.02"],
    datasets: [{
      label: ["날짜", "생장률", "조도"],
      backgroundColor: "rgba(2,117,216,0.2)",
      borderColor: "rgba(2,117,216,1)",
      pointRadius: 5,
      pointBackgroundColor: "rgba(2,117,216,1)",
      pointBorderColor: "rgba(255,255,255,0.8)",
      pointHoverRadius: 5,
      pointHoverBackgroundColor: "rgba(2,117,216,1)",
      pointHitRadius: 50,
      pointBorderWidth: 2,
      data: [32, 35, 40, 42, 50, 55, 58, 63, 68, 75],
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
          color: "rgba(86, 143, 215, .125)",
        }
      }],
    },
    legend: {
      display: false
    }
  }
});
