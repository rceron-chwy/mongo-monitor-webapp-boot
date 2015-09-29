var Servers = function () {
	
	var oldKey = null, config = {};
	
	
    var handleValidation = function() {
        $("#form_sample_1").validate(); 
    };
    
    var startMonitorHandler = function(button, serverId) {
    	$.ajax({
			type:'GET',
			url : config.BASE_URL + '/server/monitor/start/' + serverId ,
			dataType:'json',
			async: false,  
			//data : { "serverId": serverId },
			success: function(jsonData, textStatus) {
				$( "#" + button ).removeClass( "btn-default" );
		    	$( "#" + button ).addClass( "btn-primary dropdown-toggle" );
		    	$( "#" + button ).html('&nbsp;  started &nbsp;&nbsp;&nbsp;<span class="caret"></span>');
			},
			error:function(xhr,textStatus, errorThrown){
				alert(errorThrown);
			}
    	});		
    	
    }; 
    
    var stopMonitorHandler = function(button, serverId) {
    	$.ajax({
			type:'GET',
			url : config.BASE_URL + '/server/monitor/stop/' + serverId,
			dataType:'json',
			async: false,  
			//data : { "serverId": serverId },
			success: function(jsonData, textStatus) {
		    	$( "#" + button ).removeClass( "btn-primary" );
		    	$( "#" + button ).addClass( "btn-default dropdown-toggle" );
		    	$( "#" + button ).html('&nbsp;  stopped &nbsp;&nbsp;<span class="caret"></span>');
			},
			error:function(xhr,textStatus, errorThrown){
				alert(errorThrown);
			}
    	});	    	
    	
    }; 
    
    var changeUpdateMethod = function(obj) {
    	if(obj.value ==  'AGENT') {
    		$("#secureGroup").hide();
    		
    		if(oldKey == null || oldKey.length == 0) {
		    	$.ajax({
					type:'GET',
					url : 'key',
					dataType:'json',
					async: false,
					success: function(jsonData, textStatus) {
				    	$('#keyGroup').show();
				    	$('#keyInput').val(jsonData.message);
					},
					error:function(xhr,textStatus, errorThrown){
						alert(jsonData);
					}
		    	});	    	
    		}
	    	else {
	    		$('#keyGroup').show();
		    	$('#keyInput').val(oldKey);
	    	}   
    	}
    	else {
    		if(obj.value ==  'HTTP') {
    			$("#secureGroup").show();
    		}
    		else {
    			$("#secureGroup").hide();
    		}
    		$('#keyGroup').hide();
    		oldKey = $('#keyInput').val();
    		$('#keyInput').val();
    	}
    };    
    
    copyToClipboardHandler = function(obj) {
    	alert('The agent key was copied to the clipboard.');
    };
    
    var initHandler = function(params) {
    	 $.extend(config, params);
    };	
    
    return {
    	
    	init: function(params) {
    		initHandler(params);
    		handleValidation();
    	},

        validateForm: function () {
        	handleValidation();         
        },
    
	    handleSwitch: function () {
	    	handleBootstrapSwitch();         
	    },
    
	    onMethodUpdateChange: function (obj) {
	    	changeUpdateMethod(obj);        
	    },
    
	    startMonitor: function (button, serverId) {
	    	startMonitorHandler(button, serverId);        
	    },
    
	    stopMonitor: function (button, serverId) {
	    	stopMonitorHandler(button, serverId);        
	    },
	    
	    copyToClipboard: function() {
	    	copyToClipboardHandler();
	    }
    };         
	
}();