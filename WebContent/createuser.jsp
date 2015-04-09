<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Create account"></c:param>
</c:import>

		<!-- Page Content -->
	<div class="container">
	
		<div class="row text-center">
	
			<h3>Create account</h3>
			<h2>${message}</h2>
			
			<div class="login-form">
				<form action="${pageContext.request.contextPath }/library" method="post">
					<input type="hidden" name="message" />
					<input type="hidden" name="action" value="createaccount" />
					Email:<input type="text"name="email" value="${email}" /><br />
					Password:<input type="password" name="password" value="${password}" /><br />
					Repeat password:<input type="password" name="repeatpassword" value="" /><br />
					<input type="submit" value="Create" />
				</form>
			</div>
		</div>
	</div>
	<!-- /.row -->

<!-- /.container -->

<c:import url="/base/footer.jsp"></c:import>