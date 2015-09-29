<%@ include file="/WEB-INF/pages/includes/tags.jsp" %>	
	      
      <%-- footer start  --%>
      <footer class="site-footer">
          <div class="text-center">
              2014 &copy; MongoDASH
              <a href="#" class="go-top">
                  <i class="fa fa-angle-up"></i>
              </a>
          </div>
      </footer>
      <%-- footer end  --%>
  </section>

	<%-- js placed at the end of the document so the pages load faster  --%>
	<script type="text/javascript" src="${BASE_URL}/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${BASE_URL}/js/jquery-ui-1.9.2.custom.min.js"></script>
	
	<script type="text/javascript" src="${BASE_URL}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${BASE_URL}/js/jquery.dcjqaccordion.2.7.js"></script>
	<script type="text/javascript" src="${BASE_URL}/js/jquery.scrollTo.min.js"></script>
	<script type="text/javascript" src="${BASE_URL}/js/jquery.nicescroll.js"></script>
	<script type="text/javascript" src="${BASE_URL}/js/respond.min.js" ></script>
	<script type="text/javascript" src="${BASE_URL}/js/jquery.gritter.min.js"></script>

    <%--common script for all pages  --%>
    <script type="text/javascript" src="${BASE_URL}/js/common-scripts.js"></script>
    <script type="text/javascript" src="${BASE_URL}/js/app_alerts.js"></script>
    <script type="text/javascript" src="${BASE_URL}/js/app_notifications.js"></script>
    <script type="text/javascript" src="${BASE_URL}/js/app_mongom.js"></script>

<c:if test="${plugins != null}">
	<%-- common plugins for this page --%>
	<c:forEach items="${plugins}" var="plugin">
	<script type="text/javascript" src="${BASE_URL}/${plugin.file}" ></script>						
	</c:forEach>
</c:if>
<c:if test="${scripts != null}">
	<%-- common scripts for this page --%>
	<c:forEach items="${scripts}" var="script">
	<script type="text/javascript" src="${BASE_URL}/${script.file}"></script>						
	</c:forEach>
</c:if>

<script>
$(document).ready(function() {  
	var params = {
		BASE_URL: '${BASE_URL}'
		<c:if test="${subMenu == 'servers' && server != null}">
		, UPDATE_INTERVAL: ${server.interval}
		, SERVER_ID: ${server.id}
		</c:if>
		
	};
	MongoM.init(params);
	<c:if test="${initModules != null}">
		<c:forEach items="${initModules}" var="module">    	  
	<c:out value="${module}.init(params)" />
	    </c:forEach>
	</c:if>
});      
</script>
  </body>
</html>