<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:choose>
		<c:when test="${biblioForm['new']}">
			<h1>Add</h1>
		</c:when>
    <c:otherwise>
      <h1>Update</h1>
    </c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/biblios" var="biblioActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="biblioForm" action="${biblioActionUrl}">

		<form:hidden path="id" />

		<spring:bind path="author">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Author</label>
				<div class="col-sm-10">
					<form:input path="author" type="text" class="form-control " id="author" placeholder="Author" />
					<form:errors path="author" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="title">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Title</label>
				<div class="col-sm-10">
					<form:input path="title" class="form-control" id="title" placeholder="Title" />
					<form:errors path="title" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
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
		
		<spring:bind path="bibtexkey">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">BibtexKey</label>
				<div class="col-sm-10">
					<form:input path="bibtexkey" class="form-control" id="bibtexkey" placeholder="BibtexKey" />
					<form:errors path="bibtexkey" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="pages">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Pages</label>
				<div class="col-sm-10">
					<form:input path="pages" class="form-control" id="pages" placeholder="Pages" />
					<form:errors path="pages" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="volume">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Volume</label>
				<div class="col-sm-10">
					<form:input path="volume" class="form-control" id="volume" placeholder="Volume" />
					<form:errors path="volume" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="number">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Journal</label>
				<div class="col-sm-10">
					<form:input path="number" class="form-control" id="number" placeholder="Number" />
					<form:errors path="number" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${biblioForm['new']}">
						<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
          </c:when>
          <c:otherwise>
            <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
          </c:otherwise>
        </c:choose>

			</div>
		</div>
	</form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
