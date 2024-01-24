Feature: Producao pedido

  Scenario: Iniciando prepado de pedido pago
    Given Pedido em producao esta "PAID"
    When Producao informa que pedido foi para "PREPARING"
    Then Atualiza pedido em producao para "PREPARING"

  Scenario: Pedido sendo preparado fica pronto
    Given Pedido em producao esta "PREPARING"
    When Producao informa que pedido foi para "READY"
    Then Atualiza pedido em producao para "READY"

  Scenario: Pedido pronto e encerrado
    Given Pedido em producao esta "READY"
    When Producao informa que pedido foi para "FINISHED"
    Then Atualiza pedido em producao para "FINISHED"

  Scenario: Pedido pago e cancelado
    Given Pedido em producao esta "PAID"
    When Producao informa que pedido foi para "CANCELLED"
    Then Atualiza pedido em producao para "CANCELLED"

  Scenario: Pedido ja finalizado e enviado para preparo
    Given Pedido em producao esta "FINISHED"
    When Producao informa que pedido foi para "PREPARING"
    Then Nao atualiza pedido em producao

  Scenario: Pedido ja cancelado e enviado para preparo
    Given Pedido em producao esta "CANCELLED"
    When Producao informa que pedido foi para "PREPARING"
    Then Nao atualiza pedido em producao

  Scenario: Pedido em producao e atualizado para status invalido
    Given Pedido em producao esta "PAID"
    When Producao informa que pedido foi para "status invalido"
    Then Nao atualiza pedido em producao

  Scenario: Pedido que nao esta em producao e atualizado
    Given Pedido em producao nao existe
    When Producao informa que pedido foi para "PREPARING"
    Then Nao atualiza pedido em producao