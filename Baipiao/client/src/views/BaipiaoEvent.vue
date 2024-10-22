<template>
  <div>
    <hr />
    <!-- Map List -->
    <div class="row my-5">
      <l-map style="height: 600px" :zoom="zoom" :center="center">
        <l-tile-layer :url="osmurl" :attribution="attribution"></l-tile-layer>
        <l-geo-json
          :geojson="formattedEvents"
          :options="geojsonOptions"
          :options-style="styleFunction"
        />
      </l-map>
    </div>
  </div>
</template>

<script>
import { LMap, LTileLayer, LGeoJson } from 'vue2-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css'; // Import leaflet CSS

// Fix Leaflet default icon issue
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png')
});

export default {
  components: {
    LMap,
    LTileLayer,
    LGeoJson, 
  },
  computed: {
    styleFunction() {
      return () => ({
        weight: 2,
        color: "#ECEFF1",
        opacity: 1,
        fillOpacity: 1
      });
    },
    formattedEvents() {
      // Convert events data into GeoJSON format
      return {
        type: "FeatureCollection",
        features: this.events.map(event => ({
          type: "Feature",
          geometry: {
            type: "Point",
            coordinates: [event.longitude, event.latitude], // Use event's longitude and latitude
          },
          properties: {
            name: event.name,
            date: event.date,
            venue: event.venue,
            status: event.status,
            organizer: event.organizer,
          },
        }))
      };
    },
    geojsonOptions() {
      return {
        pointToLayer: this.pointToLayerFunction, // Handle point features as markers
        onEachFeature: this.onEachFeatureFunction
      };
    },
  },
  data() {
    return {
      osmurl: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 14,
      center: [37.2307590318061, -80.42062935893475],
      events: [], // Fetched events, each with longitude, latitude, etc.
    };
  },
  methods: {
    pointToLayerFunction(feature, latlng) {
      // This function turns each point feature into a marker with the default icon
      return L.marker(latlng, { icon: new L.Icon.Default() });
    },
    onEachFeatureFunction(feature, layer) {
      // Bind a tooltip with custom event details
      layer.bindTooltip(
        "<div>Name: " + feature.properties.name + "</div>" +
        "<div>Date: " + feature.properties.date + "</div>" +
        "<div>Venue: " + feature.properties.venue + "</div>" +
        "<div>Status: " + feature.properties.status + "</div>" +
        "<div>Organizer: " + feature.properties.organizer + "</div>",
        { permanent: false, sticky: true }
      );
    },
    async fetchData() {
      try {
        const eventsResponse = await this.$http.get("events");
        this.events = eventsResponse.data.map(event => ({
          ...event,
          date: event.startDate,
          name: event.name,
          longitude: event.location.x, // Custom longitude field
          latitude: event.location.y, // Custom latitude field
          venue: event.venue,
          organizer: event.organizer
        }));
      } catch (error) {
        console.error("Error fetching events:", error);
      }
    },
  },
  created() {
    this.fetchData(); // Fetch events when the component is created
  },
};
</script>

<style scoped>
</style>