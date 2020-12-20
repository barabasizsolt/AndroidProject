# Where To Eat?

## Overview
This app was create to manage different restaurants from different countries.
It pulls data from the https://ratpark-api.imok.space API.
The application was written in [Android](https://en.wikipedia.org/wiki/Android_(operating_system)).
This is the official project for my **_Mobileszközök programozása_** exam.


## Features
* The data is sorted into a local database, well known as the [ROOM](https://developer.android.com/topic/libraries/architecture/room?gclid=Cj0KCQiAifz-BRDjARIsAEElyGI7AnU7rdH2X7KIxSN3Fw4V5t3F4oCTZyH7L8uOzfPqjskj_cwZVwsaAkfjEALw_wcB&gclsrc=aw.ds)
database.
* The app has his own Login/Register system. The users will be stored into the local databse.
* Every user has his own favorite restaurant list.
* The **_User_** can manage his favorites(ADD/DELETE).
* The **_Restaurant_** can be called up, the user can visit the original reestaurant site or just simply search it on the [Google Map](https://www.google.ro/maps).

## Main Android Components
* [Activity](https://developer.android.com/reference/android/app/Activity)
* [Fragmment](https://developer.android.com/guide/fragments)
* [Recycler View](https://developer.android.com/jetpack/androidx/releases/recyclerview)
* [Room and/or SQLite](https://developer.android.com/topic/libraries/architecture/room?gclid=Cj0KCQiAifz-BRDjARIsAEElyGI7AnU7rdH2X7KIxSN3Fw4V5t3F4oCTZyH7L8uOzfPqjskj_cwZVwsaAkfjEALw_wcB&gclsrc=aw.ds)

## Used Third Party Libraries
* [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
* [Glide](https://github.com/bumptech/glide) - Fast and efficient image loading for Android.
* [Searchable Spinner](https://github.com/ashwindmk/Android-Searchable-Spinner) - Based on [ashwindmk](https://github.com/ashwindmk) solution.

## Requirements
* Minimum SDK Version: 23
* Target SDK Version: 30
* [Android Studio](https://developer.android.com/studio?gclid=Cj0KCQiAifz-BRDjARIsAEElyGI0zlqyvwRiN1k3Hgd4JahhZ4LenjWjtUzHdumviEAb_S23Xm3RDqoaAg9ZEALw_wcB&gclsrc=aw.ds) to
run the application.
* Minumum Android Version: [Android_4.1_Jelly_Bean_(API_16)](https://en.wikipedia.org/wiki/Android_version_history#Android_4.1_Jelly_Bean_(API_16))

## Supervisor Teachers
* [Dr. Antal Marigt](https://ms.sapientia.ro/hu/tanszekek/matematika-informatika-tanszek/dr-antal-margit)
* Osztián Pálma-Rozália
