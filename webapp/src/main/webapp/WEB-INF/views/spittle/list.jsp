<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
	
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/spittle/list.css?appRev=${appRev}"/>">

<div class="linksContainer">
	<sec:authorize access="hasAuthority('ADD_SPITTLE')">
		<div class="newSpittleLink">
			<a href="<c:url value="/spittle/new"/>"><s:message code="spittle.list.add"/></a>
		</div>
	</sec:authorize>
	
	<sec:authorize access="hasAuthority('USER_MANAGEMENT')"> 
		<div class="userManagementLink">
			<a href="<c:url value="/user/list"/>"><s:message code="spittle.list.usersLink"/></a>
		</div>
	</sec:authorize>
</div>

<div class="spittleList">
	<c:forEach var="spittle" items="${spittles}" varStatus="status">
		<div class="spittle">
			<div class="userInfo">
				<div class="dates">
					<c:set var="user" value="${spittle.createUser}" />
					<div>Created: <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${spittle.time}"/></div>
					<c:if test="${spittle.updateTime != null}">
						<c:set var="user" value="${spittle.editUser}" />
						<div>Edited: <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${spittle.updateTime}"/></div>
					</c:if>
				</div>
				<div class="userImage">
					<img src="<c:url value="/resources/images/no-picture-icon1.png"/>"/>
				</div>
				<div class="userName">by: <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></div>
			</div>
			<div class="spittleInfo">
				<div class="title">
					<span class="titleText">
						<c:set var="canEdit" value="false" /> 
						<sec:authorize access="hasAuthority('EDIT_ALL_SPITTLES') or (hasAuthority('EDIT_OWN_SPITTLE') and principal.user.id == ${spittle.createUser.id})">
							<c:set var="canEdit" value="true" /> 
						</sec:authorize>
						<c:out value="${spittle.title}" />
					</span>
					<span class="actions">
						<c:if test="${canEdit}">
							<a href="<c:url value='/spittle/edit/${spittle.id}'/>">
								<img src="<c:url value="/resources/images/edit.png"/>"/>
							</a>
						</c:if>
					</span>
				</div>
				<div class="message">
					<c:out value="${spittle.message}" />
				</div>
			</div>
		</div>
		<hr <c:if test="${status.index == spittles.size()-1}">class="last"</c:if>/>
	</c:forEach>
</div>

