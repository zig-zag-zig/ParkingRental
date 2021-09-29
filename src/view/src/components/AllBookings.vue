<template>
  <div class="flex-wrapper">
    <button @click="this.$router.push(`/booking/all/ownedspots/`)" class="btn">Bookings on owned parkingspots</button>
    <div v-for="booking in bookings">
      <div class="flex-container">
        <a class="link" :href="`http://localhost:8080/#/booking/${booking.id}`">
          <p>Booking-ID: {{ booking.id }}</p>
          <p>Spot-ID: {{ booking.spot.id }} {{ booking.spot.type }} </p>

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
          <button class="btn" @click="this.deleteBooking(booking.id);">Delete</button>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import {deleteRequest} from "@/restApi/ApiCaller";

export default {
  name: "AllBookings",
  data: () => ({
    bookings: [],
    isAdmin: '',
  }),
  methods: {
    deleteBooking(id) {
      deleteRequest(`api/booking/delete/${id}`)
    },
    getAllBookingsAdmin() {
      let path = "http://localhost:8080/api/booking/all";
      if (this.$route.params.username.length > 0) {
        path += "/" + this.$route.params.username
      }
      this.fetchAllTheBookings(path);
    },
    fetchAllTheBookings(path) {
      fetch(path, {
        credentials: 'include'
      }).then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error();
        }
      }).then(data => this.bookings = data)
          .catch(error => alert('No bookings found!'));
    },
    getAdminStatus() {
      fetch('http://localhost:8080/api/auth/admin', {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => {
            this.isAdmin = data
            if (this.isAdmin === true)
              this.getAllBookingsAdmin();
            else
              this.fetchAllTheBookings("http://localhost:8080/api/booking/all");
          })
    },
  },
  created() {
    this.getAdminStatus();
  }
}
</script>