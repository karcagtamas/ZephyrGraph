build-backend-win:
	cd backend && make build-win

build-backend-lin:
	cd backend && make build-lin

convert-docs:
	cd docs && make convert