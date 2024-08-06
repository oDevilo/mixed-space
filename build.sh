#! /bin/sh

# npm run build --prefix ./web-ui
cnpm run build --prefix ./web-ui

mv web-ui/dist/* server/src/main/resources/static

mvn clean package -Dmaven.test.skip=true -Dprod
