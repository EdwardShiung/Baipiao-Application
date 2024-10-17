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
              <th scope="col">Username</th>
              <th scope="col">Display Name</th>
              <th scope="col">Email</th>
              <th scope="col">Phone No.</th>
              <th scope="col">Role</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(user, index) in users" :key="user.id">
              <th scope="row">{{ index + 1 }}</th>
              <td>{{ user.userName }}</td>
              <td>{{ user.displayName}}</td>
              <td>{{ user.email}}</td>
              <td>{{ user.phoneNumber}}</td>
              <td>{{ user.userType}}</td>
              <td>
                <button @click="viewUser(user.id)" class="btn btn-primary btn-sm">
                  View
                </button> &nbsp;
                <button @click="editUser(user.id)" class="btn btn-secondary btn-sm">
                  Edit
                </button>&nbsp;
                <button @click="deleteUser(user.id)" class="btn btn-danger btn-sm">
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
    <div v-if="showModal" class="modal" tabindex="-1" role="dialog" style="display: block;">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 v-if="modalMode=='create'" class="modal-title">Create New User</h5>
            <h5 v-if="modalMode=='edit'" class="modal-title">Edit User: {{ currentUser.userName }}</h5>
            <h5 v-if="modalMode=='view'" class="modal-title">User: {{ currentUser.userName }}</h5>
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
            <!-- Form for creating user -->
            <form >
              <div class="form-group">
                <label for="userName">User Name</label>
                <input
                  type="text"
                  class="form-control"
                  id="userName"
                  v-model="currentUser.userName"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>
              <div class="form-group">
                <label for="password">Password</label>
                <input
                  type="password"
                  class="form-control"
                  id="password"
                  v-model="currentUser.password"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>
              <div class="form-group">
                <label for="displayName">Display Name</label>
                <input
                  type="text"
                  class="form-control"
                  id="displayName"
                  v-model="currentUser.displayName"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>

              <div class="form-group">
                <label for="email">Email</label>
                <input
                  type="email"
                  class="form-control"
                  id="email"
                  v-model="currentUser.email"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>

              <div class="form-group">
                <label for="phoneNumber">Phone #</label>
                <input
                  type="phonenumber"
                  class="form-control"
                  id="phoneNumber"
                  v-model="currentUser.phoneNumber"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>
              <div class="form-group">
                <label for="role">Role</label>
                <v-select
                  :options="userTypes"
                  v-model="currentUser.userType"
                  :reduce="role => role"
                  label="role"
                  :disabled="modalMode === 'view'"
                  required
                />
              </div>
              <div class="modal-footer  ">
                <button type="submit" class="btn btn-secondary" style="float: right; margin-left: 1em;" @click="closeModal">{{ (modalMode==='view') ? "Close" :  "Cancel" }}</button>
                <button v-if="modalMode=='create'" type="submit" class="btn btn-primary" style="float: right"  @click="createUser">Save</button> 
                <button v-if="modalMode=='edit'" type="submit" class="btn btn-primary" style="float: right"  @click="updateUser">Update</button> 
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
      userTypes: ["admin", "organization", "user"],

      currentUser: {},
      users: [], // To store the fetched users
      currentUser: {},
      modalMode: 'create',
      showModal: false, // To control the visibility of the modal
    };
  },
  methods: {
    async fetchData() {
      try {
        const response = await this.$http.get("users");
        this.users = response.data;
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    },
    createUser() {
      console.log(this.currentUser);
      this.$http
        .post("users", this.currentUser)
        .then((response) => {
          this.closeModal(); // Close the modal after creation
          this.resetNewUser(); // Reset the form
          this.fetchData();
        })
        .catch((error) => {
          console.error("Error creating user:", error);
        });
    },
    updateUser() {
      this.$http
        .put(`users/${this.currentUser.id}`, this.currentUser)
        .then((response) => {
          this.closeModal(); // Close the modal after creation
          this.resetNewUser(); // Reset the form
          this.fetchData();
        })
        .catch((error) => {
          console.error("Error updating user:", error);
        });
    },
    closeModal() {
      this.showModal = false;
    },
    openModal() {
      if(this.modalMode=='create'){
        this.resetNewUser();
      }
      this.showModal = true;
    },
    viewUser(userId) {
      this.currentUser = this.users.filter(user => user.id == userId)[0]; 
      this.modalMode = 'view';
      this.showModal = true;
    },
    editUser(userId) {
      this.currentUser = this.users.filter(user => user.id == userId)[0]; 
      this.modalMode = 'edit';
      this.showModal = true;
    },
    resetNewUser() {
      // Reset the form fields
      this.currentUser = {
          name: "",
          description: "",
        }
    },
    // Delete Data 
    async deleteUser(userId) {
      const confirmDelete = confirm("Are you sure you want to delete this user?");
      if (confirmDelete) {
        try {
          await this.$http.delete(`users/${userId}`);
          // Update the Event Table
          this.users = this.users.filter(user => user.id !== userId); 
          this.fetchData();
          this.resetNewUser()
        } catch (error) {
          console.error("Error deleting user:", error);
          alert("Delete failed!"); 
        }
      }
    },
  },
  created() {
    this.fetchData(); // Fetch users when the component is created
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