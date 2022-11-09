# Assignment 1 - Online Chat

## Run Commands

Avviare per prima cosa il server eseguendo

```
mvn clean compile exec:java@TCPServer 
```

e dopodichè avviare uno o più client su altri terminali eseguendo

```
mvn clean compile exec:java@TCPClientGUI
```

Alternativamente è possibile generare i jar eseguendo

```
mvn clean package
```

successivamente è possibile trovare i jar all'interno della cartella target

## Generating Javadoc

Per generare la documentazione del progetto è necessario eseguire

```
mvn clean javadoc:javadoc
```

La documentazione completa sarà poi disponibile in target/site/apidocs/index.html

## Tasks: 

- [ ] far funzionare messaggistica in LAN  
- [x] al client deve esser chiesto il nome  
- [x] il server si deve tenere i nomi di tutti i client connessi (getClientName method della Connessione)  
- [x] il server salva in un log tutto cio' che succede
- [x] implementare broadcasting system  
- [x] connessione e disconnessione del client devono essere comunicati
- [x] far chiedere insieme al nome anche l'indirizzo ip al quale connettersi (forse anche la porta?)
- [x] fixare che /quit fa realmente chiudere il processo
- [ ] documentazione (da vedere divisione)  
- [x] GUI 
- [x] documentare un minimo il codice
- [ ] emoticon
