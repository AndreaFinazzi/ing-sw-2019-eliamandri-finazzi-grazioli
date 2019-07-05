Ingegneria del Software - progetto finale - 2019

Autori:
    Elia Mandri Antonino
    Finazzi Andrea
    Grazioli Enrico

Funzionalitá implementate:
    Regole Complete + CLI + GUI + Socket + RMI + 1 FA (Partite Multiple)

Avvio dell'applicazione:

L'applicazione é composta da un unico file .jar ambivalente. La configurazione di default prevede l'avvio in modalitá server, mentre é previsto l'utilizzo dei seguenti parametri per l'utilizzo delle modalitá client:
    - client : avvio in modalitá client con GUI e Socket di default.
        - rmi : modalitá con connessione rmi.
        - cli : modalitá con interfaccia testuale.

p.e.
    "java -jar JAR/adrenaline-efg.jar client rmi"

avvia l'applicazione con interfaccia grafica e connessione RMI.
La quasi totalitá dei parametri applicativi, di gioco e di interfaccia grafica é configurabile intervenendo sui file all'interno della cartella JAR/config.
L'arhivio jar contiene una configurazione di default ed é quindi utilizzabile in modo indipendente dai file di configurazione e dagli asset grfici personalizzati.