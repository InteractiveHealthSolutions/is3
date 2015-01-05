<%@page import="com.ihsinformatics.is3web.client.ServerService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
<meta charset="UTF-8">
<title>IS3</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
 --><script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<style type="text/css">
    .btn-toolbar{
    	margin: 20px;
    }
</style>
<script type="text/javascript" language="javascript" src="jquery-1.11.1.min.js"></script>
<script type="text/javascript" language="javascript" src="jquery.dataTables.min.js"></script>

<link rel="stylesheet" type="text/css" href="media/css/jquery.dataTables.css">
 <link rel="stylesheet" type="text/css" href="menustyle.css">
 <style type="text/css" class="init">

	tfoot input {
		width: 100%;
		padding: 3px;
		box-sizing: border-box;
	}

	</style>
<script type="text/javascript" language="javascript" class="init">

 $(document).ready(function() {

	
	
	//	alert("sdasd");
	$("#searchButton").click(function(){
		 //alert("Data: \nStatus: ");
		 var basic=document.getElementById('selectbasic').value;
		 if(basic.toLowerCase()=='survey'){
		 var type=document.getElementById('selecttype').value;
			 if(type.toLowerCase()=='complete')
				 {
				 var by=document.getElementById('selectby').value;
				 if(by.toLowerCase()=='id')
					 {
					 $("#tableDiv").val('');
						var searcht=document.getElementById('searchtext').value;
						  $.get("JspServlet?action=survey&searchtype=complete&searchby=id&searchtext="+searcht,function(data,status){
							//  alert("Data: " + data + "\nStatus: " + status);
							 // document.getElementById('#tableDiv').value=data;
					
							 $("#tableDiv").prepend(data);
							 initDataTable();
						//	 alert('there');
						  });
					 }else if(by.toLowerCase()=='name')
					 { $("#tableDiv").val('');
						 
							var searcht=document.getElementById('searchtext').value;
							  $.get("JspServlet?action=survey&searchtype=complete&searchby=name&searchtext="+searcht,function(data,status){
								//  alert("Data: " + data + "\nStatus: " + status);
								 // document.getElementById('#tableDiv').value=data;
						
								 $("#tableDiv").prepend(data);
								 initDataTable();
							//	 alert('there');
							  });
						 }
				 
				 } else if(type.toLowerCase()=='incomplete')
				 {
					 var by=document.getElementById('selectby').value;
					 if(by.toLowerCase()=='id')
						 {
						 
							var searcht=document.getElementById('searchtext').value;
							  $.get("JspServlet?action=survey&searchtype=incomplete&searchby=id&searchtext="+searcht,function(data,status){
								//  alert("Data: " + data + "\nStatus: " + status);
								 // document.getElementById('#tableDiv').value=data;
						
								 $("#tableDiv").prepend(data);
								 initDataTable();
							//	 alert('there');
							  });
						 }else if(by.toLowerCase()=='name')
						 {
							 
								var searcht=document.getElementById('searchtext').value;
								  $.get("JspServlet?action=survey&searchtype=incomplete&searchby=name&searchtext="+searcht,function(data,status){
									//  alert("Data: " + data + "\nStatus: " + status);
									 // document.getElementById('#tableDiv').value=data;
							
									 $("#tableDiv").prepend(data);
									 initDataTable();
								//	 alert('there');
								  });
							 }
					 
					 }
				 else if(type.toLowerCase()=='pending')
				 {
					 var by=document.getElementById('selectby').value;
					 if(by.toLowerCase()=='id')
						 {
						 
							var searcht=document.getElementById('searchtext').value;
							  $.get("JspServlet?action=survey&searchtype=pending&searchby=id&searchtext="+searcht,function(data,status){
								//  alert("Data: " + data + "\nStatus: " + status);
								 // document.getElementById('#tableDiv').value=data;
						
								 $("#tableDiv").prepend(data);
								 initDataTable();
							//	 alert('there');
							  });
						 }else if(by.toLowerCase()=='name')
						 {
							 
								var searcht=document.getElementById('searchtext').value;
								  $.get("JspServlet?action=survey&searchtype=pending&searchby=name&searchtext="+searcht,function(data,status){
									//  alert("Data: " + data + "\nStatus: " + status);
									 // document.getElementById('#tableDiv').value=data;
							
									 $("#tableDiv").prepend(data);
									 initDataTable();
								//	 alert('there');
								  });
							 }
					 
					 }
	
		 } else if (basic.toLowerCase()=='participant')
			 {
			 var by=document.getElementById('selectby').value;
			 if(by.toLowerCase()=='id')
			 {
			 
				var searcht=document.getElementById('searchtext').value;
				  $.get("JspServlet?action=participant&searchby=id&searchtext="+searcht,function(data,status){
					//  alert("Data: " + data + "\nStatus: " + status);
					 // document.getElementById('#tableDiv').value=data;
			
					 $("#tableDiv").prepend(data);
					 initDataTable();
				//	 alert('there');
				  });
			 }else if(by.toLowerCase()=='name')
			 {
				 
					var searcht=document.getElementById('searchtext').value;
					  $.get("JspServlet?action=participant&searchby=name&searchtext="+searcht,function(data,status){
						//  alert("Data: " + data + "\nStatus: " + status);
						 // document.getElementById('#tableDiv').value=data;
				
						 $("#tableDiv").prepend(data);
						 initDataTable();
					//	 alert('there');
					  });
				 }
			 
			 }
		  
		});
	
	
	
	 } );//end of ready function!
 
 
 
function initDataTable(){
	 $('#dataT').dataTable({
	        "iDisplayLength": 20,
	        "sPaginationType": "full_numbers",
	        "aLengthMenu": [[20, 50, 100, -1], [20, 50, 100, "All"]],
	        "sEmptyTable": "No record found!",
	        "oLanguage": {
	            "search": "Filter: "
	        },
	    });

  
 $('#dataT tfoot th').each( function () {
	// alert('in footer loader');
        var title = $('#dataT thead th').eq( $(this).index() ).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
       
    } );
 
  //alert('he;llo');
    // DataTable
    var table = $('#dataT').DataTable();
 
    // Apply the search
    table.columns().eq( 0 ).each( function ( colIdx ) {
    //	alert('in footer loader aa');
    	$( 'input', table.column( colIdx ).footer() ).on( 'keyup change', function () {
            table
                .column( colIdx )
                .search( this.value )
                .draw();
          //  alert('aalala');
        } );
    } );
    
    
  
}

</Script>
<script type="text/javascript">
function  checkData()
{
	var id=document.getElementById('selectbasic').value;
	//alert(id);
	if(id=='1')
		{
		
		document.getElementById('searchtype').style.display='block';
		//document.getElementById('participantForm').style.display='none';
	
		
		}
	else if(id=='2')
		{
		document.getElementById('searchtype').style.display='none';
		//document.getElementById('participantForm').style.display='block';
		
		}
	}

</script>
</head>
	<body>
	<div class='container'>
		<div  class='row'>
<!--  <form role="form" action="/JspServlet" method="get">	
	--><span class='col-md-2 '>
	<!-- Select Basic -->
	<input type='hidden' id=action name='action' value='survey'/>
	<div class="form-group">
	  <label class="control-label" for="selectbasic">Select Search</label>
	  <div >
		<select id="selectbasic" name="selectbasic" class="form-control" onchange='checkData();'>
		  <option value="survey">Survey</option>
		  <option value="participant">Participant</option>
		</select>
	  </div>
	  
	</div>
	</span>
	<span class='col-md-2  form-group' id='searchtype'  >
		<Label class='control-label' for='selecttype'> Search Type</Label>
		<select id='selecttype' name='selecttype' class='form-control'>
			<option value='complete'> Complete</option>
			<option value='incomplete'> Incomplete</option>
			<option value='pending'> Pending</option>
		</select>
	</span>
	
	<span class='col-md-2 form-group' >
		<Label class='control-label' for='selectby' > Search Type</Label>
		<select id='selectby' name='selectby' class='form-control'>
			<option value='name'> Name</option>
			<option value='id'> ID</option>
		</select>
	</span>
		
	

	<div class='col-md-3  form-group'>
	
	<Label class='control-label' for='searchtext'>Search</Label>
	<input type='text' class='form-control' id='searchtext' name='searchtext' placeholder='Search'/>
	
	
	
	</div>
	<div class='col-md-1 form-group'>
	<Label class='control-label' for='searchfield'>&nbsp;</Label>
	

	<Button type="submit"  class="btn btn-primary form-control " id='searchButton' name='searchButton' ><img src="search.png"   width='20px' height='20px'
   class="img-rounded" ></Button> 
	</div>

	</div>
	
		
	</div>
	
<div id='tableDiv' name='tableDiv' class='table'></div>



	</body>

<html>