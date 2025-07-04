# GWeather

## 🔧 Tech Stack
- Jetpack Compose
- Kotlin Coroutines & Flow
- Koin 
- Ktor 
- Room 
---

## 🧱 Architecture Overview

- **UI Layer**: Composables + ViewModels with `StateFlow`
- **Domain Layer**: UseCases + Repository interfaces
- **Data Layer**: Repository implementation using Ktor (remote) and Room (local)

## ▶️ How to Run the App

1. Clone the repository

```bash
git clone https://github.com/farsuller/GWeather.git
cd GWeather
```

1. Open the project in **Android Studio**

2. Sync Gradle and build the project

3. Connect a device or emulator and click **Run**

---