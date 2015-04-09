
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Account"></c:param>
</c:import>


<c:if test="${ADMIN == null }">
	<c:redirect url="/library" />
</c:if>



<sql:transaction dataSource="jdbc/library">

	<sql:query sql="select * from users order by id" var="results">
	</sql:query>

</sql:transaction>

<!-- Page Content -->
<div style="margin-top: 40px;" class="span4"></div>
<div class="container">

	<div class="row">
		<div class="col-lg-12 text-center">
			<p class="lead">List of users.</p>
			<table class="table table-bordered table-condensed">
				<tr>
					<td>email</td>
					<td>book_id</td>
				</tr>
				<c:forEach var="user" items="${results.rows }" varStatus="row">
					<c:choose>
						<c:when test="${user.book_id == null }">
							<tr class="success">
						</c:when>
						<c:otherwise>
							<tr class="danger">
						</c:otherwise>
					</c:choose>
					<td><c:out value="${user.email }" /></td>
					<td><c:out value="${user.book_id} " /></td>
					<c:choose>
						<c:when test="${user.book_id == null }">
							<td><a href="${pageContext.request.contextPath }/library?action=du&uid=${user.id}">Delete user</a></td>
						</c:when>
						<c:otherwise>
							<td><a
								href="${pageContext.request.contextPath }/library?action=dr&uid=${user.id}&rbid=${user.book_id}">Delete
									request</a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</table>
			<p>${param.message }</p>
		</div>
	</div>
	<!-- /.row -->

</div>
<!-- /.container -->

<c:import url="/base/footer.jsp"></c:import>