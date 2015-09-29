var Forms = function () {
	
    var multiselect = function() {
    	if($('#my_multi_select1').length > 0) {
    		$('#my_multi_select1').multiSelect();
    	}
    	if($('#my_multi_select2').length > 0) {
    		$('#my_multi_select2').multiSelect();
    	} 
    	if($('#my_multi_select3').length > 0) {
    		$('#my_multi_select3').multiSelect();
    	}
    };
    
    return {
    	
    	init: function(params) {
    		multiselect();
    	}
    };         
	
}();