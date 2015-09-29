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
						<div class="panel-body">
							<form:form id="form_sample_1" method="POST" action="${BASE_URL}/reports/save" modelAttribute="report" class="form-horizontal">
								<dash:form_errors />
								<c:if test="${not empty report && not empty report.id}">
									<form:hidden path="id"/>
								</c:if>
								<div class="form-body">															
									<div class="form-group">
										<form:label path="name" class="col-md-3 control-label">Name<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:input path="name" class="form-control" required="required"/>
										</div>
									</div>								
									<div class="form-group">
										<form:label path="enabled" class="col-md-3 control-label">Enabled</form:label>
										<div class="col-md-5">
											<form:checkbox path="enabled"  class="form-control" style="width: 20px"/>
										</div>
									</div>	
									<div class="form-group">
										<form:label path="serverIds" class="col-md-3 control-label">Servers</form:label>
										<div class="col-md-9">
											<form:select path="serverIds" multiple="multiple" class="multi-select" id="my_multi_select3">
												<c:forEach var="server" items="${servers}">
	                  								<form:option value="${server.id}"><spring:eval expression="server.name"/></form:option>
	              								</c:forEach>
											</form:select>										
										</div>
									</div>
									<div class="form-group">
										<form:label path="reportPeriod" class="col-md-3 control-label">Report Period</form:label>
										<div class="col-md-5">
											<form:select path="reportPeriod" class="form-control m-bot15">
												<form:option value="" label="Select One" />
												<c:forEach var="item" items="${aggregationRanges}">
                  									<form:option value="${item}"><spring:eval expression="item.text"/></form:option>
              									</c:forEach>
											</form:select>									
										</div>
									</div>
									<div class="form-group">
										<form:label path="aggregationFields" class="col-md-3 control-label">Fields</form:label>
										<div class="col-md-9">
											<form:select path="aggregationFields" multiple="multiple" class="multi-select" id="my_multi_select1" >
												<c:forEach var="type" items="${types}">
	                  								<form:option value="${type}"><spring:eval expression="type.text"/></form:option>
	              								</c:forEach>
											</form:select>										
										</div>
									</div>	
									<div class="form-group">
										<form:label path="aggregationOperators" class="col-md-3 control-label">Aggregation Info</form:label>
										<div class="col-md-9">
											<form:select path="aggregationOperators" multiple="multiple" class="multi-select" id="my_multi_select2" >
												<c:forEach var="type" items="${aggregationTypes}">
	                  								<form:option value="${type}"><spring:eval expression="type.text"/></form:option>
	              								</c:forEach>
											</form:select>										
										</div>
									</div>	
									<div class="form-group">
										<label class="col-md-3 control-label">Group by</label>
										<form:label path="aggregationOperators" class="col-md-3 control-label">Group By</form:label>
										<div class="col-md-5">
											<form:select path="groupBy" class="form-control m-bot15">
												<form:option value="" label="Select One" />
												<c:forEach var="item" items="${aggregationGroups}">
                 									<form:option value="${item}" ><spring:eval expression="item.text"/></form:option>
             									</c:forEach>
											</form:select>									
										</div>
									</div>							
									<div class="form-group">
										<form:label path="emailEnabled" class="col-md-3 control-label">Send by Email</form:label>
										<label class="col-md-3 control-label">Send by Email</label>
										<div class="col-md-5">
											<form:checkbox path="emailEnabled"  class="form-control" style="width: 20px"/>
										</div>
									</div>							
									<div class="form-group">
										<form:label path="recipientList" class="col-md-3 control-label">Recipient List</form:label>
										<div class="col-md-5">
											<form:input path="recipientList" class="form-control"/>
										</div>
									</div>				
									<form:button class="btn btn-primary">
										<i class="fa fa-save fa-fw fa-lg"></i> Save</form:button>
									<a class="btn btn-default" href="${BASE_URL}/reports/list">Cancel</a>
								</div>
							</form:form>
						</div>
                    </section> 
				</div>
			</div>
          </section>
      </section>
      <!--main content end-->		
<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>