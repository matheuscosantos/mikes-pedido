provider "aws" {
  region = var.region
}

# -- iam

resource "aws_iam_role" "ecs_execution_role" {
  name = "${var.name}_ecs_execution_role"
  assume_role_policy = file("iam/role/ecs_execution_role.json")
}

resource "aws_iam_role_policy_attachment" "ecs_execution_role_ecr_policy_attachment" {
  role       = aws_iam_role.ecs_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/EC2InstanceProfileForImageBuilderECRContainerBuilds"
}

resource "aws_iam_role_policy_attachment" "ecs_execution_role_cloudwatch_policy_attachment" {
  role       = aws_iam_role.ecs_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchLogsFullAccess"
}

resource "aws_iam_role_policy_attachment" "ecs_execution_role_sns_policy_attachment" {
  role       = aws_iam_role.ecs_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSNSFullAccess"
}

resource "aws_iam_role_policy_attachment" "ecs_execution_role_sqs_policy_attachment" {
  role       = aws_iam_role.ecs_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSQSFullAccess"
}

# -- topics

resource "aws_sns_topic" "sns_topic_pedido_recebido" {
  name = var.sns_name_pedido_recebido
}

resource "aws_sns_topic" "sns_topic_pedido_confirmado" {
  name = var.sns_name_pedido_confirmado
}

# -- queues

data "aws_caller_identity" "current" {}

resource "aws_sqs_queue" "sqs_pagamento_pedido" {
  name                      = var.sqs_name_pagamento_pedido
  delay_seconds             = 0
  max_message_size          = 262144
  message_retention_seconds = 259200
  visibility_timeout_seconds = 30

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.sqs_pagamento_pedido_dlq.arn
    maxReceiveCount     = 3
  })
}

resource "aws_sqs_queue" "sqs_pagamento_pedido_dlq" {
  name                      = "${var.sqs_name_pagamento_pedido}-dlq"
  delay_seconds             = 0
  max_message_size          = 262144
  message_retention_seconds = 1209600
  visibility_timeout_seconds = 30
}

resource "aws_sqs_queue_policy" "sqs_pagamento_pedido_policy" {
  queue_url = aws_sqs_queue.sqs_pagamento_pedido.id

  policy = templatefile("iam/policy/sqs_queue_policy.json", {
    CALLER_ARN = data.aws_caller_identity.current.arn
    QUEUE_ARN = aws_sqs_queue.sqs_pagamento_pedido.arn
  })
}

resource "aws_sqs_queue" "sqs_producao_pedido" {
  name                      = var.sqs_name_producao_pedido
  delay_seconds             = 0
  max_message_size          = 262144
  message_retention_seconds = 259200
  visibility_timeout_seconds = 30

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.sqs_producao_pedido_dlq.arn
    maxReceiveCount     = 3
  })
}

resource "aws_sqs_queue" "sqs_producao_pedido_dlq" {
  name                      = "${var.sqs_name_producao_pedido}-dlq"
  delay_seconds             = 0
  max_message_size          = 262144
  message_retention_seconds = 1209600
  visibility_timeout_seconds = 30
}

resource "aws_sqs_queue_policy" "sqs_producao_pedido_policy" {
  queue_url = aws_sqs_queue.sqs_producao_pedido.id

  policy = templatefile("iam/policy/sqs_queue_policy.json", {
    CALLER_ARN = data.aws_caller_identity.current.arn
    QUEUE_ARN = aws_sqs_queue.sqs_producao_pedido.arn
  })
}

# -- subscribes

data "aws_sns_topic" "sns_topic_status_pagamento" {
    name = var.sns_name_status_pagamento
}

resource "aws_sns_topic_subscription" "sqs_pagamento_pedido_subscription_sns_status_pagamento" {
  topic_arn            = data.aws_sns_topic.sns_topic_status_pagamento.arn
  protocol             = "sqs"
  endpoint             = aws_sqs_queue.sqs_pagamento_pedido.arn
  raw_message_delivery = true
}

data "aws_sns_topic" "sns_topic_status_producao_alterado" {
    name = var.sns_name_status_producao_alterado
}

resource "aws_sns_topic_subscription" "sqs_producao_pedido_subscription_sns_status_producao_alterado" {
  topic_arn            = data.aws_sns_topic.sns_topic_status_producao_alterado.arn
  protocol             = "sqs"
  endpoint             = aws_sqs_queue.sqs_producao_pedido.arn
  raw_message_delivery = true
}

# -- task definition

data "aws_db_instance" "db_instance" {
  db_instance_identifier = "mikes-db"
}

data "aws_elasticache_cluster" "redis" {
  cluster_id = var.mikes_redis_cluster_name
}

data "aws_secretsmanager_secret" "db_credentials" {
  name = "mikes/db/db_credentials"
}

data "aws_secretsmanager_secret_version" "db_credentials_current" {
  secret_id = data.aws_secretsmanager_secret.db_credentials.id
}

locals {
  db_credentials = jsondecode(data.aws_secretsmanager_secret_version.db_credentials_current.secret_string)
}

resource "aws_cloudwatch_log_group" "ecs_log_group" {
  name = "/ecs/${var.name}"
}

resource "aws_ecs_task_definition" "ecs_task_definition" {
  family                   = var.name
  network_mode             = "awsvpc"
  execution_role_arn = aws_iam_role.ecs_execution_role.arn

  container_definitions = templatefile("container/definitions/mikes_app_container_definitions.json", {
    NAME                        = "${var.name}-container"
    DB_HOST                     = data.aws_db_instance.db_instance.address
    DB_PORT                     = data.aws_db_instance.db_instance.port
    DB_NAME                     = var.db_name
    DB_USER                     = local.db_credentials["username"]
    DB_PASSWORD                 = local.db_credentials["password"]
    SNS_ORDER_RECEIVED_ARN      = aws_sns_topic.sns_topic_pedido_recebido.arn
    SNS_ORDER_CONFIRMED_ARN     = aws_sns_topic.sns_topic_pedido_confirmado.arn
    SQS_ORDER_PAYMENT_URL       = aws_sqs_queue.sqs_pagamento_pedido.url
    SQS_ORDER_PRODUCTION_URL    = aws_sqs_queue.sqs_producao_pedido.url
    REGION                      = var.region
    LOG_GROUP_NAME              = aws_cloudwatch_log_group.ecs_log_group.name
    REDIS_HOST                  = data.aws_elasticache_cluster.redis.cache_nodes.0.address
  })
}

# -- service

data "aws_ecs_cluster" "ecs_cluster" {
  cluster_name = "${var.infra_name}_cluster"
}

data "aws_security_group" "security_group" {
  name  = "${var.infra_name}_security_group"
}

data "aws_lb_target_group" "lb_target_group" {
  name = "${var.infra_name}-lb-target-group"
}

resource "aws_ecs_service" "ecs_service" {
  name            = "${var.name}_service"
  cluster         = data.aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.ecs_task_definition.arn
  desired_count = 1 // minimo possivel p/ economizar resources

  network_configuration {
    subnets = var.subnets
    security_groups = [data.aws_security_group.security_group.id]
  }

  load_balancer {
    target_group_arn = data.aws_lb_target_group.lb_target_group.arn
    container_name   = "${var.name}-container"
    container_port   = 8080
  }

  force_new_deployment = true

  placement_constraints {
    type = "distinctInstance"
  }

  capacity_provider_strategy {
    capacity_provider = "${var.infra_name}_capacity_provider"
    weight            = 100
  }
}
