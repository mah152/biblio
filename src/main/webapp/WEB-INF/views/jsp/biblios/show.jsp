<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>

	<h1>Detail</h1>
	<br />

	<div class="row">
		<label class="col-sm-2">ID</label>
		<div class="col-sm-10">${biblio.id}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Author</label>
		<div class="col-sm-10">${biblio.author}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Title</label>
		<div class="col-sm-10">${biblio.title}</div>
	</div>
	
	<div class="row">
		<label class="col-sm-2">Year</label>
		<div class="col-sm-10">${biblio.year}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Journal</label>
		<div class="col-sm-10">${biblio.journal}</div>
	</div>
	
	<div class="row">
		<label class="col-sm-2">BibtexKey</label>
		<div class="col-sm-10">${biblio.bibtexkey}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Pages</label>
		<div class="col-sm-10">${biblio.pages}</div>
	</div>
	
	<div class="row">
		<label class="col-sm-2">Volume</label>
		<div class="col-sm-10">${biblio.volume}</div>
	</div>

	<div class="row">
		<label class="col-sm-2">Number</label>
		<div class="col-sm-10">${biblio.number}</div>
	</div>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>