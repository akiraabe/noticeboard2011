<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>person Create</title>
</head>
<body>
<p>Personの新規作成</p>
<hr/>
<ul>
<c:forEach var="e" items="${f:errors()}">
<li>${f:h(e)}</li>
</c:forEach>
</ul>

<form method="post" action="insert">
<table>
	<tr>
		<td> ファーストネーム </td>
		<td>
			<input type="text" ${f:text("firstName")} class="${f:errorClass('firstName', 'err')}" size="40" maxlength="20" />
		</td>	
	</tr>
	<tr>
		<td> ラストネーム </td>
		<td>
			<input type="text" ${f:text("lastName")} class="${f:errorClass('lastName', 'err')}" size="40" maxlength="20" />
		</td>	
	</tr>
	<tr>
		<td> 行き先 </td>
		<td>
			<input type="text" ${f:text("place")} class="${f:errorClass('place', 'err')}" size="40" maxlength="20" />
		</td>	
	</tr>
</table>
<input type="submit" value="登録"/>
</form>
<br/>
<hr/>
<a href="${f:url('/')}">index</a>
</body>
</html>
