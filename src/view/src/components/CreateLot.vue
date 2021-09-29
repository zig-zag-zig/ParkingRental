<template>
  <div>
    <form @submit.prevent="createLot" method="post">
      <p>
        <label for="addressStreet">Address Street: </label><br>
        <input v-model="address" type="text" name="addressStreet" id="addressStreet" required="required">
      </p>

      <p>
        <label for="addressNumber">Address Number: </label><br>
        <input v-model="number" type="number" name="addressNumber" min="1" id="addressNumber" required="required"
               oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="addressZip">Address Zip: </label><br>
        <input v-model="zipcode" type="number" name="addressZip" min="1" id="addressZip" required="required"
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
        <button class="btn" type="submit">Create</button>
      </p>
    </form>
  </div>
</template>

<script>
import {postRequest} from "@/restApi/ApiCaller";

export default {
  name: "CreateLot",
  data: () => ({
    address: '',
    number: '',
    zipcode: '',
    city: '',
    area: '',
  }),
  methods: {
    createLot() {
      let lotInfo = [this.city, this.address, this.number, this.zipcode, this.area];
      postRequest("api/parkinglot/create", lotInfo, "Successfully created the parkinglot successfully!", "Failed to create the parkinglot!");
    }
  }
}
</script>