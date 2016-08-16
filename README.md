# selecao-dbserver-enquete
Teste técnico para vaga de desenvolvimento na DBServer: sistema web para escolha do local onde funcionários de uma empresa irão almoçar. 

#Tecnologias e ferramentas utilizadas no sistema desenvolvido:
- Spring Boot;
- Spring MVC;
- Spring Data JPA;
- Spring JdbcTemplate;
- Spring Security;
- Thymeleaf;
- Maven;
- Junit;
- Mockito;
- Flyway;
- Bootstrap;
- JQuery UI;
- Java 8;
- Javascript;
- MySQL SGBD
- MySQl Workbench – Modelagem MER
- SQL
- IDE Spring Tool Suite

#Informações para uso

Para subir a aplicação será necessário o SGBD MySQL e alterar os seguintes dados de conexão no arquivo aplication.properties no caminho enquete\src\main\resources:

 - spring.datasource.url=jdbc:mysql://localhost/enquete?createDatabaseIfNotExist=true&useSSL=false
 - spring.datasource.username=root
 - spring.datasource.password=qwe123
