<%@page import="at.ac.tuwien.big.we15.lab2.api.Avatar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="categories" type="java.util.List<at.ac.tuwien.big.we15.lab2.api.impl.SimpleCategory>" scope="session" />
<jsp:useBean id="user" type="at.ac.tuwien.big.we15.lab2.api.User" scope="session" />
<jsp:useBean id="opponent" type="at.ac.tuwien.big.we15.lab2.api.User" scope="session" />
<% final Avatar avatar = user.getAvatar(); %>
<% final Avatar opponentAvatar = opponent.getAvatar(); %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Jeopardy! - Fragenauswahl</title>
        <link rel="stylesheet" type="text/css" href="style/base.css" />
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
   </head>
   <body id="selection-page">
      
      <a class="accessibility" href="#question-selection">Zur Fragenauswahl springen</a>
      <!-- Header -->
      <header role="banner" aria-labelledby="bannerheading">
         <h1 id="bannerheading">
            <span class="accessibility">Business Informatics Group </span><span class="gametitle">Jeopardy!</span>
         </h1>
      </header>
      
      <!-- Navigation -->
		<nav role="navigation" aria-labelledby="navheading">
			<h2 id="navheading" class="accessibility">Navigation</h2>
			<ul>
				<li><a class="orangelink navigationlink" id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
			</ul>
		</nav>
      
      <!-- Content -->
      <div role="main"> 
         <!-- info -->
         <section id="gameinfo" aria-labelledby="gameinfoinfoheading">
            <h2 id="gameinfoinfoheading" class="accessibility">Spielinformationen</h2>
            <section id="firstplayer" class="playerinfo leader" aria-labelledby="firstplayerheading">
               <h3 id="firstplayerheading" class="accessibility">Führender Spieler</h3>
               <img class="avatar" src="img/avatar/<%= avatar.getImageHead() %>" alt="Spieler-Avatar <%= avatar.getName() %>>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"> <%= user.getName() %> (Du)</td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">2000 €</td>
                  </tr>
               </table>
            </section>
            <section id="secondplayer" class="playerinfo" aria-labelledby="secondplayerheading">
               <h3 id="secondplayerheading" class="accessibility">Zweiter Spieler</h3>
               <img class="avatar" src="img/avatar/<%= opponentAvatar.getImageHead() %>" alt="Spieler-Avatar <%= opponentAvatar.getName() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%= opponentAvatar.getName() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">400 €</td>
                  </tr>
               </table>
            </section>
            <p id="round">Fragen: <%= session.getAttribute("counter") %> / 10</p>
         </section>

         <!-- Question -->
         <section id="question-selection" aria-labelledby="questionheading">
            <h2 id="questionheading" class="black accessibility">Jeopardy</h2>
            <p class="user-info positive-change">Du hast richtig geantwortet: +1000 €</p>
            <p class="user-info negative-change">Deadpool hat falsch geantwortet: -500 €</p>
            <p class="user-info">Deadpool hat TUWIEN für € 1000 gewählt.</p>
            <form id="questionform" action="QuestionServlet" method="post">
               <fieldset>
               <legend class="accessibility">Fragenauswahl</legend>
               <c:forEach items="${categories}" var="category" varStatus="loop" begin="0">
               <section class="questioncategory" aria-labelledby="heading${loop.index+1}">
                  <h3 id="heading${loop.index+1}" class="tile category-title"><span class="accessibility">Kategorie: </span><c:out value="${category.name}"/></h3>
                  <ol class="category_questions">      
                  <c:forEach items="${category.questions}" var="question" varStatus="innerloop">
        		  	<li><input name="question_selection" id="question_${question.id}" value="${question.id}" type="radio"/><label class="tile clickable" for="question_${question.id}">€ ${10 * question.value}</label></li>
        		  </c:forEach>
                  </ol>
               </section>
               </c:forEach>
               </fieldset>               
               <input class="greenlink formlink clickable" name="question_submit" id="next" type="submit" value="wählen" accesskey="s" />
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
            $(document).ready(function() {
                // set last game
                if(supportsLocalStorage()) {
	                var lastGameMillis = parseInt(localStorage['lastGame'])
	                if(!isNaN(parseInt(localStorage['lastGame']))){
	                    var lastGame = new Date(lastGameMillis);
	                	$("#lastgame p").replaceWith('<p>Letztes Spiel: <time datetime="'
	                			+ lastGame.toUTCString()
	                			+ '">'
	                			+ lastGame.toLocaleString()
	                			+ '</time></p>')
	                }
            	}
            });            
            //]]>
        </script>
    </body>
</html>
