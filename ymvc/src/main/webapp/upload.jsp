<%--
  Created by IntelliJ IDEA.
  User: wlt
  Date: 2021/12/8
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件上传</title>
</head>
<body>
<form action="/file/upload" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>上传文件</td>
            <td>
                <input type="file" name="file" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>
