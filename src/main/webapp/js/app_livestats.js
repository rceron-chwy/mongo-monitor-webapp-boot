var LiveStats = function (console) {
	
	var oldKey = null, config = {};
	var currentConn, availableConn;
	var ops = [];
	var operationsChart, queuesChart, faultsChart, locksChart, networkChart, connChart, memoryChart, nonMappedChart, assertsChart;
    
    var initHandler = function(params) {
    	 $.extend(config, params);
    };	
    
    var initiateCharts = function() {
    	
    	operationsChart = Morris.Line({
            element: 'operationsChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['insert', 'query', 'update', 'delete'],
            labels: ['Inserts', 'Queries', 'Updates', 'Deletes'],
            lineColors: ['#6ccac9', '#57c8f2', '#f8d347', '#ff6c60'],
        });    
    	
    	queuesChart = Morris.Line({
            element: 'queuesChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['ar', 'aw', 'qr', 'qw'],
            labels: ['Active readers', 'Active writes', 'Queued readers', 'Queued writers'],
            lineColors: ['#1ABC9C', '#16A085', '#E67E22', '#D35400'],
          });   
    	
    	faultsChart = Morris.Line({
            element: 'faultsChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['faults', 'flushes'],
            labels: ['Page Faults', 'Flushes'],
            lineColors: ['#6ccac9', '#57c8f2'],
          }); 
        
    	locksChart = Morris.Line({
            element: 'locksChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['locked', 'misses'],
            labels: ['DB Locked %', 'Index Miss %'],
            lineColors: ['#ff6c60', '#57c8f2'],
          }); 
        
        networkChart = Morris.Line({
            element: 'networkChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['bytesIn', 'bytesOut'],
            labels: ['Bytes In', 'Bytes Out'],
            yLabelFormat: function(y) {
            	return getNet(y);
            },
            lineColors: ['#6ccac9', '#57c8f2'],
          });
        
        connChart = Morris.Line({
            element: 'connChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['conn', 'available'],
            labels: ['Current', 'Available'],
            lineColors: [ '#57c8f2', '#6ccac9'],
          });    
        
        memoryChart = Morris.Line({
            element: 'memoryChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['mapped', 'res', 'vsize'],
            labels: ['Mapped', 'Resident', 'Virtal'],
            yLabelFormat: function(y) {
            	return getMemory(y);
            },
            lineColors: ['#6ccac9', '#4a8bc2', '#f8d347'],
          });
        
        nonMappedChart = Morris.Area({
            element: 'nonMappedChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['nonmapped'],
            labels: ['Non Mapped'],
            yLabelFormat: function(y) {
            	return getMemory(y);
            },
            lineColors: ['#4a8bc2'],
          });   
        
        assertsChart = Morris.Line({
            element: 'assertsChart',
            data: ops,
            hideHover: 'auto',
            lineWidth: 2,
            xkey: 'period',
            smooth: true,
            ykeys: ['regular', 'warning', 'msg', 'user'],
            labels: ['Regular', 'Warning', 'Msg', 'User'],
            lineColors: ['#6ccac9', '#57c8f2', '#f8d347', '#ff6c60'],
          });
    };
    
    var collectData = function() {
    	$.ajax({
			type:'GET',
			url : config.BASE_URL + '/stats/list/' + config.SERVER_ID,
			dataType:'json',
			async: false,  
			success: function(jsonData, textStatus) {
				opcounters(jsonData.stats);
			},
			error:function(xhr,textStatus, errorThrown){
				//alert(errorThrown);
			}
    	});	
    	setTimeout(collectData, config.UPDATE_INTERVAL * 1000);
    };
    
    var startStats = function() {
    	initiateCharts();
    	setTimeout(collectData, config.UPDATE_INTERVAL * 1000);
    	//updateInterval = setInterval(collectData, config.UPDATE_INTERVAL * 1000);
    };
    
    var opcounters = function(data) {
    	//console.log(data);
    	//console.log(last.created);
    	
    	$("#insertsCount").html(data[0].insert);
    	$("#queriesCount").html(data[0].query);
    	$("#updatesCount").html(data[0].update);
    	$("#deletesCount").html(data[0]["delete"]);
    	
    	ops = [];
    	for (var i = data.length-1; i >= 0; i--) {
    		ops.push({
    			period: data[i].created, 
    			insert: data[i].insert, 
    			query: data[i].query, 
    			update: data[i].update,
    			"delete": data[i]["delete"],
    			command: data[i].command, 
    			getmore: data[i].getmore, 
    			faults: data[i].faults, 
    			flushes: data[i].flushes,  
    			locked: data[i].locked, 
    			misses: data[i].misses, 
    			ar: data[i].ar, 
    			aw: data[i].aw, 
    			qr: data[i].qr, 
    			qw: data[i].qw, 
    			available: data[i].available, 
    			conn: data[i].conn,
    			bytesIn: data[i].bytesIn,
    			bytesOut: data[i].bytesOut,
    			nonmapped: data[i].nonmapped,
    			mapped: data[i].mapped,
    			res: data[i].res,
    			vsize: data[i].vsize,
    			regular: data[i].regular,
    			warning: data[i].warning,
    			msg: data[i].msg,
    			user: data[i].user
    		});
    	} 

    	operationsChart.setData(ops);
    	queuesChart.setData(ops);
    	faultsChart.setData(ops);
    	locksChart.setData(ops);
    	networkChart.setData(ops);
    	connChart.setData(ops);
    	memoryChart.setData(ops);
    	nonMappedChart.setData(ops);
    	assertsChart.setData(ops);
        appendRow(data[0]);
    };
    
    var appendRow = function(data) {
        console.log("in callback"); // to confirm we have reached here
        rowCount = $('#mongostat tbody tr').length;
        if(rowCount >= 10) {
            $('#mongostat tbody tr:first').fadeOut(300, function() {
                $('#mongostat tbody tr:first').remove();
                newRow = $(makeRow(data)).hide();
                $('#mongostat tbody').append(newRow);
                newRow.fadeIn(300);
            });        	
        }
        else {
        	newRow = $(makeRow(data)).hide();
            $('#mongostat tbody').append(newRow);
            newRow.fadeIn(300);
        }
    };   
    
    var makeRow = function(data) {
    	var row = '<tr>' +
    		'<td>' + data.insert + '</td>' +
    		'<td>' + data.query + '</td>' +
    		'<td>' + data.update + '</td>' +
    		'<td>' + data["delete"] + '</td>' +
    		'<td>' + data.getmore + '</td>' +
    		'<td class="hidden-xs">' + data.command + '</td>' +
    		'<td class="hidden-xs">' + data.flushes + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + getMemory(data.mapped) + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + getMemory(data.vsize) + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + getMemory(data.res) + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + data.faults + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + data.locked + '%</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + data.misses + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + data.qr + '|' + data.qw + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + data.ar + '|' + data.aw + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + getNet(data.bytesIn) + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + getNet(data.bytesOut) + '</td>' +
    		'<td class="hidden-xs hidden-sm hidden-md">' + data.conn + '</td>' +
    		'<td class="hidden-xs">' + data.time + '</td>' +
    		'</tr>';
    	return row;
    };
    
    var getMemory = function(memory) {
    	var unit = 'm';
    	if(memory > 1024) {
    		unit = 'g';
    		memory /= 1024;
    	}
    	
    	if(memory > 1000) {
    		return memory+unit;
    	}
    	
    	var num = new Number(memory);
    	return num.toPrecision(3)+unit;
    };
     
    
    var getNet = function(bytes) {
    	var div = 1000;
    	var unit = 'b';
    	if ( bytes >= div ) {
            unit = "k";
            bytes /= div;
        }

        if ( bytes >= div ) {
            unit = "m";
            bytes /= div;
        }

        if ( bytes >= div ) {
            unit = "g";
            bytes /= div;
        } 
        return parseInt(bytes) + unit;
    };
    
    
    //TODO: a modal ta funcionando de boa agora mas ainda tem q melhorar.
    //O legal seria parar o timer, aplicar os estilo no selectedChart pra height e width e reiniciar o timer
    //pois o chart tem um lag pra aplicar o width e height
    var zoom = function() {
    
    	var zoomChartModal 		= $('#zoomChart');
    	var selectedChartDiv    = $(this).closest('div');
    	var selectedChartPanel 	= selectedChartDiv.find(".panel-body");
    	var selectedChartName   = selectedChartDiv.attr('class').split(/[ ,]+/)[1];
    	var selectedChart 		= $('#'+selectedChartName);
    	var selectedChartClone  = selectedChart.clone(); 
    	
    	//move chart to Modal 
    	zoomChartModal.empty();
    	selectedChart.addClass("zoomIn");
    	selectedChart.appendTo(zoomChartModal);
    	
    	//TODO: perhaps add some image on the live-stats page while the chart is in the modal..
    	// currently adding a cloned static html..
    	selectedChartPanel.html(selectedChartClone);
    	
    	//TODO: map titles per chart
    	$('#chartsModalTitle').html(selectedChartName);
    	
    	$('#chartsModal').modal('show').on('hidden.bs.modal', function () {
    		//put chart back to where it belongs
    		selectedChartPanel.empty();
    		selectedChart.removeClass("zoomIn");
    		selectedChart.appendTo(selectedChartPanel);
    	});
    };
    
    var info = function(chart, title) {
    	console.log("INFO");
    	/*
    	$('#infoModal').modal('show');
    	$('#infoModalTitle').html(title);
    	*/
    };    
    
    return {
    	init: function(params) {
    		initHandler(params);
    		startStats();
    		
    		$(".zoom").click(zoom);
    		$(".info").click(info);
    	},
    };         
	
}(window.console || {});