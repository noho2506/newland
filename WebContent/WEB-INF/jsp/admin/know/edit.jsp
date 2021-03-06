<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/templates/taglib.jsp" %>
                <div id="content">
                    <div class="outer">
                        <div class="inner bg-light lter">
                            <!--Begin Datatables-->
<div class="row">
  <div class="col-lg-12">
        <div class="box">
            <header class="title">
                <div class="icons"><i class="fa fa-table"></i></div>
                <h5>Sửa thông tin </h5>
            </header>
             <c:if test="${not empty msg }">
			      <div class="alert alert-success">
					<strong>${msg }</strong>
				  </div>
			  </c:if>
            <div id="collapse4" class="body">
            <form action="" method="POST" enctype="multipart/form-data">
                 <div class="edit-detail">
                       <div class="form-group">
                           <label for="name">Tên tin</label>
                           <input type="text" name="title" value="${objknow.title}" class="form-control" placeholder="Nhập tên tin">
                       </div>

  						<div class="form-group">
                           <label for="name">Mô tả</label>
                           <input type="text" name="description" value="${objknow.description }" class="form-control" placeholder="Nhập diện tích">
                       </div>

                       <div class="form-group">
                           <label>Chi tiết</label>
                           <textarea name="detail" class="form-control ckeditor" rows="3">${objknow.description}</textarea>
                       </div>
                       <div class="form-group">
	                           <label>Hình ảnh</label>
	                           <input type="file" name="hinhanh" class="btn btn-default">
	                           <p class="help-block"><em>Định dạng: jpg, png, jpeg,...</em></p>
	                           <img style="width:100px; height:100px" alt="" src="${pageContext.request.contextPath}/files/${objknow.image }">
	                       </div>
					 	<div class="row" style="text-align: center; ">
		                     <div class="col-sm-12">
		                         <input type="submit" value="Sửa" class="btn btn-success" />
		                         <input type="reset" value="Nhập lại" class="btn btn-default" />
		                     </div>
		                 </div>

                   </div>
                    <!-- The Modal -->
                    </form>
            </div>
        </div>
    </div>
</div>
<!-- /.row -->
<!--End Datatables-->

                        </div>
                        <!-- /.inner -->
                    </div>
                    <!-- /.outer -->
                </div>
                <!-- /#content -->
				</div>
                    
                    <!-- /#right -->
            </div>
            
            <!-- /#wrap -->
            
