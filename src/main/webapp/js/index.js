var type;
var workersList = "";
var oktell;

var permissions;

var map = null;
var placemark = null;
var map_created = false;
$(document).ready(function () {
    parseUrl(location.href);
    $("#closeAlert").click(function () {
        $("#errorBlock").css("display", "none");
    });
    if (workersList == "") {
        getAllWorkersList();
    }
});

function getMainPage() {
    $.get("main.html", function (data) {
        $("#mainContainer").html(data);
    });
    getMain();
}

function getNewsPage() {
    $.get("news.html", function (data) {
        $("#mainContainer").html(data);
    });
    getNews();
}

function showDanger(message) {
    var some_html = '<div class="bs-callout bs-callout-danger">';
    some_html += '<h4>Ошибка</h4>';
    some_html += '<p>' + message + '</p>';
    some_html += '</div>';
    bootbox.alert(some_html);
}

function showWarning(message) {
    var some_html = '<div class="bs-callout bs-callout-warning">';
    some_html += '<h4>Предупреждение</h4>';
    some_html += '<p>' + message + '</p>';
    some_html += '</div>';
    bootbox.alert(some_html);
}

function showSuccess(message) {
    var some_html = '<div class="bs-callout bs-callout-info">';
    some_html += '<h4>Выполнено успешно</h4>';
    some_html += '<p>' + message + '</p>';
    some_html += '</div>';
    bootbox.alert(some_html);
}

function getAllWorkersList() {
    $.ajax({
        type: "GET",
        url: "api/auth/userslist",
        async: false,
        success: function (data) {
            workersList = JSON.parse(data);
            return true;
        },
        error: function (data) {
            showDanger(data.responseText);
            return false;
        }
    });
}

function getWorkersFullNameById(idWorker) {
    for (var i = 0; i < workersList.length; ++i) {
        var a = workersList[i];
        if (idWorker === a[0]) {
            return a[1] + " " + a[3];
        }
    }
}

function timeConverter(UNIX_timestamp, param) {
    var a = new Date(UNIX_timestamp);
    var year = a.getFullYear();
    var months = ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июнь', 'Июль', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'];
    var month = a.getMonth() + 1;
    month = (parseInt(month, 10) < 10) ? ('0' + month) : (month);
    var date = a.getDate();
    date = (parseInt(date, 10) < 10) ? ('0' + date) : (date);
    var hour = a.getHours();
    hour = (parseInt(hour, 10) < 10) ? ('0' + hour) : (hour);
    var min = a.getMinutes();
    min = (parseInt(min, 10) < 10) ? ('0' + min) : (min);
    var sec = a.getSeconds();
    sec = (parseInt(sec, 10) < 10) ? ('0' + sec) : (sec);
    switch (param) {
        case 'short':
            return year + '-' + month + '-' + date;
        case 'human':
            var month = months[a.getMonth()];
            return date + '-' + month + '-' + year + ' ' + hour + ':' + min;
        case 'human-short':
            var month = months[a.getMonth()];
            return date + '-' + month + '-' + year;
        case 'full':
            return year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec;
        default:
            return year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec;
    }
}

function getTimeStamp(date) {
    return new Date(date).getTime();
}