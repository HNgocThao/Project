function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#img')
                .attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

$().ready(function(){
	window.onscroll = function() {myFunction()};
	
	var header = document.getElementById("myHeader");
	var sticky = header.offsetTop;
	
	function myFunction() {
	  if (window.pageYOffset > sticky) {
	    header.classList.add("sticky");
	  } else {
	    header.classList.remove("sticky");
	  }
	}
});
	
$().ready(function(){	
	const d = new Date();
let text = d.toLocaleTimeString();
document.getElementById("demo").innerHTML = text;
document.getElementById("demo1").innerHTML = text;
document.getElementById("demo2").innerHTML = text;
document.getElementById("demo3").innerHTML = text;
	
});	
	
	
$().ready(function(){	
	var dataChartProductOrderedByCategory = [];
    var labelChartProductOrderedByCategory = [];
    getListProductOrderedByCategory();
        function getListProductOrderedByCategory() {
            $.ajax({
                url: '/admin/dashboards/ahihi',
                type: 'GET',
                contentType: 'application/json',
                success: function (res) {
                    var data_get = res;
                    for (var i = 0; i < data_get.length; i++) {
                        dataChartProductOrderedByCategory.push(data_get[i].count);
                        labelChartProductOrderedByCategory.push(data_get[i].group);
                    }
                    if ($("#barChart").length) {
                        var barChartCanvastop10 = $("#barChart").get(0).getContext("2d");
                        // This will get the first returned node in the jQuery collection.
                        var barChart = new Chart(barChartCanvastop10, {
                            type: 'bar',
                            data: data,
                            options: doughnutPieOptions
                        });
                    }
                }
            })
        }

        let data = {
            labels: labelChartProductOrderedByCategory,
            datasets: [{
				label: 'Số lượng sản phẩm',
                data: dataChartProductOrderedByCategory,
                backgroundColor: [
                    'rgba(153, 102, 255, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)',
                   	'rgba(255, 99, 132, 0.5)'
                ],
                borderColor: [
			      'rgb(153, 102, 255)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)',
			      'rgb(255, 99, 132)'
			      ],
			    borderWidth: 1
            }],
        };

        let doughnutPieOptions = {
            responsive: true,
            animation: {
                animateScale: true,
                animateRotate: true
            }
        };   
        
    let dataPrices = [];
   let labelPrices = [];
	   
    getAllPrice();    
	function getAllPrice() {
            $.ajax({
                url: '/admin/dashboards/ahihi1',
                type: 'GET',
                contentType: 'application/json',
                success: function (res) {
                    let data_get = res;
                    for (var i = 0; i < data_get.length; i++) {
						dataPrices.push(data_get[i].count);
                        labelPrices.push(data_get[i].group.name);
                        
                    }
                    if ($("#barChart1").length) {
                        var barChartCanvas = $("#barChart1").get(0).getContext("2d");
                        // This will get the first returned node in the jQuery collection.
                        var barChart1 = new Chart(barChartCanvas, {
                            type: 'polarArea',
                            data: data1,
                            options: doughnutPieOptions1
                        });
                    }
                }
            })
        }
		
        let data1 = {
            labels: labelPrices,
            datasets: [{
                data: dataPrices,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.5)',
			      'rgba(255, 159, 64, 0.5)',
			      'rgba(54, 162, 235, 0.5)',
			      'rgba(153, 102, 255, 0.5)',
			      'rgba(75, 192, 192, 0.5)'
                ],
                borderColor: [
			      'rgb(255, 99, 132)',
			      'rgb(255, 159, 64)',
			      'rgb(54, 162, 235)',
			      'rgb(153, 102, 255)',
			      'rgb(75, 192, 192)'
			    ],
			    borderWidth: 1
            }],
        };

        let doughnutPieOptions1 = {
            responsive: true,
            animation: {
                animateScale: true,
                animateRotate: true
            }
        };        
});	
	
$().ready(function(){	
	let chart = new Morris.Bar({
            element: 'chart',
            barColors: ["#FF6384"],
            parseTime: false,
            hideHover: 'auto',
            xkey: 'statisDate',
            ykeys: ['amount'],
            labels: ['Doanh số']
        });

        function char30day() {
            $.ajax({
                url: '/admin/dashboards/ahihi2',
                type: 'GET',
                contentType: 'application/json',
                success: function (data) {
                    chart.setData(data);
                }
            })
        }

        $('#btn-dashboard-filter').click(function () {
            let toDate = $('#datepicker').val();
            let fromDate = $('#datepicker2').val();
            req = {
                toDate: toDate,
                fromDate: fromDate
            }
            let myJson = JSON.stringify(req)
            $.ajax({
                url: '/admin/dashboards/ahihi2',
                type: 'POST',
                data: myJson,
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if(data == null){
                        console.log("ahihi!");
                    }
                    chart.setData(data);
                }
            })
        });

        function init() {
            char30day();
           
        }
        init();
});		