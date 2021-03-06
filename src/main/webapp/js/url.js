function parseUrl(str) {
    // http://kevin.vanzonneveld.net
    // +      original by: Steven Levithan (http://blog.stevenlevithan.com)
    // + reimplemented by: Brett Zamir (http://brett-zamir.me)
    // + input by: Lorenzo Pisani
    // + input by: Tony
    // + improved by: Brett Zamir (http://brett-zamir.me)
    // %          note: Based on http://stevenlevithan.com/demo/parseuri/js/assets/parseuri.js
    // %          note: blog post at http://blog.stevenlevithan.com/archives/parseuri
    // %          note: demo at http://stevenlevithan.com/demo/parseuri/js/assets/parseuri.js
    // %          note: Does not replace invalid characters with '_' as in PHP, nor does it return false with
    // %          note: a seriously malformed URL.
    // %          note: Besides function name, is essentially the same as parseUri as well as our allowing
    // %          note: an extra slash after the scheme/protocol (to allow file:/// as in PHP)
    // *     example 1: parse_url('http://username:password@hostname/path?arg=value#anchor');
    // *     returns 1: {scheme: 'http', host: 'hostname', user: 'username', pass: 'password', path: '/path', query: 'arg=value', fragment: 'anchor'}
    var query, key = ['source', 'scheme', 'authority', 'userInfo', 'user', 'pass', 'host', 'port',
        'relative', 'path', 'directory', 'file', 'query', 'fragment'],
            ini = (this.php_js && this.php_js.ini) || {},
            mode = (ini['phpjs.parse_url.mode'] &&
                    ini['phpjs.parse_url.mode'].local_value) || 'php',
            parser = {
                php: /^(?:([^:\/?#]+):)?(?:\/\/()(?:(?:()(?:([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?))?()(?:(()(?:(?:[^?#\/]*\/)*)()(?:[^?#]*))(?:\?([^#]*))?(?:#(.*))?)/,
                strict: /^(?:([^:\/?#]+):)?(?:\/\/((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?))?((((?:[^?#\/]*\/)*)([^?#]*))(?:\?([^#]*))?(?:#(.*))?)/,
                loose: /^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/\/?)?((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/ // Added one optional slash to post-scheme to catch file:/// (should restrict this)
            };

    var m = parser[mode].exec(str),
            uri = {},
            i = 14;
    while (i--) {
        if (m[i]) {
            uri[key[i]] = m[i];
        }
    }
    if (mode !== 'php') {
        var name = (ini['phpjs.parse_url.queryKey'] &&
                ini['phpjs.parse_url.queryKey'].local_value) || 'queryKey';
        parser = /(?:^|&)([^&=]*)=?([^&]*)/g;
        uri[name] = {};
        query = uri[key[12]] || '';
        query.replace(parser, function ($0, $1, $2) {
            if ($1) {
                uri[name][$1] = $2;
            }
        });
    }
    delete uri.source;
//    ##################
    $(document).scrollTop(0, 0);
    var pathApartaments = "apartaments";
    var pathWorkers = "workers";
    var pathNews = "news";

    if (!uri.fragment) {
        getMainPage();
        return;
    }
    var arr = trim(JSON.stringify(uri.fragment)).split('/');
    if (!arr) {
        getMainPage();
        return;
    }
    if (arr[0] === pathNews) {
        getNewsPage();
        return;
    }
    if (arr[0] === pathWorkers) {
        helpParseUrl(uri, arr, "workers");
        return;
    }
    if (arr[0] === pathApartaments) {
        helpParseUrl(uri, arr, "apartaments");
        return;
    }
    get404Page();
//    ##################
}

function trim(str) {
    return str.replace(/"/g, "");
}

function helpParseUrl(uri, arr, type) {
    if ((type === "apartaments")) {
        if (arr[1] === "view") {
            if (!arr[2]) {
                showDanger();
                return;
            }
            getApartamentViewPage(arr[2]);
            return;
        }
        if (arr[1] === "list")
        {
            if (!arr[2]) {
                showDanger();
                return;
            }
            switch (arr[2]) {
                case "prepare":
                    getApartamentsListPage(0, "Прозвон");
                    break
                case "price":
                    getApartamentsListPage(1, "Прайс");
                    break
                case "archive":
                    getApartamentsListPage(4, "Архив");
                    break
                case "notset":
                    getApartamentsListPage(5, "Не выбран");
                    break
            }
            return;
        }
        if ((arr[1] === "view") && (type === "workers")) {
            if (!arr[2]) {
                showDanger();
                return;
            }
            getWorkersViewPage(arr[2]);
            return;
        }
        get404Page(uri);
        return;
    }
}

function get404Page(uri) {
    console.log(uri);
    $.get("404.html", function (data) {
        $("#mainContainer").html(data);
        //FIXME
        $("#currentLocation").text();
        location.hash = "404";
    });
}

$(document).ready(function () {
    $(window).bind('hashchange', function () {
        $('#ajaxProgress').css('display', 'none');
        $("#errorBlock").css("display", "none");
        parseUrl(location.hash);
    });
});
//        if ((arr[1] == "solve") && (type == "task")) {
//            if (!arr[2]) {
//                showDanger();
//                return;
//            }
//            getSolveTaskPage(arr[2]);
//            return;
//        }
//
//        if ((arr[1] == "diff") && (type == "task")) {
//            if (!arr[2]) {
//                showDanger();
//                return;
//            }
//            getListByDiff(type, undefined, arr[2]);
//            return;
//        }
//
//        if (arr[1] == "add") {
//            if (type == "task") {
//                getAddTaskPage();
//                return;
//            }
//            if (type == "course") {
//                getAddCoursePage();
//                return;
//            }
//        }
//
//        if (arr[1] == "view") {
//            getContent(type, arr[2]);
//            return;
//        }
//
//        if (arr[1] == "edit") {
//            getEditContentPage(type, arr[2]);
//            return;
//        }

//        if (arr[1] == "tag") {
//            var afterDecoding = decodeURIComponent(arr[2]);
//            getListByTag(type, afterDecoding, 0);
//            return;
//        }