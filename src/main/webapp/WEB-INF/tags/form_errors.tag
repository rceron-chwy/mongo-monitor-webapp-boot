<%@ tag body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:bind path="*">
	<c:if test="${not empty status.errorMessages}">
		<div class="alert alert-block alert-danger">
			<button data-dismiss="alert" class="close close-sm" type="button">
				<i class="fa fa-times"></i>
			</button>
			<c:forEach items="${status.errorMessages}" var="error">
			${error}<br />
			</c:forEach>
		</div>
	</c:if>
</spring:bind>