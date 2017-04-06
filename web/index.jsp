<%@ page import="ties437.servlets.BookingServlet" %>
<%@ page import="ties437.servlets.CityServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Home</title>

    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/index.css" rel="stylesheet">
    <link href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet">
</head>
<body>
<table style="width: 100%">
    <tbody>
    <tr>
        <td id="td-left">
            <div class="container">
                <form enctype="multipart/form-data" class="form-horizontal">
                    <div class="form-group row">
                        <div class="col-md-12">
                            <label>Your name</label>
                            <input type="text" class="form-control" id="bookerName" name="bookerName"
                                   placeholder="e.g. Pekka"/>
                        </div>

                    </div>

                    <div class="form-group row">
                        <label class="col-md-9">Number of Places</label>
                        <div class="col-md-3">
                            <input type="text" class="form-control" id="minTotalPlaceCount" name="minTotalPlaceCount"
                                   placeholder="e.g. 2"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-9">Number of Bedrooms</label>
                        <div class="col-md-3">
                            <input type="text" class="form-control" id="minBedroomCount" name="minBedroomCount"
                                   placeholder="e.g. 2"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-9">Max Distance to a Lake (m)</label>
                        <div class="col-md-3">
                            <input type="text" class="form-control" id="maxDistanceToLake" name="maxDistanceToLake"
                                   placeholder="e.g. 42"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-3">City</label>
                        <div class="col-md-9">
                            <select class="form-control" id="city" name="city"></select>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-9">Max Distance from City (km)</label>
                        <div class="col-md-3">
                            <input type="text" class="form-control" id="maxDistance" name="maxDistance"
                                   placeholder="e.g. 4.2"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-12">
                            <label>Booking Start Date</label>
                            <div class='input-group date'>
                                <input type='text' id="startDateString" name="startDateString" class="form-control"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-time"></span>
                                </span>
                            </div>
                        </div>

                    </div>

                    <div class="form-group row">
                        <label class="col-md-9">Booking Duration (days)</label>
                        <div class="col-md-3">
                            <input type="text" class="form-control" id="durationDay" name="durationDay"
                                   placeholder="e.g. 6"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-md-9">Flexible Date (days)</label>
                        <div class="col-md-3">
                            <input type="text" class="form-control" id="flexDay" name="flexDay"
                                   placeholder="e.g. 3"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-12">
                            <div id="div-button">
                                <input class="btn btn-primary btn-block"
                                       id="button-search" type="button"
                                       value="SEARCH"/>
                                <img id="img-load" src="resources/loading.gif" style="display: none;"/>
                            </div>
                        </div>

                    </div>
                </form>
            </div>
        </td>
        <td>
            <div class="container" id="result">
                <%--<div &lt;%&ndash;class="form-control"&ndash;%&gt; id="result" class="row"></div>--%>
                <%--<div>--%>
                <%--<textarea class="form-control" id="textarea-result"></textarea>--%>
                <%--</div>--%>

            </div>

        </td>
    </tr>
    </tbody>
</table>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
<%--<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>--%>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script>
    var bookingUrl = '${pageContext.request.contextPath}' + '<%= BookingServlet.PATH %>';
    var cityUrl = '${pageContext.request.contextPath}' + '<%= CityServlet.PATH %>';

</script>
</body>
</html>
