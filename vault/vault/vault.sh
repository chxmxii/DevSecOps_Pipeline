#!/bin/bash

sudo cat <<EOF > /vault/config/config.hcl
storage "file" {
  path    = "/vault/file"
}

listener "tcp" {
  address     = "127.0.0.1:8202"
  tls_disable = "true"
}

api_addr = "http://127.0.0.1:8202"
cluster_addr = "https://127.0.0.1:8201"
ui = true
EOF
