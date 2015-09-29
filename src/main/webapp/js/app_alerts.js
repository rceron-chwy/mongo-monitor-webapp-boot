var Alerts = function () {

	var lastAlertId = null;
	var config = {};
	
    var initAlertsHandler = function() {
    	
    	if(lastAlertId == null) {
    		//get last id and start displaying alerts after this id
    		
    	}
    	else {
    		
    	}
    };	
	
    var initHandler = function(params) {
      	 $.extend(config, params);
      };	    
	
	return {

	    initAlerts: function(params) {
    		initHandler(params);
	    	initAlertsHandler();
	    }	
		
	};
}();