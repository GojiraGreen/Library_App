<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Account"></c:param>
</c:import>

<sql:transaction dataSource="jdbc/library">

	<sql:query sql="select * from books where id=?" var="results">
		<sql:param>${requestScope.bookId }</sql:param>
	</sql:query>
	
</sql:transaction>

<c:set scope="page" var="book" value="${results.rows[0]}" />

<c:choose>
	<c:when test="${USER == null }">
		<c:redirect url="/library"/>
	</c:when>	
	<c:otherwise>
	<div style=" margin-top: 40px;" class="span4"></div>
	<div class="container">
		<div class="row">
			<h3>Welcome to your account page.</h3>
			<c:choose>
				<c:when test="${book != null }">
					<table class="table table-bordered table-condensed">
						<tr>
		           			<td>${book.title}</td>
		           			<td>${book.author}</td>
		           		</tr>
		        	</table>			
				</c:when>
				<c:otherwise>
					<p>Currently you don't have a book request.</p>
				</c:otherwise>			
			</c:choose>

		</div>
	</div>	
	</c:otherwise>
</c:choose>

<c:import url="/base/footer.jsp"></c:import>