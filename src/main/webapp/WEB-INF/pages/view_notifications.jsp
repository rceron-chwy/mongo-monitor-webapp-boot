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
                   	  	<dash:alerts type="success" message="form.success" bundle="true" visible="${not empty alert}" smessage="${alert.text}"/>
	                    <table class="table table-striped table-hover table-condensed cf">
	                    
	                    </table>
	                </section>

				</div>
			</div>


          </section>
      </section>
      <!--main content end-->

<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>
