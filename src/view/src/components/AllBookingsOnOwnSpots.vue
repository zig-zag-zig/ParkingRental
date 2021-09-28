<template>
  <div class="flex-wrapper">
    <div v-for="booking in bookings">
      <div class="flex-container">
            <div>
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
            </div>
          <p>
            <button class="btn" @click="this.deleteBooking(booking.id);">Delete</button>
          </p>
      </div>
    </div>
  </div>
</template>

<script>
import {deleteRequest} from "@/restApi/ApiCaller";

export default {
  name: "AllBookingsOnOwnSpots",
  methods: {
    deleteBooking(id) {
      deleteRequest(`api/booking/delete/${id}`)
    },
    getAll() {
      let path = "http://localhost:8080/api/booking/";
      if (this.$route.params.username.length > 0) {
        path += `bookingsOnOwnedSpots/${this.$route.params.username}`
      } else {
        path += "bookingsOnOwnedSpots"
      }
      fetch(path, {
        credentials: 'include'
      }).then(response => response.json())
          .then(data => this.bookings = data)
          .catch(error => alert('Error:', error));
    }
  },
  data: () => {
    return {
      bookings: [],
    }
  },
  created() {
    this.getAll();
  }
}
</script>