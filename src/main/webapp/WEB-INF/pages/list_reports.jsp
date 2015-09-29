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
                   	  	<dash:alerts type="success" message="form.success" bundle="true" visible="${not empty report}" smessage="${report.name}"/>
	                    <table class="table table-striped table-hover table-condensed cf">
	 						<thead>
								<tr>
									<th>Report</th>
									<th>Last Execution Date</th>
									<th align="center" width="70">Active</th>
									<th align="center" width="100">Actions</th>
								</tr>
							</thead>                   
							<tbody>
								<c:forEach items="${reports}" var="report" varStatus="status">
								<tr>
									<td>${report.name}</td>
									<td>${report.lastExecutionDate}</td>
									<td align="center">
										<c:if test="${report.enabled}">
											<button class="btn btn-success btn-xs" disabled><i class="fa fa-thumbs-up"></i></button>
										</c:if>
										<c:if test="${not report.enabled}">
											<button class="btn btn-danger btn-xs" disabled><i class="fa fa-thumbs-down"></i></button>
										</c:if>
									</td>
									<td>
										<button class="btn btn-info btn-xs" onClick="window.location='${BASE_URL}/reports/run/${report.id}'"><i class="fa fa-play"></i></button>
										<button class="btn btn-primary btn-xs" onClick="window.location='${BASE_URL}/reports/edit/${report.id}'"><i class="fa fa-pencil"></i></button>
	                                    <button class="btn btn-danger btn-xs" onClick="window.location='${BASE_URL}/reports/remove/${report.id}'"><i class="fa fa-trash-o"></i></button>   
									</td>
								</tr>
								</c:forEach>
							</tbody>
                    	</table>
           	
                    </section> 
				    <div class=" add-task-row">
                    	<a class="btn btn-primary" href="${BASE_URL}/reports/new">
                    	<i class="fa fa-file-text-o fa-fw fa-lg"></i> Add New Report</a>
                    </div>                            			
				</div>
			</div>
			
          </section>
      </section>
      <!--main content end-->		
			
<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>
