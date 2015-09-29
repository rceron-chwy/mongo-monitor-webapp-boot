var MongoM = function () {
	
    var initHandler = function(params) {
        Alerts.initAlerts(params);
        Notifications.init(params);   
    };	
    
	
	return {
		
	    init: function(params) {
	    	initHandler(params);
	    }		
		
	};
	
}();