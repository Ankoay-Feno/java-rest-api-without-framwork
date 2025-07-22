# Système de Gestion de Tickets Java

##  Comment lancer le projet

### Option 1: Avec Java (local)

#### Prérequis
- Java 17 ou supérieur installé
- Tous les fichiers `.java` dans le dossier `src/`

#### Étapes
```bash
# 1. Compiler le code
javac src/*.java

# 2. Lancer l'application
java -cp src Main

# 3. Vérifier que le serveur démarre
# Vous devriez voir: " Serveur lancé sur http://localhost:8080"
```

### Option 2: Avec Docker

#### Dockerfile
```dockerfile
FROM openjdk:17-slim
WORKDIR /app
COPY src/ ./src
RUN javac src/*.java
CMD ["java", "-cp", "src", "Main"]
EXPOSE 8080
```

#### Étapes
```bash
# 1. Construire l'image Docker
docker build -t ticket-system .

# 2. Lancer le conteneur
docker run -p 8080:8080 ticket-system

# 3. Le serveur sera accessible sur http://localhost:8080
```

##  Comment tester l'API

### Test 1: Vérifier le statut initial
```bash
curl http://localhost:8080/status
# Réponse attendue: {"size": 0, "isEmpty": true}
```

### Test 2: Ajouter un ticket
```bash
curl -X POST http://localhost:8080/add -d "Jean Dupont"
# Réponse: JSON du ticket créé
```

### Test 3: Vérifier le statut après ajout
```bash
curl http://localhost:8080/status
# Réponse attendue: {"size": 1, "isEmpty": false}
```

### Test 4: Consulter le prochain ticket (sans le supprimer)
```bash
curl http://localhost:8080/peek
# Réponse: JSON du prochain ticket
```

### Test 5: Traiter le prochain ticket (le supprimer de la file)
```bash
curl http://localhost:8080/next
# Réponse: JSON du ticket traité
```

### Test 6: Vérifier que la file est vide
```bash
curl http://localhost:8080/status
# Réponse attendue: {"size": 0, "isEmpty": true}
```

### Test 7: Tester un endpoint inexistant
```bash
curl http://localhost:8080/invalid
# Réponse attendue: {"message":"Endpoint inconnu"}
```

## 📋 Séquence de test complète

```bash
# Script de test complet
echo "=== Test du système de tickets ==="

echo "1. Statut initial:"
curl -s http://localhost:8080/status
echo

echo "2. Ajout de tickets:"
curl -s -X POST http://localhost:8080/add -d "Alice"
echo
curl -s -X POST http://localhost:8080/add -d "Bob"
echo
curl -s -X POST http://localhost:8080/add -d "Charlie"
echo

echo "3. Statut après ajouts:"
curl -s http://localhost:8080/status
echo

echo "4. Consultation du prochain:"
curl -s http://localhost:8080/peek
echo

echo "5. Traitement d'un ticket:"
curl -s http://localhost:8080/next
echo

echo "6. Statut final:"
curl -s http://localhost:8080/status
echo
```

## 🔍 Points de contrôle

### Vérifications à faire:

1. **Le serveur démarre correctement** sur le port 8080
2. **L'endpoint /status** retourne la taille de la file
3. **L'endpoint /add** accepte les requêtes POST
4. **L'endpoint /peek** permet de consulter sans supprimer
5. **L'endpoint /next** traite et supprime les tickets
6. **La gestion d'erreurs** fonctionne pour les endpoints invalides

### Réponses JSON attendues:
- **Status** : `{"size": nombre, "isEmpty": booléen}`
- **Ticket** : Format JSON défini par la méthode `toJson()` de votre classe Ticket
- **Erreur** : `{"error": "message d'erreur"}` ou `{"message": "Endpoint inconnu"}`

##  Dépannage

### Problèmes courants:

**Port déjà utilisé:**
```bash
# Trouver le processus utilisant le port 8080
lsof -i :8080
# Tuer le processus si nécessaire
kill -9 <PID>
```

**Erreurs de compilation:**
- Vérifiez que tous les fichiers `.java` sont présents
- Assurez-vous d'avoir Java 17 ou supérieur

**Docker ne démarre pas:**
```bash
# Vérifier les logs du conteneur
docker logs <container_id>
```
