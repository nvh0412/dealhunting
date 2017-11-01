const firebase = require("firebase/app");
require("firebase/auth");
require("firebase/database");

const config = {
  apiKey: "AIzaSyBtg7HFM-RDumy2mtgBe8aWeqd9loscn7k",
  authDomain: "dealhunting-1479746504210.firebaseapp.com",
  databaseURL: "https://dealhunting-1479746504210.firebaseio.com",
  projectId: "dealhunting-1479746504210",
  storageBucket: "dealhunting-1479746504210.appspot.com",
  messagingSenderId: "2328104608"
};

firebase.initializeApp(config);
