<%@ include file="/WEB-INF/pages/includes/header.jsp" %>

<%@ include file="/WEB-INF/pages/includes/top_bar.jsp" %>

<%@ include file="/WEB-INF/pages/includes/menus.jsp" %>	
	
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
          
          	<%@ include file="/WEB-INF/pages/includes/page_header.jsp" %>        
          
             <div class="row state-overview">
                  <div class="col-lg-3 col-sm-6">
                      <section class="panel">
                          <div class="symbol terques"><i class="fa fa-arrow-circle-o-right"></i></div>
                          <div class="value"><h1 class="count"><div id="insertsCount">0</div></h1><p>Inserts</p></div>
                      </section>
                  </div>
                  <div class="col-lg-3 col-sm-6">
                      <section class="panel">
                          <div class="symbol blue"><i class="fa fa-search"></i></div>
                          <div class="value"><h1 class="count2"><div id="queriesCount">0</div></h1><p>Queries</p></div>
                      </section>
                  </div>
                  <div class="col-lg-3 col-sm-6">
                      <section class="panel">
                          <div class="symbol yellow"><i class="fa fa-retweet"></i></div>
                          <div class="value"><h1 class="count3"><div id="updatesCount">0</div></h1><p>Updates</p></div>
                      </section>
                  </div>
                  <div class="col-lg-3 col-sm-6">
                      <section class="panel">
                          <div class="symbol red"><i class="fa fa-times-circle-o"></i></div>
                          <div class="value"><h1 class="count4"><div id="deletesCount">0</div></h1><p>Deletes</p></div>
                      </section>
                  </div>
              </div>
              <!--state overview end-->
			  <div class="row">
                    <div class="col-lg-4 operationsChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Operations
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('operationsChart', 'Operations');"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="javascript:LiveStats.info('operationsChart', 'Operations');"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            	 --%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="operationsChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>			  	
                    <div class="col-lg-4 queuesChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Queues
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('queuesChart', 'Queues');"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="javascript:LiveStats.info('queuesChart', 'Queues');"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            	--%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="queuesChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>
                    <div class="col-lg-4 faultsChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Page Faults / Flushes
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('faultsChart', 'Page Faults / Flushes');"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="javascript:LiveStats.info('faultsChart', 'Page Faults / Flushes');"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            	 --%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="faultsChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>				  
			  </div>
			  <div class="row">		  	
                    <div class="col-lg-4 locksChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Lock %, Index Miss %
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('locksChart', 'Lock %, Index Miss %');"><i class="fa fa-expand fa-lg"></i></a>
	                            	<a href="javascript:LiveStats.info('locksChart', 'Lock %, Index Miss %');"><i class="fa fa-info-circle fa-lg"></i></a>
	                            	 --%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="locksChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>
                    <div class="col-lg-4 networkChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Network
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('networkChart', 'Network');"><i class="fa fa-expand fa-lg"></i></a>
	                            	<a href="javascript:LiveStats.info('networkChart', 'Network');"><i class="fa fa-info-circle fa-lg"></i></a>
	                            	 --%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                	<div id="networkChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>			  	
                    <div class="col-lg-4 connChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Connections
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md" >
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('connChart', 'Connections');"><i class="fa fa-expand fa-lg"></i></a>
	                            	<a href="javascript:LiveStats.info('connChart', 'Connections');"><i class="fa fa-info-circle fa-lg"></i></a>
	                            	 --%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="connChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>			  
			  </div>

			  <div class="row">		  	
                    <div class="col-lg-4 memoryChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Memory
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('memoryChart', 'Memory');"><i class="fa fa-expand fa-lg"></i></a>
	                            	<a href="javascript:LiveStats.info('memoryChart', 'Memory');"><i class="fa fa-info-circle fa-lg"></i></a>
	                            	--%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="memoryChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>
                    <div class="col-lg-4 nonMappedChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Non-Mapped Memory
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('nonMappedChart', 'No-Mapped Memory);"><i class="fa fa-expand fa-lg"></i></a>
	                            	<a href="javascript:LiveStats.info('nonMappedChart' 'No-Mapped Memory);"><i class="fa fa-info-circle fa-lg"></i></a>
	                            	 --%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="nonMappedChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>			  	
                    <div class="col-lg-4 assertsChart">
                        <section class="panel panel-primary">
                            <header class="panel-heading">Asserts
	                            <span class="tools pull-right hidden-xs hidden-sm hidden-md">
	                            	<%--
	                            	<a href="javascript:LiveStats.zoom('assertsChart', 'Asserts');"><i class="fa fa-expand fa-lg"></i></a>
	                            	<a href="javascript:LiveStats.info('assertsChart', 'Asserts');"><i class="fa fa-info-circle fa-lg"></i></a>
	                            	 --%>
	                            	<a href="#" class="zoom"><i class="fa fa-expand fa-lg" style='color: #ffffFF;'></i></a>
	                            	<a href="#" class="info"><i class="fa fa-info-circle fa-lg" style='color: #ffffFF;'></i></a>
	                            </span> 
                            </header>
                            <div class="panel-body">
                                <div id="assertsChart" class="live-stats chart"></div>
                            </div>
                        </section>
                    </div>			  
			  </div>			  			  
			  <div class="row">
                    <div class="col-lg-12">
                        <section class="panel panel-primary">
                            <header class="panel-heading">mongostat
	                            <span class="tools pull-right">
	                            	<a href="javascript:;"><i class="fa fa-chevron-down fa-lg" style='color: #ffffFF;'></i></a>
	                            </span>  
                            </header>
                            <div class="panel-body">
								<table width="100%" class="table-striped" id="mongostat">
			 						<thead>
										<tr>
											<th>insert</th>
											<th>query</th>
											<th>update</th>
											<th>delete</th>
											<th>getmore</th>
											<th class="hidden-xs">cmd</th>
											<th class="hidden-xs">flushes</th>
											<th class="hidden-xs hidden-sm hidden-md">mapped</th>
											<th class="hidden-xs hidden-sm hidden-md">vzise</th>
											<th class="hidden-xs hidden-sm hidden-md">res</th>
											<th class="hidden-xs hidden-sm hidden-md">faults</th>
											<th class="hidden-xs hidden-sm hidden-md">locked</th>
											<th class="hidden-xs hidden-sm hidden-md">idx miss</th>
											<th class="hidden-xs hidden-sm hidden-md">qr|qw</th>
											<th class="hidden-xs hidden-sm hidden-md">ar|aw</th>
											<th class="hidden-xs hidden-sm hidden-md">netIn</th>
											<th class="hidden-xs hidden-sm hidden-md">netOut</th>
											<th class="hidden-xs hidden-sm hidden-md">conn</th>
											<th class="hidden-xs">time</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
                            </div>                     
                        </section>
                    </div>
              </div>
			<!-- Charts Modal -->
              <div class="modal" id="chartsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                  <div class="modal-dialog" style="width: calc(100% - 200px);">
                      <div class="modal-content">
                          <div class="modal-header">
                              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                              <h4 class="modal-title" id="chartsModalTitle"></h4>
                          </div>
                          <div class="modal-body" style="height: 450px;">
                              <div id="zoomChart" class="chart"></div>
                          </div>
                          <div class="modal-footer">
                              <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>
                          </div>
                      </div>
                  </div>
              </div>
              <!-- modal -->  
              
			  <!-- Charts Modal -->
              <div class="modal" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                  <div class="modal-dialog" style="width: 800px;">
                      <div class="modal-content">
                          <div class="modal-header">
                              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                              <h4 class="modal-title" id="infoModalTitle"></h4>
                          </div>
                          <div class="modal-body" style="height: 450px;">
                              <div id="zoomChart" class="chart"></div>
                          </div>
                          <div class="modal-footer">
                              <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>
                          </div>
                      </div>
                  </div>
              </div>
              <!-- modal -->                           
          </section>
      </section>
      <!--main content end-->

<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>
