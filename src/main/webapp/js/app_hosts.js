var Hosts = function () {

	var lastAlertId = null;
	var config = {};
	
    var initTimeAgo = function() {
        $("td.timeago").timeago();
    };	
	
    var initHandler = function(params) {
      	 $.extend(config, params);
      };	    
	
	return {

	    init: function(params) {
    		initTimeAgo();
	    }	
		
	};
}();