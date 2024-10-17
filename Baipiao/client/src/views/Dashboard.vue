<template>
  <div>
    <!-- Stats cards -->
    <div class="row">
      <div
        class="col-md-6 col-xl-3"
        v-for="stats in statsCards"
        :key="stats.title"
      >
        <stats-card>
          <div
            class="icon-big text-center"
            :class="`icon-${stats.type}`"
            slot="header"
          >
            <i :class="stats.icon"></i>
          </div>
          <div class="numbers" slot="content">
            <p>{{ stats.title }}</p>
            {{ events.length }}
          </div>
          <div class="stats" slot="footer">
            <i :class="stats.footerIcon"></i> {{ stats.footerText }}
          </div>
        </stats-card>
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
                <button @click="deleteEvent(event.id)" class="btn btn-danger btn-sm">
                  Delete
                </button> &nbsp;
                <button @click="editEvent(event.id)" class="btn btn-primary btn-sm">
                  Edit
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
            <h5 v-if="modalMode=='create'" class="modal-title">Create New Event</h5>
            <h5 v-if="modalMode!=='create'" class="modal-title">Edit Event: {{currentEvent.name}}</h5>
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
                  required
                />
              </div>

              <div class="form-group">
                <label for="eventDetails">Event Details</label>
                <textarea
                  class="form-control"
                  id="eventDetails"
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
                  required
                />
              </div>
              <div class="modal-footer  ">
                <button type="submit" class="btn btn-secondary" style="float: right; margin-left: 1em;" @click="closeModal">Cancel</button>
                <button v-if="modalMode=='create'" type="submit" class="btn btn-primary" style="float: right"  @click="createEvent">Save</button> 
                <button v-if="modalMode!=='create'" type="submit" class="btn btn-primary" style="float: right"  @click="updateEvent">Update</button> 
              
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
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";

export default {
  components: {
    StatsCard,vSelect
  },
  computed: {
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
      events: [], // To store the fetched events
      venues: [],
      categories: [],
      organizers: [],
      eventStatuses: ["upcoming", "completed", "cancelled"],
      currentEvent: {
      }, // For storing the new event data from the form
      modalMode: 'create',
      showModal: false, // To control the visibility of the modal
    };
  },
  methods: {
    async fetchData() {
      try {
        const eventsResponse = await this.$http.get("events");
        this.events = eventsResponse.data;
        const venuesResponse = await this.$http.get("venues");
        this.venues = venuesResponse.data;
        const categoriesResponse = await this.$http.get("categories");
        this.categories = categoriesResponse.data;
        const organizersResponse = await this.$http.get("organizations");
        this.organizers = organizersResponse.data;
      } catch (error) {
        console.error("Error fetching events:", error);
      }
    },
    createEvent() {
      console.log(this.currentEvent);
      this.$http
        .post("events", this.currentEvent)
        .then((response) => {
          this.events.push(response.data); // Add the new event to the list
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
  created() {
    this.fetchData(); // Fetch events when the component is created
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
.toggle-container {
  display: flex;
  align-items: center;
}

.toggle-checkbox {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}




.switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 25px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 25px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 21px;
  width: 21px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #4caf50;
}

input:checked + .slider:before {
  transform: translateX(25px);
}
</style>