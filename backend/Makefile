DOCKER_IMAGE := karcagtamas/ceg-backend

build-win:
	gradlew.bat build
	gradlew.bat installShadowDist
	docker build -t $(DOCKER_IMAGE) .

build-lin:
	gradlew build
	gradlew installShadowDist
	docker build -t $(DOCKER_IMAGE) .