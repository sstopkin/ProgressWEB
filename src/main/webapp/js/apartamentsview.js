function getApartamentViewPage(apartamentId) {
    $.get("apartamentsview.html", function (data) {
        $("#mainContainer").html(data);
        var content = "";
        var array;
        $.ajax({
            type: "GET",
            url: "api/apartament/getapartament?id=" + apartamentId,
            async: false,
            success: function (data) {
                $("#errorBlock").css("display", "none");
                array = JSON.parse(data);
                content += '<p class="lead">Основная информация</p>';
                content += "<p>";
                content += "ID = " + array.id;
                content += "</p>";
                content += "<p>";
                content += "Статус: ";
                switch (array.typeOfSales) {
                    case 0:
                        content += "Прозвон";
                        break;
                    case 1:
                        content += "В работе";
                        break;
                    case 4:
                        content += "Архив";
                        break;
                    case 5:
                        content += "Не выбран";
                        break;
                    default:
                        content += array.typeOfSales;
                }
                content += "</p>";
                content += "<p>";
                switch (array.typeOfSales) {
                    case 1:
                        content += "Эксклюзивная продажа";
                        break;
                    case 2:
                        content += "Общая продажа";
                        break;
                    default:
                        content += "";
                }
                content += "</p>";
                content += "<p>";
                content += "Адрес: "
                        + array.cityName + " "
                        + array.streetName + " "
                        + array.houseNumber + " "
                        + array.buildingNumber + " - "
                        + array.roomNumber + " ";
                content += "</p>";
                content += "<p>";
                content += "Количество комнат: " + array.rooms;
                content += "</p>";
                content += "<p>";
                content += "Цена: " + array.price;
                content += "</p>";
                content += '<p class="lead">Дополнительная информация</p>';
                if (array.MethodOfPurchase_Mortgage) {
                    content += "<p>";
                    content += "Ипотека";
                    content += "</p>";
                }
                if (array.MethodOfPurchase_PureSale) {
                    content += "<p>";
                    content += "Чистая продажа";
                    content += "</p>";
                }
                if (array.MethodOfPurchase_Exchange) {
                    content += "<p>";
                    content += "Обмен";
                    content += "</p>";
                }
                if (array.MethodOfPurchase_Rent) {
                    content += "<p>";
                    content += "Аренда";
                    content += "</p>";
                }

                content += "<p>";
                content += "Перепланировки: ";
                if (array.rePplanning) {
                    content += "Да";
                }
                else {
                    content += "Нет";
                }
                content += "</p>";
                content += "<p>";
                switch (array.cityDistrict) {
                    case 1:
                        content += "Кировский административный округ";
                        break;
                    case 2:
                        content += "Ленинский административный округ";
                        break;
                    case 3:
                        content += "Октябрьский административный округ";
                        break;
                    case 4:
                        content += "Советский административный округ";
                        break;
                    case 5:
                        content += "Центральный административный округ";
                        break;
                    default:
                        content += "";
                }
                content += "</p>";
                content += "<p>";
                content += "Балкон: ";
                if (array.balcony === 0) {
                    content += "Нет";
                }
                else {
                    content += array.balcony;
                }
                content += "</p>";
                content += "<p>";
                content += "Лоджия: ";
                if (array.loggia === 0) {
                    content += "Нет";
                }
                else {
                    content += array.loggia;
                }
                content += "</p>";
                content += "<p>";
                content += "Этажность: " + array.floors;
                content += "</p>";
                content += "<p>";
                content += "Этаж: " + array.floor;
                content += "</p>";
                content += "<p>";
                content += "Год постройки дома: " + array.yearOfConstruction;
                content += "</p>";
                content += "<p>";
                content += "Материал дома: ";
                switch (array.material) {
                    case 1:
                        content += "Панельный";
                        break;
                    case 2:
                        content += "Кирпичный";
                        break;
                    case 3:
                        content += "Сборный ж/б";
                        break;
                    case 4:
                        content += "Другое/Не указан";
                        break;
                    default:
                        content += "";
                }
                content += "</p>";
                content += "<p>";
                content += "Объект добавлен: " + timeConverter(array.сreationDate, 'human');
                content += "</p>";
                content += "<p>";
                content += "Объект изменен: " + timeConverter(array.lastModify, 'human');
                content += "</p>";
                content += "<p>";
                content += "Добавлено: " + getWorkersFullNameById(array.idWorker);
                content += "</p>";
                content += "<p>";
                content += "Риэлтор: " + getWorkersFullNameById(array.idWorkerTarget);
                content += "</p>";
                console.log("FIXME: array KLADRid" + array.kladrId);
                content += "<p>";
                content += "Площадь общая: " + array.sizeApartament;
                content += "</p>";
                content += "<p>";
                content += "Площадь кухни: " + array.sizeKitchen;
                content += "</p>";
                content += "<p>";
                content += "Площадь жилая: " + array.sizeLiving;
                content += "</p>";
                content += '<p class="lead">Описание: </p>';
                content += "<p>";
                content += array.description;
                content += "</p>";
                initMapView(array.apartamentLan, array.apartamentLon,
                        array.cityName + " "
                        + array.streetName + " "
                        + array.houseNumber + " "
                        + array.buildingNumber + " - "
                        + array.roomNumber);
            },
            error: function (data) {
                showDanger(data.responseText);
                return false;
            }
        });
        $("#apartamentsFeatures").html(content);
    });
}

function initMapView(apartamentLan, apartamentLon, address) {
    var myMap;
    ymaps.ready(init);
    function init() {
        myMap = new ymaps.Map('mapApartamentsView', {
            center: [apartamentLan, apartamentLon],
            zoom: 16,
            type: "yandex#publicMap",
            controls: ['largeMapDefaultSet']
        });
    }
    addPlacemark(myMap, apartamentLan, apartamentLon, address);
    function addPlacemark(myMap, lat, lon, address) {
        myMap.geoObjects.add(new ymaps.Placemark([lat, lon], {
            balloonContentHeader: "",
            balloonContentBody: address,
            hintContent: ""
        }, {
            preset: 'islands#darkGreenDotIcon'
        }));
    }
}