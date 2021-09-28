<template>
  <div>
    <form @submit.prevent="createSpot" method="post">
      <select id="typeSelect">Type
        <option>Regular</option>
        <option>Handicap</option>
        <option>Truck</option>
      </select>
      <p>
        <label for="hourlyPrice">Hourly Price: </label><br>
        <input v-model="hourlyPrice" type="number" name="hourlyPrice" min="1" id="hourlyPrice" required="required"
               oninput="validity.valid||(value='');">
      </p>
      <p>
        <button class="btn" type="submit">Create</button>>
      </p>
    </form>
  </div>
</template>

<script>
import {postRequest} from "@/restApi/ApiCaller";

export default {
  name: "CreateSpot",
  data: () => ({
    hourlyPrice: 1,
  }),
  methods: {
    createSpot () {
      let typeSelect = document.getElementById("typeSelect");
      let type = typeSelect.options[typeSelect.selectedIndex].text;
      let spotInfo = [this.$route.params.id, type, this.hourlyPrice];
      postRequest('api/parkingspot/create', spotInfo);
    }
  }
}
</script>