# Github-Api-Demo-SmartOne
This is a test repository created as per the requirments laid down by SmartOne team.

## <a href="https://github.com/TusharGogna/Github-Api-Demo-SmartOne/raw/main/app-debug.apk" download>Click to Download the APK file</a>

###### The application uses the following components:
1. [Kotlin](https://kotlinlang.org/) as the base language.
2. [Retrofit](https://square.github.io/retrofit/) to fetch data from API.
3. [Dagger Hilt](https://dagger.dev/hilt/) as Dependency Injector.
4. [MVVM](https://developer.android.com/topic/architecture) with Clean architecture.
5. [Jetpack Compose](https://developer.android.com/jetpack/compose) for UI.
6. [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for Navigation.
7. [Coil Compose](https://coil-kt.github.io/coil/compose/) for remote Image management.
8. [Splash Screen API](https://developer.android.com/develop/ui/views/launch/splash-screen) for Splash Screen.
9. [Mockito](https://github.com/mockito/mockito-kotlin) for Mocks.


## This is a simple application which uses internet permission in order to fetch user details from Github public API.

The flow is as following:
- On Start Up, the Splash Screen with animation is loaded.
- After that, default UserId is used to load my personal Github details and Repositories.
- On the same page i.e. Home Page, the user can also enter any other UserId in the TextField and click on Search button to fetch that user's Github details.
- If you click on any of the Repositories, then you will navigate to a new Compose Screen where details related to that repository is disaplyed.
- There are two buttons provided on this screen: 1. Visit Repository 2. Go Back.
  1. Visit Repository: This will open Github application as a Deeplink and navigate directly to the repository (if Github application is installed), else it will open the same in browser.
  2. Go Back: This will simply close the detail screen and go back to home screen.
 
Negative cases which are covered:
- If user clicks on Search button without entering anything in the TextField, an error will be shown.
- If there is no internet or the response fails, the corresponding error message will be shown.


## Here are few screenshots: 
<img src="https://github.com/TusharGogna/Github-Api-Demo-SmartOne/assets/36148180/3dc13bf9-fd67-4bd4-be97-1d6dd642bd4f" width=50% height=50%>

This is the first screen which is displayed to the user. The Repository list is a scrollable list.

<img src="https://github.com/TusharGogna/Github-Api-Demo-SmartOne/assets/36148180/e829ce1d-18c7-4e50-884d-dac11b474ebb" width=50% height=50%>

This is a repository details screen. In this, you can scroll the properties related to the repository and view the things such as stars, forks, language used along with 2 aforementioned buttons
   
