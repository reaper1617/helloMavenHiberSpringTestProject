<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Transporters a Transportation Category Flat Bootstrap Responsive Website Template | Home :: W3layouts</title>
    <!-- Meta tag Keywords -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="Transporters web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, Smartphone Compatible web template, free webDesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web Designs" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
    function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!--// Meta tag Keywords -->

    <link rel="stylesheet" href="resources/web/css/flexslider.css" type="text/css" media="all" /><!-- for testimonials -->

    <!-- css files -->
    <link rel="stylesheet" href="resources/web/css/bootstrap.css"> <!-- Bootstrap-Core-CSS -->
    <link rel="stylesheet" href="resources/web/css/style.css" type="text/css" media="all" /> <!-- Style-CSS -->
    <link rel="stylesheet" href="resources/web/css/font-awesome.css"> <!-- Font-Awesome-Icons-CSS -->
    <!-- //css files -->

    <!-- web-fonts -->
    <link href="//fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;subset=latin-ext" rel="stylesheet">
    <link href="//fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i&amp;subset=cyrillic,cyrillic-ext,greek,greek-ext,latin-ext,vietnamese" rel="stylesheet">
    <!-- //web-fonts -->

    <style type="text/css">
        .ramka {
            border: none;
            width: 100%;
            background: rgba(0, 0, 0, 0.5);
            padding: 10px 15px;
            margin-bottom: 15px;
            outline: none;
            font-size: 14px;
            color: #fff;
            letter-spacing: 1px;
        }

        .selectWidth{
            width: 300px;
            color: #d58512;
        }

        .tableheadstyle{
            color: orangered;
            font-size: 24px;
        }

        .tableheadstyle2{
            color: #012231;
            font-size: 24px;
        }

        .tableheadstyle3{
            color: lime;
            font-size: 24px;
        }

    </style>

</head>
<body>
<div class="header">
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <h1><a href="/index">Logisticon-3000</a></h1>
        </div>

        <!-- navbar-header -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a class="hvr-underline-from-center active" href="/logout">Log out</a></li>
            </ul>
        </div>

        <div class="clearfix"> </div>
    </nav>

</div>

<div class="slider">
    <div class="callbacks_container">
        <ul class="rslides" id="slider">
            <li>
                <div class="w3layouts-banner-top w3layouts-banner-top2">
                    <div align="center">
                        <table>
                            <tr>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Users:</b></h12>
                                    </div>
                                </td>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Managers:</b></h12>
                                    </div>
                                </td>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Drivers:</b></h12>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <div>
                                        <select size="10" class="ramka selectWidth" >
                                            <c:forEach items="${users}" var="cell">
                                                <option value="${cell.id}">id: ${cell.id} ${cell.userName} ${cell.middleName} ${cell.lastName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>


                                <td>

                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${managers}" var="cell">
                                            <option value="${cell.id}">id: ${cell.id} position: ${cell.managerPosition}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>

                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${drivers}" var="cell">
                                            <option value="${cell.id}">id: ${cell.id} state: ${cell.state} hours: ${cell.hoursWorked}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Orders:</b></h12>
                                    </div>
                                </td>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Cargos:</b></h12>
                                    </div>
                                </td>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Trucks:</b></h12>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${orders}" var="cell">
                                            <option value="${cell.orderId}">id: ${cell.orderId} ${cell.orderDescription} ${cell.orderDate}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>

                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${cargos}" var="cell">
                                            <option value="${cell.id}">id: ${cell.id} ${cell.cargoName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>

                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${trucks}" var="cell">
                                            <option value="${cell.id}">id: ${cell.id} ${cell.registrationNumber}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Cities:</b></h12>
                                    </div>
                                </td>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Route points:</b></h12>
                                    </div>
                                </td>
                                <td>
                                    <div class="tableheadstyle" align="center" >
                                        <h12><b>Routes:</b></h12>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${cities}" var="cell">
                                            <option value="${cell.id}">id: ${cell.id} ${cell.cityName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>

                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${routepoints}" var="cell">
                                            <option value="${cell.id}">id: ${cell.id} </option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select size="10" class="ramka selectWidth">
                                        <c:forEach items="${routes}" var="cell">
                                            <option value="${cell.id}">id: ${cell.id} }</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                                <td>

                                    <table align="center">
                                        <tr>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Change" >
                                                </div>
                                            </td>
                                            <td>
                                                <div class="banner-form-agileinfo ramka" align="center">
                                                    <input  type="submit" value="Delete" >
                                                </div>
                                            </td>

                                        </tr>

                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="clearfix"></div>
</div>
<footer>
    <div class="agileits-w3layouts-footer">
        <div class="container">
            <div class="col-md-4 w3-agile-grid">
                <h5>About Us</h5>
                <p>Logisticon-3000 is the best company EVER!</p>
                <div class="footer-agileinfo-social">
                    <ul>
                        <li><a href="#"><i class="fa fa-vk"></i></a></li>
                    </ul>
                </div>
            </div>

            <div class="col-md-4 w3-agile-grid">
                <h5>Address</h5>
                <div class="w3-address">
                    <div class="w3-address-grid">
                        <div class="w3-address-left">
                            <i class="fa fa-phone" aria-hidden="true"></i>
                        </div>
                        <div class="w3-address-right">
                            <h6>Phone Number</h6>
                            <p>+7(953) 367 25 24</p>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <div class="w3-address-grid">
                        <div class="w3-address-left">
                            <i class="fa fa-envelope" aria-hidden="true"></i>
                        </div>
                        <div class="w3-address-right">
                            <h6>Email Address</h6>
                            <p>Email :<a href="mailto:example@email.com"> com.gerasimchuk.maksim@gmail.com</a></p>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <div class="w3-address-grid">
                        <div class="w3-address-left">
                            <i class="fa fa-map-marker" aria-hidden="true"></i>
                        </div>
                        <div class="w3-address-right">
                            <h6>Location</h6>
                            <p>
                            <div>Saint-Petersburg</div>
                            <div> Telephone : +7(953) 367 25 24</div>
                            </p>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
            </div>

            <div class="clearfix"> </div>
        </div>
    </div>
    <div class="copyright">
        <div class="container">
            <p>© 2017 Transporters. All rights reserved | Design by <a href="http://w3layouts.com">W3layouts</a></p>
        </div>
    </div>
</footer>
<!-- //footer -->



<!-- js-scripts -->
<!-- js -->
<script type="text/javascript" src="resources/web/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="resources/web/js/bootstrap.js"></script> <!-- Necessary-JavaScript-File-For-Bootstrap -->
<!-- //js -->

<!-- start-smoth-scrolling -->
<script src="resources/web/js/SmoothScroll.min.js"></script>
<script type="text/javascript" src="resources/web/js/move-top.js"></script>
<script type="text/javascript" src="resources/web/js/easing.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function($) {
        $(".scroll").click(function(event){
            event.preventDefault();
            $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
        });
    });
</script>
<!-- here stars scrolling icon -->
<script type="text/javascript">
    $(document).ready(function() {
        /*
            var defaults = {
            containerID: 'toTop', // fading element id
            containerHoverID: 'toTopHover', // fading element hover id
            scrollSpeed: 1200,
            easingType: 'linear'
            };
        */

        $().UItoTop({ easingType: 'easeOutQuart' });

    });
</script>
<!-- //here ends scrolling icon -->
<!-- start-smoth-scrolling -->

<!-- Baneer-js -->
<script src="resources/web/js/responsiveslides.min.js"></script>
<script>
    $(function () {
        $("#slider").responsiveSlides({
            auto: true,
            pager:false,
            nav: true,
            speed: 1000,
            namespace: "callbacks",
            before: function () {
                $('.events').append("<li>before event fired.</li>");
            },
            after: function () {
                $('.events').append("<li>after event fired.</li>");
            }
        });
    });
</script>
<!-- //Baneer-js -->

<!-- banner bottom video script -->
<script src="resources/web/js/simplePlayer.js"></script>
<script>
    $("document").ready(function() {
        $("#video").simplePlayer();
    });
</script>
<!-- //banner bottom video script -->

<!-- Stats-Number-Scroller-Animation-JavaScript -->
<script src="resources/web/js/waypoints.min.js"></script>
<script src="resources/web/js/counterup.min.js"></script>
<script>
    jQuery(document).ready(function( $ ) {
        $('.counter').counterUp({
            delay: 100,
            time: 1000
        });
    });
</script>
<!-- //Stats-Number-Scroller-Animation-JavaScript -->


<!-- FlexSlider-JavaScript -->
<script defer src="resources/web/js/jquery.flexslider.js"></script>
<script type="text/javascript">
    $(function(){
        SyntaxHighlighter.all();
    });
    $(window).load(function(){
        $('.flexslider').flexslider({
            animation: "slide",
            start: function(slider){
                $('body').removeClass('loading');
            }
        });
    });
</script>
<!-- //FlexSlider-JavaScript -->

<!-- //js-scripts -->
</body>
</html>