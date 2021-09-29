<template>
  <div>
    <h2>Find Available</h2>

    <p>City: {{ parkinglot.location.city }}</p>
    <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
    <p>ZipCode: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>

    <br>

    <form @submit.prevent="search" method="post">

      <p>
        Date <input type="date" id="datePicker" v-model="date">
      </p>

      <p>
        Time
        <select v-model="selectedTime">
          <option v-for="oneHour in hoursArray" v-bind:value="{ timeSelected: oneHour }">{{oneHour}}</option>
        </select>
      </p>

      <p>
        Type
        <select  v-model="selectedType">
          <option v-bind:value="{ typeSelected: 'All types' }">All types</option>
          <option v-for="oneType in typesArray" v-bind:value="{ typeSelected: oneType }">{{oneType}}</option>
        </select>
      </p>

      <p>
        <label for="hours">Hours you wish to book for </label><br>
          <input v-model="hours" type="number" name="hours" min="1" id="hours" required="required"
                 oninput="validity.valid||(value='');">
      </p>

      <p>
        <button class="btn" type="submit">Search</button>
      </p>
    </form>

    <p>
      Choose An Available Spot To Book
      <select id="spotSelect"></select>
    </p>

    <button class="btn" @click="this.book">Book</button>

  </div>

</template>

<script>
import {postRequest} from "@/restApi/ApiCaller";

export default {
  name: "Search",
  data: () => ({
    parkinglot: null,
    spots: [],
    datePicker: null,
    hours: null,
    hoursArray: [],
    typesArray: [],
    date: new Date().toLocaleDateString(),
    selectedTime: '',
    selectedType: '',
    spotSelect: '',
  }),
  created() {
    this.getLot();
  }, methods: {
    getLot() {
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
        this.get24HoursArray();
        this.getAllTypesArray();
      }).catch(error => alert('Parkinglot not found!'));
    },
    get24HoursArray() {
      for (let i = 0; i < 24; i++) {
        this.hoursArray[i] =  String(i).padStart(2, '0');
      }
    },
    getAllTypesArray() {
      let spots = this.parkinglot.spots;
      for (let i = 0; i < spots.length; i++) {
        if(this.typesArray.indexOf(spots[i].type) === -1){
          this.typesArray.push(spots[i].type);
        }
      }
    },
    search() {
      let time = this.selectedTime.timeSelected;
      this.date += ` ${time}:00`;
      let type = this.selectedType.typeSelected;
      fetch(`http://localhost:8080/api/booking/onlyavailable/${this.$route.params.id}/${type}/${this.date}/${this.hours}`)
          .then(response => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error();
            }
          })
          .then(res => {
            this.spots = res;
            this.fillSelectWithSpots();
          })
          .catch(error  => alert("Could not find any available parkingspots!"));
      },
    book() {
      let spotId = this.spotSelect.options[this.spotSelect.selectedIndex].value;
      let bookingInfo = [spotId, this.parkinglot.id, this.date, this.hours];
      postRequest("api/booking/book", bookingInfo, "Successfully booked the parkingspot!", "Failed to book the parkingspot!");
    },
    fillSelectWithSpots() {
      this.spotSelect = document.getElementById('spotSelect');
      this.spotSelect.innerHTML = "";
      for (let i = 0; i < this.spots.length; i++) {
        let newSpot = document.createElement("option");
        newSpot.value = this.spots[i].id;
        newSpot.text = `Type ${this.spots[i].type}, Total Price ${ this.spots[i].hourlyPrice * this.hours}`;
        this.spotSelect.appendChild(newSpot);
      }
    }
  }
}
</script>