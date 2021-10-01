tag_name := openinfo-api
tag := latest
cont_name := openinfo-java-api
network := openinfonetwork

.PHONY: build run stop

build:
	docker build -t "$(tag_name):$(tag)" .

# docker run -d --name=$(cont_name) -p 9200:9200 "$(tag_name):$(tag)"
run:
	@if [ "$(shell docker ps --format \"{{.Names}}\" | grep $(cont_name))" == "" ]; then\
		if [ ! "$(shell docker ps -a --format \"{{.Names}}\" | grep $(cont_name))" == "" ]; then\
			echo "Deleting containner before running";\
			docker rm $(cont_name);\
		fi;\
		docker run -dit --name "$(cont_name)" -p 9200:9200 --net "$(network)" --restart unless-stopped "$(tag_name):$(tag)";\
	else\
		echo "Java API is already running";\
	fi

stop:
	docker stop $(cont_name)