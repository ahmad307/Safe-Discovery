var data = [
    [30.012815,31.289761,30.013502,31.287544, 'green'],
    [30.013502,31.287544,30.014068,31.285439,'green'],
    [30.014068,31.285439,30.015095,31.281898,'yellow'],
    [30.013327,31.287314,30.012036,31.286758,'red'],
    [30.012036,31.286758,30.010504,31.286185,'yellow'],
    [30.012668,31.289687,30.010888,31.290153,'yellow'],
    [30.011965,31.286937,30.011402,31.288732,'yellow'],
    [30.011012,31.288624,30.011566,31.286756,'green'],
    [30.011371,31.288813,30.011621,31.289887,'red'],
    [30.010996,31.288651,30.009645,31.28912,'green'],
    [30.010793,31.287207,30.010332,31.288831,'green'],
    [30.011346,31.287348,30.010275,31.286953,'yellow'],
    [30.012621,31.289707,30.010886,31.290185,'red'],
    [30.013573,31.287538,30.01423,31.287773,'red'],
    [30.014086,31.287568,30.014499,31.286058,'yellow']
]
/**
* Adds a polyline between Dublin, London, Paris and Berlin to the map
*
* @param  {H.Map} map      A HERE Map instance within the application
*/
function addPolylineToMap(map) {
    for (var i=0;i<data.length;i++){
        var lineString = new H.geo.LineString();

        lineString.pushPoint({lat:data[i][0], lng:data[i][1]});
        lineString.pushPoint({lat:data[i][2], lng:data[i][3]});

        var myColor;
        if (data[i][4] == 'red')
            myColor = '#ff0000';
        else if (data[i][4] == 'yellow')
            myColor = '#ffd900';
        else if (data[i][4] == 'green')
            myColor = '#0de99a';
        
        map.addObject(new H.map.Polyline(
        lineString, { style: { lineWidth: 4, strokeColor: myColor }}
        ));
    }
}


/**
* Boilerplate map initialization code starts below:
*/

//Step 1: initialize communication with the platform
var platform = new H.service.Platform({
    app_id: 'qGiZiXtd8ERGAjh19GVy',
    app_code: '1_lj4pbQ6BrtThgc-0kzQQ',
    useHTTPS: true
});
var pixelRatio = window.devicePixelRatio || 1;
var defaultLayers = platform.createDefaultLayers({
    tileSize: pixelRatio === 1 ? 256 : 512,
    ppi: pixelRatio === 1 ? undefined : 320
});

//Step 2: initialize a map - this map is centered over Europe
var map = new H.Map(document.getElementById('map'),
defaultLayers.normal.map,{
    center: {lat:30.013, lng:31.28},
    zoom: 15,
    pixelRatio: pixelRatio
});

//Step 3: make the map interactive
// MapEvents enables the event system
// Behavior implements default interactions for pan/zoom (also on mobile touch environments)
var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

// Create the default UI components
var ui = H.ui.UI.createDefault(map, defaultLayers);


// Now use the map as required...
addPolylineToMap(map);