<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
	<title>IP-Localizator</title>
	<script src="js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
	<div class="container-fluid bg-dark text-white col h-100 centered">
		<div class="text-center">
			<h1>Ingrese dirección IP:</h1>
			<form id="ipinfo" action="ipinfo" method="post">
				<label>IP: </label> <input type="text" name="ip_address">
				<button class="btn btn-primary" title="Search">
					<i class="material-icons">search</i>
				</button>
			</form>
			<div>
				<ul>

				</ul>
			</div>
		</div>
	</div>
</body>
</html>
