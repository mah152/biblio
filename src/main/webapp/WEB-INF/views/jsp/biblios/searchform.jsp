<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

  <h1>Search</h1>

	<br />

	<spring:url value="/biblios/dosearch" var="searchUrl" />

	<!-- form:form class="form-horizontal" method="post" modelAttribute="searchform" action="${searchUrl}/title123" -->
	<form:form class="form-horizontal" method="post" modelAttribute="searchform" action="${searchUrl}">

		<form:hidden path="title" />
<!--
		<spring:bind path="author">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Author</label>
				<div class="col-sm-10">
					<form:input path="author" type="text" class="form-control " id="author" placeholder="Author" />
					<form:errors path="author" class="control-label" />
				</div>
			</div>
		</spring:bind>
-->

		<spring:bind path="title">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Title</label>
				<div class="col-sm-10">
					<form:input path="title" class="form-control" id="title" placeholder="Title" />
					<form:errors path="title" class="control-label" />
				</div>
			</div>
		</spring:bind>

<!--	
		<spring:bind path="year">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Year</label>
				<div class="col-sm-10">
					<form:input path="year" class="form-control" id="year" placeholder="Year" />
					<form:errors path="year" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="journal">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Journal</label>
				<div class="col-sm-10">
					<form:input path="journal" class="form-control" id="journal" placeholder="Journal" />
					<form:errors path="journal" class="control-label" />
				</div>
			</div>
		</spring:bind>
-->
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
           <button type="submit" class="btn-lg btn-primary pull-right">Search</button>
			</div>
		</div>
	</form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
