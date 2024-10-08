var map;
function initMap() {
    var elem = document.getElementById('tt-google-map');
    var _lat = 37.5665;  // 서울의 위도
    var _lng = 126.9780; // 서울의 경도
    var _color = elem.getAttribute('data-color');
    var _sat = elem.getAttribute('data-saturation');
    var _zoom = parseInt(elem.getAttribute('data-zoom'), 10);
    var _marker = elem.getAttribute('data-marker');
    var _contentAddress = elem.innerHTML;
    elem.innerHTML = '';

    var styles = [
        {
            stylers: [
                { hue: _color },
                { saturation: _sat }
            ]
        },
        {
            featureType: "road",
            elementType: "geometry",
            stylers: [
                { lightness: 20 },
                { visibility: "simplified" }
            ]
        }
    ];

    var styledMap = new google.maps.StyledMapType(styles, {name: "Styled Map"});
    var curLatlng = new google.maps.LatLng(_lat, _lng);

    var mapOptions = {
        zoom: _zoom,
        center: curLatlng,
        mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'map_style']
        },
        scrollwheel: false,
        disableDefaultUI :true,
        streetViewControl: false
    };

    map = new google.maps.Map(elem, mapOptions);

    var marker = new google.maps.Marker({
        position: curLatlng,
        title: "Seoul",
        map: map,
        icon: _marker
    });
    marker.setMap(map);

    var infowindow = new google.maps.InfoWindow({
        content: _contentAddress,
        maxWidth: 400
    });

    marker.addListener('click', function() {
        infowindow.open(map, marker);
    });

    map.mapTypes.set('map_style', styledMap);
    map.setMapTypeId('map_style');
}
