<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Login"></c:param>
</c:import>

	<!-- Page Content -->
	<div class="container">
	
		<div class="row text-center">
	
			<h3>Administrator login page</h3>
			<h2>${message}</h2>
			
			<div class="login-form">
				<form action="${pageContext.request.contextPath }/library" method="post">
					<input type="hidden" name="message" />
					<input type="hidden" name="action" value="dologinadmin" />
					Email:<input type="text"name="email" value="${email}" /><br />
					Password:<input type="password" name="password" value="${password}" /><br />
					<input type="submit" value="OK" />
				</form>
			</div>
		</div>
	</div>
	<!-- /.row -->

<!-- /.container -->

<c:import url="/base/footer.jsp"></c:import>