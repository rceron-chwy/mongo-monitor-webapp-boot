<%@ tag body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- info/danger/warning/success -->
<%@ attribute name="type" type="java.lang.String" required="true"%>
<%@ attribute name="visible" type="java.lang.Boolean" required="true"%>

<!-- message to display -->
<%@ attribute name="message" type="java.lang.String" required="true"%>
<%@ attribute name="bundle" type="java.lang.Boolean" required="true"%>

<%@ attribute name="smessage" type="java.lang.String" required="false"%>

<c:if test="${visible}">
<div class="panel-body" id="alerts">
	<div class="alert alert-${type} fade in">
		<button data-dismiss="alert" class="close close-sm" type="button" onClick="$('#alerts').remove();">
			<i class="fa fa-times"></i>
		</button>
		<c:if test="${not empty smessage}"><strong>${smessage}</strong></c:if>
		<c:choose>
			<c:when test="${bundle}"><spring:message code="${message}" /></c:when>
			<c:otherwise>${message}</c:otherwise>
		</c:choose>
	</div>
</div>
</c:if>