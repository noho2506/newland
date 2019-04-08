<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file = "/templates/taglib.jsp" %>
    <script src="<%=request.getContextPath() %>/templates/public/js/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/series-label.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<style>
table, th, td {
  border: 1px solid #1cc3b2;
  border-collapse: collapse;
}
tr{
text-align: center;}
th{
padding :10px 60px;

color: #fff;
font-weight:bold;
background-color: #1cc3b2;}
td{
text-align: center;}
</style>
    <!-- Rooms Area Start -->
    <div class="roberto-rooms-area section-padding-100-0">
        <div class="container">
            <div class="row">
                <div class="col-12 col-lg-9">
                <c:if test="${not empty msg }">
			      <div class="alert alert-success">
					<strong>${msg }</strong>
				</div>
				</c:if>
                <c:choose>
                	<c:when test="${chitieu==1}">
                		<div id="dientich"></div>
                		<div style="width:100%;margin: 50px 0px">
                			<table >
							  <tr>
							    <th>STT</th>
							    <th>Quận</th> 
							    <th>Diện tích đất bán </th>
							  </tr>
							  <c:set var="i" value="0"></c:set>
							  <c:forEach items="${getMap}" var="objM">
							  <c:set var="a" value="${i=i+1}"></c:set>
							  	 <tr>
							    <td>${a}</td>
							    <td>${objM.key}</td>
							    <td>${objM.value}</td>
							  </tr>
							  </c:forEach>
							</table>
                		</div>
                	</c:when>
                	<c:when test="${chitieu==2}">
                		<div id="mucgia"></div>
                		<div style="width:100%;margin: 50px 0px">
                			<table >
							  <tr>
							    <!-- <th>STT</th> -->
							     <th>Khu vực </th> 
							    <th>Từ 400 --> 1 Tỷ</th> 
							    <th>Từ 1 --> 4 Tỷ</th>
							    <th>Từ 4 --> 8 Tỷ</th>
							    <th>>= 8 Tỷ</th>
							  </tr>
							  <c:set var="i" value="0"></c:set>
							  <c:forEach items="${geta}" var="objM">
							  <c:set var="a" value="${i=i+1}"></c:set>
							  	 <tr>
							    <%-- <td>${a}</td> --%>
							    <td>${objM.key}</td>
							    <td>${objM.value}</td>
							  </tr>
							  </c:forEach>
							  <c:forEach items="${getb}" var="objM">
							  <c:set var="a" value="${i=i+1}"></c:set>
							  	 <tr>
							    <%-- <td>${a}</td> --%>
							    <td>${objM.value}</td>
							  </tr>
							  </c:forEach>
							</table>
                		</div>
                	</c:when>
                	<c:otherwise>
                		<div id="sotin"></div>
                	</c:otherwise>
                </c:choose>
                <div id="mucgia">
                
                </div>
                </div>

                <div class="col-12 col-lg-3">
                    <!-- Hotel Reservation Area -->
                    <div class="hotel-reservation--area mb-100">
                        <form action="" method="POST">
                            <div class="form-group mb-30">
                                <label for="checkIn">--- Thống kê theo ---</label>
                               <select name="id_cat" id="room" class="form-control">
                               <option value="1">Theo năm và Loại đất </option>
                                </select>
                            </div>
                              <div class="form-group mb-30">
                               <label for="checkOut">Chỉ tiêu thống kê</label>
                                <select name="chitieu" id="room" class="form-control">
                                <c:set var="id" value="${chitieu}"></c:set>
                                	<option value="0">---</option>
                                	<option value="1" <c:if test="${id==1}">selected = 'selected'</c:if>>Tổng diện tích</option>
                                	<option value="2" <c:if test="${id==2}">selected = 'selected'</c:if> >Mức giá </option>
                                	<option value="3" <c:if test="${id==3}">selected = 'selected'</c:if> >Số tin đăng</option>
                                </select>
                            </div>
                            <div class="form-group mb-30">
                                <label for="checkOut">Năm </label>
                                	<select name="year" id="room" class="form-control">
                                	 <c:set var="year" value="${year}"></c:set>
                                		<option value="0">---</option>
	                                    <option value="2019" <c:if test="${year==2019}">selected = 'selected'</c:if>>Năm 2019</option>
	                                    <option value="2018" <c:if test="${year==2018}">selected = 'selected'</c:if>>Năm 2018</option>
	                                    <option value="2017"  <c:if test="${year==2017}">selected = 'selected'</c:if>>Năm 2017</option>
	                                    <option value="2016" <c:if test="${year==2016}">selected = 'selected'</c:if>>Năm 2016</option>
	                                    
                                	</select>
                            </div>
                            <div class="form-group mb-30">
                                <label for="checkIn">Loại đất</label>
                               <select name="cat" id="" class="form-control">
                                <c:set var="cid" value="${cat.id }"></c:set>
                               		<option value="0">--Chọn loại-- </option>
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
                            <div class="form-group">
                                <button type="submit" class="btn roberto-btn w-100">Lọc thông tin </button>
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
<script type="text/javascript">
	Highcharts.chart('sotin', {
		title: {
	        text: 'Solar Employment Growth by Sector, 2010-2016'
	    },

	    subtitle: {
	        text: 'Source: thesolarfoundation.com'
	    },
	    xAxis: {
	    	categories:[" ", 
	    	<c:forEach var="reportName" items="${getMap.keySet()}">
	    	"${reportName}",
	    	</c:forEach>],
	    	 title: {
	   	      text: 'Khu vực '
	   	    }
	    },
	    yAxis: {
	        title: {
	            text: 'Tin đất bán'
	        }
	    },

	    plotOptions: {
	        series: {
	            label: {
	                connectorAllowed: false
	            },
	            pointStart: 1
	        }
	    },

	    series: [ {
	        name: 'Năm ${year}',
	        data: ${getMap.values()}
	    }],

	    responsive: {
	        rules: [{
	            condition: {
	                maxWidth: 500
	            },
	            chartOptions: {
	                legend: {
	                    layout: 'horizontal',
	                    align: 'center',
	                    verticalAlign: 'bottom'
	                }
	            }
	        }]
	    }
	});


</script>
<script type="text/javascript">
Highcharts.chart('dientich', {
	  chart: {
	    type: 'column'
	  },
	  title: {
	    text: 'Tổng diện tích ${cat.name} theo khu vực - Năm ${year}'
	  },
	  xAxis: {
		  categories:[ 
		    	<c:forEach var="reportName" items="${getMap.keySet()}">
		    	"${reportName}",
		    	</c:forEach>],
	    	 title: {
	   	      text: 'Khu vực '
	   	    }
	  },
	  yAxis: {
	    min: 0,
	    title: {
	      text: 'Tổng diện tích'
	    },
	  },
	  tooltip: {
	    headerFormat: '<b>{point.x}</b><br/>',
	    pointFormat: '{series.name}: {point.y}<br/>'
	  },
	  plotOptions: {
	    column: {
	      stacking: 'normal',
	      dataLabels: {
	        enabled: true,
	        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
	      }
	    }
	  },
	  series: [{
		  name: 'Năm ${year}',
	      data: ${getMap.values()}
		  
	  }]
	});
</script>
<script type="text/javascript">
Highcharts.chart('mucgia', {
	  chart: {
	    type: 'column'
	  },
	  title: {
	    text: 'Monthly Average Rainfall'
	  },
	  subtitle: {
	    text: 'Source: WorldClimate.com'
	  },
	  xAxis: {
		  categories:[ 
		    	<c:forEach var="reportName" items="${geta.keySet()}">
		    	"${reportName}",
		    	</c:forEach>],
	    title: {
	        text: 'Khu vực '
	      }
	  },
	  yAxis: {
	    min: 0,
	    title: {
	      text: 'Số tin đăng bán '
	    }
	  },
	  tooltip: {
	    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	      '<td style="padding:0"><b>{point.y} tin</b></td></tr>',
	    footerFormat: '</table>',
	    shared: true,
	    useHTML: true
	  },
	  plotOptions: {
	    column: {
	      pointPadding: 0.2,
	      borderWidth: 0
	    }
	  },
	  series: [{
	    name: 'Từ 400 - 1 Tỷ VNĐ',
	    data: ${geta.values()}

	  }, {
	    name: 'Từ 1 - 4 Tỷ VNĐ',
	    data: ${getb.values()}

	  }, {
	    name: 'Từ 4 - 8 Tỷ VNĐ',
	    data: ${getc.values()}

	  }, {
	    name: 'Lớn hơn 8 Tỷ',
	    data: ${getd.values()}

	  }]
	});
</script>


  