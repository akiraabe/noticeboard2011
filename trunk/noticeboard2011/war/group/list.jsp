<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>noticeboard2011 group List</title>
</head>
<body>
<p>Hello group List !!!</p>
<hr />
<table id="result_list">
	<thead>
		<tr>
			<th width="030">Group name</th>
			<th width="050">Description</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="e" items="${groups}">
			<tr>
				<td>${f:h(e.name)}</td>
				<td>${f:h(e.description)}</td>
				<c:set var="showUrl" value="show/${f:h(e.key)}/${e.version}" />
				<c:set var="editUrl" value="edit/${f:h(e.key)}/${e.version}" />
				<c:set var="deleteUrl" value="delete/${f:h(e.key)}/${e.version}" />
				<td><a href="${f:url(showUrl)}">Show</a></td>
				<td><a href="${f:url(editUrl)}">Edit</a></td>
				<td><a href="${f:url(deleteUrl)}"
					onclick="return confirm('delete OK?')">Delete</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<hr />
<a href="${f:url('create')}">create</a> <a href="${f:url('/')}">index</a>
<br />
<br />
<img
	src="http://code.google.com/appengine/images/appengine-silver-120x30.gif"
	alt="Powered by Google App Engine" />
</body>
</html>