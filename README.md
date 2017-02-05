#Yomoka projec
Yomoka is a sandbox where to mess with technologies I want to learn. Maybe I'll got something by all this effort.

### DOCKER notes
1.Running on Windows 7 64 docker toolbox. Go to root of Yomoka/YoOffer project.
docker build -t yowildfly -f /Users/myUser/myWorkspace/Yomoka/YoOffer/src/test/resources/DOCKER/wildfly/Dockerfile --build-arg webapp=target/YoOffer.war /Users/myUser/myWorkspace/Yomoka/YoOffer

Here I'm getting some hard times. While the dockerfile runs fine by docker cli, building the image by maven, docker plugin does not find the artifact.
