<template>
  <div>
    <hr />
    <div class="row" v-if="usertype==='user'">
      <div class="col-md-2">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>Today</p> {{ eventsTodayCount }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Count
          </div>
        </stats-card>
      </div>
      <div class="col-md-3">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>This Week</p> {{ eventsThisWeekCount }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Count
          </div>
        </stats-card>
      </div>
      <div class="col-md-2">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>Next Week</p> {{ eventsNextWeekCount }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Count
          </div>
        </stats-card>
      </div>
      <div class="col-md-3">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>Registered</p> {{ eventsRegistered }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Count
          </div>
        </stats-card>
      </div>
      <div class="col-md-2">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>Attended</p> {{ eventsAttended }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Events you attended
          </div>
        </stats-card>
      </div>
    </div>
    <div class="row" v-if="usertype==='organization'">
      <div class="col-md-4">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>Events</p> {{ totalEventsCount }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Total Number of Events
          </div>
        </stats-card>
      </div>
      <div class="col-md-4">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>Attended</p> {{ totalAttendance }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Avg {{ avgAttendance.toFixed(2) }} - Min {{ minAttendance }} - Max {{ maxAttendance }}

          </div>
        </stats-card>
      </div>
      <div class="col-md-4">
        <stats-card>
          <div class="icon-big text-center icon-warning" slot="header" >
            <i class="ti-user"></i>
          </div>
          <div class="numbers" slot="content">
            <p>Capacity</p> {{ totalCapacity }}
          </div>
          <div class="stats" slot="footer">
            <i class="ti-reload"></i> Avg {{ avgCapacity.toFixed(2) }} - Min {{ minCapacity }} - Max {{ maxCapacity }}

          </div>
        </stats-card>
      </div>
      
     </div>
    <!-- Map List -->
    <div class="row my-5">
      <div>
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: activeTab === 'map' }"
            @click="activeTab = 'map'"
            href="#"
          >
            Map View
          </a>
        </li>
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: activeTab === 'list' }"
            @click="activeTab = 'list'"
            href="#"
          >
            List View
          </a>
        </li>
      </ul>
    </div>
      <div class="tab-content">
        <div v-show="activeTab === 'map'" class="tab-pane fade show active" >
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
        <div v-show="activeTab === 'list'" class="tab-pane fade show active">
            <table class="table bg-white rounded shadow-lg table-hover">
              <thead>
                <tr>
                  <th scope="col" width="10">#</th>
                  <th scope="col">Event</th>
                  <th scope="col">Location</th>
                  <th scope="col">Date</th>
                  <th scope="col">Status</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(event, index) in events" :key="event.id">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ event.name }}</td>
                  <td>{{ event.venue }}</td>
                  <td>{{ event.startDate }}</td>
                  <td>{{ event.status }}</td>
                  <td>
                    <button @click="viewEvent(event.id)" class="btn btn-primary btn-sm">
                      View
                    </button> &nbsp;
                    <button v-if="usertype!='user'"  @click="editEvent(event.id)" class="btn btn-secondary btn-sm">
                      Edit
                    </button>&nbsp;
                    <button v-if="usertype!='user'"  @click="deleteEvent(event.id)" class="btn btn-danger btn-sm">
                      Delete
                    </button> 
                </td>
                </tr>
              </tbody>
            </table>
        </div>
        
      </div>

    </div>
    <div class="row">
        <div class="col text-right" v-if="usertype!='user'">
          <button @click="openModal" class="btn btn-primary">Create</button>
        </div>
      </div>
      <!-- Modal Dialog for Creating Events -->
      <div v-if="showModal" class="modal" tabindex="-1" role="dialog" style="display: block;">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 v-if="modalMode=='create'" class="modal-title">Create New Event</h5>
              <h5 v-if="modalMode=='edit'" class="modal-title">Edit Event: {{currentEvent.name}}</h5>
              <h5 v-if="modalMode=='view'" class="modal-title">Event: {{currentEvent.name}}</h5>
              <button
                  type="button"
                  class="close"
                  @click="closeModal"
                  aria-label="Close"
                  style="margin-left: auto"
                >
                  <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
              <!-- Form for creating event -->
              <form>
                <div class="form-group">
                  <label for="eventName">Event Name</label>
                  <input
                    type="text"
                    class="form-control"
                    id="eventName"
                    v-model="currentEvent.name"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
  
                <div class="form-group">
                  <label for="eventDetails">Event Details</label>
                  <textarea
                    class="form-control"
                    id="eventDetails"
                    :disabled="modalMode === 'view'"
                    rows="3"
                    required>{{currentEvent.details}}</textarea>
                </div>
                <div class="form-group">
                  <label for="startDate">Start Date</label>
                  <input
                    type="datetime-local"
                    class="form-control"
                    id="startDate"
                    v-model="formattedStartDate"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group">
                  <label for="endDate">End Date</label>
                  <input
                    type="datetime-local"
                    class="form-control"
                    id="endDate"
                    v-model="formattedEndDate"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group toggle-container">
                <label for="registrationRequired" class="toggle-label">Registration Required</label> &nbsp; &nbsp;
                <label class="switch">
                  <input
                    type="checkbox"
                    id="registrationRequired"
                    v-model="currentEvent.registrationRequired"
                    :disabled="modalMode === 'view'"
                    class="toggle-checkbox"
                  />
                  <span class="slider"></span>
                </label>
              </div>
                <div class="form-group" v-if="currentEvent.registrationRequired">
                  <label for="registrationLink">Registration Link</label>
                  <input
                    type="text"
                    class="form-control"
                    id="registrationLink"
                    v-model="currentEvent.registrationLink"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group" v-if="currentEvent.registrationRequired">
                  <label for="registrationDeadline">Registration Deadline</label>
                  <input
                    type="datetime-local"
                    class="form-control"
                    id="registrationDeadline"
                    v-model="formattedRegistrationDeadline"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group">
                  <label for="contactEmail">Contact Email</label>
                  <input
                    type="email"
                    class="form-control"
                    id="contactEmail"
                    v-model="currentEvent.contactEmail"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group">
                  <label for="contactPhoneNumber">Contact Phone</label>
                  <input
                    type="phone"
                    class="form-control"
                    id="contactPhoneNumber"
                    v-model="currentEvent.contactPhoneNumber"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group">
                  <label for="capacity">Capacity</label>
                  <input
                    type="number"
                    class="form-control"
                    id="capacity"
                    v-model="currentEvent.capacity"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group">
                  <label for="image">Image</label>
                  <input
                    type="url"
                    class="form-control"
                    id="image"
                    v-model="currentEvent.image"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
  
                <div class="form-group">
                  <label for="venue">Venue</label>
                  <v-select
                    :options="venues"
                    v-model="currentEvent.venueId"
                    :reduce="venue => venue.id"
                    label="name"
                    placeholder="Search Venue"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
  
                <div class="form-group">
                  <label for="category">Category</label>
                  <v-select
                    :options="categories"
                    v-model="currentEvent.categoryId"
                    :reduce="category => category.id"
                    label="name"
                    placeholder="Search Category"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
  
                <div class="form-group">
                  <label for="organizer">Organizer</label>
                  <v-select
                    :options="organizers"
                    v-model="currentEvent.organizerId"
                    :reduce="organizer => organizer.id"
                    label="name"
                    placeholder="Search Organizer"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="form-group">
                  <label for="status">Status</label>
                  <v-select
                    :options="eventStatuses"
                    v-model="currentEvent.status"
                    :reduce="status => status"
                    label="status"
                    :disabled="modalMode === 'view'"
                    required
                  />
                </div>
                <div class="modal-footer  ">
                  <button v-if="modalMode=='view' && !registed " type="submit" class="btn btn-primary" style="float: right"  @click="register(currentEvent.id)">RSVP</button> 
                  <button v-if="modalMode=='view' && registed " type="submit" class="btn btn-danger" style="float: right"  @click="unregister(currentEvent.id)">Unregister</button> 
                  <button type="submit" class="btn btn-secondary" style="float: right; margin-left: 1em;" @click="closeModal">{{ (modalMode==='view') ? "Close" :  "Cancel" }}</button>
                  <button v-if="modalMode=='create'" type="submit" class="btn btn-primary" style="float: right"  @click="createEvent">Save</button> 
                  <button v-if="modalMode=='edit'" type="submit" class="btn btn-primary" style="float: right"  @click="updateEvent">Update</button> 
                
                </div>
                
              </form>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import { LMap, LTileLayer, LGeoJson } from 'vue2-leaflet';
import { StatsCard } from "@/components/index";
import vSelect  from "vue-select";
import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';
import L from 'leaflet';
import 'leaflet-draw';

import "vue-select/dist/vue-select.css";
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
    StatsCard,
    vSelect
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
            id: event.id,
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
    formattedStartDate: {
        get() {
          if (this.currentEvent.startDate) {
            return this.currentEvent.startDate.slice(0, 16);
          }
          return "";
        },
        set(value) {
          this.currentEvent.startDate = value;
        },
      },
    formattedEndDate: {
      get() {
        if (this.currentEvent.endDate) {
          return this.currentEvent.endDate.slice(0, 16);
        }
        return "";
      },
      set(value) {
        this.currentEvent.endDate = value;
      },
    },
    formattedRegistrationDeadline: {
      get() {
        if (this.currentEvent.registrationDeadline) {
          return this.currentEvent.registrationDeadline.slice(0, 16);
        }
        return "";
      },
      set(value) {
        this.currentEvent.registrationDeadline = value;
      },
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
      eventsTodayCount:0,
      eventsThisWeekCount:0,
      eventsNextWeekCount:0,
      eventsAttended:0,
      eventsRegistered:0,
      totalEventsCount:0,
      totalAttendance:0, 
      avgAttendance:0,
      minAttendance:0,
      maxAttendance:0,
      totalCapacity:0,
      avgCapacity:0,
      minCapacity:0,
      maxCapacity:0,
      registed:false,
      events: [], // To store the fetched events
      venues: [],
      activeTab: "map", 
      categories: [],
      organizers: [],
      usertype: localStorage.getItem("token") ? JSON.parse(localStorage.getItem("token")) : null,
      eventStatuses: ["upcoming", "completed", "cancelled"],
      currentEvent: {
      }, // For storing the new event data from the form
      modalMode: 'create',
      showModal: false, // To control the visibility of the modal
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
        // `<Button @click='register(${feature.properties.id})'>RSVP</button>`,
        { permanent: false, sticky: true }
      );
      layer.on("click", () => {
        if (feature.properties.id) {
          this.viewEvent(feature.properties.id); // Use your existing method
        } else {
          console.warn("Feature does not have an 'id' property.");
        }
      });
    },
    register(id){
      const username = localStorage.getItem('username');
      this.$http.get(`/events/register/${id}/${username}`).then(response => {
        this.closeModal(); // Close the modal after creation
        this.resetNewEvent(); // Reset the form
        window.location.reload();
      });
        
    },
    unregister(id){
      const username = localStorage.getItem('username');
      this.$http.get(`/events/unregister/${id}/${username}`).then(response => {
        this.closeModal(); // Close the modal after creation
        this.resetNewEvent(); // Reset the form
        this.fetchData();
        window.location.reload();
      });
        
    },
    async fetchData(url) {
      try {
        const eventsResponse = await this.$http.get(url);
        this.events = eventsResponse.data.map(event => ({
          ...event,
          id: event.id,
          date: event.startDate,
          name: event.name,
          longitude: event.location.x,
          latitude: event.location.y,
          venue: event.venue,
          organizer: event.organizer
        }));
      
        const venuesResponse = await this.$http.get("venues");
        this.venues = venuesResponse.data;
        const categoriesResponse = await this.$http.get("categories");
        this.categories = categoriesResponse.data;
        const organizersResponse = await this.$http.get("organizations");
        this.organizers = organizersResponse.data;

        const eventsTodayResponse = await this.$http.get("eventsCountToday");
        this.eventsTodayCount = eventsTodayResponse.data;

        const eventsThisWeekResponse = await this.$http.get("eventsCountThisWeek");
        this.eventsThisWeekCount = eventsThisWeekResponse.data;

        const eventsNextWeekResponse = await this.$http.get("eventsCountNextWeek");
        this.eventsNextWeekCount = eventsNextWeekResponse.data;
        let username = localStorage.getItem('username');
        const eventsRegisteredResponse = await this.$http.get(`eventsRegistered/${username}`);
        this.eventsRegistered = eventsRegisteredResponse.data;

        const eventsAttendedResponse = await this.$http.get(`eventsAttended/${username}`);
        this.eventsAttended = eventsAttendedResponse.data;

        const organizerStatsResponse = await this.$http.get("organizerStats");
        this.totalEventsCount = organizerStatsResponse.data.totalEvents;
        this.totalAttendance = organizerStatsResponse.data.totalAttendance;
        this.avgAttendance = organizerStatsResponse.data.averageAttendance;
        this.minAttendance = organizerStatsResponse.data.minAttendance;
        this.maxAttendance = organizerStatsResponse.data.maxAttendance;
        
        this.totalCapacity = organizerStatsResponse.data.totalCapacity;
        this.avgCapacity = organizerStatsResponse.data.averageCapacity;
        this.minCapacity = organizerStatsResponse.data.minCapacity;
        this.maxCapacity = organizerStatsResponse.data.maxCapacity;


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

    createEvent() {
        console.log(this.currentEvent);
        this.$http
          .post("events", this.currentEvent)
          .then((response) => {
            this.closeModal(); // Close the modal after creation
            this.resetNewEvent(); // Reset the form
            this.fetchData();
          })
          .catch((error) => {
            console.error("Error creating event:", error);
          });
      },
  
      updateEvent() {
        // this.currentEvent.location = null;
        // Send the new event to the server
        console.log(this.currentEvent); 
        this.$http
          .put(`events/${this.currentEvent.id}`, this.currentEvent)
          .then((response) => {
            this.closeModal(); // Close the modal after creation
            this.resetNewEvent(); // Reset the form
            this.fetchData();
          })
          .catch((error) => {
            console.error("Error updating event:", error);
          });
      },
      closeModal() {
        this.showModal = false;
      },
      openModal() {
        this.showModal = true;
        this.modalMode = 'create';
      },
  
      viewEvent(eventId) {
        this.currentEvent = this.events.filter(event => event.id == eventId)[0]; 
        this.modalMode = 'view';
        this.showModal = true;
        let username = localStorage.getItem('username');

        const eventRegisteredResponse = this.$http.get(`events/isRegistered/${eventId}/${username}`).then(response => {
          this.registed = response.data;
        });
        
      },
      editEvent(eventId) {
        this.currentEvent = this.events.filter(event => event.id == eventId)[0]; 
        this.modalMode = 'edit';
        this.showModal = true;
      },
      resetNewEvent() {
        // Reset the form fields
        this.currentEvent = {
          name: "",
          location: "",
          startDate: "",
          status: "upcoming",
        };
      },
      // Delete Data 
      async deleteEvent(eventId) {
        const confirmDelete = confirm("Are you sure you want to delete this event?");
        if (confirmDelete) {
          try {
            await this.$http.delete(`events/${eventId}`);
            // Update the Event Table
            this.events = this.events.filter(event => event.id !== eventId); 
          } catch (error) {
            console.error("Error deleting event:", error);
            alert("Delete failed!"); 
          }
        }
      },
  },
  watch: {
  events() {
    this.updateGeoJsonLayer();
  },
  activeTab(newTab) {
    if (newTab === "map") {
      this.$nextTick(() => {
        this.$refs.map.mapObject.invalidateSize();
      });
    }
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
<style>
.nav-tabs .nav-link.active {
  background-color: #007bff;
  color: white;
}
</style>