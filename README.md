# 🐘 Smart Elephant Fence Breach Detector System

An IoT-based real-time monitoring system designed to detect elephant fence breaches and deliver instant alerts through a mobile application and backend infrastructure.

---

## 📌 Overview

The **Smart Elephant Fence Breach Detector System** is an engineering design project developed at the **University of Moratuwa** to help reduce human–elephant conflict in Sri Lanka.

The system detects abnormal disturbances in electric fences using embedded sensors and triggers real-time alerts through multiple channels:

- 📞 **GSM Voice Calls** to authorities (critical alert path)  
- 📱 **Android Mobile Application** notifications for farmers and villagers  

This repository focuses on the **Android mobile application and system integration**, which was my primary contribution.

---

## 📱 Mobile Application Features

- 🔐 Firebase OTP Authentication (secure login system)  
- 🔔 Real-time push notifications using Firebase Cloud Messaging (FCM)  
- 📍 Alert dashboard for monitoring fence breach events  
- 📩 Message history and event tracking  
- 🧭 Multi-screen navigation system  
- 💬 Feedback submission system  
- ☁️ Backend integration for live data updates  

---

## 🧠 System Architecture

```text
Sensor Unit (ESP32 + Gyroscope)
            ↓
      GSM Module (SIM800A)
            ↓
     Backend Server (Oracle / API Layer)
            ↓
     Firebase Cloud Messaging (FCM)
            ↓
     Android Mobile Application
