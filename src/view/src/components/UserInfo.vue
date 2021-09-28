<template>
  <div>
    <h1>User Info</h1>
    <span v-if="isAdmin === true">
      <p>
      <button class="btn" @click="this.$router.push(`/user/all`)">All Users</button>
      </p>
    </span>
    <p>
      <label for="username">Username: </label><br>
      <input v-model="user.username" type="text" name="username" id="username" required="required">
    </p>

    <p>
      <label for="firstname">Firstname: </label><br>
      <input v-model="user.firstname" type="text" name="firstname" id="firstname" required="required">
    </p>

    <p>
      <label for="lastname">Lastname: </label><br>
      <input v-model="user.surname" type="text" name="lastname" id="lastname" required="required">
    </p>

    <p>
      <label for="addressStreet">Address Street: </label><br>
      <input v-model="user.location.address" type="text" name="addressStreet" id="addressStreet" required="required">
    </p>

    <p>
      <label for="addressNumber">Address Number: </label><br>
      <input v-model="user.location.number" type="number" name="addressNumber" min="0" id="addressNumber" required="required"
             oninput="validity.valid||(value='');">
    </p>

    <p>
      <label for="addressZip">Address Zip: </label><br>
      <input v-model="user.location.zipcode" type="number" name="addressZip" min="0" id="addressZip" required="required"
             oninput="validity.valid||(value='');">
    </p>

    <button class="btn" @click="updateUser">Update</button>
    <br>
    <button class="btn" @click="deleteUser">Delete</button>
  </div>

</template>

<script>
const { deleteRequest, putRequest } = require("@/restApi/ApiCaller");
export default {
  name: "UserInfo",
  data: () => ({
    user: null,
    isAdmin: '',
  }),
  methods: {
    updateUser() {
      let userInfo = [this.user.firstname, this.user.surname, this.user.location.city, this.user.location.address, this.user.location.number, this.user.location.zipcode, this.user.location.area];
      putRequest(`api/user/update/${this.user.username}`, userInfo);
    },
    deleteUser() {
      deleteRequest(`api/user/delete/${this.user.username}`);
    },
    getAdminStatus() {
      fetch('http://localhost:8080/api/auth/admin', {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => this.isAdmin = data)
    },
    getUserInfo() {
      let path = "http://localhost:8080/api/user/";

      if (this.$route.params.username.length > 0) {
        path += `get/${this.$route.params.username}`
      } else {
        path += "get"
      }

      fetch(path, {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => this.user = data)
          .catch(error => alert('Error:', error));
    }
  },
  created() {
    this.getAdminStatus();
    this.getUserInfo();
  }
}
</script>