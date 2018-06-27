<%--<!-- РѕР±СЂР°С‚РёС‚Рµ РІРЅРёРјР°РЅРёРµ РЅР° spring С‚СЌРіРё -->--%>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%--<html>--%>

<%--<head>--%>
<%--<title>Index Page</title>--%>
<%--</head>--%>

<%--<body>--%>
<%--<spring:form method="post"  modelAttribute="userJSP" action="check-user">--%>

<%--Name: <spring:input path="name"/> (path="" - СѓРєР°Р·С‹РІР°РµС‚ РїСѓС‚СЊ, РёСЃРїРѕР»СЊР·СѓРµРјС‹Р№ РІ modelAttribute=''. РІ РЅР°С€РµРј СЃР»СѓС‡Р°Рµ User.name)  <br/>--%>
<%--Password: <spring:input path="password"/>   <br/>--%>
<%--<spring:button>Next Page</spring:button>--%>

<%--</spring:form>--%>

<%--</body>--%>

<%--</html>--%>
<!DOCTYPE html>
<html>

<head>
    <title>Form</title>

    <%--<link href='style.css' rel='stylesheet' type='text/css'>--%>

    <style type="text/css">
        .styletest {
            color: indigo;
            font-size: 72px;
            font-family: "Baskerville Old Face";
            text-align-all: center;
            display: block;
        }



        .ramka {
            background: #eeeee5;
            border: 1px dashed #abab9a;
            padding: 5px;
            font: 8pt Tahoma;
            color: #2c2c2c;
        }

        .style1 {

            color: mediumblue;
            font-size: 24px;
            font-family: "Baskerville Old Face";
            text-align-all: center;
            display: block;
        }

        .style2 {

            color: green;
            font-size: 24px;
            font-family: "Coronetscript";
            text-align-all: center;
            display: block;
        }

    </style>

</head>

<div class="styletest" align="center">
    <body>
    Welcome to the logistic company!
    </body>

</div>


<div class="style1"  align = center>

    <h1>Sign Up </h1>
</div>



<div class = "style2" align = "center">
    <form action="/index" id="signup" method="post">
        <table>
            <tr>
                <td> Name: </td><td>    <label> <input name="uname" type="text" form="signup" required></label>        </td>
            </tr>


            <tr>
                <td> Last name: </td><td>    <label> <input name="lastname" type="text" form="signup" required></label> </td>
            </tr>
            <tr>
                <td> Personal number: </td> <td>    <label> <input name="unumber" type="password" form="signup" required></label> </td>
            </tr>

        </table>
        <form>
            <button  formmethod="post">Submit</button>
        </form>
    </form>
</div>


<%--<div align="center">--%>
    <%----%>
<%--</div>--%>

<div class="style1"  align = center>
    <h1>Sign In</h1>
</div>



<div class = "style2" align = "center">
    <form>
        <table>
            <tr>
                <td> Name: </td><td>    <label> <input type="text" required></label>        </td>
            </tr>


            <tr>
                <td> Password: </td><td>    <label> <input type="password" required></label> </td>
            </tr>

            <tr>
                <td> PersonalNumber: </td><td> <label><input type="text" required></label> </td>
            </tr>
        </table>
    </form>
</div>


<div align="center">
    <form>
        <button formmethod="get">Submit</button>
    </form>


</html>