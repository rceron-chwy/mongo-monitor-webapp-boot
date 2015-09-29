<%@ include file="/WEB-INF/pages/includes/header.jsp" %>

<%@ include file="/WEB-INF/pages/includes/top_bar.jsp" %>

<%@ include file="/WEB-INF/pages/includes/menus.jsp" %>	

<%@ taglib tagdir="/WEB-INF/tags" prefix="dash" %>
	
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
          
          	<%@ include file="/WEB-INF/pages/includes/page_header.jsp" %>        
          
			<div class="row">			
				<div class="col-lg-12">			
				
					<section class="panel">
                        <header class="panel-heading">
                        	${title}
                        	<div style="font-size: 12px">${description}</div>
                   	  	</header>
                   	  	<dash:alerts type="success" message="form.success" bundle="true" visible="${not empty server}" smessage="${server.name}"/>
	                    <table class="table table-striped table-hover table-condensed cf">
	 						<thead>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th align="center">Monitoring</th>
									<th class="hidden-xs hidden-sm hidden-md">Update Method</th>
									<th class="hidden-xs hidden-sm">Connection</th>
									<th class="hidden-xs hidden-sm">Update Interval</th>
									<th class="hidden-xs hidden-sm hidden-md" width="70">Secure</th>
									<th align="center" width="80">Actions</th>
								</tr>
							</thead>                   
							<tbody>
								<c:forEach items="${servers}" var="server" varStatus="status">
								<tr>
									<td>${server.id}</td>
									<td>${server.name}</td>
									<td>
										<c:if test="${not empty server && server.method != 'agent'}">
										<div class="btn-group">
											<c:if test="${server.monitorRunning}">
												<button id="btnMonitor${server.id}" data-toggle="dropdown" class="btn btn-primary dropdown-toggle btn-xs" type="button">&nbsp;  started &nbsp;<span class="caret"></span></button>
											</c:if>
											<c:if test="${not server.monitorRunning}">
												<button id="btnMonitor${server.id}" data-toggle="dropdown" class="btn btn-default dropdown-toggle btn-xs" type="button">&nbsp;  stopped &nbsp;<span class="caret"></span></button>
											</c:if>
                                  			<ul role="menu" class="dropdown-menu">
                                      			<li><a onClick="Servers.startMonitor('btnMonitor${server.id}', ${server.id});">Start</a></li>
                                      			<li><a onClick="Servers.stopMonitor('btnMonitor${server.id}', ${server.id});">Stop</a></li>
                                  			</ul>
                             		 	</div>
                             		 	</c:if>
									</td>
									<td class="hidden-xs hidden-sm hidden-md">
										<c:if test="${not empty server && server.method == 'TCP'}">Monitor TCP</c:if>
										<c:if test="${not empty server && server.method == 'HTTP'}">Monitor HTTP</c:if>
										<c:if test="${not empty server && server.method == 'AGENT'}">Remote Agent</c:if>
									</td>
									<td class="hidden-xs hidden-sm">
										<c:if test="${not empty server && server.method == 'TCP'}">tcp://${server.hostname}:${server.port}</c:if>
										<c:if test="${not empty server && server.method == 'HTTP' && not server.secure}">http://${server.hostname}:${server.port}</c:if>
										<c:if test="${not empty server && server.method == 'HTTP' && server.secure}">https://${server.hostname}:${server.port}</c:if>
									</td>							
									<td class="hidden-xs hidden-sm">${server.interval} seconds</td>
									<td class="hidden-xs hidden-sm hidden-md" align="center">
										<c:if test="${server.secure}">
											<button class="btn btn-success btn-xs" disabled><i class="fa fa-thumbs-up"></i></button>
										</c:if>
										<c:if test="${not server.secure}">
											<button class="btn btn-danger btn-xs" disabled><i class="fa fa-thumbs-down"></i></button>
										</c:if>
									</td>
									<td>
										<button class="btn btn-primary btn-xs" onClick="window.location='${BASE_URL}/server/edit/${server.id}'"><i class="fa fa-pencil"></i></button>
	                                    <button class="btn btn-danger btn-xs" onClick="window.location='${BASE_URL}/server/remove/${server.id}'"><i class="fa fa-trash-o"></i></button>   
									</td>
								</tr>
								</c:forEach>
							</tbody>
                    	</table>
           	
                    </section> 
				    <div class=" add-task-row">
                    	<a class="btn btn-primary" href="${BASE_URL}/server/new">
                    	<i class="fa fa-laptop fa-fw fa-lg"></i> Add New Server</a>
                    </div>                            			
				</div>
			</div>
			
          </section>
      </section>
      <!--main content end-->		
			
<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>
