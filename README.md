<p align="center">(Converted from an original docx document)</p>

<h1 align="center">MEMORIA DE PROYECTO</h1>

<p align="center"><img src='media/image10.png' width='60%' height='60%'><p>

**Autores: Carlos Castellanos y Víctor Chamizo**

**ÍNDICE**

1.  > Introducción ……………………………………………………………………………....... 2

2.  > Manual de instalación ……………………………………………………………………... 2
    
    1.  Introducción …………………………………………………………………….. 2
    
    2.  Requisitos ………………………………………………………………………. 2
    
    3.  Instalación del APK ……………………………………………………………. 3
    
    4.  Configuración de proyecto en Android Studio ………………………….…... 3

3.  > Manual de usuario …………………………………………………………………………. 3
    
    5.  Introducción …………………………………………………………………….. 3
    
    6.  Funcionalidad …………………………………………………………………... 4
    
    7.  Módulos …………………………………………………………………………. 4
        
        1.  > Principal ………………………………………………………………........ 4
        
        2.  > Cervecerías ……………………………………………………………….. 5
        
        3.  > Cervezas ……………………………………………………………...…... 6

4.  > Arquitectura ………………………………………………………………….……..………. 7
    
    8.  Introducción …………………………………………………………………….. 7
    
    9.  Diseño …………………………………………………………………………... 7

5.  > Consideraciones técnicas ………………………………………………………………… 8
    
    10. Introducción …………………………………………………………………….. 8
    
    11. General ………………………………………………………………………….. 8
    
    12. Módulos …………………………………………………………………………. 9
        
        4.  > Principal ………………………………………………………………........ 9
        
        5.  > Cervecerías y cervezas ………………………………………………….. 9

6.  > Desarrollo …………………………………………………………………………………… 9
    
    13. Introducción …………………………………………………………………….. 9
    
    14. Aportaciones ………………………………………………………………….. 10
    
    15. Metodología de trabajo ………………………………………………………. 10
    
    16. Conclusiones ………………………………………………………………..… 10

7.  > Bibliografía ………………………………………………………………………………… 11

# Introducción

Este documento de memoria es realizado para el proyecto de la asignatura
de Programación de Aplicaciones para Dispositivos Móviles (PAD).

El documento proporciona las indicaciones y herramientas necesarias para
el uso correcto de la aplicación móvil MyBeer. Además, contiene un
desglose detallado de la arquitectura interna y consideraciones técnicas
llevadas a cabo en el desarrollo del proyecto, con la finalidad de poder
acercar al usuario al entendimiento completo de la aplicación, y así
mismo, ratificar la correcta implementación de los requisitos impuestos
por el profesor de la asignatura para llevar a cabo dicho proyecto.

La aplicación fue diseñada para la gestión de cervecerías y cervezas por
parte del usuario, con la finalidad de poder localizar los
establecimientos en un mapa y poder guardar la información de dichos
elementos para que el usuario pueda acceder a ellos siempre que quiera.

2.  # Manual de instalación
    
    1.  ## Introducción

Esta sección proporciona los detalles y requerimientos para la correcta
instalación de la aplicación móvil MyBeer, así como una correcta
configuración de la misma en el SDK elegido para su desarrollo y
despliegue.

## Requisitos

La aplicación para este proyecto está desarrollada de forma nativa para
Android, por lo que el dispositivo del usuario ha de tener dicho sistema
instalado.

Además, el sistema operativo debe tener como mínimo la versión 23 de
compilación del SDK (Android 6.0, Marshmallow), aunque la versión de SDK
que se ha utilizado para el desarrollo de la aplicación es la 28
(Android 9.0, Pie).

La aplicación precisa de localización mediante GPS por lo que será
necesario tener activada esta opción en nuestro dispositivo móvil, así
como acceso a la red de datos o a una red wifi. Así mismo, la primera
vez que iniciemos la aplicación, esta nos solicitará permisos para poder
acceder a dichos servicios GPS.

## Instalación del APK

A continuación se detallan los pasos a seguir para la correcta
instalación de la aplicación en el dispositivo móvil:

1.  Descargar el archivo *myBeerInstaller.apk* en el móvil.

2.  Ejecutar el archivo *myBeerInstaller.apk*.

3.  Por motivos de seguridad, Android suele tener desactivado el permiso
    para instalar aplicaciones ajenas a Play Store (*Aplicaciones de
    origen desconocido*). En caso de tener esta opción habilitada, se
    puede pasar directamente al paso 4.

En caso contrario, se le pedirá que active este permiso y se le
redirigirá a la ventana de ajustes donde aparece tal opción. También se
puede acceder directamente mediante los siguientes pasos:

Ajustes \> Ajustes avanzados \> Seguridad \> Aplicaciones de origen
desconocido.

NOTA: La navegación a través de los ajustes puede variar ligeramente en
función de la versión de Android y de la capa de personalización del
fabricante.Se recomienda volver a desactivar esta opción una vez
instalada la aplicación

4.  Volver a ejecutar el archivo *myBeerInstaller.apk* y finalizar la
    instalación.
    
    4.  ## Configuración de proyecto en Android Studio

En primer lugar debemos abrir el proyecto en Android Studio (File \>
Open...). A continuación, debemos construir el proyecto para su correcto
uso y para que todas las librerías e importaciones de API´s externas
funcionen correctamente (Build \> Make Project).

Una vez que el proceso anteriormente descrito finalice, nuestro proyecto
estará listo para continuar con su desarrollo.

3.  # Manual de usuario
    
    5.  ## Introducción

Este manual proporciona los detalles y requerimientos para el uso de la
aplicación móvil MyBeer. Los siguientes epígrafes proporcionan los
detalles y requerimientos necesarios para el uso correcto de la
aplicación móvil, con la finalidad de brindar al usuario una
herramienta que asegure un uso satisfactorio de la misma.

## Funcionalidad

La funcionalidad principal de la aplicación está formada por tres
módulos (Principal, Cervecerías y Cervezas), que son accesibles a
través de un menú lateral. A dicho menú podrá accederse pulsando sobre
el botón superior izquierdo de la interfaz o bien deslizando el dedo
desde la parte izquierda de la pantalla hacia la parte derecha.

Los distintos módulos y sus funcionalidades será explicados en el
siguiente epígrafe (3.3 Módulos).

7.  ## Módulos
    
    1.  ### Principal

El módulo principal consiste en un mapa que nos mostrará nuestra
geolocalización, así como las cervecerías (en caso de tener alguna)
situadas en sus respectivas localizaciones.

<img src='media/image8.png' width='20%' height='20%'><img src='media/image9.png' width='20%' height='20%'>

Figura 3.3.1.1 Figura 3.3.1.2

Las cervecerías pueden mostrarse de diferentes colores según su estado.
En primer lugar, las cervecerías que se encuentran en color amarillo son
las que el usuario ha calificado como positivas (“Me gusta”), por otro
lado aquellas que se encuentran de color rojo son aquellas calificadas
de forma negativa (“No me gusta”), por último, podemos encontrarnos
cervecerías en color gris, lo que significa que el usuario la ha
introducido como “pendiente”, es decir, aún no las ha visitado (Figura
3.3.1.1).

Cabe destacar que al pulsar sobre una cervecería, aparecerá el nombre
que el usuario eligió para ella en el momento de añadirlas.

Y por último, en la parte inferior derecha podemos observar un botón
flotante, el cual si pulsamos sobre él, nos redirigirá de forma
automática a nuestra posición en el mapa (Figura 3.3.1.2).

### Cervecerías

El módulo ‘cervecerías’ tiene dos funcionalidades principales: añadir
una nueva cervecería y listar las cervecerías que el usuario ha
guardado.

La primera funcionalidad consiste en un formulario en el que tendremos
que introducir la dirección de la cervecería (una vez seleccionada
podremos visualizarla en el mapa), el nombre que el usuario quiere darle
a dicha cervecería y el estado; recordemos que estos estados son: “Me
gusta”, “No me gusta” y “Pendiente”.

Una vez hayamos completado el formulario podremos añadir la cervecería
pulsando sobre el botón situado en la parte inferior (Figura 3.3.2.1).

NOTA: todos los campos deben estar completados, de lo contrario nos
aparecerá un mensaje de advertencia indicándonos el error y no podremos
añadir la cervecería cuando pulsemos el botón.

La otra funcionalidad principal de este módulo consiste en una lista con
las diferentes cervecerías que el usuario ha ido añadiendo (Figura
3.3.2.2). En cada una de las cervecerías listadas podemos apreciar que
aparece indicado su nombre y el icono que refleja el estado de la
cervecería, siguiendo el mismo patrón que se ha comentado en el módulo
anterior (3.3.1. Principal).

Además se podrá eliminar una cervecería de la lista pulsando en la ‘X’
que aparece a su derecha.

Por último, al pulsar sobre cada una de las cervecerías listadas nos
redirigirá a una vista donde podremos apreciar la información de la
cervecería con más detalle (Figura 3.3.2.3).

<img src='media/image1.png' width='20%' height='20%'><img src='media/image6.png' width='20%' height='20%'><img src='media/image2.png' width='20%' height='20%'>

Figura 3.3.2.1 Figura 3.3.2.2 Figura 3.3.2.3

### Cervezas

El módulo ‘cervezas’ al igual que el módulo cervecerías está constituido
de dos funcionalidades principales: añadir una nueva cerveza y listas
las cervezas guardadas por el usuario.

En la funcionalidad añadir tendremos un formulario muy similar al
utilizado en añadir cervecería, en el que tendremos que completar todos
los campos mostrados y proceder posteriormente a añadir una nueva
cerveza (Figura 3.3.3.1). Al igual que en el módulo anterior, es
necesario que todos los campos del formulario estén completados para
poder añadir la cerveza.

La funcionalidad ‘listar’ consiste en mostrar, de forma idéntica a
‘listar cervecerías’, el conjunto de cervezas guardadas por el usuario
(Figura 3.3.3.2). Podremos pulsar sobre cada una de las cervezas, lo que
nos mostrará una interfaz más detallada con la información de dicha
cerveza (Figura 3.3.3.3).

Además, aquí también podremos eliminar una cerveza al igual que en el
anterior módulo: pulsando sobre la ‘X’ a la derecha en la lista.

<img src='media/image4.png' width='20%' height='20%'><img src='media/image7.png' width='20%' height='20%'><img src='media/image5.png' width='20%' height='20%'>

Figura 3.3.3.1 Figura 3.3.3.2 Figura 3.3.3.3

4.  # Arquitectura
    
    8.  ## Introducción

En el siguiente capítulo se detallarán las diferentes capas de las que
consta la aplicación, así como la organización interna de la misma, con
el fin de que la aplicación sea mantenible y fácil de comprender para
posibles desarrollos de mejora o ampliación en el futuro.

## Diseño

La aplicación sigue una arquitectura multicapa; cada una de dichas capas
sigue una finalidad de abstracción bien diferenciadas, aportando así
completa independencia unas de otras. A continuación, detallaremos cada
una de las capas:

  - > *Capa de presentación*: representada por el paquete *activities*,
    > es la encargada de renderizar las vistas para que el usuario
    > obtenga el resultado esperado en todo momento. Esta capa, aunque
    > no se encarga de la lógica de la aplicación, si realiza pequeñas
    > validaciones que están estrechamente ligadas al formato de entrada
    > (por ejemplo para evitar que los datos lleguen con campos vacíos),
    > y así, de esta forma, garantizar que la experiencia de usuario sea
    > más satisfactoria.

  - > *Capa de negocio*: formada por los servicios de la aplicación, se
    > encuentra ubicada en el paquete model. En esta capa tiene lugar
    > toda la lógica de la aplicación, encargada de que se cumplan las
    > reglas de negocio necesarias para el correcto funcionamiento de la
    > misma. Existe un servicio de aplicación por cada módulo
    > (Principal, Cervecería y Cerveza).

  - > *Capa de integración*: capa encargada de comunicarse con el
    > dispositivo móvil para la lectura y escritura de los datos
    > necesarios para que la aplicación se desarrolle correctamente. Se
    > encuentra en el paquete *wrapper* y está formada por una única
    > clase llamada *FileManager* que se encarga de implementar los
    > métodos de todos los módulos.

<!-- end list -->

5.  # Consideraciones técnicas
    
    10. ## Introducción

En el siguiente capítulo se detallarán los aspectos técnicos llevados a
cabo a lo largo del desarrollo de la aplicación tratando así de plasmar
de forma clara y concisa que la declaración de requisitos previos al
desarrollo del proyecto han sido implementados satisfactoriamente.

## General

Como parte generalizada de la implementación de los requisitos podemos
destacar la presencia en toda la aplicación de los siguientes aspectos:

  - > *Visualización apropiada en diferentes configuraciones de
    > pantalla*: se ha cuidado el aspecto visual de la de aplicación
    > incidiendo especialmente en la visualización vertical de la misma,
    > además se ha probado en múltiples plataformas con diferentes
    > configuraciones de pantalla con el fin de que la aplicación tenga
    > un uso óptimo y satisfactorio en el mayor número de dispositivos
    > posibles.

  - > *Almacenamiento permanente de la información*: toda el desarrollo
    > relacionado con el almacenamiento de la información de la
    > aplicación (cervecerías y cervezas) se ha concebido de forma que
    > dicha información quede registrada de forma permanente en el
    > dispositivo del usuario. De esta forma logramos que el intercambio
    > de información sea más rápido, mejorando así, la experiencia del
    > usuario.
    
    12. ## Módulos
        
        4.  ### Principal

En el módulo principal se ha implementado el requisito: *uso de al menos
un servicio externo a la aplicación*. Se ha utilizado la API de mapas
MapBox que nos ha permitido implementar la renderización del mapa que
muestra la geolocalización del dispositivo del usuario así como
visualizar el posicionamiento de las diferentes cervecerías que el
usuario haya querido guardar.

Además de las características comentadas anteriormente este servicio
externo nos permite navegar con total libertad por todo el mapa
renderizado así como hacer búsquedas completas o parciales de
localizaciones en cualquier parte de dicho mapa.

### Cervecerías y cervezas

En ambos módulos se han implementado los siguientes requisitos:

  - *Un mínimo de dos formularios diferentes*: a la hora de registrar
    una nueva cervecería o cerveza el usuario cuenta con dos formularios
    diferenciados (cada uno accesible desde su parte correspondiente del
    módulo) en el que se ven reflejados los distintos campos necesarios
    para completar la información de registro de ambos módulos.

  - *Una parte desarrollada con tecnologías web, con interacción con la
    parte nativa*: al igual que con el requisito anterior, cada módulo
    consta de una página web que consiste en mostrar la información
    relacionada con la cervecería o cerveza seleccionada desde la lista
    de la interfaz anterior, pudiendo interaccionar con la parte nativa
    pulsando el botón *back* de la parte superior izquierda de la
    pantalla que nos permite retornar a las listas anteriormente
    mostradas.

<!-- end list -->

6.  # Desarrollo
    
    13. ## Introducción

En el siguiente capítulo se comentará cada una de las aportaciones que
han hecho los componentes del grupo a lo largo de la implementación del
proyecto así como las herramientas de desarrollo empleadas y las
conclusiones finales que la realización de este proyecto les haya podido
generar a los desarrolladores del proyecto.

## Aportaciones

Ambos miembros del equipo han hecho aportaciones similares en el
desarrollo de la aplicación. Sí cabe destacar, que al principio del
desarrollo se acordó entre los dos compañeros que uno se encargaría del
módulo cervezas y el otro del módulo cervecerías, en lo que concluyó que
Carlos desarrollara el primero y Víctor se hiciera cargo de la
implementación del segundo. Con respecto al módulo principal y el
desarrollo de la capa de integración, ambos miembros la han implementado
a la par.

## Metodología

La metodología seguida por los integrantes del grupo se ha basado en la
comunicación diaria en la que cada compañero comentaba los avances
realizados desde la última reunión o comunicación. En dichas
comunicaciones cada componente le comentaba al otro la idea que tenía
sobre la próxima iteración que quería implementar.

En cuanto a la herramienta de control de versiones se ha usado GitHub.
Los componentes del grupo acordaron realizar una rama *develop* a partir
de la rama *master* en la que se subiría las versiones estables de la
aplicación, así mismo, se han realizado ramas principales a partir de la
rama develop en la que se han ido implementando por separado los tres
módulos de la aplicación (Principal, Cervecerías y Cervezas), desde la
que los desarrolladores han ido creando subramas para implementar las
diferentes iteraciones que han sido necesarias para el desarrollo de la
aplicación.

Por último, una vez que el desarrollo ha finalizado y la aplicación se
ha encontrado en su versión más estable, el proyecto ha sido subido a la
rama *master* desde la que se ha preparado la entrega del proyecto.

## Conclusiones

Ambos miembros del equipo estamos de acuerdo en que la realización del
proyecto ha sido satisfactoria y que la experiencia ha servido para
incrementar nuestros conocimientos sobre el desarrollo de aplicaciones
nativas Android.

Pese a los errores que han ido surgiendo en la realización de la
aplicación, los cuales nos han servido para mejorar nuestra perspectiva
y recursos previos al surgimiento de los mismos, podemos dar por
concluida la aplicación habiendo satisfecho los requisitos previos a la
puesta en marcha del proyecto.

# Bibliografía

  - Documentación:
    
      - Android Developers Docs:
        [<span class="underline">https://developer.android.com/docs?hl=es-419</span>](https://developer.android.com/docs?hl=es-419)
    
      - Desarrollador Android:
        [<span class="underline">https://desarrollador-android.com/</span>](https://desarrollador-android.com/)
    
      - Material Design
        [<span class="underline">https://material.io/</span>](https://material.io/)
    
      - Mapbox Docs:
        [<span class="underline">https://docs.mapbox.com/</span>](https://docs.mapbox.com/)
    
      - GitHub:
        [<span class="underline">https://github.com/</span>](https://github.com/)

  - Recursos:
    
      - Icons8:
        [<span class="underline">https://iconos8.es/</span>](https://iconos8.es/)
    
      - Material Palette:
        [<span class="underline">https://www.materialpalette.com/</span>](https://www.materialpalette.com/)
