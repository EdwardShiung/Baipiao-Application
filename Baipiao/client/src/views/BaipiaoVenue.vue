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
              <th scope="col">Venue</th>
              <th scope="col">Location</th>
              <th scope="col">Description</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(event, index) in venues" :key="event.id">
              <th scope="row">{{ index + 1 }}</th>
              <td>{{ event.name }}</td>
              <td>{{ event.location }}</td>
              <td>{{ event.description }}</td>
              <td>
                <button @click="deleteVenue(event.id)" class="btn btn-danger btn-sm">
                  Delete
                </button>&nbsp;
                <button  class="btn btn-primary btn-sm"> Edit </button>
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
            <h5 v-if="modalMode!=='create'" class="modal-title">Edit Venue: Lane Stadium</h5>
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
            <form @submit.prevent="createEvent">
              <div class="form-group">
                <label for="eventName">Event Name</label>
                <input
                  type="text"
                  class="form-control"
                  id="eventName"
                  v-model="newVenue.name"
                  required
                />
              </div>
              <div class="form-group">
                <label for="eventLocation">Location</label>
                <input
                  type="text"
                  class="form-control"
                  id="eventLocation"
                  v-model="newVenue.location"
                  required
                />
              </div>
              <div class="form-group">
                <label for="eventDate">Description</label>
                <input
                  type="text"
                  class="form-control"
                  id="eventDate"
                  v-model="newVenue.description"
                  required
                />
              </div>
              
              <div class="modal-footer  ">
                <button type="submit" class="btn btn-secondary" style="float: right; margin-left: 1em;" @click="closeModal">Cancel</button>
              <button type="submit" class="btn btn-primary" style="float: right">Update</button> 
              
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

export default {
  components: {
    StatsCard,
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
      currentVenue: {},
      venues: [], // To store the fetched venues
      newVenue: {
        name: "",
        location: "",
        startDate: "",
        description: "upcoming",
      }, // For storing the new event data from the form
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
    createEvent() {
      // Send the new event to the server
      this.$http
        .post("venues", this.newVenue)
        .then((response) => {
          this.venues.push(response.data); // Add the new event to the list
          this.closeModal(); // Close the modal after creation
          this.resetNewEvent(); // Reset the form
        })
        .catch((error) => {
          console.error("Error creating event:", error);
        });
    },
    closeModal() {
      this.showModal = false;
    },
    openModal() {
      this.showModal = true;
    },
    resetNewEvent() {
      // Reset the form fields
      this.newVenue = {
        name: "",
        location: "",
        startDate: "",
        description: "upcoming",
      };
    },
    formatDate(dateString) {
      // Format the date (handling for datetime-local string format)
      const date = new Date(dateString);
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
      };
      return date.toLocaleDateString(undefined, options);
    },
    // Delete Data 
    async deleteVenue(eventId) {
      const confirmDelete = confirm("Are you sure you want to delete this venue?");
      if (confirmDelete) {
        try {
          await this.$http.delete(`venues/${eventId}`);
          // Update the Event Table
          this.venues = this.venues.filter(event => event.id !== eventId); 
          this.currentVenue = this.venues[0]; 
        } catch (error) {
          console.error("Error deleting event:", error);
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
</style>