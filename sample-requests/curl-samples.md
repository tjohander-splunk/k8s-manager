
#### Update an image in Deployment Pod Spec
```bash
curl --request POST \
  --url 'http://localhost:8080/clusters/deployment/image?name=nginx-deployment&namespace=default' \
  --header 'Content-Type: application/json-patch+json' \
  --data '[
	{
		"op": "replace",
		"path": "/spec/template/spec/containers/0/image",
		"value": "nginx:1.9.5"
	}
]'
```

#### Get details of deployment by deployment Name
```bash
curl --request GET \
  --url 'http://localhost:8080/clusters/deployment?name=nginx-deployment&namespace=default'
```

#### Scale a deployment by name
```bash
curl --request POST \
  --url 'http://localhost:8080/clusters/deployment/scale?name=nginx-deployment&namespace=default' \
  --header 'Content-Type: application/json-patch+json' \
  --data '[
	{
		"op": "replace",
		"path": "/spec/replicas",
		"value": 10
	}
]'
```