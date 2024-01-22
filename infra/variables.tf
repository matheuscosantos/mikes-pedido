variable "region" {
  type    = string
  default = "us-east-2"
}

variable "vpc_id" {
  type    = string
  default = "vpc-0ffc09ae69916058b"
}

variable "infra_name" {
  type    = string
  default = "mikes"
}

variable "name" {
  type    = string
  default = "mikes-pedido"
}

variable "db_name" {
  type    = string
  default = "pedido"
}

variable "sns_name_pedido_recebido" {
  type    = string
  default = "pedido-recebido"
}

variable "sns_name_pedido_confirmado" {
  type    = string
  default = "pedido-confirmado"
}

variable "sns_name_status_pagamento" {
  type    = string
  default = "status-pagamento"
}

variable "sns_name_status_producao_alterado" {
  type    = string
  default = "status_producao_alterado"
}

variable "sqs_name_pagamento_pedido" {
  type    = string
  default = "pagamento-pedido"
}

variable "sqs_name_producao_pedido" {
  type    = string
  default = "producao-pedido"
}

variable "subnets" {
  type    = list(string)
  default = [
    "subnet-0c9e1d22c842d362b",
    "subnet-08e43d2d7fa2c463e"
  ]
}

variable "mikes_redis_cluster_name" {
  type    = string
  default = "mikes-redis-cluster"
}
