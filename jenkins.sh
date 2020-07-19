docker run \
  --rm \
  -u root \
  -p 8080:8080 \
  -v "$PWD":/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkinsci/blueocean