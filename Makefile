network := openinfonetwork

.PHONY: build-admin run-admin stop-admin

setup:
	@if [ "$(shell docker network ls --format \"{{.Name}}\" | grep openinfonetwork)" == "" ]; then\
		docker network create "$(network)";\
		echo "Network created";\
	else\
		echo "Network already exists";\
	fi

# ADMIN PANEL
build-admin:
	$(MAKE) -C openinfo-admin build

run-admin:
	$(MAKE) -C openinfo-admin run

stop-admin:
	$(MAKE) -C openinfo-admin stop

# API JAVA (backend)
build-api:
	$(MAKE) -C ApiUsuarios build

run-api:
	$(MAKE) -C ApiUsuarios run

stop-api:
	$(MAKE) -C ApiUsuarios stop
	
# FRONT END
build-front:
	$(MAKE) -C ApiUsuarios build

run-front:
	$(MAKE) -C ApiUsuarios run

stop-front:
	$(MAKE) -C ApiUsuarios stop

# DATABASE
run-db:
	@if [ "$(shell docker ps --format \"{{.Names}}\" | grep openinfodb)" == "" ]; then\
		if [ ! "$(shell docker ps -a --format \"{{.Names}}\" | grep openinfodb)" == "" ]; then\
			echo "Deleting containner before running";\
			docker rm openinfodb;\
		fi;\
		docker run -d --name=openinfodb -e POSTGRES_USER=postgres -e POSTGRES_DB=DbOpenInfo -e POSTGRES_PASSWORD=12345 -v postgresData:/var/lib/postgresql/data --restart unless-stopped --net "$(network)" postgres:10;\
	else\
		echo "Database is already running";\
	fi

stop-db:
	docker stop openinfodb