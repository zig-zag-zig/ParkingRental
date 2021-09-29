import {createRouter, createWebHashHistory} from "vue-router";
import AllUsers from "../components/AllUsers";
import CreateUser from "../components/CreateUser";
import UserInfo from "../components/UserInfo";
import CreateLot from "../components/CreateLot";
import AllLots from "../components/AllLots";
import AllLotsOfUser from "../components/AllLotsOfUser";
import LotInfo from "../components/LotInfo";
import CreateSpot from "../components/CreateSpot";
import Search from "../components/Search";
import AllBookings from "../components/AllBookings";
import AllBookingsOnOwnSpots from "../components/AllBookingsOnOwnSpots";
import BookingInfo from "../components/BookingInfo";

const routes = [
    {
        path: "/user/:username?",
        name: "User Info",
        component: UserInfo,
    },
    {
        path: "/user/all",
        name: "All Users",
        component: AllUsers,
    },
    {
        path: "/createuser",
        name: "Create User",
        component: CreateUser,
    },
    {
        path: "/createlot",
        name: "Create Parkinglot",
        component: CreateLot,
    },
    {
        path: "/",
        alias: "/lot",
        name: "All Parkinglots",
        component: AllLots,
    },
    {
        path: "/lot/all/:username?",
        name: "All Parkinglots Of User",
        component: AllLotsOfUser,
    },
    {
        path: "/lot/:id",
        name: "Parkinglot Info",
        component: LotInfo,
    },
    {
        path: "/lot/:id/createspot",
        name: "Create Parkingspot",
        component: CreateSpot,
    },
    {
        path: "/lot/:id/search",
        name: "Search",
        component: Search,
    },
    {
        path: "/booking/:id",
        name: "Booking Info",
        component: BookingInfo,
    },
    {
        path: "/booking/all/:username?",
        name: "All Bookings",
        component: AllBookings,
    },
    {
        path: "/booking/all/ownedspots/:username?",
        name: "All Bookings On Owned Spots",
        component: AllBookingsOnOwnSpots,
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;