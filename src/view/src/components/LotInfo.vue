<template>
  <div>
    <span v-if="ownerOrAdmin === true">
      <p>
        <label for="addressStreet">Address Street: </label><br>
        <input v-model="parkinglot.location.address" type="text" name="addressStreet" id="addressStreet" required="required">
      </p>

      <p>
        <label for="addressNumber">Address Number: </label><br>
        <input v-model="parkinglot.location.number" type="number" name="addressNumber" min="0" id="addressNumber" required="required"
               oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="addressZip">Address Zip: </label><br>
        <input v-model="parkinglot.location.zipcode" type="number" name="addressZip" min="0" id="addressZip" required="required"
               oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="addressCity">Address City: </label><br>
        <input v-model="parkinglot.location.city" type="text" name="addressCity" id="addressCity" required="required">
      </p>

      <p>
        <label for="addressArea">Address Area: </label><br>
        <input v-model="parkinglot.location.area" type="text" name="addressArea" id="addressArea" required="required">
      </p>

      <p>
        <button class="btn" @click="updateLot">Update Parkinglot</button>
      </p>

      <p>
        <label for="expandSchedule">Expand Schedule By Days: </label><br>
        <input v-model="expandBy" type="number" name="expandSchedule" min="1" id="expandSchedule" oninput="validity.valid||(value='');">
        <br>
        <button class="btn" @click="expandSchedule(parkinglot.id)">Expand</button>
      </p>

      <p>
        <button class="btn" @click="this.$router.push(`/lot/${parkinglot.id}/createspot`)">New Parkingspot</button>
      </p>

      <p>
        <button class="btn" @click="deleteLot">Delete Parkinglot</button>
      </p>
    </span>
    <span v-else>
      <p>City: {{ parkinglot.location.city }}</p>
      <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
      <p>ZipCode And Area: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>
      <p>Owner's Name: {{ parkinglot.owner.firstname }} {{ parkinglot.owner.surname }}</p>
    </span>

    <span v-if="parkinglot.spots.length > 0">
      <p>
        <button class="btn" @click="this.$router.push(`/lot/${this.parkinglot.id}/search`)">Search For Spots</button>
      </p>
    </span>

    <span v-for="spot in this.parkinglot.spots">
      <span v-if="ownerOrAdmin === true">
        <p>Spot-ID: {{ spot.id }}</p>
        <p>
          Type:
          <select id="typeSelect">
            <option>{{spot.type}}</option>
            <span v-if="spot.type !== 'Regular'">
              <option>Regular</option>
            </span>
            <span v-if="spot.type !== 'Handicap'">
              <option>Handicap</option>
            </span>
            <span v-if="spot.type !== 'Truck'">
              <option>Truck</option>
            </span>
          </select>
          <br>
        </p>
        <p>
          <label for="hourlyPrice">Hourly Price: </label><br>
          <input v-model="spot.hourlyPrice" type="number" name="hourlyPrice" min="1" id="hourlyPrice" required="required"
                 oninput="validity.valid||(value='');">
        </p>

        <p>
          <button class="btn" @click="updateSpot(spot.id, spot.hourlyPrice)">Update Spot</button>
        </p>
        <p>
          <button class="btn" @click="deleteSpot(spot.id)">Delete Spot</button>
        </p>
      </span>
      <span v-else>
        <p>Spot-ID: {{ spot.id }}, Type: {{ spot.type }}, Hourly Price: {{ spot.hourlyPrice }}</p>
      </span>
    </span>
  </div>
</template>

<script>
import {deleteRequest, putRequest} from "@/restApi/ApiCaller";

export default {
  name: "LotInfo",
  data: () => ({
    parkinglot: null,
    ownerOrAdmin: '',
    expandBy: '',
  }),
  created() {
    this.getParkinglot();
  }, methods: {
    checkIfOwnerOrAdmin() {
      fetch(`http://localhost:8080/api/auth/admin/corretuser/${this.parkinglot.owner.username}`, {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => this.ownerOrAdmin = data)
    },
    getParkinglot() {
      fetch(`http://localhost:8080/api/parkinglot/get/${this.$route.params.id}`, {
        credentials: 'include'
      }).then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error();
        }
      }).then(data => {
        this.parkinglot = data;
        this.checkIfOwnerOrAdmin();
      }).catch(error => alert('Parkinglot not found!'));
    },
    updateSpot(spotId, newPrice) {
      let typeSelect = document.getElementById("typeSelect");
      let type = typeSelect.options[typeSelect.selectedIndex].text;
      let updatedInfo = [type, newPrice];
      putRequest(`api/parkingspot/update/${spotId}`, updatedInfo, "Successfully updated the parkingspot!", "Failed to update the parkingspot!");
    },
    expandSchedule() {
      putRequest(`api/parkinglot/expand/${this.parkinglot.id}/${this.expandBy}`, "", "Successfully expanded the schedule!", "Failed to expand the schedule!");
    },
    deleteSpot(spotId) {
      deleteRequest(`api/parkingspot/delete/${spotId}`);
    },
    updateLot() {
      putRequest(`api/parkinglot/update/${this.parkinglot.id}`,  this.parkinglot.location, "Successfully updated the parkinglot!", "Failed to update the parkinglot!");
    },
    deleteLot() {
      deleteRequest(`api/parkinglot/delete/${this.parkinglot.id}`);
    }
  }
}
</script>