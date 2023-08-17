<template>
  <div id="register" class="registration-page">
    <form @submit.prevent="register" class="registration-form">
      <h1 class="registration-title">Create Account</h1>
      <div role="alert" v-if="registrationErrors" class="error-message">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username" class="input-label">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus class="input-field" />
      </div>
      <div class="form-input-group">
        <label for="password" class="input-label">Password</label>
        <input type="password" id="password" v-model="user.password" required class="input-field" />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword" class="input-label">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required class="input-field" />
      </div>
      <div class="form-input-group">
        <label class="input-label">User Type</label>
        <div>
          <input type="radio" v-model="user.registerType" value="standard" /> Register as a standard user (free!)
        </div>
        <div>
          <input type="radio" v-model="user.registerType" value="premium" /> Register as a premium user (also free!)
        </div>
      </div>
      <button type="submit" class="registration-button">Create Account</button>
      <p class="login-link"><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
    <div class="additional-info-box">
      <h2 class="additional-info-header">Not sure what kind of account to choose? We can help!</h2>
      <ul>
        <li><strong>Standard User</strong>
          <ul>
            <li>Can create a personal collection with up to 100 comics</li>
            <li>Can view public collections</li>
          </ul>
        </li>
        <li><strong>Premium User</strong>
          <ul>
            <li>Can create a personal collection with an unlimited number of comics</li>
            <li>Can view public collections</li>
          </ul>
        </li>
      </ul>
      <p>Not ready to register? No problem! <router-link :to="{ name: 'collections' }">View our home page</router-link> to browse public collections!</p>
    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
data() {
  return {
    user: {
      username: '',
      password: '',
      confirmPassword: '',
      role: 'user',
      registerStandardUser: false,
      registerPremiumUser: false,
    },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.registration-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url(@/components/PurpleBackground.jpg);
}

.registration-form {
  background: url(@/components/GrayBackground.jpg);
  background-size: cover;
  background-position: center;
  border-radius: 0px 20px 20px 0px;
  padding: 2rem;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  width: 450px;
  height: 50%;
  max-width: 400px;
  border-style: solid;
  border-color: #000000;
}

.registration-title {
  background: #ffffff;
  border-radius: 2px;
  border-style: dashed;
  border-color: #000000;
  color: #9616ff;
  font-size: 32px;
  text-shadow: 2px 2px #000000;
  text-align: center;
  font-weight: bolder;
  margin-bottom: 1.5rem;
  font-family: 'Comic Sans MS', cursive;
}

.error-message {
  color: #e53935;
  text-shadow: 2px 2px #000000;
  font-size: 24px;
  font-weight: bold;
  text-shadow: 2px 2px #000000;
  margin-bottom: 1rem;
}

.success-message {
  color: #43a047;
  font-size: 24px;
  font-weight: bold;
  text-shadow: 2px 2px #000000;
  margin-bottom: 1rem;
}

.form-input-group {
  margin-bottom: 1rem;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
  font-family: "Comic Sans MS", cursive;
}

.input-label {
  margin-right: 0.5rem;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
  font-family: "Comic Sans MS", cursive;
}

.input-field {
  padding: 0.5rem;
  border: 1px solid #000000;
  border-radius: 5px;
  width: 100%;
}

.registration-button {
  background-color: #9616ff;
  color: #ffffff;
  border-style: solid;
  border-radius: 2px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  width: 100%;
  font-family: "Comic Sans MS", cursive;
  font-weight: bolder;
  text-shadow: 2px 2px #000000;
}

.registration-button:hover {
  background-color: #7d13d3;
}

.login-link {
  margin-top: 1rem;
  text-align: center;
  color: #000000;
  background: #ffffff;
  border-radius: 2px;
  border-style: solid;
  font-weight: bold;
}

.additional-info-box {
  background: url(@/components/GrayBackground.jpg);
  background-size: cover;
  background-position: center;
  border-style: solid;
  border-color: #000000;
  border-radius: 20px 0px 0px 20px;
  padding: 1.5rem;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  width: 450px;
  height: 52%;
}

.additional-info-header {
  color: #ffffff;
  text-shadow: 2px 2px #000000;
  text-align: center;
  font-size: 20px;
  margin-bottom: 1rem;
  font-family: 'Comic Sans MS', cursive;
}

.additional-info-box ul {
  list-style: none;
  padding-left: 0;
  margin-top: 1rem;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
  font-family: "Commic Sans MS", cursive;
  font-weight: bold;
}

.additional-info-box ul li {
  margin-bottom: 0.5rem;
}

.additional-info-box ul li ul {
  list-style: disc;
  margin-left: 1.5rem;
}

.additional-info-box p {
  margin-top: 1.5rem;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
  font-family: "Commic Sans MS", cursive;
  font-weight: bold;
}
</style>