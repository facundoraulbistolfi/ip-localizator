<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
	<div class="container-fluid bg-dark text-white col h-100 centered">
		<div>
			<h1>${ip}</h1>
			<div class="row">
				<div class="col-sm-6">
					<p>
						<strong>Pais:</strong> ${nombre} (${nombreNat})
					</p>
					<p>
						<strong>Codigo ISO:</strong> ${codigo}
					</p>
					<p>
						<strong>Coordenadas geograficas:</strong> (${latitud} ;
						${longitud})
					</p>
					<p>
						<strong>Distancia a Buenos Aires:</strong> ${distancia} Km
					</p>
					<p>
						<strong>Bandera:</strong>
					</p>
					<img src=${bandera } style="width: 300px" class="img-rounded"
						alt="bandera">
				</div>

				<div class="col-sm-6">
					<p>
						<strong>Idiomas oficiales</strong>
					</p>
					<ul>
						<c:forEach items="${idiomas}" var="idioma">

							<li><c:out value="${idioma.codigo3}" /> - <c:out
									value="${idioma.nombre}" /> (<c:out
									value="${idioma.nombreNativo}" />)</li>

						</c:forEach>
					</ul>
					<p>
						<strong>Monedas</strong>
					</p>
					<ul>
						<c:forEach items="${monedas}" var="moneda">
							<li>${moneda.codigo} - <c:out value="${moneda.nombre}" /> <c:if
									test="${moneda.cambio > 0}">
							(1 USD = <fmt:formatNumber type="number" maxFractionDigits="4"
										value="${moneda.cambio}" />
									<c:out value="${moneda.codigo}" /> /  
									1 ${moneda.codigo} = <fmt:formatNumber type="number"
										maxFractionDigits="4" value="${1 / moneda.cambio}" /> USD)
									</c:if></li>

						</c:forEach>
					</ul>
					<p>
						<strong>Zonas horarias y hora actual</strong>
					</p>
					<ul>
						<c:set var="now" value="<%=new java.util.Date()%>" />
						<c:forEach items="${timezones}" var="timezone">
							<fmt:setTimeZone value="GMT${timezone}" />
							<li><c:out value="UTC${timezone}" /> (<fmt:formatDate
									value="${now}" type="both" timeStyle="medium"
									dateStyle="medium" />)</li>
						</c:forEach>
					</ul>
				</div>
			</div>



		</div>
		<br>
		<div class="text-center">
			<a href="/" class="btn btn-primary" role="button">Volver</a>
		</div>

	</div>

</body>
</html>

