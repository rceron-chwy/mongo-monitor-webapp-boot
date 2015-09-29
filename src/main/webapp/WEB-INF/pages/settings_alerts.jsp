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
                   	  	<dash:alerts type="success" message="form.success" bundle="true" visible="${saved}" smessage="Alerts settings"/>
	                    <div class="panel-body">
							<form:form id="form_sample_1" method="POST" action="${BASE_URL}/settings/alerts/save" modelAttribute="settings" class="form-horizontal">
								<div class="form-body">							
									<div class="form-group">
										<label class="col-md-3 control-label">Enabled
										</label>
										<div class="col-md-5">
											<form:checkbox path="enabled"  class="form-control" style="width: 20px"/>
										</div>
									</div>	
						
									<div class="form-group">
										<label class="col-md-3 control-label">Email Subject
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="emailSubject" class="form-control" required="required"/>
										</div>
									</div>						
									<div class="form-group">
										<label class="col-md-3 control-label">Default Recipient List
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="emailRecipients" class="form-control" required="required"/>
										</div>
									</div>		
									<form:button class="btn btn-primary">
										<i class="fa fa-save fa-fw fa-lg"></i> Save</form:button>													
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
