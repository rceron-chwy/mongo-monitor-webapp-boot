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
                   	  	<dash:alerts type="success" message="form.success" bundle="true" visible="${saved}" smessage="LDAP settings"/>
	                    <div class="panel-body">
							<form:form id="form_sample_1" method="POST" action="${BASE_URL}/settings/ldap/save" modelAttribute="settings" class="form-horizontal">
								<div class="form-body">							
									<div class="form-group">
										<label class="col-md-3 control-label">Enabled
										</label>
										<div class="col-md-5">
											<form:checkbox path="enabled" class="form-control" style="width: 20px"/>
										</div>
									</div>								
									<div class="form-group">
										<label class="col-md-3 control-label">Host or IP
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="host" class="form-control" required="required"/>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Port Number
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="port" class="form-control" required="required"/>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Use secure connection
										</label>
										<div class="col-md-5">
											<form:checkbox path="secure"  class="form-control" style="width: 20px"/>
										</div>
									</div>									
									<div class="form-group">
										<label class="col-md-3 control-label">Administrator Bind DN
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="adminDn" class="form-control" required="required"/>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Admin Password
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="adminPassword" class="form-control" required="required"/>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Base DN
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="baseDn" class="form-control" required="required"/>
											<p class="help-block">Where users are located.</p>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Login Base Attribute
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="loginAttribute" class="form-control" required="required"/>
										</div>
									</div>		
													
									<button type="submit" class="btn btn-primary">
										<i class="fa fa-save fa-fw fa-lg"></i> Save</button>	
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
