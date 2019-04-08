<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.1" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file = "/templates/taglib.jsp" %>
    <!-- Breadcrumb Area End -->

    <!-- Rooms Area Start -->
    <div class="roberto-rooms-area section-padding-100-0">
        <div class="container">
            <div class="row">
                <div class="col-12 col-lg-9">
                   <c:forEach items="${getItemCat }" var="objA">
                    <!-- Single Room Area -->
                    <div class="single-room-area d-flex align-items-center mb-50 wow fadeInUp" data-wow-delay="900ms">
                        <!-- Room Thumbnail -->
                        <div class="room-thumbnail">
                        	<a class="image-popup-no-margins" href="">
                        		<img src="${pageContext.request.contextPath }/files/${objA.image}" alt="">
                        	</a>
                        </div>
                        <!-- Room Content -->
                        <div class="room-content">
                            <h2>${objA.title }</h2>
                            <h4>${objA.price }</h4>
                            <div class="room-feature">
                                <h6>Danh mục: <span>${objA.name_cat }</span></h6>
                                <h6>Địa điểm: <span>${objA.location }</span></h6>
                            </div>
                            <a href="${pageContext.request.contextPath }/detail/${objA.id}" class="btn view-detail-btn">View Details <i class="fa fa-long-arrow-right" aria-hidden="true"></i></a>
                        </div>
                    </div>
				</c:forEach>
                    <!-- Pagination -->
                    <%-- <nav class="roberto-pagination wow fadeInUp mb-100" data-wow-delay="1000ms">
                        <ul class="pagination">
                            <c:forEach begin="1" end="4" var="i">
                            	<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath }/cat/${cat.id}/page-${i}">${i }</a></li>
                            </c:forEach>
                            <li class="page-item"><a class="page-link" href="#">Next <i class="fa fa-angle-right"></i></a></li>
                        </ul>
                    </nav> --%>
                    <%-- <tag:paginate max="10" offset="${offset}" count="${count}" uri="/persons" next="&raquo;" previous="&laquo;"/> --%>
                </div>

                  <div class="col-12 col-lg-3">
                    <!-- Hotel Reservation Area -->
                    <div class="hotel-reservation--area mb-100">
                        <form action="" method="post">
                            <div class="form-group mb-30">
                                <label for="checkIn">Loại đất</label>
                               <select name="id_cat" id="room" class="form-control">
                               <option value="0">--Chọn loại-- </option>
                               <c:set var="cid" value="${land.id_cat }"></c:set>
                               	<c:forEach items="${listCat }" var="objC">
			                            <c:choose>
		                           			<c:when test="${objC.id == cid}">
		                           				<c:set var="selected" value="selected = 'selected'"></c:set>
			                           		</c:when>
			                           		<c:otherwise>
			                           			<c:set var="selected" value=""></c:set>
			                           		</c:otherwise>
		                           		</c:choose>
									   <option ${selected} value="${objC.id }">${objC.name }</option>
	                               	</c:forEach>
                                </select>
                            </div>
                              <div class="form-group mb-30">
                               <label for="checkOut">Địa điểm</label>
                                <select name="id_district" id="room" class="form-control">
                                	<option value="0">---- Chọn ----</option>
                                	 <c:set var="id" value="${land.id_district }"></c:set>
	                                <c:forEach items="${listQuan }" var="objQ">
	                                	<c:choose>
		                           			<c:when test="${objQ.id == id}">
		                           				<c:set var="selected" value="selected = 'selected'"></c:set>
			                           		</c:when>
			                           		<c:otherwise>
			                           			<c:set var="selected" value=""></c:set>
			                           		</c:otherwise>
		                           		</c:choose>
	                                 	<option ${selected} value="${objQ.id }">${objQ.name }</option>
	                                </c:forEach>
                                </select>
                            </div>
                            <div class="form-group mb-30">
                                <label for="checkOut">Diện tích</label>
                                <select name="dientich" id="room" class="form-control">
                                	<option value="0">Chọn diện tích</option>
                                    <option value="1" <c:if test="${dientich==1}">selected = 'selected'</c:if>>30- 100 m2</option>
                                    <option value="2"<c:if test="${dientich==2}">selected = 'selected'</c:if>>100-250 m2</option>
                                    <option value="3"<c:if test="${dientich==3}">selected = 'selected'</c:if>>250-500 m2</option>
                                    <option value="4" <c:if test="${dientich==4}">selected = 'selected'</c:if>>500-1000 m2</option>
                                    <option value="5" <c:if test="${dientich==5}">selected = 'selected'</c:if>>>=1000 m2</option>
                                </select>
                            </div>
                            <div class="form-group mb-30">
                                <label for="checkOut">Mức giá</label>
                                <select name="mucgia" id="adults" class="form-control">
                                    <option value="0">Chọn mức giá</option>
                                    <option value="1" <c:if test="${mucgia==1}">selected = 'selected'</c:if> >400 - 1 Tỷ</option>
                                    <option value="2"<c:if test="${mucgia==2}">selected = 'selected'</c:if>>1 - 4 Tỷ</option>
                                    <option value="3"<c:if test="${mucgia==3}">selected = 'selected'</c:if>>4 - 8 Tỷ</option>
                                    <option value="4"<c:if test="${mucgia==4}">selected = 'selected'</c:if>> >= 8 Tỷ</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn roberto-btn w-100">Check Available</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Rooms Area End -->

    <!-- Call To Action Area Start -->
    <section class="roberto-cta-area">
        <div class="container">
            <div class="cta-content bg-img bg-overlay jarallax" style="background-image: url(img/bg-img/1.jpg);">
                <div class="row align-items-center">
                    <div class="col-12 col-md-7">
                        <div class="cta-text mb-50">
                            <h2>Contact us now!</h2>
                            <h6>Contact (+12) 345-678-9999 to book directly or for advice</h6>
                        </div>
                    </div>
                    <div class="col-12 col-md-5 text-right">
                        <a href="#" class="btn roberto-btn mb-50">Contact Now</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Call To Action Area End -->

    <!-- Partner Area Start -->
    <div class="partner-area">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="partner-logo-content d-flex align-items-center justify-content-between wow fadeInUp" data-wow-delay="300ms">
                        <!-- Single Partner Logo -->
                        <a href="#" class="partner-logo"><img src="img/core-img/p1.png" alt=""></a>
                        <!-- Single Partner Logo -->
                        <a href="#" class="partner-logo"><img src="img/core-img/p2.png" alt=""></a>
                        <!-- Single Partner Logo -->
                        <a href="#" class="partner-logo"><img src="img/core-img/p3.png" alt=""></a>
                        <!-- Single Partner Logo -->
                        <a href="#" class="partner-logo"><img src="img/core-img/p4.png" alt=""></a>
                        <!-- Single Partner Logo -->
                        <a href="#" class="partner-logo"><img src="img/core-img/p5.png" alt=""></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Partner Area End -->

    