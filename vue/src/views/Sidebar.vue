!<template>
  <div
      class="sidebar"
      :class="{ open: isSidebarOpen }"
      @mouseenter="openSidebar"
      @mouseleave="closeSidebar"
    >
      <nav class="sidebar-nav">
        <h2>Navigation Menu</h2>
        <ul>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/login">Login</router-link></li>
          <li><router-link to="/collections">Collections</router-link></li>
          <li><router-link to="/profile">Profile</router-link></li>
        </ul>
      </nav>
    </div>
</template>

<script>
export default {
name: "Sidebar",
data() {
    return{
        isSidebarOpen: false,
    };
},
methods: {
openSidebar() {
      this.isSidebarOpen = true;
    },
closeSidebar() {
      this.isSidebarOpen = false;
    },
},
};
</script>

<style>

</style>
