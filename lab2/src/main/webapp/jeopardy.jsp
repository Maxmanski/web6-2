<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="at.ac.tuwien.big.we15.lab2.api.Avatar"%>
<%@ page import="at.ac.tuwien.big.we15.lab2.api.User"%>
<%@ page import="at.ac.tuwien.big.we15.lab2.api.impl.SimpleQuestion"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:catch var="ex">
<jsp:useBean id="categories"
	type="java.util.List<at.ac.tuwien.big.we15.lab2.api.impl.SimpleCategory>"
	scope="session" />
<jsp:useBean id="user" type="at.ac.tuwien.big.we15.lab2.api.User"
	scope="session" />
<jsp:useBean id="opponent" type="at.ac.tuwien.big.we15.lab2.api.User"
	scope="session" />
<jsp:useBean id="currentQuestion" scope="session"
	class="at.ac.tuwien.big.we15.lab2.api.impl.SimpleQuestion" />
<jsp:useBean id="currentAiQuestion" scope="session"
	class="at.ac.tuwien.big.we15.lab2.api.impl.SimpleQuestion" />

<%
	final Avatar avatar = user.getAvatar();
	final Avatar opponentAvatar = opponent.getAvatar();
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Business Informatics Group Jeopardy! - Fragenauswahl</title>
<link rel="stylesheet" type="text/css" href="style/base.css" />
<link rel="stylesheet" type="text/css" href="style/screen.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/framework.js" type="text/javascript"></script>
</head>
<body id="selection-page">

	<a class="accessibility" href="#question-selection">Zur
		Fragenauswahl springen</a>
	<!-- Header -->
	<header role="banner" aria-labelledby="bannerheading">
		<h1 id="bannerheading">
			<span class="accessibility">Business Informatics Group </span><span
				class="gametitle">Jeopardy!</span>
		</h1>
	</header>

	<!-- Navigation -->
	<nav role="navigation" aria-labelledby="navheading">
		<h2 id="navheading" class="accessibility">Navigation</h2>
		<ul>
			<li><a class="orangelink navigationlink" id="logoutlink"
				title="Klicke hier um dich abzumelden" href="LogoutServlet" accesskey="l">Abmelden</a></li>
		</ul>
	</nav>

	<!-- Content -->
	<div role="main">
		<!-- info -->
		<section id="gameinfo" aria-labelledby="gameinfoinfoheading">
			<h2 id="gameinfoinfoheading" class="accessibility">Spielinformationen</h2>
			<section id="firstplayer" class="playerinfo leader"
				aria-labelledby="firstplayerheading">
				<h3 id="firstplayerheading" class="accessibility">Führender
					Spieler</h3>
				<img class="avatar" src="img/avatar/<%=avatar.getImageHead()%>"
					alt="Spieler-Avatar <%=avatar.getName()%>>" />
				<table>
					<tr>
						<th class="accessibility">Spielername</th>
						<td class="playername"><%=user.getName()%> (Du)</td>
					</tr>
					<tr>
						<th class="accessibility">Spielerpunkte</th>
						<td class="playerpoints"><%=user.getScore()%> &euro;</td>
					</tr>
				</table>
			</section>
			<section id="secondplayer" class="playerinfo"
				aria-labelledby="secondplayerheading">
				<h3 id="secondplayerheading" class="accessibility">Zweiter
					Spieler</h3>
				<img class="avatar"
					src="img/avatar/<%=opponentAvatar.getImageHead()%>"
					alt="Spieler-Avatar <%=opponentAvatar.getName()%>" />
				<table>
					<tr>
						<th class="accessibility">Spielername</th>
						<td class="playername"><%=opponent.getName()%></td>
					</tr>
					<tr>
						<th class="accessibility">Spielerpunkte</th>
						<td class="playerpoints"><%=opponent.getScore()%> &euro;</td>
					</tr>
				</table>
			</section>
			<p id="round">
				Frage:
				<%=(Integer)(session.getAttribute("counter")) +1 %>
				/ 10
			</p>
		</section>

		<!-- Question -->
		<section id="question-selection" aria-labelledby="questionheading">
			<h2 id="questionheading" class="black accessibility">Jeopardy</h2>
			<% if(currentQuestion.getCategory() == null){ %>
					<p class="user-info">
					Du hast noch nicht geantwortet: 
			<% }else{ 
					boolean correct = ((Boolean)session.getAttribute("correctUserAnswer")).booleanValue();
				   if(correct){ %>
					<p class="user-info positive-change">
					Du hast richtig geantwortet:
				<% }else{ %>
					<p class="user-info negative-change">
					Du hast falsch geantwortet: -
				<% } 
			  } %>
				<%=currentQuestion.getCategory() == null ? "0" : currentQuestion.getValue()*10%> &euro;
			</p>
			
			<% if(currentQuestion.getCategory() == null){ %>
					<p class="user-info">
					<%=opponent.getName()%> hat noch nicht geantwortet:
			<% }else{ 
				boolean correct = ((Boolean)session.getAttribute("correctOpponentAnswer")).booleanValue();
				   if(correct){ %>
				   		<p class="user-info positive-change">
						<%=opponent.getName()%> hat richtig geantwortet:
				<% }else{ %>
					<p class="user-info negative-change">
					<%=opponent.getName()%> hat falsch geantwortet: -
				<% } 
			 } %>
			<%=currentQuestion.getCategory() == null ? "0" : currentQuestion.getValue()*10%> &euro;
			</p>
			<p class="user-info">
				<%=opponent.getName()%>
			<% if(currentAiQuestion.getCategory() == null){ %>
				hat noch keine Frage gewählt.
			<%}else{ %>
				 hat <%=currentAiQuestion.getCategory().getName()%>
				für &euro; <%=currentAiQuestion.getValue()*10%> gewählt.
			<% } %>
			</p>
			<form id="questionform" action="QuestionServlet" method="post">
				<fieldset>
					<legend class="accessibility">Fragenauswahl</legend>
					<c:forEach items="${categories}" var="category" varStatus="loop"
						begin="0">
						<section class="questioncategory"
							aria-labelledby="heading${loop.index+1}">
							<h3 id="heading${loop.index+1}" class="tile category-title">
								<span class="accessibility">Kategorie: </span>
								<c:out value="${category.name}" />
							</h3>
							<ol class="category_questions">
								<c:forEach items="${category.questions}" var="question"
									varStatus="innerloop">
									<li><input name="question_selection"
										<c:if test="${question.answered}">disabled="disabled"</c:if>
										id="question_${question.id}" value="${question.id}"
										type="radio" /><label class="tile clickable"
										for="question_${question.id}">€ ${10 * question.value}</label></li>
								</c:forEach>
							</ol>
						</section>
					</c:forEach>
				</fieldset>
				<input class="greenlink formlink clickable" name="question_submit"
					id="next" type="submit" value="wählen" accesskey="s" />
			</form>
		</section>

		<section id="lastgame" aria-labelledby="lastgameheading">
			<h2 id="lastgameheading" class="accessibility">Letztes Spielinfo</h2>
			<p>Letztes Spiel: Nie</p>
		</section>
	</div>

	<!-- footer -->
	<footer role="contentinfo">© 2015 BIG Jeopardy!</footer>

	<script type="text/javascript">
		//<![CDATA[

		// initialize time
		$(document).ready(
				function() {
					// set last game
					if (supportsLocalStorage()) {
						var lastGameMillis = parseInt(localStorage['lastGame'])
						if (!isNaN(parseInt(localStorage['lastGame']))) {
							var lastGame = new Date(lastGameMillis);
							$("#lastgame p").replaceWith(
									'<p>Letztes Spiel: <time datetime="'
											+ lastGame.toUTCString() + '">'
											+ lastGame.toLocaleString()
											+ '</time></p>')
						}
					}
				});
		//]]>
	</script>
</body>
</html>
</c:catch>
<c:if test="${not empty ex}">
	<c:redirect url="/login.jsp" />
</c:if>