clean:
	rm -f dist/*

build:
	go build -o dist/client cmd/client/main.go
	go build -o dist/service cmd/service/main.go

.PHONY: clean build