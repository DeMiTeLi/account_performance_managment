<%--
  Created by IntelliJ IDEA.
  User: Inspiron
  Date: 11/1/2015
  Time: 5:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>

<body>
  <h1>This is a test page for APM</h1>
  <input id="theFile" name="file" type="file" />
  <br />
  <button id="theFileSubmit" type="button">/upload_file</button>
  <br />

</body>
<script>

  function printLog(xhr, status, result) {
    console.log('result:::' + response);
    console.log('status:::' + status);
    console.log('xhr:::' + xhr);
  }

    var files = [];
    $(document).on("change", "#theFile", function(event) {
      files=event.target.files;
    });

    $(document).on("click", "#theFileSubmit", function() {
      processUpload();
    });

    function processUpload()
    {
      var requesBody = new FormData();
      requesBody.append("file", files[0]);
      requesBody.append("dateformat", "US");
      $.ajax({type : "POST",
              url : "https://127.0.0.1:8443/apptest/upload_file",
              dataType : 'json',
              data : requesBody,
              enctype : 'multipart/form-data',
              processData : false,
              contentType : false,
              cache : false,
              success : function(result) {
                console.log('::: result :::' + result);
                console.log('::' + result.message);
              },
              error : function(error){
                console.log('::: error :::' + error);
                console.log('::: error.message :::' + error.message);
              }
      });
    }
</script>
</html>
