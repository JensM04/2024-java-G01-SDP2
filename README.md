# 2024-java-g01-24

## Opstellen

> Hier een praktische gids voor het opstellen van de environment

### 1. Kloon de repository op de gewenste locatie

```bash
git clone https://github.com/HoGentProjectenII/2024-java-g01-24.git
```

### 2. Open het project in eclipse

1. Navigeer naar het importeer venster: `File > Import > Existing Projects into Workspace`
2. Selecteer het gecloonde project bij `Select root directory`
3. Selecteer project en finish

### 3. Stel source in

1. Navigeer naar `Java Build Path`: `Rechter klik op project > Properties > Java Build Path`
2. Navigeer naar `Source`: `Selecteer source bovenaan`
3. Wijzig `Source`: `Selecteer SDPII/src > Klik op Edit > Folder name "src" > Finish`
4. Bevestig wijzigingen: `Klik op Apply and Close`

### 4. Voeg libraries toe aan het project

**Externe libraries**:
- JavaFX:
    - javafx.base
    - javafx.controls
    - javafx.graphics
    - javafx.fxml
- Mockito:
    - byte-buddy-1.14.8
    - byte-buddy-agent-1.14.8
    - mockito-core-5.5.0
    - mockito-junit-jupiter-5.5.0
    - objenesis-3.3
- Mail:
    - mail.jar (modulepath!)
    - activation.jar (classpath!)

**Stappen**:
1. Navigeer naar `Java Build Path`: `Rechter klik op project > Properties > Java Build Path`
2. Navigeer naar `Libraries`: `Selecteer libraries bovenaan`
3. Voeg libraries toe bij `Modulepath`:
    - Voeg Externe libraries toe die hierboven vermeld staan: Klik op `Add External JARs` \> Voeg alle JARs toe
    - Voeg JUNIT5 toe: Klik op `Add Library` > `JUNIT` > klik `Next` > selecteer `JUNIT5` > klik `Finish`
4. Klik op `Apply and Close`

### 5. Voeg module-info.java toe

1. Voeg bestand `module-info.java` toe aan de `src` map
2. In dit bestand voeg je de volgende tekst toe:
```java
open module SDPII { 
    //FX
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    
    // Unit tests
    requires org.junit.jupiter.api;
    requires org.mockito.junit.jupiter;
    requires org.mockito;
    requires org.junit.jupiter.params;
    
    
    //mail
    requires mail;
    
    //JPA
    requires jakarta.persistence;
    requires java.sql;
    requires java.instrument;
    
}
```

