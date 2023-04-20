<h1 align="center">PokeCompose</h1>
<p align="center">  
PokeCompose demonstrates Android development with Hilt, Coroutines, Flow, Jetpack Compose, Ktor, and Material Design based on MVVM architecture.
</p>
</br>


<p align="center">
  <img src="https://img.shields.io/github/languages/top/davidHarush/PokeCompose.svg" alt="GitHub top language">
  <img src="https://img.shields.io/badge/API-30%2B-brightgreen.svg?style=flat" alt="API level">
<img src="https://www.codefactor.io/repository/github/davidharush/pokecompose/badge" alt="CodeFactor" />
 <img src="https://img.shields.io/github/repo-size/davidHarush/pokecompose" alt="GitHub repo size">
  <img src="https://img.shields.io/github/issues/davidHarush/pokecompose" alt="GitHub issues">

</p>



This is my first Compose app, and I had a lot of fun building it.</br>
After using Compose, I don't think I could go back to the traditional XML technique.</br>

The goal of this project was to experiment with Compose, add some animations, and learn.</br>
I'm sharing this repo as a template that can be used as a foundation for future projects.

Have fun exploring and experimenting with PokeCompose!


## Tech stack 
- Minimum SDK level 30
- [Jetpack Compose](https://developer.android.com/jetpack/compose) modern toolkit for building native UI.
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- MVVM Architecture (View - ViewModel - Model).
- [paging library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview).
- [Hilt](https://dagger.dev/hilt/): for dependency injection.
- [Kotlin serializable](https://kotlinlang.org/docs/serialization.html)

### Network 

All the network calls from sepred modle using Ktor with Kotlin serializable.
</br>
Ktor: KMM framework that allow to write asynchronous clients and servers applications, in Kotlin without android dependency.
</br>
For more info [Ktor](https://ktor.io/), [Kotlin serializable](https://kotlinlang.org/docs/serialization.html)



## Screenshot 

<p align="center">
<img src="/art/Video1.gif"   width="20%" style="margin-right: 100px;"/>
<img src="/art/Video2.gif"   width="20%" style="margin-right: 100px;"/>
<img src="/art/Video3.gif"   width="20%"/> 
</p>




### API

PokeCompose using the [PokeAPI](https://pokeapi.co/) for RESTful API calls.<br>

### License
```xml

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

