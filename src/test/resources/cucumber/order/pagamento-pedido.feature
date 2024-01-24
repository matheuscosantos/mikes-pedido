Feature: Pagar pedido

  Scenario: Pedido aguardando pagamento recebe callback de que o pagamento foi aceito
    Given Aplicacao esta processando um pagamento
    When Pedido aguardando pagamento recebe o callback de status "ACCEPTED"
    Then Atualiza pedido para status "PAID"
        And Notifica confirmacao do pedido

  Scenario: Pedido aguardando pagamento recebe callback de que o pagamento foi recusado
    Given Aplicacao esta processando um pagamento
    When Pedido aguardando pagamento recebe o callback de status "REFUSED"
    Then Atualiza pedido para status "CANCELLED"
        And Nao notifica confirmacao do pedido

  Scenario: Pedido aguardando pagamento recebe callback de que o pagamento ainda nao foi feito
    Given Aplicacao esta processando um pagamento
    When Pedido aguardando pagamento recebe o callback de status "WAITING"
    Then Nao atualiza pedido
        And Nao notifica confirmacao do pedido

  Scenario: Pedido que nao esta aguardando pagamento recebe callback de que o pagamento foi aceito
    Given Aplicacao esta processando um pagamento
    When Pedido que nao esta aguardando pagamento recebe o callback de status "ACCEPTED"
    Then Nao atualiza pedido
        And Nao notifica confirmacao do pedido

  Scenario: Pedido que nao esta aguardando pagamento recebe callback de que o pagamento foi recusado
    Given Aplicacao esta processando um pagamento
    When Pedido que nao esta aguardando pagamento recebe o callback de status "REFUSED"
    Then Nao atualiza pedido
        And Nao notifica confirmacao do pedido

  Scenario: Pedido aguardando pagamento recebe callback com status invalido
    Given Aplicacao esta processando um pagamento
    When Pedido aguardando pagamento recebe o callback de status "status invalido"
    Then Nao atualiza pedido
        And Nao notifica confirmacao do pedido

  Scenario: Pedido invalido recebe callback
    Given Aplicacao esta processando um pagamento
    When Pedido inexistente recebe callback
    Then Nao atualiza pedido
        And Nao notifica confirmacao do pedido