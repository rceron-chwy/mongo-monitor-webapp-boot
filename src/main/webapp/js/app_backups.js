var Backups = function () {
	
	var config = {};
	
    var onServerChange = function() {
    	$( "#serverSelect" ).change(function() {
    		$.ajax({
    			type:'GET',
    			url : config.BASE_URL + '/backups/dbs/' + $(this).val(),
    			dataType:'json',
    			async: false,  
    			success: function(jsonData, textStatus) {
    				$( "#my_multi_select1" ).find('option').remove();
    				
    				$(jsonData).each( function(i) {
    					$("#my_multi_select1")
    						.append($("<option></option>")
    						.attr("value", jsonData[i].name)
    						.text(jsonData[i].name));
    				});
    				$('#my_multi_select1').multiSelect("refresh");
    				
    			},
    			error: function(xhr,textStatus, errorThrown) {
    				//alert(errorThrown);
    			}
        	});	
    	});
    };
    
    return {
    	
    	init: function(params) {
    		$.extend(config, params);
    		onServerChange();
    	}
    };         
	
}();