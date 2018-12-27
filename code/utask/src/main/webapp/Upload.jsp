<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%!public boolean loginBool = false;%>
<%
  Cookie[] cks = request.getCookies();
  if (cks != null) {
    for (int i = 0; i < cks.length; i++) {
      String name = cks[i].getName();
      String value = cks[i].getValue();
      if (name.equals("userid")) {
        System.out.println("userid" + value);
        loginBool = true;
        break; // exit the loop and continue the page
      }
      i++;
    }
  }
  if (!loginBool) {
    String URL = "auth.jsp";
    response.sendRedirect(URL);
    return;
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>File Upload</title>
<link rel="shortcut icon" type="image/png" href="images/favicon.png" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700,900|Raleway"
	rel="stylesheet">

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/owl.carousel.min.css">

<link rel="stylesheet" href="fonts/ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="fonts/fontawesome/css/font-awesome.min.css">

<!-- Theme Style -->
<link rel="stylesheet" href="css/style.css">
</head>


<body>
	<header role="banner"> <nav
		class="navbar navbar-expand-md navbar-dark bg-light">
	<div class="container">
		<a class="navbar-brand" href="index.jsp">UTASK</a>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#uTaskDefaultNavBar" aria-controls="uTaskDefaultNavBar"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse navbar-light"
			id="uTaskDefaultNavBar">
			<ul class="navbar-nav ml-auto pl-lg-5 pl-0">
				<li class="nav-item"><a class="nav-link active"
					href="index.jsp">Home</a></li>
				<li class="nav-item"><a class="nav-link"
					href="studentDrive.jsp">Drive</a></li>
				<li class="nav-item"><a class="nav-link"
					href="http://www.acorn.utoronto.ca">Acorn</a></li>
				<li class="nav-item"><a class="nav-link"
					href="https://q.utoronto.ca">Quercus</a></li>
				<li class="nav-item"><a class="nav-link"
					href="https://www.studentlife.utoronto.ca/as">Accessibility</a></li>
				<li class="nav-item"><a class="nav-link"
					href="https://webmail.utoronto.ca/">Webmail</a></li>
				<li class="nav-item">
					<%
					  if (loginBool) {
					%> <a href="index.jsp" class="nav-link"
					onclick="deleteAllCookies()">Logout</a> <%
   } else {
 %> <a class="nav-link" href="auth.jsp">Login</a> <%
   }
 %>
				</li>
			</ul>
		</div>
	</div>
	</nav> </header>
	<!-- END header -->

	<section class="site-hero overlay" data-stellar-background-ratio="1.0"
		style="background-image: url(images/UTSC.png);">
	<div class="container">
		<div
			class="row align-items-center site-hero-inner justify-content-center">
			<div class="col-md-8 text-center">

				<div class="headerSection"></div>

				<!-- PAGE SWITCH BUTTON ----------------------------------------------->
				<div class="formSwitcher">
					<div class="toggle">
						<i class="toggleButton"></i>

					</div>

					<!-- SIGN UP ------------------------------------------------------>
					<div class="form" style="">
						<h2>Upload a New File</h2>
						<form method="post" action="UploadServlet"
							enctype="multipart/form-data">
							Choose a file from your computer: <input type="file" name="file"
								size="60" /> <input type="text" name="fileName"
								placeholder="Choose a File Name" /> <input class="siButton"
								type="submit" value="Upload" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>

	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/jquery-migrate-3.0.0.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.stellar.min.js"></script>
		<script>
		function deleteAllCookies() {
			var cookies = document.cookie.split(";");

			for (var i = 0; i < cookies.length; i++) {
				var cookie = cookies[i];
				var eqPos = cookie.indexOf("=");
				var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
				sessionStorage.clear();
	<%loginBool = false;%>
		document.cookie = name
						+ "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
			}
		}
	</script>

</body>
</html>
