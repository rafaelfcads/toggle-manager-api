# Toggle Management API

Esta API tem por objetivo expor serviços para o gerenciamento em tempo real e dinâmico da disponibilidade de serviços em uma plataforma. Através dos serviços disponiveis, é possivel criar usuário de sistema, onde deverá ser atribuido o API name, sua respectiva versão e os seus toggles. O usuário de sistema pode ser criado com seus respectivos toggles, basta associar a ele. Após criado, será possivel realizar a sua consulta, em suas diversas formas, edição e remoção. Para criação, edição, remoção e consultas, com exceção de consulta pelo identificador único, a requisição deverá ser realizada com um usuário de sistema com perfil ADMIN. Para consulta pelo identificador único, o mesmo poderá ser realizado com perfil USER;
Através dos serviços disponiveis, será possivel ainda, criar toggles que poderão ser associados posteriormente aos usuários de sistemas desejado. Para criação de um toggle, deverá ser atribuido o seu name e seu valor(TRUE ou FALSE). Um toggle pode ser associada a uma ou mais APIs, um toggle com um respectivo nome, pode ser criado com valores distintos para diferentes APIs(Ex: Toggle Name: is ButtonRed, Valor=TRUE associado as APIs A, B e C, e Toggle Name: isButtonRed, Valor=FALSE para as APIs D e E). O toggle poderá ser consultado pela API, caso o mesmo esteja associado a ela. Para que uma determinada API tenha acesso aos seus toggles, a API deverá fornecer seus dados de autenticação, sendo seu nome e versão. Para criação, edição, remoção e consultas, com exceção de consulta por usuário de sistema, onde trará apenas os toggles referentes aquela API, deverá ser realizada com um usuário de sistema com perfil ADMIN, para consulta por usuário de sistema, o mesmo poderá ser realizado com perfil USER;
A Toggle Management API fornece o recurso de notificação, quando um toggle é alterado. Para a utilização do mesmo, a API consumidora deverá se subscrever ao tópico que representa tal toggle. 
Salientamos que uma API somente terá acesso aos serviços, caso a mesma tenha um usuário de sistema, para que possa se autenticar e consumir os serviços respeitando seu perfil de acesso.


## Como posso fazer uso da API Toggle Management API

Os serviços da API podem ser consumidos em [Toggle Management API](https://toggle-manager-api.herokuapp.com/api)

API Toggle Management pode ser clonada em [GITHIB](https://github.com/rafaelfcads/toggle-manager-api.git)


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

## API Toggle Management em uso

Os serviços da API podem ser consumidos em [Toggle Management API](https://toggle-manager-api.herokuapp.com/api)

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
 
1. Com o usuário `admin`, você deve obter um token de acesso
 
	* Faça a seguinte requisição usando o verbo POST
 
```		https://toggle-manager-api.herokuapp.com/api/oauth/token?grant_type=password&username=admin&password=admin
 
		Response body: 
				{
					"access_token": "42da6a6c-f992-4bf5-8698-9b584dd35918",
					"token_type": "bearer",
				    "expires_in": 863999,
				    "scope": "read write trust"
				}
 ```
* Com o token em mãos, você terá o tempo do `expires_in` para fazer a utilização do `access_token: 42da6a6c-f992-4bf5-8698-9b584dd35918`
  		
  
2. Com o token do usuário `admin` em mãos e em tempo válido, podemos criar então um novo usuário de sistema, que represente nossa API consumidora 
  
	* Faça a seguinte requisição usando o verbo POST:
  	
```		https://toggle-manager-api.herokuapp.com/api/users?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
  	
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
	 				"id": "59ad767595f88f0004e2361a",
				    "userName": "apiA",
				    "password": "secret",
				    "version": "v.1.0.0",
				    "roles": [
				        "ROLE_USER"
				    ],
				    "toggles": null
	 			}
```

* Nosso usuário de sistema está criado, agora podemos obter o token referente ao mesmo, fazendo o mesmo processo do passo 1.

3. Para criação, edição, remoção e consultas, o usuário deverá ter o perfil `ROLE_ADMIN`. Sendo assim nosso usuário de sistema não tem permissão para tais requisições. Para usuários com o perfil `ROLE_USER`, somente será permitido a consulta especifica de usuário autenticado, através de seu identificador único. Vamos tentar criar um usuário de sistema.
	
	* Para obter o token corresponsente ao novo usuário de sistema, faça a seguinte requisição usando o verbo POST:

```		

	https://toggle-manager-api.herokuapp.com/api/oauth/token?grant_type=password&username=apiA&password=secret
	
	Response body: {
	    "access_token": "ef34be86-d6d7-49c3-b062-8535c3412817",
	    "token_type": "bearer",
	    "expires_in": 863999,
	    "scope": "read write trust"
	}
		
		
```


* Para criarmos um novo usuário, faça a seguinte requisição usando o verbo POST:

	
```		

	https://toggle-manager-api.herokuapp.com/api/users?access_token=ef34be86-d6d7-49c3-b062-8535c3412817
	
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
	 
	* Vejamos outro exemplo, vamos tentar fazer uma consulta de todos usuários de sistema. Para tal, faça a seguinte requisição usando o verbo GET:
	
```		

	https://toggle-manager-api.herokuapp.com/api/users?access_token=1af9cef3-f2ff-4be0-bc30-f7693ca70f84

	Response body: Access is denied / Status 403 Forbidden
	
	
```
	  
* Tais restrições são aplicadas para criação, remoção, edição e consultas de Toggles, com exceção dos toggles referentes ao usuário de sistemas autenticado.
	
	  
4. Sendo assim, vamos continuar usando o usuário `admin` para realizarmos o cadastro de alguns toggles, que associaremos ao nosso atual usuário de sistema e a outros posteriores. 
	  
	* Com um token válido do usuário de sistemas `admin`, vamos fazer uma requisição usando o verbo POST para criar uma Toggle:
	  
```		

	https://toggle-manager-api.herokuapp.com/api/toggles?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
	  
  	Request body:{
	            "name": "isButtonBlue",
	            "value": true
			}
			
 	Response body: {
			    "id": "59ad7eff95f88f0004e2361c",
			    "name": "isButtonBlue",
			    "value": true
			}
			
			
```


	* Agora vamos cria mais dois Toggles, o Yellow e o Green. Repita as requisições usando o verbo POST, mas agora passando os argumentos que represente nossos novos Toggles. Faça a seguinte requisição POST para a criação do Toggle Yellow. Observe que o mesmo está com o seu valor false:
	
	
	
```		

	https://toggle-manager-api.herokuapp.com/api/toggles?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
	  
  	Request body:{
                "name": "isButtonBlue",
                "value": false
			}
	
	Response body: {
			    "id": "59ad815b95f88f0004e2361d",
			    "name": "isButtonYellow",
			    "value": false
			}	
			
				
```


	* Faça a seguinte requisição POST para a criação do Toggle Green:
	
	
```		

	https://toggle-manager-api.herokuapp.com/api/toggles?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
	 
 	Request body:{
                "name": "isButtonGreen",
                "value": true
			}
	
	Response body: {
			    "id": "59ad822795f88f0004e2361e",
			    "name": "isButtonGreen",
			    "value": true
			}
			
			
```
	
5. Agora vamos associar alguns desses novos Toggles ao nosso usuário de sistema `apiA`. 

	1. Faça a seguinte requisição usando o verbo PUT:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59abcf98734d1d25a0f5dfba?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
	
		Request body: {
				    "id": "59ad767595f88f0004e2361a",
				    "userName": "apiA",
				    "password": "secret",
				    "version": "v.1.0.0",
				    "roles": [
				        "ROLE_USER"
				    	],
					"toggles":[
					    {
						    "id": "59ad7eff95f88f0004e2361c",
						    "name": "isButtonBlue",
						    "value": true
						},
					    {
						    "id": "59ad815b95f88f0004e2361d",
						    "name": "isButtonYellow",
						    "value": false
						}
					]
				}
	
		Response body: Status 200 OK
```

6. Agora vamos fazer uma consulta para obtermos nosso usuário de sistema e seus respectivos Toggles. Para isso, garanta um token válido para nosso usuário de sistema. 
	
	1. Faça a seguinte requisição usando o verbo GET:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59ad767595f88f0004e2361a?access_token=ef34be86-d6d7-49c3-b062-8535c3412817
	
		Response body: {
		    "id": "59ad767595f88f0004e2361a",
		    "userName": "apiA",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ad7eff95f88f0004e2361c",
		            "name": "isButtonBlue",
		            "value": true
		        },
		        {
		            "id": "59ad815b95f88f0004e2361d",
		            "name": "isButtonYellow",
		            "value": false
		        }
		    ]
		}
```

7. É possivel compartilhar um toggle com outra API. Vamos criar um novo usuário de sistema, e associar o Toggle Green a ele
	
	1. Com um token válido de um usuário de sistema `ROLE_ADMIN`, faça a seguinte requisição usando o verbo POST:
	
```		https://toggle-manager-api.herokuapp.com/api/users?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
  	
  		Request body:  { 
	  					"id": "59ad794595f88f0004e2361b",
				        "userName": "apiB",
				        "password": "secret",
				        "version": "v.1.0.0",
				        "roles": [
				            "ROLE_USER"
				        ]
			        }
	 		
	 	Response body: {
				    "id": "59ad794595f88f0004e2361b",
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

	1. Faça a seguinte requisição usando o verbo PUT:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59ad794595f88f0004e2361b?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
	
		Request body: {
				    "id": "59ad794595f88f0004e2361b",
				    "userName": "apiB",
				    "password": "secret",
				    "version": "v.1.0.0",
				    "roles": [
				        "ROLE_USER"
				    	],
					"toggles":[
					   {
				    		"id": "59ad822795f88f0004e2361e"
						}
					]
				}
	
	Response body: Status 200 OK
```

9. Vamos consultar nosso novo usuário de sistema. 
	
	1. Faça a seguinte requisição usando o verbo GET:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59ad794595f88f0004e2361b?access_token=1af9cef3-f2ff-4be0-bc30-f7693ca70f84
	
		Request body:{
				    "id": "59ad794595f88f0004e2361b",
				    "userName": "apiB",
				    "password": "secret",
				    "version": "v.1.0.0",
				    "roles": [
				        "ROLE_USER"
				    ],
				    "toggles": [
				        {
				            "id": "59ad822795f88f0004e2361e",
				            "name": "isButtonGreen",
				            "value": true
				        }
				    ]
				}
```

10. Agora vamos compartilhar o Toggle Yellow com a ApiB, assim ambos compartilham seu valor

	1. Faça a seguinte requisição usando o verbo PUT:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59ad794595f88f0004e2361b?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
	
		Request body: {
				    "id": "59ad794595f88f0004e2361b",
				    "userName": "apiB",
				    "password": "secret",
				    "version": "v.1.0.0",
				    "roles": [
				        "ROLE_USER"
				    	],
					"toggles":[
					   {
				    		"id": "59ad822795f88f0004e2361e"
						},
						{
				            "id": "59ad815b95f88f0004e2361d",
				            "name": "isButtonYellow",
				            "value": false
				        }
					]
				}
				
	Response Body: Status 200 OK
```

10. Agora podemos obter os Toggles de nosso novo usuário de sistema apiB e validar se o Toggle Yellow foi compartlhado

	1. Faça a seguinte requisição usando o verbo GET:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59ad794595f88f0004e2361b?access_token=1af9cef3-f2ff-4be0-bc30-f7693ca70f84
	
		Response body: {
		    "id": "59ad794595f88f0004e2361b",
		    "userName": "apiB",
		    "password": "secret",
		    "version": "v.1.0.0",
		    "roles": [
		        "ROLE_USER"
		    ],
		    "toggles": [
		        {
		            "id": "59ad822795f88f0004e2361e",
		            "name": "isButtonGreen",
		            "value": true
		        },
		        {
		            "id": "59ad815b95f88f0004e2361d",
		            "name": "isButtonYellow",
		            "value": false
		        }
		    ]
		}
```

	- Podemos observar que o Toggle de `id: 59ad815b95f88f0004e2361d` e `name: isButtonYellow` se encontra associado aos usuários de sistemas `apiA` e `apiB`. Quando houver uma alteração no valor do Toggle, o mesmo será compartilhado a todos os usuários de sistemas que tem tal toggle associado
	
11. Para finalizar, vamos criar um novo Toggle com `name: isButtonGreen`, porem, com seu valor false, pois para o usuário de sistema apiA, o valor do Toggle `name : isButtonGreen` deverár ser `false`

	1. Faça a seguinte requisição usando o verbo POST:
	
```		https://toggle-manager-api.herokuapp.com/api/toggles?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918

		Request body: {
				    "name": "isButtonGreen",
				    "value": false
				}
	
		Response body:  {
				    "id": "59ad915a95f88f0004e2361f",
				    "name": "isButtonGreen",
				    "value": false
				}
```

12. Agora vamos associar o Toggle `name: isButtonGreen` com `value: false` em nosso usuário de sistema apiA

	1. Faça a seguinte requisição usando o verbo PUT:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59ad767595f88f0004e2361a?access_token=42da6a6c-f992-4bf5-8698-9b584dd35918
	
		Request body: {
			        "id": "59ad767595f88f0004e2361a",
			        "userName": "apiA",
			        "password": "secret",
			        "version": "v.1.0.0",
			        "roles": [
			            "ROLE_USER"
			        ],
			        "toggles": [
			            {
			                "id": "59ad7eff95f88f0004e2361c",
			                "name": "isButtonBlue",
			                "value": true
			            },
			            {
			                "id": "59ad815b95f88f0004e2361d",
			                "name": "isButtonYellow",
			                "value": false
			            },
			            {
			            	"id": "59ad915a95f88f0004e2361f"
			            }
			        ]
			    }
			    
	Response Body: Response Body: Status 200 OK
```

13. Agora vamos consultar e garantir que temos o `Toggle green`, com `valor false para a apiA` e `valor true para a apiB`
	
	1. Faça a seguinte requisição usando o verbo GET:
	
```		https://toggle-manager-api.herokuapp.com/api/users/59ad767595f88f0004e2361a?access_token=ef34be86-d6d7-49c3-b062-8535c3412817
	
		Response body: {
				    "id": "59ad767595f88f0004e2361a",
				    "userName": "apiA",
				    "password": "secret",
				    "version": "v.1.0.0",
				    "roles": [
				        "ROLE_USER"
				    ],
				    "toggles": [
				        {
				            "id": "59ad7eff95f88f0004e2361c",
				            "name": "isButtonBlue",
				            "value": true
				        },
				        {
				            "id": "59ad815b95f88f0004e2361d",
				            "name": "isButtonYellow",
				            "value": false
				        },
				        {
				            "id": "59ad915a95f88f0004e2361f",
				            "name": "isButtonGreen",
				            "value": false
				        }
				    ]
				}
```

## Observações finais:
	
### Vimos que é possivel criar, alterar, remover e consultar um usuário de sistema, sendo necessário a autenticação e autorização para a realização dos mesmos. É possivel criar, alterar, remover e consultar um toggle, respeitando os perfis "ROLE_USER" e "ROLE_ADMIN" assim como nos serviços expostos para usuáro de sistema. Um Toggle pode ser usado por um ou mais Apis. Uma Toggle pode ter valor distinto entre APIs. Um Toggle pode ser usado por um serviço e outro pode não te-lo configurado para uso. Para acessar os Toggle a API deve se autenticar. Na proxima sessão, onde baixaremos a aplicação do GIT, veremos como trabalhar com as notificações, caso um Toggle seja alterado. E confirmamos a autorização para acesso aos serviços da API.
	
	
	
	 
 			
 	
 


