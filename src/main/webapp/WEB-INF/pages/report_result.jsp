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
                    </section>                         			
				</div>
			</div>
		
			<div class="row">			
				<div class="col-lg-12">
					<section class="panel">
                        <header class="panel-heading">
                        	${title}
                        	<div style="font-size: 12px">${description}</div>
                   	  	</header>
                    </section>                         			
				</div>
			</div>		
			
          </section>
      </section>
      <!--main content end-->		
			
<%@ include file="/WEB-INF/pages/includes/footer.jsp" %>
