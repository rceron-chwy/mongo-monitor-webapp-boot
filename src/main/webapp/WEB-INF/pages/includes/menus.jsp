<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
      <%-- sidebar start --%>
      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <%--sidebar menu start --%>
              <ul class="sidebar-menu" id="nav-accordion">
                  <li>
                      <a href="${BASE_URL}/index" <c:if test="${menu == 'index'}">class="active"</c:if>>
                          <i class="fa fa-dashboard"></i>
                          <span>Dashboard</span>
                      </a>
                  </li>
     
                   <li class="sub-menu">
                      <a href="javascript:;" <c:if test="${menu == 'hosts'}">class="active"</c:if>>
                          <i class="fa fa-leaf"></i>
                          <span>Hosts</span>
                      </a>
                      <ul class="sub">
                      	  <li <c:if test="${subMenu == 'servers'}">class="active"</c:if>><a  href="${BASE_URL}/hosts/list">Live Stats</a></li>
                          <li <c:if test="${subMenu == 'databases'}">class="active"</c:if>><a  href="${BASE_URL}/databases/list">Databases</a></li>
                          <li <c:if test="${subMenu == 'backups'}">class="active"</c:if>><a  href="${BASE_URL}/backups/list">Backups</a></li>
                          <li <c:if test="${subMenu == 'reports'}">class="active"</c:if>><a  href="${BASE_URL}/reports/list">Reports</a></li>
                          <li <c:if test="${subMenu == 'alerts'}">class="active"</c:if>><a  href="${BASE_URL}/alerts/list">Alerts</a></li>
                      </ul>
                  </li>
                  
                   <li class="sub-menu">
                      <a href="javascript:;" <c:if test="${menu == 'application'}">class="active"</c:if>>
                          <i class="fa fa-eye"></i>
                          <span>Application</span>
                      </a>
                      <ul class="sub">
                          <li <c:if test="${subMenu == 'alerts'}">class="active"</c:if>><a  href="${BASE_URL}/view/alerts">View Alerts</a></li>
                          <li <c:if test="${subMenu == 'notifications'}">class="active"</c:if>><a  href="${BASE_URL}/view/notifications">View Notifications</a></li>
                      </ul>
                  </li>                          
                  
                  <security:authorize ifAnyGranted="ROLE_MANAGER">
                  <li class="sub-menu">
                      <a href="javascript:;" <c:if test="${menu == 'settings'}">class="active"</c:if>>
                          <i class="fa fa-cogs"></i>
                          <span>Settings</span>
                      </a>
                      <ul class="sub">
                          <li <c:if test="${menu == 'settings' && subMenu == 'alerts'}">class="active"</c:if>><a  href="${BASE_URL}/settings/alerts">Alerts</a></li>
                          <li <c:if test="${subMenu == 'notifications'}">class="active"</c:if>><a  href="${BASE_URL}/settings/notifications">Notifications</a></li>
                          <li <c:if test="${subMenu == 'email'}">class="active"</c:if>><a  href="${BASE_URL}/settings/email">Email</a></li>
                          <li <c:if test="${subMenu == 'ldap'}">class="active"</c:if>><a  href="${BASE_URL}/settings/ldap">LDAP</a></li>
                      </ul>
                  </li>
                  </security:authorize> 
                  
                  <security:authorize ifAnyGranted="ROLE_ADMIN">
                  <li class="sub-menu">
                      <a href="javascript:;" <c:if test="${menu == 'administration'}">class="active"</c:if>>
                          <i class="fa fa-cogs"></i>
                          <span>Administration</span>
                      </a>
                      <ul class="sub">
                          <li <c:if test="${subMenu == 'servers'}">class="active"</c:if>><a  href="${BASE_URL}/server/list">Servers</a></li>
                          <li <c:if test="${subMenu == 'users'}">class="active"</c:if>><a  href="${BASE_URL}/user/list">Users</a></li>
                          <li <c:if test="${subMenu == 'logs'}">class="active"</c:if>><a  href="${BASE_URL}/view/logs">Logs</a></li>
                      </ul>
                  </li>                                        
				  </security:authorize>
              </ul>
              <%--sidebar menu end --%>
          </div>
      </aside>
      <%-- sidebar end --%>