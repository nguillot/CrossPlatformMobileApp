# Introduction

The goal is to demonstrate how to convert an Android app to a multiplatform (Android & iOS) app by using some C++ and the Model View Presenter pattern. Each steps will be saved on a branch. We start from a sample [application](https://bitbucket.org/StylingAndroid/material.git) created by [the Styling Android team](https://blog.stylingandroid.com) at the branch *CrossPlatformMobileApp-origin*.

Note that I does not pretend use the right way to do it, just mine.


# Steps description

* Writing some Android UI & unit tests, branch: *unit_tests* 
* Refactor the project using the Model View Presenter (MVP) pattern, branch: *MVP*
* Re write the MVP classes into C++
* Create the iOS project
