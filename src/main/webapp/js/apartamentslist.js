function getApartamentsListPage(status, statusText) {
    $.get("apartamentslist.html", function (data) {
        $("#mainContainer").html("<div id=\"mainSearchContainer\" class=\"container\"></div>" + data);
        $("#apartamentsListHeaderText").html(statusText);
        $.ajax({
            type: "GET",
            url: "api/apartament/getallapartament?status=" + status,
            success: function (data) {
                var array = JSON.parse(data);
                drawApartamentsListTable(array, status);
                drawApartamentsListMap(array, status);
            },
            error: function (data) {
                showDanger(data.responseText);
                return false;
            }
        });
    });
}

function drawApartamentsListTable(array, status) {
    $("#errorBlock").css("display", "none");
    var str = '<table class="table table-striped table-bordered" cellspacing="0" width="100%" id="apartamentsListTable">';
    str += "<thead>";
    str += "<tr>";
    str += "<th>#</th>";
    str += '<th>Адрес</th>';
    str += '<th>Комнат</th>';
    str += "<th>Площадь<br>О/К/Ж</th>";
    str += '<th>Этаж</th>';
    str += '<th>Цена</th>';
    str += "<th>Дата</th>";
    str += "</tr>";
    str += "</thead>";
    str += "<tbody>";
    str += draw(array, status);
    str += "</tbody>";
    $("#divApartamentsList").html(str);
    $('#apartamentsListTable').dataTable();
}

function draw(array, status) {
    var str = "";
    array.forEach(function (entry) {
        if (entry.isAD == '1') {
            str += '<tr class="success">';
        }
        else {
            str += "<tr>";
        }
        str += '<td><a href="#apartaments/view/' + entry.id + '" class="btn btn-primary"><b>' + entry.id + '</b></a></td>';
        str += "<td><address>" + entry.cityName + "<br>"
                + entry.streetName + "<br>"
                + entry.houseNumber + " "
                + entry.buildingNumber + "</address></td>";
        str += "<td>" + entry.rooms + "</td>";
        str += "<td>" + entry.sizeApartament + " / " + entry.sizeKitchen + " / " + entry.sizeLiving + "</td>";
        str += "<td>" + entry.floor + " / " + entry.floors + "</td>";
        str += "<td>" + entry.price + "</td>";
        str += "<td>" + timeConverter(entry.сreationDate, 'human') + "</td>";
        str += "</tr>";
    });
    return str;
}

function drawApartamentsListMap(array, status) {
    var myMap;
    ymaps.ready(init);
    function init() {
        myMap = new ymaps.Map('mapApartamentsListView', {
            center: [55.989342, 73.368212],
            zoom: 11,
            type: "yandex#publicMap",
            controls: ['largeMapDefaultSet']
        });
//        addPlacemark(myMap, 54.974133, 73.387205, "Пушкина 67 - офис 616");
//        addPlacemark(myMap, 56.901503, 74.373696, "ул. Советская 23 а");
//        addPlacemark(myMap, 55.048864, 74.580722, "ул. Ленина 37 а");
    }
    var myPlacemark;
    array.forEach(function (entry) {
        myPlacemark = new ymaps.Placemark([entry.apartamentLan, entry.apartamentLon], {
            iconContent: "",
            // Чтобы балун и хинт открывались на метке, необходимо задать ей определенные свойства.
            balloonContentHeader: "ID " + entry.id,
            balloonContentBody: entry.cityName + " "
                    + entry.streetName + " "
                    + entry.houseNumber + " "
                    + entry.buildingNumber + " - "
                    + entry.roomNumber,
            balloonContentFooter: "Этаж " + entry.floor + "/" + entry.floors + ", Цена " + entry.price,
            hintContent: entry.cityName + " "
                    + entry.streetName + " "
                    + entry.houseNumber + " "
                    + entry.buildingNumber + " - "
                    + entry.roomNumber
        });
        myMap.geoObjects.add(myPlacemark);
    });
}