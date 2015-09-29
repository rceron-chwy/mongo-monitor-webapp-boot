var Mail = function () {

	var lastAlertId = null;
	var config = {};
	
    var initHandler = function(params) {
    	$.extend(config, params);
    	
    	$("#btnTestMail").click(function() {
        	$("#btnTestMail").text("sending email...");
    		setTimeout(testMail, 100);
    	});
   };	
	
    var testMail = function() {
    	$.ajax({
			type:'GET',
			url : config.BASE_URL + '/settings/email/test',
			dataType:'json',
			async: false,  
			success: function(jsonData, textStatus) {
				if(jsonData.status == 'FAILED') {
					$('.modal-body').html('Error while trying to send email:<br><br>' + jsonData.message);
				}
				else {
					$('.modal-body').text(jsonData.message);
				}
				$('#myModal').modal('show');
				$("#btnTestMail").text("Send Test Mail");
			},
			error:function(xhr,textStatus, errorThrown){
				//alert(errorThrown);
			}
    	});
    	
    };	    
	
	return {

    	init: function(params) {
    	
    		initHandler(params);
    	},		
		
	    testMail: function(params) {
	    	testMail();
	    }	
		
	};
}();