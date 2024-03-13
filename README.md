# Mikes - Pedido

Este repositório funcionalidades relacionadas ao sistema de pedidos da empresa "Mikes". Aqui está um resumo do que cada classe faz:

[Desenho da arquitetura](https://drive.google.com/file/d/12gofNmXk8W2QnhxiFWCI4OmvVH6Vsgun/view?usp=drive_link)

## CustomerController
- Gerencia operações relacionadas a clientes, como criar, encontrar e excluir clientes.
- Endpoints:
    - Criar Cliente
    - Encontrar Cliente
    - Excluir Cliente

## OrderController
- Gerencia operações relacionadas a pedidos, como criar, encontrar e excluir pedidos.
- Endpoints:
    - Listar Pedidos com Descrições
    - Criar Pedido
    - Encontrar Pedido por ID

## ProductController
- Gerencia operações relacionadas a produtos, como criar, atualizar, encontrar e excluir produtos.
- Endpoints:
    - Criar Produto
    - Atualizar Produto
    - Excluir Produto
    - Encontrar Produtos por Categoria

## OrderPaymentListener
- Ouve mensagens de pagamento de pedidos e processa-as.
- Limpa o cache após o processamento da mensagem.

## OrderProductionListener
- Ouve mensagens de produção de pedidos e processa-as.
- Limpa o cache após o processamento da mensagem.

## SnsOrderConfirmedMessenger
- Envia mensagens de confirmação de pedidos para um tópico SNS.

## SnsOrderReceivedMessenger
- Envia mensagens de recebimento de pedidos para um tópico SNS.

## Executando Localmente

Este guia explica como compilar e executar localmente o código fornecido usando Docker Compose para configurar um ambiente de desenvolvimento local.

### Pré-requisitos

Certifique-se de ter o Docker, o Docker Compose e o Gradle instalados em sua máquina.

### Passos

1. Clone o repositório com o código fornecido.
2. Navegue até o diretório do projeto onde está localizado o arquivo `docker-compose.yml`.
3. Execute o comando abaixo para compilar o projeto com o Gradle:

  ```bash
  ./gradlew build
  ```


4. Depois que a compilação for concluída com sucesso, execute o comando abaixo para iniciar os serviços com o Docker Compose:

  ```bash
  docker-compose up
  ```

5. A aplicação estará acessível em http://localhost:8080.

## Padrão SAGA Coreografado

O padrão Saga Coreografado foi utilizado no projeto pois é usado em aplicações distribuídas e microserviços para garantir a consistência em transações que envolvem múltiplos serviços. Nesse padrão, cada serviço envolvido em uma transação realiza uma parte da operação e emite eventos para indicar seu estado. Outros serviços ou um coordenador monitoram esses eventos e coordenam as operações para garantir que a transação seja concluída com sucesso ou revertida de forma consistente.

Existem várias razões para usar o padrão Saga Coreografado em aplicações como as descritas nos repositórios do projeto "Mikes":

Consistência distribuída: Como as transações envolvem vários serviços, é importante garantir que eles estejam em um estado consistente, mesmo em caso de falhas.
Escalabilidade e desempenho: O padrão permite que as operações sejam distribuídas entre vários serviços, melhorando a escalabilidade e o desempenho do sistema.
Resiliência: O padrão ajuda a tornar o sistema mais resiliente a falhas, pois permite que as transações sejam revertidas de forma consistente em caso de falha em um dos serviços.
Visibilidade e monitoramento: Como cada serviço emite eventos para indicar seu estado, é mais fácil monitorar e diagnosticar problemas no sistema.
Flexibilidade e manutenção: O padrão torna o sistema mais flexível, pois permite que novos serviços sejam adicionados ou alterados sem alterar a lógica de negócios existente.
Em resumo, o padrão Saga Coreografado é usado em aplicações distribuídas e microserviços para garantir a consistência e a integridade das transações, mesmo em um ambiente distribuído e com alta escalabilidade.
