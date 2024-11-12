<template>
  <div>
    <!-- Stats cards -->
    <div class="row">
      <div
        class="col-md-6 col-xl-3"
        v-for="stats in statsCards"
        :key="stats.title"
      >
      </div>
    </div>
    <hr />
    <!-- Table List -->
    <div class="row my-5">
      <div class="col">
        <table class="table bg-white rounded shadow-lg table-hover">
          <thead>
            <tr>
              <th scope="col" width="10">#</th>
              <th scope="col">Name</th>
              <th scope="col">Location</th>
              <th scope="col">Description</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(venue, index) in venues" :key="venue.id">
              <th scope="row">{{ index + 1 }}</th>
              <td>{{ venue.name }}</td>
              <td>{{ venue.location }}</td>
              <td>{{ venue.description }}</td>
              <td>
                <button @click="viewVenue(venue.id)" class="btn btn-primary btn-sm">
                  View
                </button> &nbsp;
                <button @click="editVenue(venue.id)" class="btn btn-secondary btn-sm">
                  Edit
                </button>&nbsp;
                <button @click="deleteVenue(venue.id)" class="btn btn-danger btn-sm">
                  Delete
                </button> 
                

              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col text-right">
        <button @click="openModal" class="btn btn-primary">Create</button>
      </div>
    </div>
    <!-- Modal Dialog for Creating Events -->
    <div v-if="showModal" class="modal" tabindex="-1" role="dialog" style="display: block;">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 v-if="modalMode=='create'" class="modal-title">Edit New Venue</h5>
            <h5 v-if="modalMode=='edit'" class="modal-title">Edit Venue: {{ currentVenue.name }}</h5>
            <h5 v-if="modalMode=='view'" class="modal-title">Venue: {{ currentVenue.name }}</h5>
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
            <!-- Form for creating venue -->
            <form >
              <div class="form-group">
                <label for="venueName">Venue Name</label>
                <AutoComplete
                  type="text"
                  class="form-control"
                  id="venueName"
                  v-model="currentVenue.name"
                  :disabled="modalMode === 'view'"
                  required
                  :suggestions="locations" @complete="searchVenue" 
                  :blur="setVenueLocation"
                  :option-unselected="unsetVenueLocation"
                />
                
              </div>
              <div class="form-group">
                <label>Location</label>
                <div style="display: flex; gap: 15px;">
                  <div>
                    <label for="venueLocationX">X: </label>
                    <input
                      type="text"
                      class="form-control"
                      id="venueLocationX"
                      v-model="currentVenue.location.x"
                      :disabled="modalMode === 'view'"
                      required
                      style="width: 225px;"
                    />
                  </div>

                  <div>
                    <label for="venueLocationY">Y: </label>
                    <input
                      type="text"
                      class="form-control"
                      id="venueLocationY"
                      v-model="currentVenue.location.y"
                      :disabled="modalMode === 'view'"
                      required
                      style="width: 225px;"
                    />
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label for="description">Description</label>
                <input
                  type="text"
                  class="form-control"
                  id="description"
                  v-model="currentVenue.description"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>
              
              <div class="modal-footer  ">
                <button type="submit" class="btn btn-secondary" style="float: right; margin-left: 1em;" @click="closeModal">{{ (modalMode==='view') ? "Close" :  "Cancel" }}</button>
                <button v-if="modalMode=='create'" type="submit" class="btn btn-primary" style="float: right"  @click="createVenue">Save</button> 
                <button v-if="modalMode=='edit'" type="submit" class="btn btn-primary" style="float: right"  @click="updateVenue">Update</button> 
              </div>
              
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { StatsCard } from "@/components/index";
import { ref } from "vue";
import AutoComplete from 'primevue/autocomplete';
const Nominatim = require('nominatim-geocoder');


export default {
  components: {
    StatsCard,AutoComplete
  },
  data() {
    return {
      statsCards: [
        // Example stats
        {
          type: "warning",
          icon: "ti-pencil",
          title: "Today",
          value: "10",
          footerText: "Today",
          footerIcon: "ti-reload",
        },
      ],
      locations: [],
      currentVenue: {},
      venues: [], // To store the fetched venues
      currentVenue: {
        name: "",
        location: {
          x: "",
          y: ""
        },
        description: "",
      },
      modalMode: 'create',
      showModal: false, // To control the visibility of the modal
    };
  },
  methods: {
    async fetchData() {
      try {
        const response = await this.$http.get("venues");
        this.venues = response.data;
      } catch (error) {
        console.error("Error fetching venues:", error);
      }
    },

    searchVenue() {
      if (this.currentVenue.name.length < 5) {
        return;
      }
      const geocoder = new Nominatim();
      geocoder.search({ q: `${this.currentVenue.name}` })
        .then((response) => {
          this.locations = response.map(item => item.display_name); // Update the suggestions array
          if (response.length > 0) {
            this.currentVenue.location.x = response[0].lon;
            this.currentVenue.location.y = response[0].lat;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    setVenueLocation() {
      const geocoder = new Nominatim();
      geocoder.search({ q: `${this.currentVenue.name}` })
        .then((response) => {
          if (response.length > 0) {
            this.currentVenue.location.x = response[0].lon;
            this.currentVenue.location.y = response[0].lat;
            console.log(this.currentVenue);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    unsetVenueLocation() {
      this.currentVenue.location.x =null;
      this.currentVenue.location.y = null;
      console.log(this.currentVenue);
    },
    createVenue() {
      this.$http
        .post("venues", this.currentVenue)
        .then((response) => {
          this.closeModal(); // Close the modal after creation
          this.resetNewVenue(); // Reset the form
          this.fetchData();
        })
        .catch((error) => {
          console.error("Error creating venue:", error);
        });
    },
    updateVenue() {
      console.log(this.currentVenue);
      this.$http
        .put(`venues/${this.currentVenue.id}`, this.currentVenue)
        .then((response) => {
          this.closeModal(); // Close the modal after creation
          this.resetNewVenue(); // Reset the form
          this.fetchData();
        })
        .catch((error) => {
          console.error("Error updating venue:", error);
        });
    },
    closeModal() {
      this.showModal = false;
    },
    openModal() {
      if(this.modalMode=='create'){
        this.resetNewVenue();
      }
      this.showModal = true;
    },
    viewVenue(venueId) {
      this.currentVenue = this.venues.filter(venue => venue.id == venueId)[0]; 
      this.modalMode = 'view';
      this.showModal = true;
    },
    editVenue(venueId) {
      this.currentVenue = this.venues.filter(venue => venue.id == venueId)[0]; 
      this.modalMode = 'edit';
      this.showModal = true;
    },
    resetNewVenue() {
      // Reset the form fields
      this.currentVenue = {
          name: "",
          location: {
            x: "",
            y: ""
          },
          description: "",
        }
    },
    // Delete Data 
    async deleteVenue(venueId) {
      const confirmDelete = confirm("Are you sure you want to delete this venue?");
      if (confirmDelete) {
        try {
          await this.$http.delete(`venues/${venueId}`);
          // Update the Event Table
          this.venues = this.venues.filter(venue => venue.id !== venueId); 
          this.fetchData();
          this.resetNewVenue()
        } catch (error) {
          console.error("Error deleting venue:", error);
          alert("Delete failed!"); 
        }
      }
    },
  },
  created() {
    this.fetchData(); // Fetch venues when the component is created
  },
};
</script>

<style>
.modal {
  display: block; /* Overriding Bootstrap hidden modal */
  background-color: rgba(0, 0, 0, 0.5); /* Add a background overlay */
}

.col {
  max-height: 400px; 
  overflow-y: auto;  
  border: 1px solid #dee2e6; 
  border-radius: 0.25rem; 
}
#venueName {
border: none;
width: 100%;
height: 100%;
background: transparent
}
.p-autocomplete-panel {
    max-height: 200px; /* Controls dropdown height */
    overflow-y: auto;
    border: 1px solid #dee2e6;
    border-radius: 0.25rem;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
    background-color: white;
}
.p-autocomplete-item {
    padding: 8px 12px;
    cursor: pointer;
}

.p-autocomplete-item:hover {
    background-color: #f0f0f0; /* Light gray background on hover */
}
</style>