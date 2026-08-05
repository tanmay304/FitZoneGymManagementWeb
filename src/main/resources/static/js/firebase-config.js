// FitZone Gym Management System - Firebase SDK Configuration
import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-app.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-analytics.js";
import { getFirestore, collection, addDoc, getDocs, onSnapshot } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-firestore.js";
import { getAuth } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-auth.js";

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyAkh-3uIe7jjAwPZbHwtot96ZsIIwyDvmw",
  authDomain: "fitzonegymmanagement.firebaseapp.com",
  projectId: "fitzonegymmanagement",
  storageBucket: "fitzonegymmanagement.firebasestorage.app",
  messagingSenderId: "64167701885",
  appId: "1:64167701885:web:5c172641cb470b16327014",
  measurementId: "G-Y72BC6FNJ4"
};

// Initialize Firebase App
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const db = getFirestore(app);
const auth = getAuth(app);

window.fitzoneFirebase = { app, analytics, db, auth, collection, addDoc, getDocs, onSnapshot };
console.log("🔥 FitZone Firebase Cloud Initialized Successfully [Project: fitzonegymmanagement]");
