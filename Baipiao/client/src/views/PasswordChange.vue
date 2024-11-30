<template>
    <div>
      <h1>Update Password</h1>
  
      <!-- Display user information -->
      <div v-if="userData">
        <p><strong>Email:</strong> {{ userData.email }}</p>
        <p><strong>Current Password:</strong> {{ userData.password }}</p>
      </div>
  
      <!-- Loading indicator -->
      <div v-else-if="loading">
        <p>Loading...</p>
      </div>
  
      <!-- Error message -->
      <div v-else-if="error">
        <p style="color: red">{{ error }}</p>
      </div>
  
      <!-- Password update form -->
      <form v-if="userData" @submit.prevent="updatePassword">
        <div>
          <label for="newPassword">New Password:</label>
          <input
            type="password"
            id="newPassword"
            v-model="newPassword"
            required
            placeholder="Enter new password"
          />
        </div>
        <div>
          <label for="confirmPassword">Confirm New Password:</label>
          <input
            type="password"
            id="confirmPassword"
            v-model="confirmPassword"
            required
            placeholder="Re-enter new password"
          />
        </div>
        <button type="submit">Update Password</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        userId: 1, // The ID of the user to fetch
        userData: null, // To store the user's data fetched from the backend
        newPassword: "", // The new password entered by the user
        confirmPassword: "", // The confirmation of the new password
        loading: true, // Indicates whether the page is loading data
        error: null, // To store any error message
      };
    },
    methods: {
      /**
       * Fetch the user's information from the backend using their ID.
       */
      async fetchUserData() {
        try {
          
          const response = await this.$http.get("http://localhost:8080/users/retrieve", {
            params: { id: this.userId },
          });
          this.userData = response.data; // Store the fetched user data
        } catch (err) {
          this.error = err.response ? err.response.data : "Failed to fetch user data";
        } finally {
          this.loading = false; // Loading is complete
        }
      },
      /**
       * Update the user's password by sending the new password to the backend.
       */
      async updatePassword() {
        if (this.newPassword !== this.confirmPassword) {
          this.error = "Passwords do not match!"; // Error if the passwords are not the same
          return;
        }
  
        try {
          const response = await this.$http.put(`http://localhost:8080/users/update-password`, {
            id: this.userId,
            newPassword: this.newPassword,
          });
  
          if (response.status === 200) {
            alert("Password updated successfully!"); // Show success alert
          }
        } catch (err) {
          this.error = err.response ? err.response.data : "Failed to update password";
        }
      },
    },
    /**
     * Lifecycle hook: Fetch user data when the component is mounted.
     */
    mounted() {
      this.fetchUserData();
    },
    created(){
      this.userId = localStorage.getItem("userData");
    }
  };
  </script>
  
  <style scoped>
  /* Basic styling for the form */
  form {
    margin-top: 20px;
  }
  
  form div {
    margin-bottom: 10px;
  }
  
  button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 15px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  </style>
  