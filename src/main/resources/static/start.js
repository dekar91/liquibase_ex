const serverUrl = "http://localhost:8080/api/";
const s = document.createElement("script");
s.src = "https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js";
s.onload = function (e) {
    configure();

};

document.head.appendChild(s);

function configure() {
    const classList = [];

    $(".anal").each(function () {
        const entityClass = extractEntityClass(this);

        if (entityClass.length > 0 && typeof entityClass != "undefined") {
            classList.push(entityClass);

            $(this).on('click', hit)
        }
    });

    $.get(serverUrl + 'config', {"channels": classList.join(',')}, registerElements);
}

function registerElements(config) {

    for (var index in config) {

        var e = config[index];

        var el = $('.' + e.js_class);
        if (typeof el != "undefined") {
            if (el.tagName === 'A') {
                el.attr('href', e.url)
            } else {
                el.on('click', function () {
                    //window.location = e.url;
                })
            }
        }
    }

    console.log(config);
}

function extractEntityClass(entity) {
    const classes = $(entity).attr("class").split(/\s+/);
    for (let i = 0; i < classes.length; i++) {
        if (classes[i].startsWith('entity_')) {
            return classes[i];
        }
    }
}

function hit() {
    o = JSON.stringify({
        data: urlParams(),
        url: window.location.toString(),

    });

    $.ajax({
        url: serverUrl + extractEntityClass(this),
        type: 'POST',
        data: o,
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success: function (data){ console.log(data)}
    });
}

function urlParams() {
    const self = window.location.toString();
    const querystring = self.split("?");
    if (querystring.length > 1) {
        return JSON.parse('{"' + decodeURI(querystring[1].replace(/&/g, "\",\"").replace(/=/g, "\":\"")) + '"}');
    }
}