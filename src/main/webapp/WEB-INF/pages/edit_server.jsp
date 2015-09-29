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
							<form:form id="form_sample_1" method="POST" action="${BASE_URL}/server/save" modelAttribute="server" class="form-horizontal">
								<dash:form_errors />
								<c:if test="${not empty server}">
									<form:hidden path="id"/>
								</c:if>
								<div class="form-body">							
									<div class="form-group">
										<form:label path="name" class="col-md-3 control-label">Server Name<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:input path="name" class="form-control" required="required" readonly="${not empty server && server.monitorRunning}" />
										</div>
									</div>	
									<div class="form-group">
										<form:label path="method" class="col-md-3 control-label">Update Method</form:label>
										<div class="col-md-5">
											<form:select path="method" class="form-control m-bot15" onchange="Servers.onMethodUpdateChange(this);">
												<c:forEach var="item" items="${serverUpdateMethodTypes}">
                  									<form:option value="${item}"><spring:eval expression="item.text"/></form:option>
              									</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="form-group" id="keyGroup" <c:if test="${empty server || empty server.key}"> style="display: none;"</c:if>>
										<form:label path="key" class="col-md-3 control-label">Update Key</form:label>
										<div class="input-group col-md-5">
											<form:input id="keyInput" path="key" class="form-control" required="required" readonly="${not empty server}" />
											<span class="input-group-btn">
												<button class="btn btn-primary" type="button" onClick="Servers.copyToClipboard()">Copy!</button>
											</span>
										</div>
									</div>
									<div class="form-group" id="secureGroup" <c:if test="${not empty server && server.method != 'HTTP'}">style="display:none"</c:if>>
										<form:label path="secure" class="col-md-3 control-label">Secure Connection</form:label>
										<div class="col-md-5">
											<form:checkbox path="secure"  class="form-control" style="width: 20px"/>
										</div>
									</div>
									<div class="form-group">
										<form:label path="hostname" class="col-md-3 control-label">Hostname / IP Address<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:input path="hostname" class="form-control" required="required" placeholder="127.0.0.1"/>
										</div>
									</div>
									<div class="form-group">
										<form:label path="port" class="col-md-3 control-label">MongoDB Port</form:label>
										<div class="col-md-5">
											<form:input path="port" class="form-control" placeholder="27017"/>
										</div>
									</div>
									<div class="form-group">
										<form:label path="interval" class="col-md-3 control-label">Update Interval (seconds)</form:label>
										<div class="col-md-5">
											<form:input path="interval" class="form-control"  placeholder="5"/>										
										</div>
									</div>								
									<div class="form-group">
										<form:label path="username" class="col-md-3 control-label">MongoDB User</form:label>
										<div class="col-md-5">
											<div class="input-group m-bot15">
                                              <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                              <form:input path="username" class="form-control"/>
                                          	</div>
										</div>
									</div>													
									<div class="form-group">
										<form:label path="password" class="col-md-3 control-label">MongoDB Password</form:label>
										<div class="col-md-5">
											<div class="input-group m-bot15">
												<span class="input-group-addon"><i class="fa fa-key"></i></span>
												<form:password path="password" class="form-control" showPassword="true"/>
											</div>								
										</div>
									</div>
									<div class="form-group">
										<form:label path="retain" class="col-md-3 control-label">Retain data period (days)</form:label>
										<div class="col-md-5">
											<form:input path="retain" class="form-control" placeholder="10" />
										</div>
									</div>
									<form:button class="btn btn-primary"><i class="fa fa-save fa-fw fa-lg"></i> Save</form:button> 
									<a class="btn btn-default" href="${BASE_URL}/server/list">Cancel</a>
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