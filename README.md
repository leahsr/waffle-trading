# Waffle Trading

Dieses Projekt stellt eine Trading API mit integrierten Gatling Performance-Tests bereit. Es kann entweder über IntelliJ oder vollständig per Docker ausgeführt werden.

---
### Start mit IntelliJ

**Voraussetzungen**
- IntelliJ IDEA mit installiertem Scala Plugin
- Docker & Docker Compose

**Schritte**
1. Datenbank starten  
    Im Hauptverzeichnis des Projekts folgenden Befehl ausführen:  
    `docker compose up`
    Dies startet die Datenbank.
2. API starten  
    In IntelliJ in die `Boot` Klasse starten, um die API zu aktivieren.
3. Gatling-Tests ausführen  
    Die Tests befinden sich unter:  
    `src/it/scala/simulations`

    In IntelliJ unten in die sbt console wechseln und einen der folgenden Befehle ausführen:
    - Alle Simulationen starten:  
        `GatlingIt / test`
        
    - Eine bestimmte Simulation starten:  
        `GatlingIt / testOnly *SimulationName*`
4. Testberichte anzeigen  
    Die Reports werden nach dem Lauf unter folgendem Pfad abgelegt:  
    `target/gatling/simulation-name/index.html`
    Nach erfolgreichem Test erscheint im Terminal zudem ein Link zur index.html, der direkt geöffnet werden kann.
---
### Start Docker only

**Schritte**
1. In das it-Verzeichnis wechseln:  
    `cd it`
2. Alles starten mit:  
    `docker compose up --build -d`
    Dadurch werden folgende Komponenten automatisch gestartet:
    - Die Datenbank
    - Die API
    - Alle Gatling-Simulationen werden direkt einmal ausgeführt
3. Berichte anzeigen  
    Nach Abschluss befinden sich alle Gatling-Reports unter:  
    `it/gatling-it  `
    Hinweis: Der gesamte Ausführungsvorgang kann bis zu 30 Minuten in Anspruch nehmen, bis alle Tests abgeschlossen sind.
---

Projektstruktur (relevante Teile):

`/src/main/scala/de/thws/Boot.scala` -> Startpunkt der API  
`/src/it/scala/simulations` -> Gatling-Simulationen  
`/target/gatling/` -> Gatling-Reports bei IntelliJ-Ausführung  
`/it/gatling-it/` -> Gatling-Reports bei Docker-only-Ausführung  
`/docker-compose.yml` -> Compose-Datei für IntelliJ-Workflow  
`/it/docker-compose.yml` -> Compose-Datei für Docker-Workflow
