DOCKER_IMAGE := karcagtamas/ceg-backend

build-win:
	gradlew.bat clean
	gradlew.bat installShadowDist
	docker build -t $(DOCKER_IMAGE) .

build-lin:
	./gradlew clean
	./gradlew installShadowDist	
	docker build -t $(DOCKER_IMAGE) .

gradle-build-win:
	gradlew.bat clean
	gradlew.bat installShadowDist

gradle-build-lin:
	./gradlew clean
	./gradlew installShadowDist	