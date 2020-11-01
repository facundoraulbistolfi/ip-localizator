<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<title>IP-Localizator</title>
</head>

<body>
	<h2>Ingrese dirección IP:</h2>
	<form id="ipinfo" action="ipinfo" method="post">
		<label>IP: </label> <input type="text" name="ip_address">
		<button>Submit</button>
	</form>
</body>

</html>
