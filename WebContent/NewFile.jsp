<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Twitter Map</title>
<style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
      }
#floating-panel {
  position: absolute;
  bottom: 10px;
  left: 25%;
  right:25%;
  z-index: 5;
  background:transparent;
  text-align: center;
  font-family: 'Roboto','sans-serif';
  line-height: 100px;
}

#dropdown{
  position: absolute;
  right:10px;
  z-index: 5;
  top:45%;
  bottom:50%;
  background:transparent;
  text-align: center;
  font-family: 'Roboto','sans-serif';
  line-height: 30px;
  
}

   </style>
</head>
<body>
<%
double[] latitude=(double[])request.getAttribute("latitude");
double[] longitude=(double[])request.getAttribute("longitude");
int[] news=(int[])request.getAttribute("news");
int[] sports=(int[])request.getAttribute("sports");
int[] music=(int[])request.getAttribute("music");
int[] personal=(int[])request.getAttribute("personal");
%>
<div id="floating-panel">
      <button onclick="toggleHeatmap()">Toggle Heatmap</button>
      <button onclick="changeGradient()">Change gradient</button>
      <button onclick="changeRadius()">Change radius</button>
      <button onclick="changeOpacity()">Change opacity</button>
</div>
<div id="dropdown">
      <select id="dropvalue" onchange="initMap()">
      <option value="all" selected="selected">All</option>
      <option value="personal">Personal</option>
      <option value="news">News</option>
      <option value="sports">Sports</option>
      <option value="music">Music</option> 
      </select>
</div>

<div id="map"> </div>
<script>

var map,heatmap;
function initMap() {
  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 2,
    center: {lat: 0, lng: 0},
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });

  heatmap = new google.maps.visualization.HeatmapLayer({
    data: getPoints(),
    map: map
  });	
  <%response.setIntHeader("Refresh", 60);%>
}

function toggleHeatmap() {
  heatmap.setMap(heatmap.getMap() ? null : map);
}

function changeGradient() {
  var gradient = [
    'rgba(0, 255, 255, 0)',
    'rgba(0, 255, 255, 1)',
    'rgba(0, 191, 255, 1)',
    'rgba(0, 127, 255, 1)',
    'rgba(0, 63, 255, 1)',
    'rgba(0, 0, 255, 1)',
    'rgba(0, 0, 223, 1)',
    'rgba(0, 0, 191, 1)',
    'rgba(0, 0, 159, 1)',
    'rgba(0, 0, 127, 1)',
    'rgba(63, 0, 91, 1)',
    'rgba(127, 0, 63, 1)',
    'rgba(191, 0, 31, 1)',
    'rgba(255, 0, 0, 1)'
  ]
  heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
}

function changeRadius() {
  heatmap.set('radius', heatmap.get('radius') ? null : 20);
}

function changeOpacity() {
  heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
}

// Heatmap data: 500 Points
function getPoints() {
	
	var points;
	points=new Array();
	var value1=document.getElementById("dropvalue");
	var dropvalue=value1.options[value1.selectedIndex].value;
	var c=0;
	<%
	for(int i=0;i<latitude.length;i++){
		%>
		if(dropvalue=="all"){
			var a='<%=latitude[i]%>';
			var b='<%=longitude[i]%>';
			var l = new google.maps.LatLng(a,b);
			points[c]=l;
			c=c+1;
		}
		else{
			if(dropvalue=="personal"){
				var p=<%=personal[i]%>;
				if(p==1){
					var a='<%=latitude[i]%>';
					var b='<%=longitude[i]%>';
					var l = new google.maps.LatLng(a,b);
					points[c]=l;
					c=c+1;
				}
			}
			else{
				if(dropvalue=="news"){
					var p=<%=news[i]%>;
					if(p==1){
						var a='<%=latitude[i]%>';
						var b='<%=longitude[i]%>';
						var l = new google.maps.LatLng(a,b);
						points[c]=l;
						c=c+1;
					}
				}
				else{
					if(dropvalue=="sports"){
						var p=<%=sports[i]%>;
						if(p==1){
							var a='<%=latitude[i]%>';
							var b='<%=longitude[i]%>';
							var l = new google.maps.LatLng(a,b);
							points[c]=l;
							c=c+1;
						}
					}
					else{
						if(dropvalue=="music"){
							var p=<%=music[i]%>;
							if(p==1){
								var a='<%=latitude[i]%>';
								var b='<%=longitude[i]%>';
								var l = new google.maps.LatLng(a,b);
								points[c]=l;
								c=c+1;
							}
						}
					}
				}
			}
		}
	<%
	}
	%>
	return points;
	    
}

    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=APIKEY&signed_in=true&libraries=visualization&callback=initMap">
</script>

</body>
</html>