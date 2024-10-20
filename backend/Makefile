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
	gradlew.bat build
	gradlew.bat installShadowDist

gradle-build-lin:
	./gradlew clean
	./gradlew build
	./gradlew installShadowDist

gradle-build-graph-model-win:
	gradlew.bat libs:graph-model:clean
	gradlew.bat libs:graph-model:build

gradle-build-graph-model-lin:
	./gradlew libs:graph-model:clean
	./gradlew libs:graph-model:build