<%@ include file="/WEB-INF/pages/includes/header.jsp" %>

<%@ include file="/WEB-INF/pages/includes/top_bar.jsp" %>

<%@ include file="/WEB-INF/pages/includes/menus.jsp" %>	
	
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
	                    <table class="table table-striped table-hover table-condensed cf">
	 						<thead>
								<tr>
									<th></th>
									<th>Name</th>
									<th>Type</th>
									<th class="hidden-xs hidden-sm">Process</th>
									<th class="hidden-xs hidden-sm">Version</th>
									<th class="hidden-xs hidden-sm hidden-md">Update Method</th>
									<th class="hidden-xs hidden-sm hidden-md">Up Since</th>
									<th class="hidden-xs hidden-sm hidden-md">Last Update</th>
								</tr>
							</thead>                   
							<tbody>
								<c:forEach items="${servers}" var="server" varStatus="status">
								<tr>
									<td><i class="fa fa-archive"></i></td>
									<td><a href="${BASE_URL}/stats/live/${server.id}">${server.name}</a></td>
									<td>
										<c:if test="${not empty server && server.type == 'standalone'}">
											<span 120px;" class="label label-default">STANDALONE</span>
										</c:if>
										<c:if test="${not empty server && server.type == 'master'}">
											<span class="label label-success" title="Replica set: ${server.replName}">MASTER</span>
										</c:if>
										<c:if test="${not empty server && server.type == 'secondary'}">
											<span class="label label-primary" title="Replica set: ${server.replName}">SECONDARY</span>
										</c:if>
									</td>
									<td class="hidden-xs hidden-sm">${server.process}</td>
									<td class="hidden-xs hidden-sm">${server.version}</td>
									<td  class="hidden-xs hidden-sm hidden-md">
										<c:if test="${not empty server && server.method == 'TCP'}">Monitor TCP</c:if>
										<c:if test="${not empty server && server.method == 'HTTP'}">Monitor HTTP</c:if>
										<c:if test="${not empty server && server.method == 'AGENT'}">Remote Agent</c:if>
									</td>							
									<td class="hidden-xs hidden-sm hidden-md">${server.upSince}</td>
									<td class="timeago hidden-xs hidden-sm hidden-md" title="${server.lastMonitorUpdate}">
									</td>
								</tr>
								</c:forEach>
							</tbody>
                    	</table>
                    </section>                        			
				</div>
			</div>
			
          </section>
      </section>
      <!--main content end-->		
			
<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>
