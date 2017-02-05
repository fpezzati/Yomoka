#Yomoka projec
Yomoka is a sandbox where to mess with technologies I want to learn. Maybe I'll got something by all this effort.

### DOCKER notes
1.Running on Windows 7 64 docker toolbox. Go to root of Yomoka project.
docker build -t yowildfly -f /Users/myUser/myWorkspace/Yomoka/YoOffer/src/test/resources/DOCKER/wildfly/Dockerfile --build-arg webapp=/Users/myUser/myWorkspace/Yomoka/YoOffer/target/YoOffer.war /Users/myUser/myWorkspace/Yomoka

Here I'm getting some hard times: while building the image docker does not find file indicated by *webapp* argument. I think it is because docker does not follow simlinks. I will try on linux and see if it works.
