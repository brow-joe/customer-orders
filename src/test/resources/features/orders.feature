@orders
Feature: OrdersController

  Background:
    * url application

  Scenario: POST: orders.create - all fields
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "a0942a48-f54b-4a89-9a04-d97b24308a9a", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100, "quantidade": 1, "codigoCliente": 1 }, { "numeroControle": "a4ea1bdf-7487-48ab-a4c9-b7ba37f4eea4", "dataCadastro": "2024-08-10T22:13:20.048Z", "nome": "Integration Tests", "valor": 100, "quantidade": 6, "codigoCliente": 2 }, { "numeroControle": "ff14ebb5-95e3-435c-926e-8762fe0d0f3d", "dataCadastro": "2024-08-10T23:13:20.048Z", "nome": "Integration Tests", "valor": 100, "quantidade": 11, "codigoCliente": 3 } ] }
    When method POST
    Then status 201
    
  Scenario: POST: orders.create - required fields
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "97af14e9-0448-4033-94cd-3f117cf4a773", "nome": "Integration Tests", "valor": 100, "codigoCliente": 4 }, { "numeroControle": "c817c50b-4dbe-445c-9b3b-fbd4c8999739", "nome": "Integration Tests", "valor": 100, "quantidade": 0, "codigoCliente": 5 }, { "numeroControle": "c910a7e0-9fc8-4847-92bf-e6b51f905948", "nome": "Integration Tests", "quantidade": -1, "valor": 100, "codigoCliente": 6 }, { "numeroControle": "954e7421-0172-4ad5-875c-b4bf4449633f", "nome": "Integration Tests", "valor": 100, "codigoCliente": 7 }, { "numeroControle": "63c028b7-bb60-454c-940d-821955d5980e", "nome": "Integration Tests", "valor": 100, "codigoCliente": 8 }, { "numeroControle": "2bc898a0-c42c-4808-9557-4fca1b57a049", "nome": "Integration Tests", "valor": 100, "codigoCliente": 9 }, { "numeroControle": "a1e151e8-4a74-44f8-af05-59ab863f0294", "nome": "Integration Tests", "valor": 100, "codigoCliente": 10 } ] }
    When method POST
    Then status 201
    
  Scenario: POST: orders.fail - orders is null
    Given path '/api/v1/orders'
    And request { }
    When method POST
    Then status 400
    And match response == { "orders": "A lista de pedidos não pode ser vazia." }
    
  Scenario: POST: orders.fail - orders is empty
    Given path '/api/v1/orders'
    And request { }
    When method POST
    Then status 400
    And match response == { "orders": "A lista de pedidos não pode ser vazia." }
    
  Scenario: POST: orders.fail - orders max size
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "0a3d1ccb-e0cc-4b27-879a-7d1259199b66", "nome": "Integration Tests", "valor": 100, "codigoCliente": 1 }, { "numeroControle": "65a50f39-9896-428b-a89f-d902a980709d", "nome": "Integration Tests", "valor": 100, "codigoCliente": 2 }, { "numeroControle": "fe84d2c7-fc54-4b84-871e-4dbc7ccabfb4", "nome": "Integration Tests", "valor": 100, "codigoCliente": 3 }, { "numeroControle": "2ec7023c-51ae-4fc4-b670-f1830111043a", "nome": "Integration Tests", "valor": 100, "codigoCliente": 4 }, { "numeroControle": "2fbb4fb6-e187-4d26-924e-d2ac085db6f5", "nome": "Integration Tests", "valor": 100, "codigoCliente": 5 }, { "numeroControle": "68c89789-37c3-467b-a7a4-55169a006ca4", "nome": "Integration Tests", "valor": 100, "codigoCliente": 6 }, { "numeroControle": "14dec2df-3f50-4758-8161-44ea9c80fbb7", "nome": "Integration Tests", "valor": 100, "codigoCliente": 7 }, { "numeroControle": "bcd0cd95-c53e-45cd-b809-dc7bff8f0a92", "nome": "Integration Tests", "valor": 100, "codigoCliente": 8 }, { "numeroControle": "07ce657b-3915-4672-978f-063fe0683862", "nome": "Integration Tests", "valor": 100, "codigoCliente": 9 }, { "numeroControle": "ef4f6a4a-84dc-4dfc-be42-eba5bbce6a16", "nome": "Integration Tests", "valor": 100, "codigoCliente": 10 }, { "numeroControle": "d809010b-11b9-4af1-a6f1-ee4c14042deb", "nome": "Integration Tests", "valor": 100, "codigoCliente": 11 } ] }
    When method POST
    Then status 400
    And match response == { "orders": "A lista de pedidos não pode ultrapassar 10 pedidos" }
    
  Scenario: POST: orders.fail - numberControl already exists
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "a0942a48-f54b-4a89-9a04-d97b24308a9a", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100, "quantidade": 1, "codigoCliente": 1 } ] }
    When method POST
    Then status 400
    And match response == { "extendedProperties": "Número de controle já cadastrado" }
    
  Scenario: POST: orders.fail - numberControl is empty
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100, "quantidade": 1, "codigoCliente": 1 } ] }
    When method POST
    Then status 400
    And match response == { "orders[0].numberControl": "O número de controle é obrigatório e não pode estar vazio." }
    
  Scenario: POST: orders.fail - name is empty
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "b74d43cf-028f-41a6-bd59-78583f7fbdd1", "dataCadastro": "2024-08-10T21:13:20.048Z", "valor": 100, "quantidade": 1, "codigoCliente": 1 } ] }
    When method POST
    Then status 400
    And match response == { "orders[0].name": "O nome do produto é obrigatório e não pode estar vazio." }
    
  Scenario: POST: orders.fail - value is empty
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "67b03923-3843-4a1c-a8ac-755cf50294fc", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "quantidade": 1, "codigoCliente": 1 } ] }
    When method POST
    Then status 400
    And match response == { "orders[0].value": "O valor do produto é obrigatório." }
    
  Scenario: POST: orders.fail - value is negative
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "681a8fca-1236-41ec-9c48-1428558351ca", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": -1, "quantidade": 1, "codigoCliente": 1 } ] }
    When method POST
    Then status 400
    And match response == { "orders[0].value": "O valor do produto deve ser um número positivo." }
    
  Scenario: POST: orders.fail - customerCode does not exist
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "324a5d1a-1b68-46ea-ae29-82be1b946710", "nome": "Integration Tests", "valor": 100, "codigoCliente": 99 } ] }
    When method POST
    Then status 400
    And match response == { "extendedProperties": "Cliente não encontrado" }
    
  Scenario: POST: orders.fail - customerCode is empty
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "6be30379-eba9-494c-a38d-139b513324ae", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100, "quantidade": 1 } ] }
    When method POST
    Then status 400
    And match response == { "orders[0].customerCode": "O código do cliente é obrigatório." }
    
  Scenario: POST: orders.fail - customerCode is negative
    Given path '/api/v1/orders'
    And request { "pedidos": [ { "numeroControle": "d5429ba9-f463-4017-8902-5c80b3e6f4c4", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100, "quantidade": 1, "codigoCliente": -1 } ] }
    When method POST
    Then status 400
    And match response == { "orders[0].customerCode": "O código do cliente deve ser um número positivo." }

  Scenario: GET: orders.retrieve - all
    Given path '/api/v1/orders'
    When method GET
    Then status 200
    And match response.content == [ { "numeroControle": "a0942a48-f54b-4a89-9a04-d97b24308a9a", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 1, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "a4ea1bdf-7487-48ab-a4c9-b7ba37f4eea4", "dataCadastro": "2024-08-10T22:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 6, "codigoCliente": 2, "desconto": 0.05, "valorTotal": 570.0 }, { "numeroControle": "ff14ebb5-95e3-435c-926e-8762fe0d0f3d", "dataCadastro": "2024-08-10T23:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 11, "codigoCliente": 3, "desconto": 0.1, "valorTotal": 990.0 }, { "numeroControle": "97af14e9-0448-4033-94cd-3f117cf4a773", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 4, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "c817c50b-4dbe-445c-9b3b-fbd4c8999739", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 5, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "c910a7e0-9fc8-4847-92bf-e6b51f905948", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 6, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "954e7421-0172-4ad5-875c-b4bf4449633f", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 7, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "63c028b7-bb60-454c-940d-821955d5980e", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 8, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "2bc898a0-c42c-4808-9557-4fca1b57a049", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 9, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "a1e151e8-4a74-44f8-af05-59ab863f0294", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 10, "desconto": 0.0, "valorTotal": 100.0 } ]
    
Scenario: GET: orders.retrieve - filter numberControl - no date given
    Given path '/api/v1/orders'
    And param numeroControle = 'c817c50b-4dbe-445c-9b3b-fbd4c8999739'
    When method GET
    Then status 200
    And match response.content == [ { "numeroControle": "c817c50b-4dbe-445c-9b3b-fbd4c8999739", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 5, "desconto": 0.0, "valorTotal": 100.0 } ]
    
Scenario: GET: orders.retrieve - filter numberControl - no quantity reported
    Given path '/api/v1/orders'
    And param numeroControle = '97af14e9-0448-4033-94cd-3f117cf4a773'
    When method GET
    Then status 200
    And match response.content == [ { "numeroControle": "97af14e9-0448-4033-94cd-3f117cf4a773", "dataCadastro": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 4, "desconto": 0.0, "valorTotal": 100.0 } ]

  Scenario: GET: orders.retrieve - filter numberControl - no discount
    Given path '/api/v1/orders'
    And param numeroControle = 'a0942a48-f54b-4a89-9a04-d97b24308a9a'
    When method GET
    Then status 200
    And match response.content == [ { "numeroControle": "a0942a48-f54b-4a89-9a04-d97b24308a9a", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 1, "desconto": 0.0, "valorTotal": 100.0 } ]

  Scenario: GET: orders.retrieve - filter numberControl - 5% discount
    Given path '/api/v1/orders'
    And param numeroControle = 'a4ea1bdf-7487-48ab-a4c9-b7ba37f4eea4'
    When method GET
    Then status 200
    And match response.content == [ { "numeroControle": "a4ea1bdf-7487-48ab-a4c9-b7ba37f4eea4", "dataCadastro": "2024-08-10T22:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 6, "codigoCliente": 2, "desconto": 0.05, "valorTotal": 570.0 } ]

  Scenario: GET: orders.retrieve - filter numberControl - 10% discount
    Given path '/api/v1/orders'
    And param numeroControle = 'ff14ebb5-95e3-435c-926e-8762fe0d0f3d'
    When method GET
    Then status 200
    And match response.content == [ { "numeroControle": "ff14ebb5-95e3-435c-926e-8762fe0d0f3d", "dataCadastro": "2024-08-10T23:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 11, "codigoCliente": 3, "desconto": 0.1, "valorTotal": 990.0 } ]

  Scenario: GET: orders.retrieve - filter registrationDate
    Given path '/api/v1/orders'
    And param dataCadastroDe = '2024-08-10T21:13:20.048Z'
    And param dataCadastroAte = '2024-08-10T23:13:20.048Z'
    When method GET
    Then status 200
    And match response.content == [ { "numeroControle": "a0942a48-f54b-4a89-9a04-d97b24308a9a", "dataCadastro": "2024-08-10T21:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 1, "codigoCliente": 1, "desconto": 0.0, "valorTotal": 100.0 }, { "numeroControle": "a4ea1bdf-7487-48ab-a4c9-b7ba37f4eea4", "dataCadastro": "2024-08-10T22:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 6, "codigoCliente": 2, "desconto": 0.05, "valorTotal": 570.0 }, { "numeroControle": "ff14ebb5-95e3-435c-926e-8762fe0d0f3d", "dataCadastro": "2024-08-10T23:13:20.048Z", "nome": "Integration Tests", "valor": 100.0, "quantidade": 11, "codigoCliente": 3, "desconto": 0.1, "valorTotal": 990.0 } ]
