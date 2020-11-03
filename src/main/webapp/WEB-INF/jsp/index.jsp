<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>IP-Localizator</title>
<script src="js/jquery-3.5.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<style>
.material-icons, .icon-text {
	vertical-align: middle;
}
body {
	background: #343a40 !important;
}
</style>
</head>
<body>
	<div class="container-fluid text-white col centered">
		<div class="text-center">
			<h1>Ingrese dirección IP:</h1>
			<form id="ipinfo" action="ipinfo" method="get">
				<label><strong>IP: </strong></label>
				<!-- <input type="text" name="ip"> -->
				<input type="text" name="ip"
					required="required" placeholder="Ingrese una IP válida"
					pattern="^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\.(?!$)|$)){4}$">

				<button class="btn btn-primary" title="Search">
					<i class="material-icons">search</i>
				</button>
			</form>
			<div>

				<p>
					<i class="material-icons">public</i> <label>Distancia más
						corta: <fmt:formatNumber type="number" maxFractionDigits="2"
							value="${minDist}" /> Km
					</label>
				</p>
				<p>
					<i class="material-icons">public</i> <label>Distancia más
						larga: <fmt:formatNumber type="number" maxFractionDigits="2"
							value="${maxDist}" /> Km
					</label>
				</p>
				<p>
					<i class="material-icons">public</i> <label>Distancia
						promedio: <fmt:formatNumber type="number" maxFractionDigits="2"
							value="${avgDist}" /> Km
					</label>
				</p>

			</div>
		</div>
	</div>
</body>
</html>
