var myCenter=new google.maps.LatLng(23.7639212,90.429871);
    function initialize()
    {
        var mapProp = {
            center:myCenter,
            scrollwheel: false,
            zoom:11,
            mapTypeId:google.maps.MapTypeId.ROADMAP
        };
        var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
        var marker=new google.maps.Marker({
            position:myCenter,
            map: map,
        });
	
		var styles =  [
			{
				"stylers": [
					{
						"saturation": 10
					},
					{
						"lightness": 10
					}
				]
			},
			{
				"featureType": "water",
				"stylers": [
					{
						"color": "#effefd"
					}
				]
			},
			{
				"featureType": "all",
				"elementType": "labels",
				"stylers": [
					{
						"visibility": "off"
					}
				]
			},
			{
				"featureType": "administrative",
				"elementType": "labels",
				"stylers": [
					{
						"visibility": "on"
					}
				]
			},
			{
				"featureType": "road",
				"elementType": "all",
				"stylers": [
					{
						"visibility": "off"
					}
				]
			},
			{
				"featureType": "transit",
				"elementType": "all",
				"stylers": [
					{
						"visibility": "off"
					}
				]
			}
		];
		
        map.setOptions({styles: styles});
        marker.setMap(map);
    }
google.maps.event.addDomListener(window, 'load', initialize);

	