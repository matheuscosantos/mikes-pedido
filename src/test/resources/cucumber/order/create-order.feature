Feature: Fazer pedido

  Scenario: Pedido com cadastro aceito
    Given Cliente esta fazendo um pedido
    When Cliente informa cadastro existente
    Then Salva pedido
        And Notifica que pedido foi recebido

  Scenario: Pedido sem cadastro aceito
    Given Cliente esta fazendo um pedido
    When Cliente nao informa cadastro
    Then Salva pedido
        And Notifica que pedido foi recebido

  Scenario: Pedido rejeitado por nao encontrar cadastro
    Given Cliente esta fazendo um pedido
    When Cliente informa cadastro inexistente
    Then Nao salva pedido
        And Nao notifica que pedido foi recebido

  Scenario: Pedido rejeitado por nao encontrar produto
    Given Cliente esta fazendo um pedido
    When Cliente informa produto inexistente
    Then Nao salva pedido
        And Nao notifica que pedido foi recebido
