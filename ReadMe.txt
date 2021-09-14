https://www.youtube.com/watch?v=Fq85GschdLw&list=PLSVW22jAG8pDY3lXXEv1hKVIAlnJ9nDN_&index=3
Need to change git repo url and id/pwd in Config server properties
Also required Microservices-config-server repo used by Config Server

4 Microservices
	1. Product - random port- use MongoDB - takes care of product configuration
	2. Inventory - 8083 - takes care of inventory - takes care of checking stock and respond back to order service - use MYSQL
	3. Order - random port - synchronous comunication to inventory, use resilience4j library- use MYSQL - takes care of Placing processing the order
		Usre spring-cloud-starter-feign dependency and Feign client to call Inventory Service
	4. Notification - asynchronous communication using event drivern architecture,  as soon as order is placed, order service fires event and notification service would send notification.

API Gateway service - 8081 - all requests routed to corresponding service
	Client makes call to api gateway and then it will route the request to appropriate service.
	Also takes care of authentcating the user and other tasks like monitoring and rate limiting.
	
	- Dependencies - spring-cloud-starter-gateway and spring-cloud-starter-netflix-eureka-client
	- Add properties in application.properties file and start the service
	- access URL http://localhost:<api-gateway port>/api/product, it will redirect the request to product service.
	
Secure microservices using KEY CLOAK authorization server
	KEY CLOAK - is Identity and access management server that handles authentication and authorization specific logic. Provides Single Sign on and social login also.
	- Download keyclock and run it. Go to bin dir and run command  "standalone.bat -Djboss.http.port=8180" 
	- http://localhost:8180 -> Create admin credentials. admin/admin. http://localhost:8180/auth login using admin credentials.
	- Add Realm
	- Register gateway as client in KeyCloak - Create Client
	- Create test user - email id testuser@gmail.com, set pwd -  test
	- Add dependency in api gateway - spring-cloud-starter-security, spring-boot-starter-oauth2-client
	- Add property in properties file.
	- Add SecurityConfiguration class in apigateway
	- Define product service as resource server.
	- Add product sevice properties
	- Access http://localhost:8081/api/product - it will redirect to Key Cloak login page.
	- id/pwd - testuser/test
	- 
		
Eureka - Discovery server - http://localhost:8761/
	Product/order service registers itself to discovery server and also reads this registry and cache its copy.
	
Centralized config server which is backed by git server - used to store configurations and properties of all microservices
	http://localhost:8888/order-service/default
	http://localhost:8888/inventory-service/default
	http://localhost:8888/product-service/default
	
	allows to dynamically load the configuration without restarting the service.
	spring-cloud-config-server
	- Create config server project - dependencies: config server, actuator. Add @EnableConfigServer annotation
	- Create GIT repo where to store configuration and add confgurations
	- Point config server to git repo
	- Url to access properties- http://localhost:8888/order-service/default . For different profile create folder and add configurtaion. in URL use folder name instead of default.
	- change Order service - add dependency spring-cloud-config-client and bootstrap,actuator. 
		Add bootstrap.propertes file to add config server uri and management.endpoints.web.exposure.include=* to refreshscope to work.
	
	Refresh Configuration
		- @RefreshScope at controller level
		- move all values from application.properties file to bootstrap.properties
		- change test.name property in product service configuration on github
		- Call /actuator/refresh POST request to read latest configuration
	
	Automatic refresh of configuration using Spring Bus
		- Triggering /actuator/refresh not practical for every microservice 
		- Alternative to use message broker (like Rabbit MQ or KAFCA) that will broadcast the changes to all services that have subscribed to this message broker and then services will reload the configurations.
		- Dependency - spring-cloud-starter-bus-amqp
		- Install RabbitMQ and Erlang required by RabbitMQ
		- Configure RabbitMQ details in bootstrap.properties or order and product
		- restart both services
		- Call /actuator/busrefresh from any service and RabbitMQ will broadcast the changes and all other services will reload the configurations
		
		
	
Secret information like pwds - in VAULT - not implemented
	- VAULT software can be used to save secret keys like uri, passowrd etc.
	- Need to install and then add properties in it.
	- Change service to add dependency to spring-cloud-starter-vault-config
	- Add vault server details like uri, post,token etc in bootstrap.properties.file
	- restart the service

Circuit Breaker + Resilience4J - For Fault tolerance
	OrderService Calls Inventory Service
	
Event Driven Microservices:
		Spring Cloud Stream - provided rabbit mq and Kafca 
		Order service will communicate with Notification service asynchronously using Rabbit MQ.
		After successfully placing order send message to Rabbit MQ message broker that will distribute the message to all subscribers.
		
Notification Service - implemented as Spring Function provided by cloud stream.
	- spring-cloud-stream-binder-rabbit dependency in Order service and norification service. This is already added in order service as transitive dependency of ampq.
	- Define binders information in proeprties file(Output binder in order service and input binder in Notification service)
	- Send message from order service using StreamBridge
	- Define functional interface in Notification service to receive this message and process it.
	
		
Distributed tracing - 
	Spring Cloud Sleuth and Spring Cloud ZIPKIN(Provided dashboard to visulaize the request journey along with latency info)
	- It is microservice pattern which allows us to track request from one service to another by providing unique request id(called trace id) across the journey.
	- Add these 2 dependencies to api-gateway, order, inventory and notification microservices.
	- Also, add some properties to pass trace id to notification service that is async call.
	- Zipkin can be downloaded and started as per steps in https://zipkin.io/pages/quickstart
	- zipkin url http://localhost:9411 - to show the call trace.
	
	
Centralized logging using ELK(Elastic Search, Logstash and Kibana)
	Need to use logging lib like Log4J or Logback
	integrate with logstash
	
	ElasticSearch - Search Engine implemntaion in Java used to store logs
	Logstash - used to send logs to Elastic Search
	Kibana - provides dashboard to visualize logs
	
	Download and start all softwares
	



