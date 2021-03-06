<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="/" var="urlHome" />
<spring:url value="/biblios" var="urlBib" />

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<body>

	<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>

		<h1>Bibliography List</h1>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Author</th>
					<th>Title</th>
					<th>Year</th>
					<th>Journal</th>
					<th>BibtexKey</th>
					<th>Pages</th>
					<th>Volume</th>
					<th>Number</th>
				</tr>
			</thead>

			<c:forEach var="biblio" items="${biblios}">
				<tr>
					<td>
						${biblio.id}
					</td>
					<td>${biblio.author}</td>
					<td>${biblio.title}</td>
					<td>${biblio.year}</td>
					<td>${biblio.journal}</td>
					<td>${biblio.bibtexkey}</td>
					<td>${biblio.pages}</td>
					<td>${biblio.volume}</td>
					<td>${biblio.number}</td>
          <td class="active"><a href="${urlBib}/${biblio.id}/update">Update</a></td>
          <td class="active"><a href="${urlBib}/${biblio.id}/delete">Delete</a></td>
      	</tr>

			</c:forEach>
		</table>

	</div>

	<jsp:include page="../fragments/footer.jsp" />

</body>
</html>
