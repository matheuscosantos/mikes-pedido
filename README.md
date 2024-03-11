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
