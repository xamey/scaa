### Dependencies

In order to run SCAA application, you must install those dependencies on the machine that
will run the application :
- JDK 8
- Maven 3
- OpenJFX 

On any Linux distribution, run this command in any terminal ```sudo apt install openjdk-8-jdk maven openjfx```

### Running

To run SCAA, just open a terminal in the current directory and type the following command :
```mvn spring-boot:run```

As the application isn't fully functional, a component platform is used and available at
the root of the project, which is named ```platform.json```. Components and services
associated are loaded from this file. 

Two UI will appear at the start of the application, with testing cases :
- the user UI appears first, with a sample of components which are defined in the platform.json file
- the expert UI appears after you close the expert UI, with a sample of components defined in the main class

### Usage

Two UI will be opened once the application started :
- User UI: this UI aims to be used by anyone that want to connect a component to others 
available, and to present the application constructed.
- Expert UI: this UI aims to be used by an expert who has to resolve conflicts
that happens during the application construction
