var Notifications = function () {
	
	var lastNotificationId = null;
	var config = {};
	
    var initNotificationsHandler = function() {
    	
    	if(lastNotificationId == null) {
    		$.ajax({
    			type:'GET',
    			url : config.BASE_URL + '/notifications/last',
    			dataType:'json',
    			async: false,  
    			success: function(jsonData, textStatus) {
    				lastNotificationId = jsonData.id;
    			},
    			error:function(xhr,textStatus, errorThrown){
    				//alert(errorThrown);
    			}
        	});	
    		setTimeout(initNotificationsHandler, 5000);
    		
    	}
    	else {
    		$.ajax({
    			type:'GET',
    			url : config.BASE_URL + '/notifications/next/' + lastNotificationId,
    			dataType:'json',
    			async: false,  
    			success: function(jsonData, textStatus) {
    				if(jsonData.notifications != null) {
    					jQuery.each(jsonData.notifications, function(i, val) {
    						$.gritter.add({
    				            // (string | mandatory) the heading of the notification
    				            title: 'Notification:',
    				            // (string | mandatory) the text inside the notification
    				            text: val.text
    				        });    					
    					});
    				}
    				lastNotificationId = jsonData.id;
    				setTimeout(initNotificationsHandler, 5000);
    			},
    			error:function(xhr,textStatus, errorThrown){
    				//alert(errorThrown);
    			}
        	});	    		
    		
    	}
    	
    };	
    
   var initHandler = function(params) {
   	 $.extend(config, params);
   };	    
	
	return {
		
		init: function(params) {
    		initHandler(params);
    		initNotificationsHandler();
    	}	
		
	};
	
}();