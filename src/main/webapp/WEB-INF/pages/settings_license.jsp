<%@ include file="/WEB-INF/pages/includes/tags.jsp" %>	
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${BASE_URL}/img/favicon.png">
    <title>MongoDASH - 500</title>
    <!-- Bootstrap core CSS -->
    <link href="${BASE_URL}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${BASE_URL}/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="${BASE_URL}/css/font-awesome.min.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="${BASE_URL}/css/style.css" rel="stylesheet">
    <link href="${BASE_URL}/css/style-responsive.css" rel="stylesheet" />
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>
  <body class="body-license-key">
    <div class="container">
      <section class="error-wrapper">
          <i class="icon-500"></i>
          <h1>Activation</h1>
          <p class="page-license-key">Please enter your license key.</p>
          <form:form id="license_form" method="POST" action="${BASE_URL}/settings/license/save" modelAttribute="settings" class="form-horizontal">
			<div class="form-body">			
				<div class="form-group">				
					<form:input path="privateKey" class="form-control" required="required"/>
				</div>
				<form:button class="btn btn-danger btn-shadow btn-lg"><i class="fa fa-save fa-fw fa-lg"></i>Save</form:button>													
			</div>        
          </form:form>
      </section>
    </div>
  </body>
</html>