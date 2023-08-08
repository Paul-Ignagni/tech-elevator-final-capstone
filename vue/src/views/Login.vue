<template>
  <div id="login" class="login-page">
    <form @submit.prevent="login" class="login-form">
      <h1 class="login-title">Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials" class="error-message">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration" class="success-message">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username" class="input-label">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus class="input-field" />
      </div>
      <div class="form-input-group">
        <label for="password" class="input-label">Password</label>
        <input type="password" id="password" v-model="user.password" required class="input-field" />
      </div>
      <button type="submit" class="login-button">Sign in</button>
      <p class="signup-link"><router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
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
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f2f2f2;
}

.login-form {
  background-color: #ffffff;
  border-radius: 10px;
  padding: 2rem;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.login-title {
  color: #e53935;
  font-size: 24px;
  margin-bottom: 1.5rem;
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
}

.input-field {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
}

.login-button {
  background-color: #2962ff;
  color: #ffffff;
  border: none;
  border-radius: 5px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  width: 100%;
}

.login-button:hover {
  background-color: #0039cb;
}

.signup-link {
  margin-top: 1rem;
  text-align: center;
  color: #2962ff;
}
</style>