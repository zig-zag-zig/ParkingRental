<template>
  <div class="grid">
    <p>Booking-ID: {{ booking.id }}</p>
    <p>Spot-ID: {{ booking.spot.id }}, Type: {{ booking.spot.type }}, Hourly Price: {{ booking.spot.hourlyPrice }} </p>

    <span style="display:none;">{{ i = 0 }}</span>

    <p>Bookingdates:
      <span v-for="dates in booking.dateAndTime">
        {{ dates }}<span v-if="i + 1 !== booking.dateAndTime.length">, </span>
        <span style="display:none;">{{ i++ }}</span>
      </span>
    </p>

    <p>Parkinglot Location City: {{ booking.parkinglot.location.city }}</p>
    <p>Address: {{ booking.parkinglot.location.address }}, {{ booking.parkinglot.location.number }}</p>
    <p>ZipCode And Area: {{ booking.parkinglot.location.zipcode }}, {{ booking.parkinglot.location.area }}</p>
    <br>
    <p>Parkinglot Owner Name: {{ booking.parkinglot.owner.firstname }}, {{ booking.parkinglot.owner.surname }}</p>
    <br>
    <p>Booker Name: {{ booking.user.firstname }} {{ booking.user.surname }}</p>
    <p>Price: {{ booking.price }}</p>
    <p>
      <button class="btn" @click="deleteBooking;">Delete</button>
    </p>
  </div>
</template>

<script>
import {deleteRequest} from "@/restApi/ApiCaller";

export default {
  name: "BookingInfo",
  data: () => ({
    booking: null,
  }),
  methods: {
    deleteBooking() {
      deleteRequest(`api/booking/delete/${this.booking.id}`)
    },
    getBooking() {
      fetch(`http://localhost:8080/api/booking/get/${this.$route.params.id}`, {
        credentials: 'include'
      }).then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error();
        }
      }).then(data => this.booking = data)
          .catch(error => alert('Booking not found!'));
    }
  },
  created() {
    this.getBooking();
  }
}
</script>