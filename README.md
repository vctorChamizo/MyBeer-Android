![ic_launcher_round](https://user-images.githubusercontent.com/32466953/73373783-75258e80-42b9-11ea-9b09-e6257c316f1c.png)

# MyBeer-Android

## Introduction
MyBeer was created like a application was used for the management of breweries and beers by the user, with the determination to be able to locate the data on a map and be able to save the information of said elements so that the user can access them whenever they want.


## Documentation
The main functionality of the application consists of three modules (Main, Breweries and Beers), which are accessible through a side menu. This menu can be accessed by clicking on the upper left button of the interface or by sliding your finger from the left side of the screen to the right side.

### Main Module
The main module consists of a map that will show us our geolocation, as well as the breweries located in their respective locations.

Breweries can be displayed in different colors according to their status. In addition to clicking on a brewery, its name will appear, and finally, in the lower right we can the floating button will automatically redirect us to our position on the map.

![main](https://user-images.githubusercontent.com/32466953/73434316-ce87cf00-4346-11ea-9ed2-9e0960fcbcf4.png)


### Breweries Modules
The module "breweries" has two main functionalities: add a new brewery and list the breweries that the user has saved. In addition, clicking on each of the listed breweries will redirect us to a view where we can appreciate the information of the brewery in more detail.

![breweries](https://user-images.githubusercontent.com/32466953/73434688-7e5d3c80-4347-11ea-9d7e-0e7efe313029.png)


### Beers Module
El módulo ‘cervezas’ al igual que el módulo cervecerías está constituido de dos funcionalidades principales: añadir una nueva cerveza y listas las cervezas guardadas por el usuario.

![beers](https://user-images.githubusercontent.com/32466953/73434581-4bb34400-4347-11ea-8c97-a3763d8c84e7.png)


## Architecture
- **Presentation layer**: represented by the activities package, it is responsible for rendering the views. This layer, although not responsible for the logic of the application, does perform small validations that are closely linked to the input format.

- **Business layer**: formed by the services of the application, it is located in the model package. In this layer, all the logic of the application takes place, in charge of complying with the business rules necessary for its proper functioning.

- **Integration layer**: layer responsible for communicating with the mobile device for reading and writing the data necessary for the application to develop correctly. Implemented by the FileManager class.


## Project Content
- Application complete dodumentation: /doc-mybeer.pdf
- APK application installer: /mybeer_app.apk
- Source code: /src-mybeer


## Technologies
  - **Android Studio**: for the client application development and graphical interface. This module has been 
    developed with **Java**.
  
  - **Mapbox API**: to render and manage the map management used by the application.


## Setup
This section provides the necessary indications and requirementsfor the correct installation of the ShuttleGo mobile application.

  1. Requirements
    The client of this project is developed for the platform Android, at least you have to have version 6.0 (Marshmallow), although the     SDK used is 28 (Android 9.0, Pie).
  
  2. Most mobile devices have the option “install applications of unknown use ”deactivated. To activate said option should go to  Settings> Security. NOTE: the navigation through the settings may vary slightly in function of the Android version of the device and / or the layer of manufacturer customization.
  
  3. Once inside activate the option “Unknown origins”. Google will display a message warning of the risks
That this implies.

  4. Now you can proceed to the installation of the application, running the mybeer_app.apk file from the device.
  
  5. This file can be copied by connecting the terminal to a computer through a USB and dragging it to any device directory; or, it can be downloaded from the repository where it is hosted. In this manual, the following will be followed Steps of the second option.
  
  6. The file is downloaded and the device automatically starts the installation process. Must be press install to continue.

## Authors
The project has been carried out by [Carlos Castellanos](https://github.com/carlosCharlie) and [Victor Chamizo](https://github.com/vctorChamizo).

Happy coding! 💻

