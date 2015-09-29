<%@ include file="/WEB-INF/pages/includes/tags.jsp" %>
			<%--BEGIN PAGE HEADER --%>
			<div class="row">
                  <div class="col-lg-12">
                      <%--breadcrumbs start --%>
                      <ul class="breadcrumb">
                          <li><a href="index"><i class="fa fa-home"></i> Home</a></li>
                       <c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
                          <li <c:if test="${status.last == false}">class="active"</c:if>>
                          	<c:if test="${not empty breadcrumb.link}"><a href="${BASE_URL}/${breadcrumb.link}"></c:if>
                          		${breadcrumb.title}
                          	<c:if test="${not empty breadcrumb.link}"></a></c:if>
                          </li>
                      </c:forEach>  
                      </ul>
                      <%--breadcrumbs end --%>
                  </div>
              </div>  
			<%--END PAGE HEADER --%>