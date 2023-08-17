<template>
  <div id="login" class="login-page">
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
          <li><router-link to="/logout">Logout</router-link></li>
          <li><router-link to="/collections">Collections</router-link></li>
          <li><router-link to="/profile">Profile</router-link></li>
        </ul>
      </nav>
    </div>
    <form @submit.prevent="login" class="login-form">
      <h1 class="login-title">Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials" class="error-message">
        Invalid username and password!
      </div>
      <div
        role="alert"
        v-if="this.$route.query.registration"
        class="success-message"
      >
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username" class="input-label">Username</label>
        <input
          type="text"
          id="username"
          v-model="user.username"
          required
          autofocus
          class="input-field"
        />
      </div>
      <div class="form-input-group">
        <label for="password" class="input-label">Password</label>
        <input
          type="password"
          id="password"
          v-model="user.password"
          required
          class="input-field"
        />
      </div>
      <button type="submit" class="login-button">Start Collecting!</button>
      <p class="signup-link">
        <router-link :to="{ name: 'register' }"
          >Need an account? Sign up.</router-link
        >
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
      isSidebarOpen: false,
    };
  },

  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
    openSidebar() {
      this.isSidebarOpen = true;
    },
    closeSidebar() {
      this.isSidebarOpen = false;
    },
  },
};
</script>

<style scoped>

.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url(@/components/PurpleBackground.jpg);
}

.login-form {
  background-color: #ffffff;
  border-radius: 10px;
  border-style: solid;
  border-color: black;
  padding: 2rem;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  background: url(@/components/GrayBackground.jpg);
  background-size: cover;
  background-position: center;
}

.login-title {
  color: #9616ff;
  background-color: #ffffff;
  text-align: center;
  text-shadow: 2px 2px #000000;
  font-size: 24px;
  border-style: solid;
  border-radius: 5px;
  border-color: #000000;
  padding: 0.5rem;
  margin-bottom: 1.5rem;
  font-family: "Comic Sans MS", cursive;
  width: 95%;
}

.error-message {
  color: #e53935;
  margin-bottom: 1rem;
}

.success-message {
  color: #43a047;
  margin-bottom: 1rem;
}

.form-input-group {
  margin-bottom: 1rem;
}

.input-label {
  margin-right: 0.5rem;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
  font-weight: bolder;
  font-family: "Comic Sans MS", cursive;
}

.input-field {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  border-style: solid;
  border-color: #000000;
  width: 95%;
}

.login-button {
  background-color: #9616ff;
  color: #ffffff;
  border: none;
  border-radius: 5px;
  border-style: solid;
  border-color: #000000;
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
  width: 100%;
  color: #ffffff;
  font-size: 30px;
  text-shadow: 2px 2px #000000;
  font-weight: bolder;
  font-family: "Comic Sans MS", cursive;
}

.login-button:hover {
  background-color: #7411c5;
}

.signup-link {
  margin-top: 1rem;
  text-align: center;
  color: #2962ff;
  background: #ffffff;
  border-style: solid;
  border-color: #000000;
  border-radius: 2px;
}

.sidebar {
  position: fixed;
  background: url(@/components/GrayBackground.jpg);
  background-size: cover;
  background-position: center;
  border-radius: 0px 25px 25px 0px;
  border-style: solid;
  border-color: #000000;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  top: 20%;
  left: 0;
  left: -250px;
  width: 250px;
  height: 250px;
  transition: left 0.3s ease-in-out;
  z-index: 1000; /* Ensure the sidebar is above other content */
}

.sidebar.open {
  left: 0; /* Slide the sidebar into view */
}

.sidebar h2 {
  text-align: center;
  padding: 0px;
  margin: 10px;
  color: #9616ff;
  text-shadow: 2px 2px #000000;
  background: #ffffff;
  font-family: "Comic Sans MS", cursive;
  font-weight: bold;
  border-style: outset;
  border-radius: 5px;
}

.sidebar-nav {
  padding: 0px;
}

.sidebar-nav ul {
  list-style: none;
  padding: 20;
  margin: 20;
}

.sidebar-nav li {
  margin-bottom: 10px;
}

.sidebar-nav a {
  font-family: "Comic Sans MS", cursive;
  color: #ffffff;
  text-decoration: none;
  text-shadow: 2px 2px #000000;
  font-weight: 300;
  font-size: 24px;
  transition: font-size 0.3s, margin-bottom 0.3s;
}

.sidebar-nav a:hover {
  font-weight: 500;
  font-size: 36px;
  margin-bottom: 20px;
}
</style>