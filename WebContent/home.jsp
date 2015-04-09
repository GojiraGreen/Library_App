<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<c:import url="/base/header.jsp">
	<c:param name="title" value="Home"></c:param>
</c:import>
	<!-- .container -->
	<div style=" margin-top: 40px;" class="span4"></div>
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <h2>Welcome to the Library!</h2>
                <c:if test="${ USER != null}">
                	<h3>Logged as ${USER}</h3>
                </c:if>
            </div>
        </div>
        <!-- /.row -->

    </div>
    <!-- /.container -->

<c:import url="/base/footer.jsp"></c:import>
