#!/bin/bash

awslocal sqs create-queue --queue-name pagamento-pedido
awslocal sqs create-queue --queue-name producao-pedido
awslocal sns create-topic --name pedido-recebido
awslocal sns create-topic --name pedido-confirmado
