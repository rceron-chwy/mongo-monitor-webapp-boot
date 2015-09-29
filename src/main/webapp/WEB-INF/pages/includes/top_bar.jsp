
	<%--header start--%>
	<header class="header white-bg">
          <div class="sidebar-toggle-box">
              <div data-original-title="Toggle Navigation" data-placement="right" class="fa fa-bars tooltips"></div>
          </div>
          <%--logo start--%>
          <a href="${BASE_URL}/index" class="logo" >Mongo<span>Dash</span></a>
          <%--logo end--%>
 
          <div class="top-nav ">
              <ul class="nav pull-right top-menu">
                  <%-- user login dropdown start--%>
                  <li class="dropdown">
                      <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                          <img alt="" src="${BASE_URL}/img/avatar1_small.jpg">
                          <span class="username"><security:authentication property="principal.name"/></span>
                          <b class="caret"></b>
                      </a>
                      <ul class="dropdown-menu extended logout">
                          <div class="log-arrow-up"></div>
                          <li><a href="${BASE_URL}/logout"><i class="fa fa-key"></i> Log Out</a></li>
                      </ul>
                  </li>
                  <%-- user login dropdown end --%>
              </ul>
          </div>
      </header>
      <%--header end--%>
