<?xml version="1.0" encoding="UTF-8"?>
<taglib version="2.1" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file = "/templates/taglib.jsp" %>
    <style>
	.mySlides {display:none;}
	
	</style>
    <!-- Breadcrumb Area End -->

    <!-- Rooms Area Start -->
    <div class="roberto-rooms-area section-padding-100-0">
        <div class="container">
            <div class="row">
                <div class="col-12 col-lg-8">
					<div class="know">
						<h4>Kiến Thức Bất Động Sản</h4>
					</div>
                   <c:forEach items="${getItemKnow }" var="objA">
                    <!-- Single Room Area -->
                    <div class="single-room-area d-flex align-items-center mb-50 wow fadeInUp news-nd" data-wow-delay="900ms">
                        <!-- Room Thumbnail -->
                        <div class="thumbnail">
                        	<a class="image-popup-no-margins new-content-img" href="${pageContext.request.contextPath }/kien-thuc/${slugUtil.makeSlug(objA.title)}-${objA.id}">
                        		<img src="${pageContext.request.contextPath }/files/${objA.image}" alt="">
                        	</a>
                        </div>
                        <!-- Room Content -->
                        <div class="new-content">
                            <h5><a href="${pageContext.request.contextPath }/kien-thuc/${slugUtil.makeSlug(objA.title)}-${objA.id}">${objA.title }</a></h5>
                            <p class="new-content-date content-know">${objA.time }</p>
                            <p class="content-know">${slugUtil.substringWord(objA.description,150) }</p>
                            <a href="${pageContext.request.contextPath }/kien-thuc/${slugUtil.makeSlug(objA.title)}-${objA.id}" class="btn view-detail-btn">View Details <i class="fa fa-long-arrow-right" aria-hidden="true"></i></a>
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

                  <div class="col-12 col-lg-4">
                  	<!-- quảng cáo -->
                  	<div class="hotel-reservation--area mb-100 ">
                  		<div class="w3-content w3-section quangcao">
                  		<c:forEach items="${getAdv }" var="obj">
						  <a href="${obj.link}"><img class="mySlides" src="${pageContext.request.contextPath }/files/${obj.image}" style="width:100%"></a>
						</c:forEach>
						</div>
                    </div>
                    <!-- Hotel Reservation Area -->
                    <div class="hotel-reservation--area mb-100 news-cat">
						<div>
							<h4 >Kiến thức nổi bật</h4>
						</div>
						<div>
							<c:forEach items="${getTopViewKnow }" var="objP">
								<div class="row tin">
									<div class="  col-4 news-img">
										<a href="${pageContext.request.contextPath }/kien-thuc/${slugUtil.makeSlug(objP.title)}-${objP.id}"><img src="${pageContext.request.contextPath}/files/${objP.image}" alt=""></a>
						      		</div>
						      		<div class=" col-8 news-content">
						      			<p><a href="">${objP.title }</a></p>
						      		</div>
					      		</div>
							</c:forEach>
							</div>
                        
                    </div>
                    
                    <!-- Hotel Reservation Area -->
                    <div class="hotel-reservation--area mb-100 binhchon">
                    	<h5 style="background: url(<%=request.getContextPath()%>/templates/public/img/utilities.png) 6px 13px no-repeat;">Bạn biết http://cland.. qua hình thức nào?</h5>
	                    <form id="hinhthuc"class="khaosat" action="${pageContext.request.contextPath }/kien-thuc" method = "POST" >
						  <div class="thu">
							  <div>
								  <input type="radio" name="form" value="1 " id="myRadio">
								  <div class="name-khaosat">Google.com.vn, bing.com, ...</div>
								  <div class="meter">
									  <span style="width:${defines.percent(about.form1)}%"></span>
									  <p></p>
									</div><SPAN class="numberofvote">${about.form1} phiếu</SPAN>
							  </div>
							  <div>
								  <input type="radio" name="form" value="2" id="myRadio">
								  <div class="name-khaosat">Bạn bè giới thiệu </div>
								  <div class="meter">
									  <span style="width:${defines.percent(about.form2)}%"></span>
									  <p></p>
									</div><SPAN class="numberofvote">${about.form2} phiếu</SPAN>
							  </div>
							  <div>
								  <input type="radio" name="form" value="3 " id="myRadio">
								  <div class="name-khaosat">Báo chí, truyền thông</div>
								  <div class="meter">
									  <span style="width:${defines.percent(about.form3)}%"></span>
									  <p></p>
									</div><SPAN class="numberofvote">${about.form3} phiếu</SPAN>
							  </div>
							  <div>
								  <input type="radio" name="form" value="4" id="myRadio">
								  <div class="name-khaosat">Mạng xã hội </div>
								  <div class="meter">
									  <span class="nhan" style="width:${defines.percent(about.form4)}%"></span>
									  <p></p>
									</div><SPAN class="numberofvote">${about.form4} phiếu</SPAN>
							  </div>
							  <div>
								  <input type="radio" name="form" value="5" id="myRadio">
								  <div class="name-khaosat">Khác</div>
								  <div class="meter">
									  <span style="width:${defines.percent(about.form5)}%"></span>
									  <p></p>
									</div><SPAN class="numberofvote">${about.form5} phiếu</SPAN>
							  </div>
							  
						  </div>
						  <div class="chon"><a href="javascript:void(0)" title="" class="btn chon-ht" onclick="doComment()">Chọn</a></div>
						  <div id="server-results"><!-- For server results --></div>
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
    <script type="text/javascript">
    function doComment(){  
		var form_temp = $("input[name='form']:checked").val();// nếu gán dữ liệu thì val('dkfjs')
		console.log(form_temp)
		alert('Cảm ơn bạn');
		$.ajax({
			url:'${pageContext.request.contextPath }/kien-thuc',
			type: 'POST',
			cache: false,
			data:{
					//Dữ liệu gửi đi
					form: form_temp,//temp file tạm// chú ý dấu , 
					},
			success: function(data){// lấy dữ liệu về data// từ server gửi về data
				// Xử lý thành công
				$('.thu').html(data);//truy cập đến thẻ mẹ là .ajax-data
				
			},
			error: function (){
			// Xử lý nếu có lỗi
			alert("Vui lòng chọn !");
			//alert('Có lỗi xảy ra');
			}
		});
	}
	</script>
    <script type="text/javascript">
	var myIndex = 0;
	carousel();
	
	function carousel() {
	  var i;
	  var x = document.getElementsByClassName("mySlides");
	  for (i = 0; i < x.length; i++) {
	    x[i].style.display = "none";  
	  }
	  myIndex++;
	  if (myIndex > x.length) {myIndex = 1}    
	  x[myIndex-1].style.display = "block";  
	  setTimeout(carousel, 2000); // Change image every 2 seconds
	}
	</script>
	
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

    