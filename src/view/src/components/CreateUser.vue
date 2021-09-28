<template>
  <div>
    <form @submit.prevent="createUser" method="post">

      <p>
        <label for="username">Username: </label><br>
          <input v-model="username" type="text" name="username" id="username" required="required">
      </p>

      <p>
        <label for="password">Password: </label><br>
        <input v-model="password" type="password" name="password" id="password" required="required">
      </p>

      <p>
        <label for="passwordConf">Password Confirmed: </label><br>
        <input v-model="passwordConf" type="password" name="passwordConf" id="passwordConf" required="required">
      </p>

      <p>
        <label for="firstname">Firstname: </label><br>
        <input v-model="firstname" type="text" name="firstname" id="firstname" required="required">
      </p>

      <p>
        <label for="lastname">Lastname: </label><br>
        <input v-model="lastname" type="text" name="lastname" id="lastname" required="required">
      </p>

      <p>
        <label for="addressStreet">Address Street: </label><br>
        <input v-model="address" type="text" name="addressStreet" id="addressStreet" required="required">
      </p>

      <p>
        <label for="addressNumber">Address Number: </label><br>
        <input v-model="number" type="number" name="addressNumber" min="0" id="addressNumber" required="required"
               oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="addressZip">Address Zip: </label><br>
        <input v-model="zipcode" type="number" name="addressZip" min="0" id="addressZip" required="required"
               oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="addressArea">Address Area: </label><br>
        <input v-model="area" type="text" name="addressArea" id="addressArea" required="required">
      </p>

      <p>
        <label for="addressCity">Address City: </label><br>
        <input v-model="city" type="text" name="addressCity" id="addressCity" required="required">
      </p>

      <p>
        <button class="btn" type="submit">Create</button>>
      </p>

    </form>
  </div>
</template>

<script>
const {postRequest} = require("@/restApi/ApiCaller");
export default {
  name: "CreateUser",
  data: () => ({
    username: '',
    password: '',
    passwordConf: '',
    firstname: '',
    lastname: '',
    city: '',
    address: '',
    number: '',
    zipcode: '',
    area: '',
  }),
  methods: {
    createUser() {
      let userInfo = [this.username, this.password, this.passwordConf, this.firstname, this.lastname, this.city, this.address, this.number.toString(), this.zipcode.toString(), this.area];

      postRequest("api/user/create", userInfo);
    }
  }
}
</script>