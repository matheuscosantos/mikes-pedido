variable "region" {
  type    = string
  default = "us-east-2"
}

variable "name" {
  type    = string
  default = "mikes-pedido"
}

variable "db_schema" {
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

variable "subnets" {
  type    = list(string)
  default = [
    "subnet-0c9e1d22c842d362b",
    "subnet-08e43d2d7fa2c463e"
  ]
}
