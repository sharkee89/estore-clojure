/**
 * 
 */

$(document).ready(function(){
	$('#quantity').on('change', function(){
		var quantity = $(this).val();
		$('.input-quantity').html(quantity);
	});
	
	$('.add-to-cart').click(function(e){
		e.preventDefault();
		var id = $('.product-id').text();
		var quantity = $('#quantity').val();
		var name = $('.name').text();
		var price = $('.price').text();
		var overallprice = price * quantity;
		var url = "/addToCart/" + id + "/" + name + "/" + quantity + "/" + price + "/" + overallprice;
		
		window.location.href = url;
	});
});