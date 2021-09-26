<template>
  <div>
    <nav class="navbar navbar-toggleable-md navbar-light bg-faded">
      <div class="collapse navbar-collapse" id="navbarNav">
        <span v-if="isLoggedIn === true">
          <ul class="navbar-nav">
            <li class="nav-item"><router-link to="/user/" class="nav-link">User Info</router-link></li>
            <li class="nav-item"><router-link to="/" class="nav-link">All Parkinglots</router-link></li>
            <li class="nav-item"><router-link to="/createlot" class="nav-link">New Parkinglot</router-link></li>
            <li class="nav-item"><router-link to="/booking/all" class="nav-link">Bookings</router-link></li>
            <li class="nav-item"><router-link to="/booking/all/ownedspots" class="nav-link">Bookings On Onwed Spots</router-link></li>
            <li class="nav-item"><a :href="'http://localhost:8080/logout'" class="nav-link">Logout</a></li>
          </ul>
        </span>
        <span v-else>
          <ul class="navbar-nav">
            <li class="nav-item"><router-link to="/createuser/" class="nav-link">New User</router-link></li>
            <li class="nav-item"><a :href="'http://localhost:8080/login'" class="nav-link">Login</a></li>
          </ul>
        </span>
      </div>
    </nav>
    <router-view class="view"></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  data: () => {
    return {
      isLoggedIn: ''
    }
  }, methods: {
    isAuthenticated() {
      fetch('http://localhost:8080/api/auth/loggedin', {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => {
            this.isLoggedIn = data;
          })
          .catch(error => {
            if (!window.location.toString().includes("createuser"))
              location.replace("http://localhost:8080/login")
          });
    }
  },
  created() {
    this.isAuthenticated();
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
