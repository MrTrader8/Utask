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
%>
<!doctype html>
<html lang="en">
<head>
<title>Your Drive Search Results</title>
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
		style="background-image: url(images/arcLibrary.png)";>
		<div class="container">
			<div
				class="row align-items-center resultDisplay-inner justify-content-center">
				<div class="col-md-8 text-center">
					<div class="mb-5 element-animate">
						<h5>
							<br> <br> <br> <br> <br>Drive Search
							Results
						</h5>
						<p>Here's what we found in UTask Drive relating to your topic:
						</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- END section -->

	<section class="resultPage" style="background: #E9E9E9";>
		<div class="container">
					<form action="DriveResultServlet" method="post">
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
			</form>
        <!--Search Results should be added HERE ------------------------------->
        <!--Choose between the default 6 results per page --------------------->
        <!--OR Add results dynamically and extend the page. ------------------->
        <% ArrayList<Document> list = (ArrayList<Document>) request.getAttribute("LuceneServlet");
        System.out.println("jsp size: " + list.size());
        cks = request.getCookies();
        String query = "";
		if (cks != null) {
			for (int i = 0; i < cks.length; i++) {
				String name = cks[i].getName();
				String value = cks[i].getValue();
				if (name.equals("query")) {
				  	System.out.println("Drive page cookie: " + value);
					query = value;
					break;
				}
				i++;
			}
		}
			if(list.size() == 0){
			 %> 
              <a><strong>No Results Found</strong><br></a>
              <p class="docPreview">
				 <strong>We Recommend:</strong><br>
              - Making sure that all words are spelled correctly<br>
              - Trying different keywords<br>
              - Generalizing your search<br>
              - Using fewer keywords<br>
              - Search the Web Instead<br>
              - Running your search on an all-purpose search engine<br>
                </p>
<%
			}
       		for(Document doc: list){
       		  %>
              <div class='searchResult'>
              <a href=<%=doc.get("filepath").substring(doc.get("filepath").indexOf("uploadfiles"))%> download=<%=doc.get("filename")%>><%=doc.get("filename")%><br></a>
              <p class="infoFlag">Uploaded By:</p>
              <p class="docInfo"><%=DatabaseHelper.getNameGivenId(Integer.parseInt(doc.get("userid")))%></p>
              <p class="infoFlag">Type:</p>
              <p class="docInfo"><%=luceneDirectory.getExtension(doc.get("filepath"))%></p>
              <p class="infoFlag">Size:</p>
              <p class="docInfo"><%=WebUtils.convertSize(Long.parseLong(doc.get("size")))%></p>
              <p class="docPreview"><%=luceneDirectory.generatePreview(doc, query)%></p>
          </div>
          <%
       		}
        %>
          <!-- Show All Results Button DELETED ---------------------------------------->
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
