
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Browse"></c:param>
</c:import>

<sql:transaction dataSource="jdbc/library">

	<sql:query sql="select * from books order by title"
		var="results">
	</sql:query>

</sql:transaction>
	
    <!-- Page Content -->
    <div style=" margin-top: 40px;" class="span4"></div>
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
            	<p>${param.message }</p>
            	<p class="lead">Find book in our databse.</p>
            	<table class="table table-bordered table-condensed">
					<tr>
            			<td>title</td>
            			<td>author</td>
            		</tr>
	                <c:forEach var="book" items="${results.rows }" varStatus="row">
		                <c:choose>
	           				<c:when test="${book.available == true }">
	           					<tr class="success">
	           				</c:when>
	           				<c:otherwise>
	           					<tr class="danger">
	           				</c:otherwise>
	           			</c:choose>
		   					<td><c:out value="${book.title }"/></td>
		   					<td><c:out value="${book.author} "/></td>
		   					<c:choose>
		           				<c:when test="${book.available == true }">
		           					<c:choose>
		           						<c:when test="${USER != null }">
		           							<td><a style="color=blue"
		           								href="${pageContext.request.contextPath }/library?action=request&bid=${book.id}">Request</a>
		           							</td>
		           						</c:when>
		           						<c:otherwise>
		           							<td>Available</td>
		           						</c:otherwise>

		           					</c:choose>
		           				</c:when>
		           				<c:otherwise>
		           					<td style="color: red;" >Not available</td>
		           				</c:otherwise>
	           				</c:choose>
					</c:forEach>
				</table>
            </div>
        </div>
        <!-- /.row -->

    </div>
    <!-- /.container -->

<c:import url="/base/footer.jsp"></c:import>