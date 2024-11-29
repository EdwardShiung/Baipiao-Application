<template>
  <div class="wrapper">
    <side-bar>
      <template slot="links">
        <!-- <sidebar-link to="/" name="Baipiao Events" icon="ti-calendar" /> -->
        <sidebar-link
          v-for="item in filteredMenuItems"
          :key="item.name"
          :to="item.path"
          :name="item.name"
          :icon="item.icon"
        />
      </template>
      <!-- Mobile Side Bar -->
      <mobile-menu>
        <li class="nav-item">
          <a class="nav-link" @click.prevent="logout">
            <i class="ti-settings"></i>
            <p>Logout</p>
          </a>
        </li>
        <li class="divider"></li>
      </mobile-menu>
    </side-bar>
    <div class="main-panel">
      <top-navbar></top-navbar>
      <dashboard-content @click.native="toggleSidebar"> </dashboard-content>
      <content-footer></content-footer>
    </div>
  </div>
</template>
  <style lang="scss"></style>
  
<script>
import TopNavbar from "../components/TopNavBar/TopNavbar.vue";
import DashboardContent from "../views/Content.vue";
import MobileMenu from "./MobileMenu";
import ContentFooter from "./ContentFooter.vue";
export default {
  components: {
    TopNavbar,
    DashboardContent,
    MobileMenu,
    ContentFooter,
  },
  data() {
    return {
      menuItems: [
        {
          name: "Events",
          path: "/baipiaoEvents",
          icon: "ti-map",
          requiredUserTypes: ['admin', 'organization', 'user'], 
        },
        // {
        //   name: "Baipiao list",
        //   path: "/baipiaolist",
        //   icon: "ti-map",
        //   requiredUserTypes: ['admin'], 
        // },
        {
          name: "Users",
          path: "/baipiaouser",
          icon: "ti-user",
          requiredUserTypes: ['admin'],
        },
        {
          name: "Organizations",
          path: "/baipiaoOrganization",
          icon: "ti-briefcase",
          requiredUserTypes: ['admin'],
        },
        {
          name: "Venues",
          path: "/baipiaoVenue",
          icon: "ti-location-pin",
          requiredUserTypes: ['admin', 'organization'],
        },
        {
          name: "Event Categories",
          path: "/baipiaoCategory",
          icon: "ti-tag",
          requiredUserTypes: ['admin'],
        },
        {
          name: "Admin Statistics",
          path: "/baipiaoStat",
          icon: "ti-dashboard",
          requiredUserTypes: ['admin'],
        },
        {
          name: "Org Statistics",
          path: "/baipiaoStat",
          icon: "ti-dashboard",
          requiredUserTypes: ['organization'],
        },
      ],
    };
  },
  computed: {
    filteredMenuItems() {
      const userType = this.getUserType(); 
      return this.menuItems.filter(item => {
        if (!item.requiredUserTypes) return true;
        return item.requiredUserTypes.includes(userType);
      });
    },
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    },
    getUserType() {
      const userType = localStorage.getItem("token");
      return userType ? JSON.parse(userType) : null;
    },
    logout() {
      // Clear localStorage
      localStorage.removeItem("token");
      // Redirect to Login
      this.$router.push('/login');
    },
  },
};
</script>