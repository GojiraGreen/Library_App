<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Request"></c:param>
</c:import>
<sql:transaction dataSource="jdbc/library">

</sql:transaction>

	
	<!-- Page Content -->
	<div style=" margin-top: 40px;" class="span4"></div>
	<div class="container">
	
		<div class="row text-center">
			<c:choose>
				<c:when test="${USER == null }">
					<c:redirect url="/library?action=login"/>
				</c:when>
				<c:when test="${fail != null}">
					<p>${message }</p>
	           	</c:when>
				<c:otherwise>
					<p>Book successfully requested.</p>
					<p>Visit library to aquire requested book.</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<!-- /.row -->

<!-- /.container -->

<c:import url="/base/footer.jsp"></c:import>