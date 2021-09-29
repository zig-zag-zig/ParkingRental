<template>
  <div class="flex-wrapper">
    <button @click="this.$router.push(`/lot/all/`)" class="btn">My Parkinglots</button>
    <div v-for="parkinglot in parkinglots">
      <span v-if="parkinglot.spots.length > 0">
        <div class="flex-container">
            <a class="link" :href="`http://localhost:8080/#/lot/${parkinglot.id}`">
              <p>City: {{ parkinglot.location.city }}</p>
              <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
              <p>ZipCode: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>
              <p>Owner's Name: {{ parkinglot.owner.firstname }} {{ parkinglot.owner.surname }}</p>
              <button class="btn" @click="this.$router.push(`/lot/${parkinglot.id}/search`)">Search For Spots</button>
              <div v-for="spot in parkinglot.spots">
                <p>Spot-ID: {{ spot.id }}, Type: {{ spot.type }}, Hourly Price: {{ spot.hourlyPrice }}</p>
              </div>
            </a>
        </div>
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "AllLots",
  data: () => ({
    parkinglots: []
  }),
  created() {
    this.getAllParkinglots();
  },
  methods: {
    getAllParkinglots() {
      fetch('http://localhost:8080/api/parkinglot/all')
          .then(response => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error();
            }
          })
          .then(res => this.parkinglots = res)
          .catch(() => alert("No parkinglots found!"));
    }
  }
}
</script>