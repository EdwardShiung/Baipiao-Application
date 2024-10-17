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
              <th scope="col">Phone No.</th>
              <th scope="col">Description</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(organization, index) in organizations" :key="organization.id">
              <th scope="row">{{ index + 1 }}</th>
              <td>{{ organization.name }}</td>
              <td>{{ organization.phoneno}}</td>
              <td>{{ organization.description }}</td>
              <td>
                <button @click="viewOrganization(organization.id)" class="btn btn-primary btn-sm">
                  View
                </button> &nbsp;
                <button @click="editOrganization(organization.id)" class="btn btn-secondary btn-sm">
                  Edit
                </button>&nbsp;
                <button @click="deleteOrganization(organization.id)" class="btn btn-danger btn-sm">
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
            <h5 v-if="modalMode=='create'" class="modal-title">Edit New Organization</h5>
            <h5 v-if="modalMode=='edit'" class="modal-title">Edit Organization: {{ currentOrganization.name }}</h5>
            <h5 v-if="modalMode=='view'" class="modal-title">Organization: {{ currentOrganization.name }}</h5>
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
            <!-- Form for creating organization -->
            <form >
              <div class="form-group">
                <label for="organizationName">Organization Name</label>
                <input
                  type="text"
                  class="form-control"
                  id="organizationName"
                  v-model="currentOrganization.name"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>
              
              <div class="form-group">
                <label for="description">Description</label>
                <input
                  type="text"
                  class="form-control"
                  id="description"
                  v-model="currentOrganization.description"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>

              <div class="form-group">
                <label for="organizationEmail">Email</label>
                <input
                  type="email"
                  class="form-control"
                  id="organizationEmail"
                  v-model="currentOrganization.email"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>

              <div class="form-group">
                <label for="organizationPhone">Phone #</label>
                <input
                  type="phonenumber"
                  class="form-control"
                  id="organizationPhone"
                  v-model="currentOrganization.phoneno"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>
              
              <div class="modal-footer  ">
                <button type="submit" class="btn btn-secondary" style="float: right; margin-left: 1em;" @click="closeModal">{{ (modalMode==='view') ? "Close" :  "Cancel" }}</button>
                <button v-if="modalMode=='create'" type="submit" class="btn btn-primary" style="float: right"  @click="createOrganization">Save</button> 
                <button v-if="modalMode=='edit'" type="submit" class="btn btn-primary" style="float: right"  @click="updateOrganization">Update</button> 
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
      currentOrganization: {},
      organizations: [], // To store the fetched organizations
      currentOrganization: {
        name: "",
        description: "",
        email: "",
        phoneno: "",
      },
      modalMode: 'create',
      showModal: false, // To control the visibility of the modal
    };
  },
  methods: {
    async fetchData() {
      try {
        const response = await this.$http.get("organizations");
        this.organizations = response.data;
      } catch (error) {
        console.error("Error fetching organizations:", error);
      }
    },
    createOrganization() {
      console.log(this.currentOrganization);

      this.$http
        .post("organizations", this.currentOrganization)
        .then((response) => {
          this.closeModal(); // Close the modal after creation
          this.resetNewOrganization(); // Reset the form
          this.fetchData();
        })
        .catch((error) => {
          console.error("Error creating organization:", error);
        });
    },
    updateOrganization() {
      this.$http
        .put(`organizations/${this.currentOrganization.id}`, this.currentOrganization)
        .then((response) => {
          this.closeModal(); // Close the modal after creation
          this.resetNewOrganization(); // Reset the form
          this.fetchData();
        })
        .catch((error) => {
          console.error("Error updating organization:", error);
        });
    },
    closeModal() {
      this.showModal = false;
    },
    openModal() {
      if(this.modalMode=='create'){
        this.resetNewOrganization();
      }
      this.showModal = true;
    },
    viewOrganization(organizationId) {
      this.currentOrganization = this.organizations.filter(organization => organization.id == organizationId)[0]; 
      this.modalMode = 'view';
      this.showModal = true;
    },
    editOrganization(organizationId) {
      this.currentOrganization = this.organizations.filter(organization => organization.id == organizationId)[0]; 
      this.modalMode = 'edit';
      this.showModal = true;
    },
    resetNewOrganization() {
      // Reset the form fields
      this.currentOrganization = {
          name: "",
          description: "",
        }
    },
    // Delete Data 
    async deleteOrganization(organizationId) {
      const confirmDelete = confirm("Are you sure you want to delete this organization?");
      if (confirmDelete) {
        try {
          await this.$http.delete(`organizations/${organizationId}`);
          // Update the Event Table
          this.organizations = this.organizations.filter(organization => organization.id !== organizationId); 
          this.fetchData();
          this.resetNewOrganization()
        } catch (error) {
          console.error("Error deleting organization:", error);
          alert("Delete failed!"); 
        }
      }
    },
  },
  created() {
    this.fetchData(); // Fetch organizations when the component is created
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