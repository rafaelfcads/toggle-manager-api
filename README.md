# Toggle Management API

Esta API tem por objetivo expor serviços para o gerenciamento em tempo real e dinâmico da disponibilidade de serviços em uma plataforma. Através dos serviços disponiveis, é possivel criar usuário de sistema, onde deverá ser atribuido o API name, sua respectiva versão e os seus toggles. O usuário de sistema pode ser criado com seus respectivos toggles, basta associar a ele. Após criado, será possivel realizar a sua consulta, em suas diversas formas, edição e remoção. Para criação, edição, remoção e consultas, com exceção de consulta pelo identificador único, a requisição deverá ser realizada com um usuário de sistema com perfil ADMIN. Para consulta pelo identificador único, o mesmo poderá ser realizado com perfil USER;
Através dos serviços disponiveis, será possivel ainda, criar toggles que poderão ser associados posteriormente aos usuários de sistemas desejado. Para criação de um toggle, deverá ser atribuido o seu name e seu valor(TRUE ou FALSE). Um toggle pode ser associada a uma ou mais APIs, um toggle com um respectivo nome, pode ser criado com valores distintos para diferentes APIs(Ex: Toggle Name: is ButtonRed, Valor=TRUE associado as APIs A, B e C, e Toggle Name: isButtonRed, Valor=FALSE para as APIs D e E). O toggle poderá ser consultado pela API, caso o mesmo esteja associado a ela. Para que uma determinada API tenha acesso aos seus toggles, a API deverá fornecer seus dados de autenticação, sendo seu nome e versão. Para criação, edição, remoção e consultas, com exceção de consulta por usuário de sistema, onde trará apenas os toggles referentes aquela API, deverá ser realizada com um usuário de sistema com perfil ADMIN, para consulta por usuário de sistema, o mesmo poderá ser realizado com perfil USER;
A Toggle Management API fornece o recurso de notificação, quando um toggle é alterado. Para a utilização do mesmo, a API consumidora deverá se subscrever ao tópico que representa tal toggle. 
Salientamos que uma API somente terá acesso aos serviços, caso a mesma tenha um usuário de sistema, para que possa se autenticar e consumir os serviços respeitando seu perfil de acesso.


## Como posso fazer uso da API Toggle Management API

Os serviços da API podem ser consumidos em [Toggle Management API](https://toggle-manager-api.herokuapp.com/api)

Para consumir Toggle Management API, você pode utilizar a API [Consumer Toggle Manager API](https://consumer-toggle-manager-api.herokuapp.com/)

API Toggle Management pode ser clonada em [GITHUB](https://github.com/rafaelfcads/toggle-manager-api.git)


## Features

* Criação, Edição, Remoção e Consultas para usuário de sistema
* Criação, Edição, Remoção e Consultas para toggles representativos quanto a disponibilidade da API
* Associação de um toggle para um ou mais APIs
* Associação de um toggle XPTO para um ou mais APIs com um de terminado valor e associar um Toggle com o mesmo nome(XPTO) porem com outro valor para outras APIs
* Uso de um toggle somente pelo API cujo o Toggle está associado,
* Acesso ao toggle via autenticação(nome e verão da API) e autorização,
* Notificações nas alterações dos toggles

## Tecnologias

* Java 8
* Spring Boot
* Spring Data Rest
* Spring Data MongDB
* Spring Security
* Spring Security OAuth2
* Spring Kafka
* Lombok
* Spring Test

## EndPoints

* Token

```
/api/oauth/token?grant_type=password&username=username&password=password
```
* Usuário

Todos os usuários

```
GET
/api/users?access_token=token
```

* Usuário por identificador único

```
GET
/api/users/userId?access_token=token
```

* Usuário por nome

```
GET
/api/users/search/findByName?name=name&access_token=token
```

* Criação de usuário

```
POST
/api/users?access_token=token

body : {
	userName: userName,
	password: password,
	version: version,
	roles: [],
	toggles: []
}
```

* Alteração de usuário

```
PUT
/api/users/userId?access_token=token

body : {
	    id: id,
	    userName: userName,
	    password: password,
	    version: version,
	    roles: [],
	    toggles: []
	}
```

* Deleção de usuário

```
DELETE
/api/users/userId?access_token=token
```

* Toggle

Todos os toggles

```
GET
/api/toggles?access_token=token
```

* Toggle por identificador único

```
GET
/api/toggles/toggleId?access_token=token
```

* Toggle por nome

```
GET
/api/toggles/search/findByName?name=name&access_token=token
```

* Criação de toggle

```
POST
/api/toggles?access_token=token

body : {
	name: name,
	value: value
}
```

* Alteração de toggle

```
PUT
/api/toggles/toggleId?access_token=token

body : {
	    id: id,
	    name: name,
	    value: value
	}
```

* Deleção de toggle

```
DELETE
/api/toggles/toggleId?access_token=token
```

## API Toggle Management em uso

> Utilize a extensão do Chrome POSTMAN para a realização das requisições

Os serviços da API podem ser consumidos em [Toggle Management API](https://toggle-manager-api.herokuapp.com/api)

Para consumir Toggle Management API, você pode utilizar a API [Consumer Toggle Manager API](https://consumer-toggle-manager-api.herokuapp.com/)

###### Para fins de teste, você poderá usar o usuário de sistema "admin", password "admin", version v1.0.0 com perfil "ADMIN"

```
	{
	    "id": "59ad70d495f88f0004e23619",
	    "userName": "admin",
	    "password": "admin",
	    "version": "v.1.0.0",
	    "roles": [
	        "ROLE_ADMIN"
	    ]
	}
```

###### Criando um usuário de sistema para uma nova API
 
1. Com o usuário `admin`, você deve obter um token de acesso. 

	* Configure a extensão POSTMAN para o verbo POST, adicione a url descrita abaixo e na opção Headers, adicione a Key:Authorization e Value:Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0
 
	* Faça a seguinte requisição usando o verbo POST
 
```		https://toggle-manager-api.herokuapp.com/api/oauth/token?grant_type=password&username=admin&password=admin
 
		Response body: 
				{
					"access_token": "7b66685f-fae2-4769-b242-67b803868f63",
					"token_type": "bearer",
				    "expires_in": 863999,
				    "scope": "read write trust"
				}
 ```
* Com o token em mãos, você terá o tempo do `expires_in` para fazer a utilização do `access_token: 7b66685f-fae2-4769-b242-67b803868f63`
  		
  
2. Com o token do usuário `admin` em mãos e em tempo válido, podemos criar então um novo usuário de sistema, que represente nossa API consumidora 
 
	* Configure a extensão POSTMAN para o verbo POST e adicione a url descrita abaixo
 	
	* Faça a seguinte requisição usando o verbo POST:
  	
```		

	https://toggle-manager-api.herokuapp.com/api/users?access_token=7b66685f-fae2-4769-b242-67b803868f63
  	
	Request body:  
			{
                "userName": "apiA",
                "password": "secret",
                "version": "v.1.0.0",
                "roles": [
                    "ROLE_USER"
                ]
 			}
 	Response body:
 			{
			    "id": "59ade55b1be2e40004f626b4",
			    "userName": "apiA",
			    "password": "secret",
			    "version": "v.1.0.0",
			    "roles": [
			        "ROLE_USER"
			    ],
			    "toggles": null
			}
 			
```

* Nosso usuário de sistema foi criado com sucesso, agora podemos obter o token referente ao mesmo, fazendo o mesmo processo do passo 1.

3. Para criação, edição, remoção e consultas, o usuário deverá ter o perfil `ROLE_ADMIN`, sendo assim, nosso usuário de sistema não tem permissão para tais requisições. Para usuários com o perfil `ROLE_USER`, somente será permitido a consulta especifica de usuário autenticado, através de seu identificador único. Vamos tentar criar um usuário de sistema.
	
	* Configure a extensão POSTMAN para o verbo POST e adicione a url descrita abaixo
	
	* Para obter o token corresponsente ao novo usuário de sistema, faça a seguinte requisição usando o verbo POST:

```		

	https://toggle-manager-api.herokuapp.com/api/oauth/token?grant_type=password&username=apiA&password=secret
	
	Response body: {
	    "access_token": "28e31630-1d9b-46ff-b941-4ccab2bc5645",
	    "token_type": "bearer",
	    "expires_in": 863999,
	    "scope": "read write trust"
	}
		
		
```

* Configure a extensão POSTMAN para o verbo POST e adicione a url descrita abaixo

* Para criarmos um novo usuário, faça a seguinte requisição usando o verbo POST:

	
```		

	https://toggle-manager-api.herokuapp.com/api/users?access_token=28e31630-1d9b-46ff-b941-4ccab2bc5645
	
	Request body: {
        "userName": "apiB",
        "password": "secret",
        "version": "v.1.0.0",
        "roles": [
            "ROLE_USER"
        ]
	}
	
	 Response body: Access is denied 
	 
	 
```

* Obtemos o `Status 403 Forbidden` para nosso usuário de sistema. O mesmo ocorreu porque nosso usuário sendo `ROLE_USER`, não tem permissão para criação de outro usuário de sistema. O mesmo acontece para remoções, edições e consultas que não são respectivas ao usuário de sistema autenticado.
	 
	* Vejamos outro exemplo, vamos tentar fazer uma consulta de todos usuários de sistema. Para tal, configure a extensão POSTMAN para o verbo GET e adicione a url descrita abaixo. Faça a seguinte requisição usando o verbo GET:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users?access_token=28e31630-1d9b-46ff-b941-4ccab2bc5645

	Response body: Access is denied / Status 403 Forbidden
	
	
```
	  
* Tais restrições são aplicadas para criação, remoção, edição e consultas de Toggles, com exceção dos toggles referentes ao usuário de sistemas autenticado.
	
	  
4. Sendo assim, vamos continuar usando o usuário `admin` para realizarmos o cadastro de alguns toggles, que associaremos ao nosso atual usuário de sistema e a outros posteriores. 
	  
	* Com um token válido do usuário de sistemas `admin`, vamos criar um toggle. Configure a extensão POSTMAN para o verbo POST e realize uma requisição usando o verbo POST com a url descrita abaixo:
	  
```		

	https://toggle-manager-api.herokuapp.com/api/toggles?access_token=7b66685f-fae2-4769-b242-67b803868f63
	  
  	Request body:{
	            "name": "isButtonBlue",
	            "value": true
			}
			
 	Response body: {
			    "id": "59ade77e1be2e40004f626b5",
			    "name": "isButtonBlue",
			    "value": true
			}
			
			
```


* Agora vamos criar mais dois toggles, o Yellow e o Green. Repita as requisições usando o verbo POST, mas agora passando os argumentos que represente nossos novos toggles. Configure a extensão POSTMAN para o verbo POST e realize uma requisição usando o verbo POST com a url descrita abaixo, para que a criação do Toggle Yellow seja realizada com sucesso. Observe que o mesmo está com o seu valor false:
	
	
	
```		

	https://toggle-manager-api.herokuapp.com/api/toggles?access_token=7b66685f-fae2-4769-b242-67b803868f63
	  
  	Request body:{
                "name": "isButtonYellow",
                "value": false
			}
	
	Response body: {
			    "id": "59ade8401be2e40004f626b6",
			    "name": "isButtonYellow",
			    "value": false
			}	
			
				
```


* Faça o mesmo procedimento para a criação do Toggle Green:
	
	
```		

	https://toggle-manager-api.herokuapp.com/api/toggles?access_token=7b66685f-fae2-4769-b242-67b803868f63
	 
 	Request body:{
                "name": "isButtonGreen",
                "value": true
			}
	
	Response body: {
			    "id": "59ade87d1be2e40004f626b7",
			    "name": "isButtonGreen",
			    "value": true
			}
			
			
```
	
5. Agora vamos associar alguns desses novos Toggles ao nosso usuário de sistema `apiA`. 

	* Configure a extensão POSTMAN para o verbo PUT e adicione a url descrita abaixo

	* Faça a seguinte requisição usando o verbo PUT:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59ade55b1be2e40004f626b4?access_token=7b66685f-fae2-4769-b242-67b803868f63
	
	Request body: {
			    "id": "59ade55b1be2e40004f626b4",
			    "userName": "apiA",
			    "password": "secret",
			    "version": "v.1.0.0",
			    "roles": [
			        "ROLE_USER"
			    	],
				"toggles":[
				    {
					    "id": "59ade77e1be2e40004f626b5"
					},
				    {
					    "id": "59ade8401be2e40004f626b6"
					}
				]
			}
	
		Response body: Status 200 OK
		
		
```

6. Agora vamos fazer uma consulta para obtermos nosso usuário de sistema e seus respectivos Toggles. Para isso, garanta um token válido para nosso usuário(`apiA`) de sistema. 
	
	* Configure a extensão POSTMAN para o verbo GET e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo GET:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59ade55b1be2e40004f626b4?access_token=28e31630-1d9b-46ff-b941-4ccab2bc5645
	
	Response body: {
		    "id": "59ade55b1be2e40004f626b4",
		    "userName": "apiA",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ade77e1be2e40004f626b5",
		            "name": "isButtonBlue",
		            "value": true
		        },
		        {
		            "id": "59ade8401be2e40004f626b6",
		            "name": "isButtonYellow",
		            "value": false
		        }
		    ]
		}
	
	
```

7. É possivel compartilhar um toggle com outra API. Vamos criar um novo usuário de sistema, e associar o toggle Green a ele
	
	* Configure a extensão POSTMAN para o verbo POST e adicione a url descrita abaixo
	
	* Com um token válido de um usuário de sistema `ROLE_ADMIN`, faça a seguinte requisição usando o verbo POST:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users?access_token=7b66685f-fae2-4769-b242-67b803868f63
  	
	Request body:  { 
			        "userName": "apiB",
			        "password": "secret",
			        "version": "v.1.0.0",
			        "roles": [
			            "ROLE_USER"
			        ]
		        }
 		
 	Response body: {
		    "id": "59adea691be2e40004f626b8",
		    "userName": "apiB",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": null
		}
			
			
```

8. Agora vamos associar o Toggle Green ao novo usuário de sistema apiB

	* Configure a extensão POSTMAN para o verbo PUT e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo PUT:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59adea691be2e40004f626b8?access_token=7b66685f-fae2-4769-b242-67b803868f63
	
	Request body: {
			    "id": "59adea691be2e40004f626b8",
			    "userName": "apiB",
			    "password": "secret",
			    "version": "v.1.0.0",
			    "roles": [
			        "ROLE_USER"
			    ],
				"toggles":[
				   {
			    		"id": "59ade87d1be2e40004f626b7"
					}
				]
			}
	
	Response body: Status 200 OK
	
	
```

9. Vamos consultar nosso novo usuário de sistema. 
	
	* Configure a extensão POSTMAN para o verbo GET e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo GET:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59adea691be2e40004f626b8?access_token=64ddda1e-c746-4413-9650-a186e1c7efa4
	
	Request body:{
		    "id": "59adea691be2e40004f626b8",
		    "userName": "apiB",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ade87d1be2e40004f626b7",
		            "name": "isButtonGreen",
		            "value": true
		        }
		    ]
		}
			
			
```

10. Agora vamos compartilhar o Toggle Yellow com a ApiB, assim ambos compartilham seu valor

	* Configure a extensão POSTMAN para o verbo PUT e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo PUT:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59adea691be2e40004f626b8?access_token=7b66685f-fae2-4769-b242-67b803868f63
	
	Request body: {
		    "id": "59adea691be2e40004f626b8",
		    "userName": "apiB",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ade87d1be2e40004f626b7"
		        },
		        {
		            "id": "59ade8401be2e40004f626b6",
		            "name": "isButtonYellow",
		            "value": false
		        }
		    ]
		}
				
	Response Body: Status 200 OK
	
	
```

10. Agora podemos obter os Toggles de nosso novo usuário de sistema apiB e validar se o Toggle Yellow foi compartlhado

	* Configure a extensão POSTMAN para o verbo GET e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo GET:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59adea691be2e40004f626b8?access_token=64ddda1e-c746-4413-9650-a186e1c7efa4
	
	Response body: {
		    "id": "59adea691be2e40004f626b8",
		    "userName": "apiB",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ade87d1be2e40004f626b7",
		            "name": "isButtonGreen",
		            "value": true
		        },
		        {
		            "id": "59ade8401be2e40004f626b6",
		            "name": "isButtonYellow",
		            "value": false
		        }
		    ]
		}
	
	
```

* Podemos observar que o Toggle de `id: 59ade8401be2e40004f626b6` e `name: isButtonYellow` se encontra associado aos usuários de sistemas `apiA` e `apiB`. Quando houver uma alteração no valor do Toggle, o mesmo será compartilhado a todos os usuários de sistemas que tem tal toggle associado
	
11. Para finalizar, vamos criar um novo Toggle com `name: isButtonGreen`, porem, com seu valor false, pois para o usuário de sistema apiA, o valor do Toggle `name : isButtonGreen` deverár ser `false`

	* Configure a extensão POSTMAN para o verbo POST e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo POST:
	
```		

	https://toggle-manager-api.herokuapp.com/api/toggles?access_token=7b66685f-fae2-4769-b242-67b803868f63

	Request body: {
			    "name": "isButtonGreen",
			    "value": false
			}

	Response body:  {
		    "id": "59aded331be2e40004f626b9",
		    "name": "isButtonGreen",
		    "value": false
		}
			
			
```

12. Agora vamos associar o Toggle `name: isButtonGreen` com `value: false` em nosso usuário de sistema `apiA`

	* Configure a extensão POSTMAN para o verbo PUT e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo PUT:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59ade55b1be2e40004f626b4?access_token=7b66685f-fae2-4769-b242-67b803868f63
	
	Request body: {
		    "id": "59ade55b1be2e40004f626b4",
		    "userName": "apiA",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ade77e1be2e40004f626b5",
		            "name": "isButtonBlue",
		            "value": true
		        },
		        {
		            "id": "59ade8401be2e40004f626b6",
		            "name": "isButtonYellow",
		            "value": false
		        },
		        {
		        		"id": "59aded331be2e40004f626b9"
		        	}
		    ]
		}
			    
	Response Body: Response Body: Status 200 OK
	
	
```

13. Agora vamos consultar e garantir que temos o `Toggle green`, com `valor false para a apiA` e `valor true para a apiB`
	
	* Configure a extensão POSTMAN para o verbo GET e adicione a url descrita abaixo
	
	* Faça a seguinte requisição usando o verbo GET:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users/59ade55b1be2e40004f626b4?access_token=28e31630-1d9b-46ff-b941-4ccab2bc5645
	
	Response body: {
		    "id": "59ade55b1be2e40004f626b4",
		    "userName": "apiA",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ade77e1be2e40004f626b5",
		            "name": "isButtonBlue",
		            "value": true
		        },
		        {
		            "id": "59ade8401be2e40004f626b6",
		            "name": "isButtonYellow",
		            "value": false
		        },
		        {
		            "id": "59aded331be2e40004f626b9",
		            "name": "isButtonGreen",
		            "value": false
		        }
		    ]
		}
			
			
```

### Vendo o trabalho realizado através da [Consumer Toggle Manager API](https://consumer-toggle-manager-api.herokuapp.com/)

1. Obtenha um token válido da usuário de sistema que deseja consultar `apiA` ou `apiB`

2. Acesse [Consumer Toggle Manager API](https://consumer-toggle-manager-api.herokuapp.com/)

3. Adicione o token gerado no campo `Digite aqui o Token da API a cosultar` da pagina.

4. Click em `Atualizar`

5. Agora você verá os toggles pertencente ao usuário de sistema consultado, com o botão abilitado, caso o valor do toggle seja true e desabilitado caso o valor do toggle seja false.

## Observações finais:
	
Vimos que é possivel criar, alterar, remover e consultar um usuário de sistema, sendo necessário a autenticação e autorização para a realização dos mesmos. É possivel criar, alterar, remover e consultar um toggle, respeitando os perfis "ROLE_USER" e "ROLE_ADMIN" assim como nos serviços expostos para usuáro de sistema. Um Toggle pode ser usado por um ou mais Apis. Uma Toggle pode ter valor distinto entre APIs. Um Toggle pode ser usado por um serviço e outro pode não te-lo configurado para uso. Para acessar os Toggle a API deve se autenticar. Na proxima sessão, onde baixaremos a aplicação do GIT, veremos como trabalhar com as notificações, caso um Toggle seja alterado. E confirmamos a autorização para acesso aos serviços da API.





## Clonando o projeto e executando localmente

1. Instalar o [Java 8](https://www.java.com/pt_BR/download/)

2. Instala o [MongoDB](https://docs.mongodb.com/manual/installation/)

3. Inicie o MongoDB usando o comand mongod

4. Intalar o [Apache Kafka](https://kafka.apache.org/quickstart)

5. Start o kafka
	1. zkserver start
	3. /usr/local/Cellar/kafka/0.10.1.0/bin/kafka-server-start.sh /usr/local/etc/kafka/server.properties

6. Crie um tópic
	1. kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
	
7. Crie um consumidor para ver as notificações enviadas quando um toggle é alterado
	1. kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

8. Faça download da IDE [eclipse](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/oxygen/R/eclipse-inst-mac64.tar.gz)

9. Abra o eclipse

10. Acesse no menu superior do eclipse o menu Help > Eclipse Market Place

11. Agora pesquise por Spring Tools

12. Instale a extensão Spring Tools

13. Faça o clone do [projeto](https://github.com/rafaelfcads/toggle-manager-api.git)

14. No Eclipse, acesse a aba Project Explorer

15. Click com o botão direito e selecione import > import

16. Selecione Maven > Existing Maven Projects

17. Selecione uma pasta superior a pasta criada pela a ção de clone do projeto

18. Será exibido o pom do projeto clonado, selecione o mesmo

19. O workspace será contruido

20. Abra a classe `ToggleManagerApplication`

21. Agora execute o botão `Run` do eclipse

22. O servidor será started e você poderá acessar a aplicação pela url http://localhost:8080

23. A aplicação poderá ser utilizado seguindo os passos de `API Toggle Management em uso` logo acima. Você deve apenas alterar a url base de `https://toggle-manager-api.herokuapp.com/api` para `http://localhost:8080/api`





	
	
	
	 
 			
 	
 


