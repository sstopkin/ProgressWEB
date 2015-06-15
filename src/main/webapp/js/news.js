function getNews() {
    var str = "";
    $.get("api/news", function (data) {
        var array = JSON.parse(data);
        array.forEach(function (entry) {
            str += "<div class=\"panel panel-info\">";
            str += "<div class=\"panel-heading\">#" + entry.id + " | " + "<b>" + entry.header + "</b>" + " | " + timeConverter(entry.lastModify, 'human') + "</div>";
            str += "<div class=\"panel-body\">";
            str += "<div class=\"media-body\">";
            str += "<p>" + entry.text + "</p>";
            for (var it = 0; it < workersList.length; ++it) {
                var a = workersList[it];
                if (entry.idWorker == a[0]) {
                    str += '<p class="pull-right"><i>' + a[3] + ' ' + a[1] + '</i></p>';
                }
            }
            str += "</div>";
            str += "</div>";
            str += "</div>";
            str += "</div>";
        });

        $("#news").html(str);
    }).fail(function (data) {
        showDanger(data.responseText);
    });
}