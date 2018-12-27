<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!doctype html>
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
%>
<html lang="en">
<head>
<title>Sign in to UTask</title>
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

	<header role="banner">
		<nav class="navbar navbar-expand-md navbar-dark bg-light">
			<div class="container">
				<a class="navbar-brand" href="index.jsp">UTASK</a>

				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#uTaskDefaultNavBar"
					aria-controls="uTaskDefaultNavBar" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse navbar-light"
					id="uTaskDefaultNavBar">
					<ul class="navbar-nav ml-auto pl-lg-5 pl-0">
						<li class="nav-item dropdown">
		                  <a class="nav-link dropdown-toggle active" href="destination.html"
						  id="dropdown04"
						  data-toggle="dropdown"
						  aria-haspopup="true"
						  aria-expanded="false"></a>
		                  <div class="dropdown-menu" aria-labelledby="dropdown04">
		                    <div class="gcse-search"></div>
		                  </div>
		                </li>
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
		</nav>
	</header>
	<!-- END header -->

	<section class="site-hero overlay" data-stellar-background-ratio="1.0"
		style="background-image: url(images/gate.png);">
		<div class="container">
			<div
				class="row align-items-center site-hero-inner justify-content-center">
				<div class="col-md-8 text-center">

					<div class="headerSection">
						<h2>
							Forgot your password? <br> We Can Help!
						</h2>
					</div>

					<!-- PAGE SWITCH BUTTON ----------------------------------------------->
					<div class="formSwitcher">
						<div class="toggle">
							<i class="toggleButton"></i>

						</div>

						<!-- SIGN UP ------------------------------------------------------>
						<div class="form">
							<h2>Retrieve Your Password</h2>
							<form action="PasswordResetServlet" method="post">
								<input type="text" name="username" placeholder="Username" /> <input
									type="text" name="utorid" placeholder="UTORID" /> <input
									type="text" name="email" placeholder="U of T Email Address" />
								<input type="text" name="uoftnumber"
									placeholder="U of T ID Number" /> <input type="password"
									name="desiredpass" placeholder="Choose a new password"
									id="desiredpass" /> <input type="password" name="confirm"
									placeholder="Confirm your new password" id="confirm" /> <input
									class="siButton" type="submit" name="Signupsubmit"
									value="Reset my password">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- END section -->

	<!-- loader -->
	<div id="loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#f4b214" /></svg>
	</div>

	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/jquery-migrate-3.0.0.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.stellar.min.js"></script>
	<script src="js/main.js"></script>
	<script>
		(function() {
			var cx = '004578432109289487555:dg9dfw6qdug';
			var gcse = document.createElement('script');
			gcse.type = 'text/javascript';
			gcse.async = true;
			gcse.src = 'https://cse.google.com/cse.js?cx=' + cx;
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(gcse, s);
		})();
	</script>

</body>
</html>
