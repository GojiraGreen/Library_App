<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Account"></c:param>
</c:import>

<c:choose>
	<c:when test="${USER == null }">
		<c:redirect url="${pageContext.request.contextPath }/library"/>
	</c:when>	
	<c:otherwise>
	<div style=" margin-top: 40px;" class="span4"></div>
	<div class="container">
		<div class="row">
			<h2>Welcome to your account page.</h2>
		</div>
	</div>	
	</c:otherwise>
</c:choose>

<c:import url="/base/footer.jsp"></c:import>