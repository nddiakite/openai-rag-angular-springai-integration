# OPENAI BACKEND SERVICE
Backend service for OPENAI application.

Important Functionalities:

1. User Authentication
2. User Authorization
3. Interact with OPENAI Assistant API with custom Assistant
4. Interact with OPENAI Assistant API with RAG method

# IMPORTANT COMMANDS

## BUILD_WITH_TESTS
mvn clean install

## BUILD_NO_TESTS
mvn clean install -DskipTests

## RUN - Base
mvn spring-boot:run -Dspring-boot.run.profiles=local

## DEBUG 
#### (Dans le même Intellij IDEA, lancer un 1er onglet en mode debut et lancer une 2e onglet pour se connecter au port de debut)
mvn spring-boot:run -Dspring-boot.run.profiles=local "-Dspring-boot.run.jvmArguments=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
#### Infos 2e onglet
Name : LOCAL_DEBUG
Host : localhost / 79.137.38.153
Port : 5005
Commande line : -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005

## RUN - Lancement avec fichier externe
mvn spring-boot:run "-Dspring.config.location=:\DTeK CONSULTING\dtek-lab - Documents\lab-studio\training\chatgpt-challenge\chatgpt-angular-integration\openai-assistant-with-angular-spring\application-dev.properties"

## ACCESS BDD H2 CONSOLE
URL : http://localhost:8082/h2-console

## USEFUL INFOS FOR ASSISTANT

Assistants API : https://platform.openai.com/docs/assistants/overview
How Assistants work :https://platform.openai.com/docs/assistants/how-it-works

## Thread infos
https://platform.openai.com/playground?assistant=asst_MgusDCcdCLNac65jBICrR33c&mode=assistant&thread=thread_D1MgicxwEbONe9YKonZxhw0s

https://platform.openai.com/playground?assistant=asst_MgusDCcdCLNac65jBICrR33c&mode=assistant&thread=thread_kDXJVjuBbpFrMuZc9zpSHgPY


