<%@ include file="/WEB-INF/pages/includes/tags.jsp" %>	
<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link rel="shortcut icon" href="${BASE_URL}/img/favicon.png">
	    <title>MongoDASH</title>
	    
	    <%-- Bootstrap core CSS --%>
	    <link rel="stylesheet" type="text/css" href="${BASE_URL}/css/bootstrap.min.css" >
	    <link rel="stylesheet" href="${BASE_URL}/css/bootstrap-reset.css" >
	    
	    <%-- external css --%>
	    <link rel="stylesheet" type="text/css" href="${BASE_URL}/css/font-awesome.min.css"  />
	    <link rel="stylesheet" type="text/css" href="${BASE_URL}/css/jquery.gritter.css" />
	    
	    <%-- Custom styles for this template --%>
	    <link rel="stylesheet" type="text/css" href="${BASE_URL}/css/style.css" >
	    <link rel="stylesheet" type="text/css" href="${BASE_URL}/css/style-responsive.css"  />
	    
	    
		<c:if test="${styles != null}">
			<%-- page css --%>
			<c:forEach items="${styles}" var="style">
		<link rel="stylesheet" type="text/css" href="${BASE_URL}/${style.file}">				
			</c:forEach>
		</c:if>	
	    
	    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
	    <!--[if lt IE 9]>
	      <script src="js/html5shiv.js"></script>
	      <script src="js/respond.min.js"></script>
	    <![endif]-->
  	</head>
  <body>

  <section id="container" class="">
