<template>
  <div>
    <div class="nav">
      <div class="navbutton" v-if="isLoggedIn === true">
        <a :href="'http://localhost:8080/#/user/'" class="nav-link">
          <p>User Info</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a :href="'http://localhost:8080/#/'" class="nav-link">
          <p>All Parkinglots</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a :href="'http://localhost:8080/#/createlot'" class="nav-link">
          <p>New Parkinglot</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a :href="'http://localhost:8080/#/booking/all'" class="nav-link">
          <p>Bookings</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a :href="'http://localhost:8080/#/booking/all/ownedspots'" class="nav-link">
          <p>Bookings On Owned Spots</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn === true">
        <a :href="'http://localhost:8080/logout'" class="nav-link">
          <p>Logout</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn !== true">
        <a :href="'http://localhost:8080/#/createuser'" class="nav-link">
          <p>New User</p>
        </a>
      </div>

      <div class="navbutton" v-if="isLoggedIn !== true">
        <a :href="'http://localhost:8080/login'" class="nav-link">
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
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
template {
  margin: 0px;
  padding: 0px;
  border: 0px;
}
.nav {
  display: flex;
  background-color: #303030;
  margin: 0px;
  padding: 0px;
  border: 0px;
}
.navbutton {
  text-align: center;
  background-color: #D3D3D3;
  width: 150px;
  margin: 15px auto;
  padding: 0px;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
}
.navbutton a {
  text-decoration: none;
  color: black;
}
.navbutton:hover {
  background-color: #C0C0C0
}
.wrapper {
  text-align: center;
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 0px;
  margin: 0px;
  padding: 0px;
  border: 0px;
  width: 100%;
}
h1 {
  padding-top: 15px;
}
h2 {
  margin-bottom: 50px;
}
.button {
  text-align: center;
  background-color: #D3D3D3;
  width: 200px;
  margin: 15px auto;
  padding: 0px;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
}
.button a {
  text-decoration: none;
  color: black;
}
.button:hover {
  background-color: #C0C0C0
}
.smallbutton {
  text-align: center;
  background-color: #D3D3D3;
  width: 150px;
  margin: 15px auto;
  padding: 0px;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
  font-size: 14px;
}
.smallbutton a {
  text-decoration: none;
  color: black;
}
.smallbutton:hover {
  background-color: #C0C0C0
}
.grid {
  margin: auto;
  display: inline-grid;
  grid-template-columns: auto auto auto;
}
.flex {
  margin: auto;
  display: flex;
  grid-template-columns: auto;
}
.user {
  background-color: #D3D3D3;
  text-align: center;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
  margin: 15px;
  width: 200px;
  height: 230px;
}
.user a {
  text-decoration: none;
  color: black;
}
p {
  line-height: 20px;
  margin: 0px;
  padding: 10px;
}
.user:hover {
  background-color: #C0C0C0
}
.parkinglot {
  background-color: #D3D3D3;
  text-align: center;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
  margin: 15px;
  width: 400px;
  height: 810px;
}
.parkinglotedit {
  background-color: #D3D3D3;
  text-align: center;
  border: 1px solid rgba(0, 0, 0, 0.8);
  margin: auto;
  width: 400px;
  height: auto;
}
.userlist {
  background-color: #D3D3D3;
  text-align: center;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
  margin: 15px;
  width: 200px;
}
.booking {
  background-color: #D3D3D3;
  text-align: center;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
  margin: 15px;
  width: 400px;
  height: 100%;
}
.spot {
  background-color: #D3D3D3;
  text-align: center;
  border: 1px solid rgba(0, 0, 0, 0.8);
  border-radius: 5px;
  margin: 15px;
  width: 200px;
  height: auto;
}
.center {
  display: flex;
  margin: auto;
  text-align: center;
}
.inputs {
  display: none;
}
#foo:checked + .inputs {
  display: initial;
}
h1{font-family: Arial, Helvetica, sans-serif;}
h2{font-family: Arial, Helvetica, sans-serif;}
h3{font-family: Arial, Helvetica, sans-serif;}
p{font-family: Arial, Helvetica, sans-serif;}
a{font-family: Arial, Helvetica, sans-serif;}
select{font-family: Arial, Helvetica, sans-serif;}
body{margin: 0px; padding: 0px;}
</style>