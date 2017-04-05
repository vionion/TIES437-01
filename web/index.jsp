<%--<%@ page import="ties437.SpeechToTextModel" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
    <%--<meta charset="utf-8">--%>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <%--<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->--%>
    <%--<meta name="description" content="">--%>
    <%--<meta name="author" content="">--%>

    <%--<title>Home</title>--%>

    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--<link href="${pageContext.request.contextPath}/resources/css/index.css" rel="stylesheet">--%>
<%--</head>--%>
<%--<body>--%>
<%--<table style="width: 100%">--%>
    <%--<tbody>--%>
    <%--<tr>--%>
        <%--<td id="td-left">--%>
            <%--<div>--%>
                <%--<form enctype="multipart/form-data">--%>
                    <%--<label>Language</label>--%>
                    <%--<select class="form-control" id="lang" name="lang"></select>--%>
                    <%--&lt;%&ndash;<label>Audio File</label>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type="file" id="file" name="file"/>&ndash;%&gt;--%>
                    <%--<label>Audio file</label>--%>
                    <%--<div class="input-group">--%>
                        <%--<label class="input-group-btn">--%>
                        <%--<span id="button-browse" class="btn btn-primary">--%>
                        <%--Browse... <input type="file" id="file" name="file" style="display: none;">--%>
                        <%--</span>--%>
                        <%--</label>--%>
                        <%--<input id="filename" type="text" class="form-control" readonly="">--%>
                    <%--</div>--%>
                    <%--&lt;%&ndash;<td><a href="#" id="button-transcript" onclick="transcript()">TRANSCRIPT</a></td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input class="btn btn-lg btn-primary btn-block"&ndash;%&gt;--%>
                    <%--<div id="div-button">--%>
                        <%--<input class="btn btn-primary btn-block"--%>
                               <%--id="button-transcript" type="button"--%>
                               <%--value="TRANSCRIPT"/>--%>
                        <%--<img id="img-load" src="resources/loading.gif" style="display: none;"/>--%>
                    <%--</div>--%>

                <%--</form>--%>
            <%--</div>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<div>--%>
                <%--<div class="form-control" id="container"></div>--%>
                <%--<div>--%>
                    <%--<textarea class="form-control" id="textarea-result"></textarea>--%>
                <%--</div>--%>

            <%--</div>--%>

        <%--</td>--%>
    <%--</tr>--%>
    <%--</tbody>--%>
<%--</table>--%>

<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>--%>
<%--<script>--%>
    <%--var postUrl = '${pageContext.request.contextPath}' + '/speech_to_text/sessionless';--%>

    <%--var languageList = <%= SpeechToTextModel.getLanguageJSONArray() %>;--%>
    <%--for (var i = 0; i <= languageList.length - 1; i++) {--%>
        <%--var languageOptionElement = document.createElement('option');--%>
        <%--languageOptionElement.innerText = languageList[i];--%>

        <%--if (languageList[i] == '<%= SpeechToTextModel.DEFAULT_MODEL_LANGUAGE %>') {--%>
            <%--languageOptionElement.setAttribute('selected', 'selected');--%>
        <%--}--%>

        <%--document.getElementById('lang').appendChild(languageOptionElement);--%>
    <%--}--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>
