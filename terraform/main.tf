terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.49"

    }
  }

  required_version = ">= 0.14.9"
}

provider "aws" {
  profile = "local"
  region = "eu-central-1"
  access_key = "mock_access_key"
  s3_force_path_style = true
  secret_key = "mock_secret_key"
  skip_credentials_validation = true
  skip_metadata_api_check = true
  skip_requesting_account_id = true
  endpoints {
    sqs = "http://localhost:4566"
    s3 = "http://localhost:4566"
    lambda = "http://localhost:4566"
  }
}

variable "aws_region" {
  type = string
}

variable "prefix" {
  type = string
  default = ""
}

resource "aws_sqs_queue" "studentqueue" {
  name = "studentqueue"
  visibility_timeout_seconds = 30
  message_retention_seconds = 86400
}

resource "aws_s3_bucket" "import" {
  bucket = "import"
}

resource "aws_s3_bucket" "exchangedata" {
  bucket = "exchangedata"
}


resource "aws_lambda_function" "func" {
  filename      = "StudentsImport-1.0-SNAPSHOT.jar"
  function_name = "bme_import"
  role          = "arn:aws:iam::12345:role/ignoreme"
  handler       = "pl.polsl.management.ImportProcess"
  runtime = "java11"
  memory_size = 512
  timeout = 5

  environment {
    variables = {
      RUN_PROFILE = "local",
      BUCKET_EXCHANGE_DATA = "exchangedata",
      SQS_EXCHANGE_QUEUE = "studentqueue"
    }
  }
}

resource "aws_s3_bucket_notification" "bucket_notification" {
  bucket = aws_s3_bucket.import.id

  lambda_function {
    lambda_function_arn = aws_lambda_function.func.arn
    events              = ["s3:ObjectCreated:*"]
  }
}


