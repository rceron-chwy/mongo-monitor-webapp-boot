<%@ include file="/WEB-INF/pages/includes/tags.jsp" %>	
<%@ taglib tagdir="/WEB-INF/tags" prefix="dash" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="rafael.ceron, thiago.locatelli">
    <meta name="keyword" content="MongoDash MongoDB Mongo Database DB Dashboard">
    <link rel="shortcut icon" href="img/favicon.png">

    <title>MongoDASH</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="css/font-awesome.min.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/style-responsive.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

  <body class="login-body">

    <div class="container">
	<%-- j_spring_security_check --%>
      <form class="form-signin" action="loginCheck" method="POST">
      	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <h2 class="form-signin-heading">sign in now</h2>
        <div class="login-wrap">
        	
            <input type="text" name="username" class="form-control" placeholder="User ID" autofocus value="guest" />
            <input type="password" name="password" class="form-control" placeholder="Password" value="!Guest2014!" />
            <label class="checkbox">
                <input type="checkbox" name="remember" />Remember me
                <span class="pull-right">
                    <a data-toggle="modal" href="#myModal"> Forgot Password?</a>
                </span>
            </label>
            <button class="btn btn-lg btn-login btn-block" type="submit">Sign in</button>
            <c:if test="${param.error}">
            	 <span class="failure"><spring:message code="login.authenticationFailure" /></span>
           	</c:if>
        </div>
        

          <!-- Modal -->
          <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                          <h4 class="modal-title">Forgot Password ?</h4>
                      </div>
                      <div class="modal-body">
                          <p>Enter your e-mail address below to reset your password.</p>
                          <input type="text" name="email" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">

                      </div>
                      <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                          <button class="btn btn-success" type="button">Submit</button>
                      </div>
                  </div>
              </div>
          </div>
          <!-- modal -->
      </form>
    </div>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
  
</html>