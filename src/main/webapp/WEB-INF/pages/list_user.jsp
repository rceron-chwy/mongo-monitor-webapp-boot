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
						<dash:alerts type="success" message="form.success" bundle="true" visible="${not empty user}" smessage="${user.name}"/>
	                    <table class="table table-striped table-hover table-condensed cf">
	 						<thead>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th class="hidden-xs hidden-sm">Username</th>
									<th class="hidden-xs hidden-sm hidden-md">Email</th>
									<th class="hidden-xs hidden-sm">Role</th>
									<th class="hidden-xs hidden-sm hidden-md">Last Access</th>
									<th class="hidden-xs hidden-sm hidden-md" align="center" width="70">Active</th>
									<th align="center" width="80">Actions</th>
								</tr>
							</thead>                   
							<tbody>
								<c:forEach items="${users}" var="user" varStatus="status">
								<tr>
									<td>${user.id}</td>
									<td>${user.name}</td>
									<td class="hidden-xs hidden-sm">${user.username}</td>
									<td class="hidden-xs hidden-sm hidden-md">${user.email}</td>
									<td class="hidden-xs hidden-sm">${user.roleText}</td>
									<td class="hidden-xs hidden-sm hidden-md"></td>
									<td class="hidden-xs hidden-sm hidden-md" align="center">
										<c:if test="${user.active}">
											<button class="btn btn-success btn-xs" disabled><i class="fa fa-thumbs-up"></i></button>
										</c:if>
										<c:if test="${not user.active}">
											<button class="btn btn-danger btn-xs" disabled><i class="fa fa-thumbs-down"></i></button>
										</c:if>
									</td>
									<td>
										<button class="btn btn-primary btn-xs" onClick="window.location='${BASE_URL}/user/edit/${user.id}'"><i class="fa fa-pencil"></i></button>
	                                    <button class="btn btn-danger btn-xs" onClick="window.location='${BASE_URL}/user/remove/${user.id}'"><i class="fa fa-trash-o"></i></button>   
									</td>
								</tr>
								</c:forEach>
							</tbody>
                    	</table>
           	
                    </section> 
				    <div class=" add-task-row">
                    	<a class="btn btn-primary" href="${BASE_URL}/user/new">
                    	<i class="fa fa-user fa-fw fa-lg"></i> Add New User</a>
                    </div>                            			
				</div>
			</div>
			
          </section>
      </section>
      <!--main content end-->		
			
<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>
