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
		var url = "/addToCart/" + id + "/" + name + "/" + quantity + "/" + price + "/" + overallprice + "/product";
		
		window.location.href = url;
	});
	
	
	$('.catproduct').each(function(){
		var th = $(this);
		$(this).find('.add-to-cart-cat').click(function(e){
			e.preventDefault();
			var t = $(this);
			var id = th.find('.product-id-cat').html();
			var quantity = th.find('#quantitycat').val();
			var name = th.find('.name-cat').text();
			var price = th.find('.price-cat').text();
			var overallprice = price * quantity;
			var url = "/addToCart/" + id + "/" + name + "/" + quantity + "/" + price + "/" + overallprice + "/category";
			
			window.location.href = url;
		});
	});
	
	
});

$('.carousel').carousel({
    interval: 5000
});
