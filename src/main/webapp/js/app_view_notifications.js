var ViewNotifications = function () {
	
	var page = 1;
	var config = {};
	
    var initViewNotificationsHandler = function() {
  
		$.ajax({
			type:'GET',
			url : config.BASE_URL + '/view/notifications/page/' + page,
			dataType:'json',
			async: false,  
			success: function(jsonData, textStatus) {
				lastNotificationId = jsonData.id;
			},
			error:function(xhr,textStatus, errorThrown){
				//alert(errorThrown);
			}
    	});	  		

    	
    };	
    
   var initHandler = function(params) {
   	 $.extend(config, params);
   };	    
	
	return {
		
		init: function(params) {
    		initHandler(params);
    		initViewNotificationsHandler();
    	}	
		
	};
	
}();