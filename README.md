Bonjour, suivez ces étapes pour utiliser le projet :

Pré-requis :
- Java SDK
- Maven
- Npm

1) Cloner le repo
2) Ouvrir le back-end dans votre IDE
3) Exécuter la commande "mvn clean test" dans le terminal
4) Ouvrir le fichier "index.html" présent dans "back/target/site/jacoco" pour voir la couverture
5) Ouvrir le front-end dans votre IDE
6) Exécuter la commande "npm run test" dans le terminal pour voir la couverture des tests unitaires
7) Exécuter la commande "npm run e2e" dans le terminal et choisissez le navigateur de votre choix pour exécuter les tests.
8) Dans la liste des "Specs" cliquer sur "all.cy.ts". Cela va lancer tous les scripts de tests les uns après les autres.
9) Vous pouvez également exécuter la commande "npm run e2e:coverage" dans le terminal pour générer le rapport de couverture des tests end-to-end (vous trouverez le rapport ici "front/coverage/lcov-report/index.html")
