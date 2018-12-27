<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.lucene.document.Document"%>
<%@ page import="com.utask.lucene.LuceneHelper"%>
<%@ page import="com.utask.lucene.luceneDirectory"%>
<%@ page import="com.utask.webHelper.WebUtils"%>
<%@ page import="com.utask.databaseHelper.DatabaseHelper"%>

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
			if(!loginBool) {
			  	String URL = "auth.jsp";
			    response.sendRedirect(URL);
			    return;
			}
			System.out.println("login bool sDrive: " + loginBool);
	%>
<!doctype html>
<html lang="en">
<head>
<title>UTask Student Drive</title>
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

				<div class="collapse navbar-collapse navbar-light" id="uTaskDefaultNavBar">
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
					<li class="nav-item">
					  <a class="nav-link" href="index.jsp">Home</a>
					</li>
					<li class="nav-item">
					  <a class="nav-link active" href="studentDrive.jsp">Drive</a>
					</li>
					<li class="nav-item">
					  <a class="nav-link" href="http://www.acorn.utoronto.ca">Acorn</a>
					</li>
					<li class="nav-item">
					  <a class="nav-link" href="https://q.utoronto.ca">Quercus</a>
					</li>
					<li class="nav-item">
					  <a class="nav-link" href="https://www.studentlife.utoronto.ca/as">Accessibility</a>
					</li>
					<li class="nav-item">
					  <a class="nav-link" href="https://webmail.utoronto.ca/">Webmail</a>
					</li>
					<li class="nav-item">
					<%if (loginBool) { %>
					  <a href="index.jsp" class="nav-link" onclick="deleteAllCookies()">Logout</a>
					  <%} else { %>
					  <a class="nav-link" href="auth.jsp">Login</a>
					  <%} %>
					</li>
				  </ul>
				</div>
			</div>
		</nav>
	</header>
	<!-- END header -->
	<script type="text/javascript">
		function upload() {
			window.location.href = 'Upload.jsp'
		}
	</script>

	<!-- Decorative banner at the top of the page ----------------------------->
	<section class="resultDisplay resultDisplay-innerpage overlay"
		data-stellar-background-ratio="1.0"
		style="background-image: url(images/UTSCcover.png)";>
		<div class="container">
			<div
				class="row align-items-center resultDisplay-inner justify-content-center">
				<div class="col-md-8 text-center">
					<div class="mb-5 element-animate">
						<h5 id="uTaskLogo">
							<br> <br>UTask
						</h5>
						<h5 id="DriveName">DRIVE</h5>
						<p>Secure and Easy Access to All Your Files</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- END section -->
	<section class="spacer" style="background: #01346D; padding: 1px;"></section>

	<section class="UTaskDriveSearch" style="background: #ffffff;">

		<div class="container"
			style="padding-top: 20px; display: inline; align-items: center;">
			<!-- Top Section -------------------------------------------------->
			<form action="LuceneServlet" method="post">
				<div>
					<!-- Search Bar ----------------------------------------------->
					<input type="text" name="searchtext" placeholder="Drive Search"
						id="searchtext" onkeyup="stoppedTyping()" /> <input
						class="siButton" type="submit" name="search" id="Search"
						value="Search" disabled />
					<!-- Upload Button -------------------------------------------->
					<button type="button" onclick="upload(); return false;">Upload</button>
					<!-- Content Search Toggle ------------------------------------>
				</div>
				<p>Filter Results by Content</p>
				<input class="checkBox" type="checkbox" name="contentcheck"
					id="contentcheck" />
			</form>

		</div>
	</section>
	<!-- END section -->
	<section class="spacer"
		style="background: #01346D; padding: 1px; margin-top: 20px;"></section>

	<section class="UTaskDrive" style="background: #ffffff;">
		<div class="container">
			<!-- SEARCH Bar -->
			<h5 class="myDriveText">My Documents</h5>
			<%
			  // retrieve your list from the request, with casting
						ArrayList<Document> list1 = (ArrayList<Document>) request.getAttribute("myFiles");
						ArrayList<Document> list2 = (ArrayList<Document>) request.getAttribute("sharedFiles");
						luceneDirectory dir = LuceneHelper.init.getDir();
						cks = request.getCookies();
						String userid = "";
						String privilege = "";
						if (cks != null) {
							for (int i = 0; i < cks.length; i++) {
								String name = cks[i].getName();
								String value = cks[i].getValue();
								if (name.equals("userid")) {
									userid = value;
									System.out.println("Found user: " + value);
								}
								if (name.equals("privilege")) {
									privilege = value;
									System.out.println("privilege: " + value);
								}
								i++;
							}
						}
						if (list1 == null) {
							list1 = dir.stringQuery("userid", userid, 100);
						}

						if (list2 == null && privilege.equals(new String("1"))) {
							list2 = dir.getSharedFiles(userid);
						} else if (list2 == null && privilege.equals(new String("0"))) {
							list2 = dir.wildcardqueryParser("filepath", "*", 100);
						}
						// print the information about every category of the list
						for (Document doc : list1) {
			%>
			<div class='file'>
				<img src=<%=luceneDirectory.fileicon(doc)%>> <a
					href=<%=doc.get("filepath").substring(doc.get("filepath").indexOf("uploadfiles"))%>
					download=<%=doc.get("filename")%>><%=doc.get("filename")%></a>
				<p class="fileSize"><%=WebUtils.convertSize(Long.parseLong(doc.get("size")))%></p>
				<form action="ShareServlet" method="post">
					<input class="sharetext" type="submit" name="changeVis"
						value=<%=doc.get("privilege").equals("0") ? "'Make public'" : "'Revert to Private'"%> />
					<input id="filepath" name="filepath" type="hidden"
						value=<%=doc.get("filepath")%>> <input id="privilege"
						name="privilege" type="hidden" value=<%=doc.get("privilege")%>>
				</form>
				<form action="DeleteServlet" method="post">
					<input class="deletetext" type="submit" name="delFile" value="Delete File">
					<input id="filepath2" name="filepath2" type="hidden" value=<%=doc.get("filepath")%>>
				</form>
			</div>
			<%
			  }
			%>
		</div>
	</section>
	<!-- END section -->

	<section class="spacer" style="background: #01346D; padding: 1px"></section>

	<section class="UTaskDrive" style="background: #ffffff";>
		<div class="container">
			<!-- SEARCH Bar -->
			<h5 class="myDriveText">Public Files</h5>

			<!--File should be added here. The container is already configured to
            become larger or smaller based on the number of files displayed. -->
			<!-- Icon files are in ./images/fileIcons/ -->
			<%
			  for (Document doc1 : list2) {
			%>
			<div class='file'>
				<img src=<%=luceneDirectory.fileicon(doc1)%>> <a
					href=<%=doc1.get("filepath").substring(doc1.get("filepath").indexOf("uploadfiles"))%>
					download=<%=doc1.get("filename")%>><%=doc1.get("filename")%></a>
				<p class="fileSize"><%=WebUtils.convertSize(Long.parseLong(doc1.get("size")))%></p>
			</div>
			<%
			  }
			%>
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

	<script type="text/javascript">
		function stoppedTyping() {
			if (document.getElementById("searchtext").value.length > 0) {
				document.getElementById("Search").disabled = false;
			} else {
				document.getElementById("Search").disabled = true;
			}
		}
	</script>

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
