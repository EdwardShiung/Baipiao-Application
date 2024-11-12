<template>
  <div>
    <hr />
    <!-- Map List -->
    <div class="row my-5">
      <l-map style="height: 600px" :zoom="zoom" :center="center" ref="map">
        <l-tile-layer :url="osmurl" :attribution="attribution"></l-tile-layer>
        <l-geo-json
          v-if="geoJsonLayer"
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
import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';
import 'leaflet-draw';

import 'leaflet/dist/leaflet.css';
import 'leaflet-draw/dist/leaflet.draw.css';
import 'leaflet-geosearch/dist/geosearch.css';

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
      return {
        type: "FeatureCollection",
        features: this.events.map(event => ({
          type: "Feature",
          geometry: {
            type: "Point",
            coordinates: [event.longitude, event.latitude],
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
        pointToLayer: this.pointToLayerFunction,
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
      events: [],
      geoJsonLayer: null, // Track the GeoJSON layer
    };
  },
  methods: {
    pointToLayerFunction(feature, latlng) {
      return L.marker(latlng, { icon: new L.Icon.Default() });
    },
    onEachFeatureFunction(feature, layer) {
      layer.bindTooltip(
        "<div>Name: " + feature.properties.name + "</div>" +
        "<div>Date: " + feature.properties.date + "</div>" +
        "<div>Venue: " + feature.properties.venue + "</div>" +
        "<div>Status: " + feature.properties.status + "</div>" +
        "<div>Organizer: " + feature.properties.organizer + "</div>",
        { permanent: false, sticky: true }
      );
    },
    async fetchData(url) {
      try {
        const eventsResponse = await this.$http.get(url);
        this.events = eventsResponse.data.map(event => ({
          ...event,
          date: event.startDate,
          name: event.name,
          longitude: event.location.x,
          latitude: event.location.y,
          venue: event.venue,
          organizer: event.organizer
        }));
      } catch (error) {
        console.error("Error fetching events:", error);
      }
    },
    addSearchControl() {
      const provider = new OpenStreetMapProvider();
      const searchControl = new GeoSearchControl({
        provider: provider,
        style: 'bar',
        autoComplete: true,
        autoCompleteDelay: 250,
      });

      const map = this.$refs.map.mapObject;
      map.addControl(searchControl);
    },
    addDrawControl() {
      const map = this.$refs.map.mapObject;
      const drawControl = new L.Control.Draw({
        draw: {
          polyline: false,
          polygon: false,
          rectangle: false,
          marker: false,
          circlemarker: false,
          circle: true
        },
        edit: {
          featureGroup: this.drawnItems,
          remove: false,
          edit: false

        }
      });
      map.addControl(drawControl);

      // Event listener for when a circle is created
      map.on(L.Draw.Event.CREATED, (e) => {
        const type = e.layerType;
        const layer = e.layer;

        if (type === 'circle') {
          const radius = layer.getRadius();
          const center = layer.getLatLng();
          this.fetchData(`events/area/${center.lng}/${center.lat}/${radius}`);
        }

        // this.drawnItems.addLayer(layer);
      });
    },
    updateGeoJsonLayer() {
      // Remove the existing GeoJSON layer if present
      if (this.geoJsonLayer) {
        this.$refs.map.mapObject.removeLayer(this.geoJsonLayer);
      }

      // Create a new GeoJSON layer and add it to the map
      this.geoJsonLayer = L.geoJSON(this.formattedEvents, this.geojsonOptions);
      this.geoJsonLayer.addTo(this.$refs.map.mapObject);
    },
  },
  watch: {
  events() {
    this.updateGeoJsonLayer();
  },
},
  created() {
    this.fetchData("events");
  },
  mounted() {
    this.addSearchControl();
    this.drawnItems = new L.FeatureGroup();
    this.$refs.map.mapObject.addLayer(this.drawnItems);
    this.addDrawControl();
  }
};
</script>