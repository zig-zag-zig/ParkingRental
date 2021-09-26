<template>
  <div>
    <h1>All Users</h1>
    <span v-for="user in this.users" v-bind:key="user.id">
      <p>Username: {{user.username}}</p>
      <p>Firstname: {{user.firstname}}</p>
      <p>Lastname: {{user.surname}}</p>
      <p>Location: {{`${user.location.address} ${user.location.number}, ${user.location.zipcode} ${user.location.area}, ${user.location.city}`}}</p>
      <p>Role: {{user.role}}</p>

      <button @click="this.$router.push(`/user/${user.username}`)" class="nav-link">User Info</button>
      <button @click="this.deleteUser(user.username);">Delete</button>
      <br>
      <br>
    </span>
  </div>
</template>

<script>
const { deleteRequest } = require("@/restApi/ApiCaller");
export default {
  name: "GetAll",
  data() {
    return {
      users: [],
    }
  },
  created() {
    this.redirectIfNotAdmin();
    this.getAll();
  },
  methods: {
    deleteUser(username) {
      deleteRequest(`api/user/delete/${username}`)
    },
    getAll() {
      fetch('http://localhost:8080/api/user/all', {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => this.users = data)
          .catch(error => alert('Error:', error));
    },
    redirectIfNotAdmin() {
      fetch('http://localhost:8080/api/auth/admin', {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => {
            this.isAdmin = data;
            if (this.isAdmin === false)
              location.replace("http://localhost:8080/#/user");
          })
          .catch(error => alert(error));
    }
  }
}
</script>