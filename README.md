# Centro de Aplicaciones Vaadin: Tres En Raya, Ahorcado y Administrador de Tareas

## Descripción General

Este proyecto es una demostración de una aplicación web construida utilizando Vaadin y Spring Boot. Sirve como un centro de mando que proporciona acceso a tres mini-aplicaciones distintas: un juego de Tres En Raya (Tic-Tac-Toe), un juego del Ahorcado y un Administrador de Tareas. Este proyecto muestra cómo integrar diferentes funcionalidades dentro de una sola aplicación Vaadin y proporciona una comprensión básica del sistema de componentes de interfaz de usuario de Vaadin, el enrutamiento y la integración con Spring Boot.

## Tecnologías Utilizadas

*   **Vaadin:** Un framework Java para construir aplicaciones web modernas. Vaadin se encarga del renderizado de la interfaz de usuario en el navegador, lo que permite a los desarrolladores centrarse en escribir código Java.
*   **Spring Boot:** Un framework que simplifica el desarrollo de aplicaciones basadas en Spring independientes y listas para producción.
*   **Maven:** Una herramienta de automatización de construcción utilizada para la gestión de dependencias y la construcción del proyecto.

## Estructura del Proyecto

El proyecto sigue una estructura de directorio estándar de Maven:
## Estructura del Proyecto

El proyecto sigue una estructura de directorio estándar de Maven:

*   vaadin-app-hub/ (Raíz del proyecto)
    *   pom.xml (Configuración del proyecto Maven)
    *   src/
        *   main/
            *   java/
                *   com/example/demo/ (Paquete principal de la aplicación)
                    *   DemoApplication.java (Aplicación principal de Spring Boot)
                    *   model/
                        *   Task.java (Entidad de tarea)
                    *   service/
                        *   TaskService.java (Servicio de gestión de tareas)
                    *   views/
                        *   AhorcadoView.java (Interfaz de usuario del juego del Ahorcado)
                        *   HubView.java (Centro de aplicaciones / panel de control)
                        *   TaskView.java (Interfaz de usuario del administrador de tareas)
                        *   TresEnRaya.java (Interfaz de usuario del juego Tres En Raya)
            *   resources/ (Recursos de la aplicación)
                *   application.properties (Configuración de Spring Boot)
                *   META-INF/
                    *   resources/
                        *   themes/
                            *   mi-tema/ (Tema personalizado)
                                *   styles.css
                                *   components/
                                    *   (estilos específicos de los componentes)
        *   test/

## Archivos y Directorios Clave:
**Archivos y Directorios Clave:**

*   **`pom.xml`:** Define las dependencias del proyecto (Vaadin, Spring Boot), la configuración de construcción y los plugins. Este archivo es esencial para que Maven construya el proyecto correctamente.
*   **`src/main/java/com/example/demo/DemoApplication.java`:** Esta es la clase principal de la aplicación Spring Boot. Es responsable de iniciar el contexto de Spring y lanzar la aplicación web. Está anotada con `@SpringBootApplication`.
*   **`src/main/java/com/example/demo/model/Task.java`:** Define el modelo de datos `Task`, con propiedades como `id`, `description` y `completed`.
*   **`src/main/java/com/example/demo/service/TaskService.java`:** Proporciona la lógica de negocio para gestionar las tareas. Incluye métodos para agregar, recuperar y marcar las tareas como completadas. Es un `@Service` de Spring.
*   **`src/main/java/com/example/demo/views`:** Contiene las vistas de la interfaz de usuario de Vaadin:
    *   **`HubView.java`:** La página de inicio principal de la aplicación. Contiene enlaces a las otras aplicaciones. Utiliza la ruta `@Route("")` para que sea la página predeterminada.
    *   **`TaskView.java`:** La interfaz de usuario del Administrador de Tareas. Permite a los usuarios crear, ver y gestionar tareas. Utiliza la ruta `@Route("taskview")`.
    *   **`AhorcadoView.java`:** La interfaz de usuario del juego del Ahorcado. Utiliza la ruta `@Route("ahorcado")`.
    *   **`TresEnRaya.java`:** La interfaz de usuario del juego Tres En Raya. Utiliza la ruta `@Route("tresenraya")`.
*   **`src/main/resources/application.properties`:** Archivo de configuración de Spring Boot. Este archivo se utiliza para configurar varios aspectos de la aplicación, como el puerto del servidor y la configuración de la conexión de la base de datos (si corresponde).
*   **`src/main/resources/META-INF/resources/themes`**: Contiene los estilos CSS, los estilos específicos de los componentes y las variables de tema.

## Ejecutando la Aplicación

1.  **Requisitos Previos:** Asegúrate de tener lo siguiente instalado:
    *   Java Development Kit (JDK) - Se recomienda la versión 17 o superior.
    *   Maven - Asegúrate de que el comando `mvn` esté disponible en el PATH de tu sistema.
    *   Node.js y npm (Node Package Manager) - Vaadin los utiliza para construir las partes frontend de la aplicación.

2.  **Clona el Repositorio:** Clona este repositorio en tu máquina local.

3.  **Navega al Directorio del Proyecto:** Abre una terminal o símbolo del sistema y navega al directorio raíz del proyecto (el directorio que contiene el archivo `pom.xml`).

4.  **Construye la Aplicación:** Ejecuta el siguiente comando de Maven para descargar las dependencias, compilar el código y empaquetar la aplicación:

    ```bash
    mvn clean install
    ```

5.  **Ejecuta la Aplicación:** Utiliza el goal `spring-boot:run` de Maven para iniciar la aplicación:

    ```bash
    mvn spring-boot:run
    ```

6.  **Accede a la Aplicación:** Abre tu navegador web y ve a `http://localhost:8080`. Deberías ver la página principal, que proporciona enlaces a las otras mini-aplicaciones.

## Entendiendo el Código

Aquí tienes una breve explicación de cómo se utilizan Vaadin y Spring Boot en este proyecto:

*   **Vistas de Vaadin:** Cada una de las mini-aplicaciones (Hub, Administrador de Tareas, Ahorcado, Tres En Raya) se implementa como una vista de Vaadin. Una vista es una clase Java anotada con `@Route`. La anotación `@Route` especifica la ruta URL en la que se mostrará la vista.
*   **Componentes de Interfaz de Usuario:** Vaadin proporciona un rico conjunto de componentes de interfaz de usuario (botones, campos de texto, grids, etc.) que puedes utilizar para construir tu interfaz de usuario. Creas y configuras estos componentes en tu código Java y los añades a los layouts (por ejemplo, `VerticalLayout`, `HorizontalLayout`) para organizarlos en la pantalla.
*   **Manejo de Eventos:** Puedes añadir listeners de eventos a los componentes de Vaadin para responder a las acciones del usuario (por ejemplo, clics de botón). Los listeners de eventos se implementan como métodos Java que se ejecutan cuando se produce el evento.
*   **Enlace de Datos (Data Binding):** Vaadin proporciona características de enlace de datos que te permiten conectar los componentes de la interfaz de usuario a los modelos de datos. Esto facilita la visualización de los datos en la interfaz de usuario y la actualización de los datos cuando el usuario interactúa con los componentes.
*   **Integración con Spring Boot:** Vaadin se integra perfectamente con Spring Boot. Puedes utilizar las anotaciones de Spring (por ejemplo, `@Autowired`, `@Service`, `@Component`) para gestionar las dependencias y configurar tu aplicación.
*   **Tema Lumo:** Vaadin utiliza el tema Lumo por defecto, que proporciona un aspecto moderno y responsive. El proyecto utiliza variables de Lumo para dar estilo a los componentes.
* **Recarga en Caliente**: Cuando se modifican archivos CSS o HTML/JS, Vaadin recarga la UI en el navegador sin necesidad de recompilar el proyecto.

**Conceptos Clave:**

*   **Anotación `@Route`:** Mapea una clase Java a una URL específica.
*   **`VerticalLayout` y `HorizontalLayout`:** Componentes de layout para organizar los elementos de la interfaz de usuario.
*   **`Button`, `TextField`, `Grid`:** Ejemplos de componentes de interfaz de usuario de Vaadin.
*   **`addClickListener()`:** Se utiliza para manejar los clics de botón y otros eventos del usuario.
*   **`getStyle().set()`:** Aplica estilos CSS directamente desde Java.
*   **Variables de Tema Lumo:** Variables CSS predefinidas que proporcionan un aspecto coherente.
*   **`spring-boot:run`:** Un goal de Maven para iniciar la aplicación Spring Boot.

## Contribuyendo

Siéntete libre de contribuir a este proyecto enviando pull requests. Por favor, sigue estas directrices:

*   Escribe mensajes de commit claros y concisos.
*   Sigue el estilo de codificación del proyecto.
*   Prueba a fondo tus cambios.

## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).

## Más Información

*   **Documentación de Vaadin:** [https://vaadin.com/docs](https://vaadin.com/docs)
*   **Documentación de Spring Boot:** [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)

Este README proporciona una visión general completa del proyecto, incluyendo instrucciones sobre cómo ejecutar la aplicación y explicaciones de los conceptos clave implicados. Esto debería ser un gran recurso para tus amigos que están trabajando en el proyecto.
 Si algo no está bien explicado, simplemente dilo. ¡Disfruta!