<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<nav class="navbar navbar-inverse navbar-dark bg-dark">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar">
				<!--<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
				</ul>-->
				<div class="pull-right">
                    <span><spring:message code="lbl.change" /></span>:
                    <select id="locales">
                        <option value=""></option>
                        <option value="en_US"><spring:message code="lbl.en" /></option>
                        <option value="bg_BG"><spring:message code="lbl.bg" /></option>
                    </select>
                </div>
			</div>
		</div>
	</nav>