# Epic Energy Services Application Demo

## Indice

- [Introduzione](#Introduzione)
- [Tecnologie utilizzate](#Tecnologie-utilizzate)
- [Versione](#Versione)
- [Principali caratteristiche implementate](#Principali-caratteristiche-implementate)
- [Descrizione](#Descrizione)
- [Testing](#Testing)
- [Tecnologie Back-end e Front-end utilizzate](#Tecnologie-Back-end-e-Front-end-utilizzate)
- [Risorse esterne](#Risorse-esterne)
- [Licenza](#Licenza)

## Introduzione

Questa applicazione è una demo di un software gestionale destinato ad aziende di fornitura elettrica

### Tecnologie utilizzate: 

- Java + Spring Boot
- html5 (base)
- css (base)
- bootstrap (base)
- JUnit
- Eclipse
- PostgreSQL
- REST Client
- Maven
- Postman
- GitHub 

## Versione

Latest stable version: 1.0  

## Principali caratteristiche implementate

- Home page
- Pagina di gestione clienti
- Pagina di gestione fatture
- Navigabilità tramite pulsanti dinamici
- Scrittura su database PostgreSQL
- Lettura, inserimento, modifica e cancellazione di elementi per ogni categoria
- Chiamate rest
- Test Junit5
- Sicurezza con JWT

## Descrizione

Software gestionale per aziende di fornitura elettrica.
All'interno dell'app, oltre alle chiamate REST, con una piccola grafica base accessibile tramite browser 
è possibile accedere a due sezioni differenti: "Gestione clienti" e "Gestione Fatture".
in tutte le sezioni è possibile interagire con un database e fare le operazioni CRUD di base (lettura, scrittura, modifica e cancellazione).
nella sezione "Gestione clienti", inoltre, è possibile ordinare i clienti con i seguenti criteri:

- Ragione sociale
- Fatturato annuale
- Data di inserimeto
- Data di ultimo contatto

## Testing

Test effettuati con Junit5 creando apposite classi per controllare tutte le chiamate dei controller REST

## Tecnologie Back-end e Front-end utilizzate

FRONT-END: 
- Thymeleaf
- HTML5 
- CSS 
- Bootstrap

BACK-END:
- Back-end in Java + Spring Boot
- database PostgreSQL
- Authentication JWT


## Risorse esterne
IDE:  
- https://www.eclipse.org/

DATABASE:
- https://www.postgresql.org/

Postman:  
- https://www.getpostman.com/collections/b77459cac3950e588f6c

Testing: 
- Postman
- Swagger
- Junit5

Java:  
- https://spring.io/

## Licenza
MIT ©
