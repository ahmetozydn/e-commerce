# Shopie
Sample MVVM e-commerce application for Android that uses Kotlin programming language. The application has 100 different products in different categories.

- API: A set of functions and procedures allowing the creation of applications. The application uses the [link](https://dummyjson.com/) as API.


## Dependencies

` Live data:` LiveData is an Observable data holder that allows us to monitor changes in a data. LiveData is lifecycle aware.  [link](https://developer.android.com/topic/libraries/architecture/livedata)<br/>
` Retrofit:` A type-safe HTTP client for Android and Java [link](https://square.github.io/retrofit/)<br/>
` RxJava:` A library for composing asynchronous and event-based programs using observable sequences for the Java VM. [link](https://github.com/ReactiveX/RxJava)<br/>
` Coroutines:` A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously. [link](https://developer.android.com/kotlin/coroutines#:~:text=A%20coroutine%20is%20a%20concurrency,established%20concepts%20from%20other%20languages.)<br/>
` Room:` The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power       of SQLite. [link](https://developer.android.com/training/data-storage/room)<br/>
` View binding:` View binding is a feature that allows you to more easily write code that interacts with views. [link](https://developer.android.com/topic/libraries/view-binding)<br/>
` MVVM:` Model, View, ViewModel. [link](https://www.digitalocean.com/community/tutorials/android-mvvm-design-pattern)<br/>
- *Model*: This holds the data of the application. It cannot directly talk to the View.<br/>
- *View*: It represents the UI of the application<br/>
- *ViewModel*: It acts as a link between the Model and the View.<br/>
` Data Binding:` A support library that allows you to bind UI components in your layouts to data sources using a declarative format rather than programmatically. [link](https://developer.android.com/topic/libraries/data-binding)<br/>
` Jetpack: ` [link](https://developer.android.com/jetpack/androidx/explorer)<br/>
` Glide: `  Image loading framework for Android. [link](https://github.com/bumptech/glide)<br/>

# _ScreenShots_


|  _Main Page_  |      _Cart Screen;_      | 
|----------|:-------------:|
| ![s1-mainpage](https://user-images.githubusercontent.com/75504778/211359914-6a0c7660-6ca9-41a7-b329-0bbe4d165229.png) |  ![s4-cart](https://user-images.githubusercontent.com/75504778/211360996-1da23472-d04a-47ea-80b7-0dbc87b6ac76.png) | $1600 |

|  _Favorites;_ |      _Seach View;_      |  
|----------|:-------------:|
| ![s3-favorite](https://user-images.githubusercontent.com/75504778/211360555-27d7dc3e-f97f-48ca-a60d-579c64f8f584.png) |  ![s5-searchview](https://user-images.githubusercontent.com/75504778/211361199-e0654379-704c-4b61-ace4-c3e59a60e544.png) |


|  _Products by category;_ |      _Product Details_     |
|----------|:-------------:|
| ![s2-category](https://user-images.githubusercontent.com/75504778/211360309-fa20bef7-71e0-46e5-a357-15363c316c32.png) |  ![s6-detailed product](https://user-images.githubusercontent.com/75504778/211362624-4b1b2f96-806c-4691-b5ec-560e4310973f.png) |


## _TO DO_
- [x] home page
- [x] favorite screen
- [x] cart screen
- [ ] payment screen
- [ ] sign in screen
- [ ] profile screen
--------------------------------
- [ ] Jetpack compose
- [ ] Testing
- [ ] dagger-hilt

