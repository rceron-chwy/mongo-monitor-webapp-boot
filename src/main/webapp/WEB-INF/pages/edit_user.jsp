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
							<form:form id="form_sample_1" method="POST" action="${BASE_URL}/user/save" modelAttribute="user" class="form-horizontal">
								<dash:form_errors />
								<c:if test="${not empty user}">
									<form:hidden path="id"/>
									<form:hidden path="currentPassword"/>
								</c:if>
								<div class="form-body">							
									<div class="form-group">
										<form:label path="name" class="col-md-3 control-label">Name<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:input path="name" class="form-control" required="required" />
										</div>
									</div>								
									<div class="form-group">
										<form:label path="email" class="col-md-3 control-label">Email<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:input path="email" class="form-control" required="required" />
										</div>
									</div>							
									<div class="form-group">
										<form:label path="username" class="col-md-3 control-label">Username<span class="required">*</span></form:label>
										<div class="col-md-5">
											<div class="input-group m-bot15">
                                              <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                              <form:input path="username" class="form-control" required="required" />
                                          	</div>
										</div>
									</div>													
									<div class="form-group">
										<form:label path="password" class="col-md-3 control-label">Password<span class="required">*</span></form:label>
										<div class="col-md-5">
											<div class="input-group m-bot15">
												<span class="input-group-addon"><i class="fa fa-key"></i></span>
												<form:password path="password" class="form-control" showPassword="false"/>
											</div>								
										</div>
									</div>	
									<div class="form-group">
										<form:label path="active" class="col-md-3 control-label">Active<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:checkbox path="active"  class="form-control" style="width: 20px"/>
										</div>
									</div>	
									<div class="form-group">
										<form:label path="role" class="col-md-3 control-label">Role</form:label>
										<div class="col-md-5">
											<form:select path="role"  class="form-control m-bot15">
												<form:option value="" label="Select One" />
												<c:forEach var="type" items="${userRoles}">
	                  								<form:option value="${type}"><spring:eval expression="type.text"/></form:option>
	              								</c:forEach>
											</form:select>										
										</div>
									</div>	
									<form:button class="btn btn-primary"><i class="fa fa-save fa-fw fa-lg"></i> Save</form:button>
									<a class="btn btn-default" href="${BASE_URL}/user/list">Cancel</a>
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