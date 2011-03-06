<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>noticeboard2011 create group</title>
</head>
<body>
<p>Hello create group !!!</p>
<hr/>
<ul>
<c:forEach var="e" items="${f:errors()}">
<li>${f:h(e)}</li>
</c:forEach>
</ul>

<form method="post" action="insert">
<table>
	<tr>
		<td> Group name </td>
		<td>
			<input type="text" ${f:text("name")} class="${f:errorClass('name', 'err')}" size="20" maxlength="20" />
		</td>	
	</tr>
	<tr>
		<td> Description </td>
		<td>
			<input type="text" ${f:text("description")} class="${f:errorClass('description', 'err')}" size="30" maxlength="30" />
		</td>	
	</tr>
</table>
<input type="submit" value="Submit"/>
</form>
<br/>
<hr/>
<a href="${f:url('/group/list')}">Group list</a>
</body>
</html>
