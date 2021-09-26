<template>
  <div v-for="parkinglot in parkinglots">
    <p>City: {{ parkinglot.location.city }}</p>
    <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
    <p>ZipCode: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>
    <p>Owner's Name: {{ parkinglot.owner.firstname }} {{ parkinglot.owner.surname }}</p>

    <span v-if="parkinglot.spots !== null">
      <span v-for="spot in parkinglot.spots">
        <p>Spot-ID: {{ spot.id }}, Type: {{ spot.type }}, Hourly Price: {{ spot.hourlyPrice }}</p>
      </span>

      <p>
        <button @click="this.$router.push(`/lot/${parkinglot.id}`)" class="nav-link">Open Parkinglot</button>
      </p>

      <span v-if="parkinglot.spots.length > 0">
        <p>
          <button @click="this.$router.push(`/lot/${parkinglot.id}/search`)" class="nav-link">Search For Spots</button>
        </p>
      </span>
    </span>
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
          .then(res => res.json())
          .then(res => {
            this.parkinglots = res;
          })
          .catch(() => alert("No parkinglots found!"));
    }
  }
}
</script>