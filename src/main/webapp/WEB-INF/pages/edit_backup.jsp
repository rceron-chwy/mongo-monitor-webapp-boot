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
							<form:form id="form_sample_1" method="POST" action="${BASE_URL}/backups/save" modelAttribute="backup" class="form-horizontal">
								<dash:form_errors />
								<c:if test="${not empty backup}">
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
										<form:label path="serverId" class="col-md-3 control-label">Server<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:select path="serverId" class="form-control m-bot15" id="serverSelect">
												<form:option value="" label="Select One" />
													<c:forEach var="server" items="${servers}">
                  										<form:option value="${server.id}" ><spring:eval expression="server.name"/></form:option>
              										</c:forEach>
											</form:select>
										</div>
									</div>	
									<div class="form-group">
										<form:label path="databases" class="col-md-3 control-label">Databases</form:label>
										<div class="col-md-9">
											<form:select path="databases" multiple="multiple" class="multi-select" id="my_multi_select1" >
												<c:forEach var="db" items="${dbs}">
	                  								<form:option value="${db.name}"><spring:eval expression="db.name"/></form:option>
	              								</c:forEach>
											</form:select>										
										</div>
									</div>															
									<div class="form-group">
										<form:label path="dumpFormat" class="col-md-3 control-label">Format<span class="required">*</span></form:label>
										<div class="col-md-5">
											<form:select path="dumpFormat" class="form-control m-bot15">
												<form:option value="" label="Select One" />
													<c:forEach var="format" items="${dumpFormats}">
                  										<form:option value="${format}" ><spring:eval expression="format"/></form:option>
              										</c:forEach>
											</form:select>
										</div>
									</div>							
									<div class="form-group">
										<form:label path="runDaily" class="col-md-3 control-label">Run Daily</form:label>
										<div class="col-md-5">
											<form:checkbox path="runDaily"  class="form-control" style="width: 20px"/>
										</div>
									</div>									
									<div class="form-group">
										<form:label path="includeOpLog" class="col-md-3 control-label">Include Oplog</form:label>
										<div class="col-md-5">
											<form:checkbox path="includeOpLog"  class="form-control" style="width: 20px"/>
										</div>
									</div>									
									<div class="form-group">
										<form:label path="compressFolder" class="col-md-3 control-label">Compress Output Folder</form:label>
										<div class="col-md-5">
											<form:checkbox path="compressFolder"  class="form-control" style="width: 20px"/>
										</div>
									</div>										
									<div class="form-group">
										<form:label path="notifyOnFinish" class="col-md-3 control-label">Complete Notification</form:label>
										<div class="col-md-5">
											<form:checkbox path="notifyOnFinish"  class="form-control" style="width: 20px"/>
										</div>
									</div>								
									<div class="form-group">
										<form:label path="outputFolder" class="col-md-3 control-label">Output Folder</form:label>
										<div class="col-md-5">
											<form:input path="outputFolder" class="form-control"/>
										</div>
									</div>	
									<form:button class="btn btn-primary">
										<i class="fa fa-save fa-fw fa-lg"></i> Save</form:button>
									<a class="btn btn-default" href="${BASE_URL}/backups/list">Cancel</a>
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
