SHELL=/bin/bash
GITHUB_RUN_ID ?=123

cypress-open-docker:
	npm run cypress:docker
cypress-open:
	npm run cypress:open:electron
cypress-electron:
	docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-electron e2e-electron
cypress-chrome:
	docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-chrome e2e-chrome
cypress-firefox:
	docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-firefox e2e-firefox
cypress-firefox-full: build
	docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-firefox-full e2e-firefox-full
cypress-edge:
	docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-edge e2e-edge
build:
	yarn
