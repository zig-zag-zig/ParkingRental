<template>
  <div class="flex-wrapper">
    <div v-for="parkinglot in parkinglots">
      <div class="flex-container">
        <a class="link" :href="`http://localhost:8080/#/lot/${parkinglot.id}`">
          <p>City: {{ parkinglot.location.city }}</p>
          <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
          <p>ZipCode: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>
          <p>Owner's Name: {{ parkinglot.owner.firstname }} {{ parkinglot.owner.surname }}</p>
          <span v-if="ownerOrAdmin === true">
            <button class="btn" @click="this.deleteLot(parkinglot.id);">Delete</button>
          </span>
          <span v-if="parkinglot.spots.length > 0">
            <button class="btn" @click="this.$router.push(`/lot/${parkinglot.id}/search`)">Search For Spots</button>
            <div v-for="spot in parkinglot.spots">
              <p>Spot-ID: {{ spot.id }}, Type: {{ spot.type }}, Hourly Price: {{ spot.hourlyPrice }}</p>
            </div>
          </span>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import {deleteRequest} from "@/restApi/ApiCaller";

export default {
  name: "AllLotsOfUser",
  data: () => ({
    parkinglots: []
  }),
  created() {
    this.getAllParkinglots();
    this.checkIfOwnerOrAdmin();
  },
  methods: {
    checkIfOwnerOrAdmin() {
      fetch(`http://localhost:8080/api/auth/admin/corretuser/${this.parkinglot.owner.username}`, {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => this.ownerOrAdmin = data)
    },
    getAllParkinglots() {
      let path = "http://localhost:8080/api/parkinglot/";
      if (this.$route.params.username.length > 0) {
        path += `all/${this.$route.params.username}`
      } else {
        path += "allcurrentuser"
      }
      fetch(path)
          .then(response => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error();
            }
          })
          .then(res => this.parkinglots = res)
          .catch(() => alert("User does not have any parkinglots!"));
    },
    deleteLot(lotId) {
      deleteRequest(`api/parkinglot/delete/${lotId}`);
    }
  }
}
</script>