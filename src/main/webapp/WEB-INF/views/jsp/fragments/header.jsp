<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>Bibliography Management System</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/biblios/add" var="urlAdd" />
<spring:url value="/biblios/export" var="urlExp" />
<spring:url value="/biblios/import" var="urlImp" />
<spring:url value="/biblios/search" var="urlSearch" />

<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlHome}">Bibliography Management System</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlAdd}">Add Bibliography</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlExp}">Export to Bibtex</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlImp}">Import from Bibtex</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlSearch}">Search</a></li>
			</ul>
	</div>
	</div>
</nav>
