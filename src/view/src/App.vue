<template>
  <div>
    <div class="nav">
      <div class="navbutton" v-if="isLoggedIn === true">
        <a class="link" :href="'http://localhost:8080/#/user/'">
          <p>User Info</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a class="link" :href="'http://localhost:8080/#/'">
          <p>All Parkinglots</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a class="link" :href="'http://localhost:8080/#/createlot'">
          <p>New Parkinglot</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a class="link" :href="'http://localhost:8080/#/booking/all'">
          <p>Bookings</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a class="link" :href="'http://localhost:8080/#/booking/all/ownedspots'">
          <p>Bookings On Owned Spots</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a class="link" :href="'http://localhost:8080/logout'">
          <p>Logout</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn !== true">
        <a class="link" :href="'http://localhost:8080/#/createuser'">
          <p>New User</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn !== true">
        <a class="link" :href="'http://localhost:8080/login'">
          <p>Login</p>
        </a>
      </div>
    </div>
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
template {
  margin: 0;
  padding: 0;
  border: 0;
}

body {
  margin: 0px;
  padding: 0px;
  background-color: black;
  text-decoration: none;
  color: darkgray;
}

.btn {
  text-align: center;
  background-color: #D3D3D3;
  width: 150px;
  margin: 15px auto;
  padding: 0px;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
  font-size: 14px;
}

.btn:hover {
  background-color: #C0C0C0
}

.link {
  color:inherit;
  text-decoration: none;
}

.flex-container {
  display: flex;
  background-color: dimgrey;
  text-align: center;
  flex-direction: column;
  margin: 2vw auto 2vw auto;
  width: 40vw;
  padding: 2vw;
}

* {
  text-align: center;
}

.flex-container:hover{
  background-color: darkolivegreen;
}

.flex-wrapper {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
}

.nav {
  display: flex;
  flex-wrap: wrap;
  background-color: darkgray;
  justify-content: space-evenly;
  align-items: center;
}

.navbutton {
  text-align: center;
  color: black;
}

.navbutton a:hover {
  color: dodgerblue;
}

.linkbutton {
  background-color: lightsteelblue;
  color: black;
  padding: 5px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}

.linkbutton:hover {
  background-color: cornflowerblue;
}

input[type=submit] {
  border: none;
  background-color: lightsteelblue;
  color: black;
  padding: 5px;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: cornflowerblue;
}

</style>