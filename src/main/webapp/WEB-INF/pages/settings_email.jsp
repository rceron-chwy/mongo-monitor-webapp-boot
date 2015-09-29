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
                   	  	<dash:alerts type="success" message="form.success" bundle="true" visible="${saved}" smessage="Email settings"/>
	                    <div class="panel-body">
							<form:form id="form_sample_1" method="POST" action="${BASE_URL}/settings/email/save" modelAttribute="settings" class="form-horizontal">
								<div class="form-body">							
									<div class="form-group">
										<label class="col-md-3 control-label">Enabled
										</label>
										<div class="col-md-5">
											<form:checkbox path="enabled"  class="form-control" style="width: 20px"/>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Mail Server
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="server" class="form-control" required="required"/>
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
										<label class="col-md-3 control-label">Use SSL
										</label>
										<div class="col-md-5">
											<form:checkbox path="ssl"  class="form-control" style="width: 20px"/>
										</div>
									</div>																	
									<div class="form-group">
										<label class="col-md-3 control-label">Mail User
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="username" class="form-control" required="required"/>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Mail Password
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:password path="password" class="form-control" required="required" showPassword="true"/>
										</div>
									</div>							
									<div class="form-group">
										<label class="col-md-3 control-label">Mail Sender
											<span class="required">*</span>
										</label>
										<div class="col-md-5">
											<form:input path="sender" class="form-control" required="required"/>
										</div>
									</div>
									<button type="submit" class="btn btn-primary">
										<i class="fa fa-save fa-fw fa-lg"></i> Save</button>
									<button type="button" class="btn btn-info" id="btnTestMail">
										<i class="fa fa-envelope-o fa-fw fa-lg"></i> Send Test Email</button>
									
								</div> 
								<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                  <div class="modal-dialog">
                                      <div class="modal-content">
                                          <div class="modal-header">
                                              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                              <h4 class="modal-title">MongoDASH</h4>
                                          </div>
                                          <div class="modal-body"></div>
                                          <div class="modal-footer">
                                              <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>
                                          </div>
                                      </div>
                                  </div>
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
