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
			$('.owl-carousel').owlCarousel({
				loop:true,
				margin:30,
				dots:false,
				
//				autoplay:true,
//    			autoplayTimeout:2000,
				responsive:{
					0:{
						items:1
					},
					600:{
						items:3
					},
					1000:{
						items:5
				
					}
				}
			})
		});

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
	
	var curDate = new Date();
      
    // Ngày hiện tại
    var curDay = curDate.getDate();
 
    // Tháng hiện tại
    var curMonth = curDate.getMonth() + 1;
      
    // Năm hiện tại
    var curYear = curDate.getFullYear();
 
    // Gán vào thẻ HTML
    document.getElementById('current-time').innerHTML = curDay + "/" + curMonth + "/" + curYear;

	});

