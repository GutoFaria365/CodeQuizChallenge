#important to make aws commands work without an actual aws account
#--endpoint-url http://localhost:4566
#command to run the DynamoDB docker
run-dynamo-db:
	docker run --publish 4566:8000 amazon/dynamodb-local:1.19.0 -jar DynamoDBLocal.jar -inMemory -sharedDb

#package the app
clean:
	mvn clean package

#run the app using the packaged JAR file
run:
	java -jar target/quarkus-app/quarkus-run.jar

#command to list all the tables
listTables:
	aws dynamodb list-tables --endpoint-url http://localhost:4566

#command to scan the entire table and return all it's entries
scanTables:
	aws dynamodb scan --table-name Challenges --endpoint-url http://localhost:4566

#command to query on the table using the PK as a search condition (WINDOWS ONLY)
queryTables:
	aws dynamodb query --table-name Challenges --key-condition-expression "PK= :pk" --expression-attribute-values "{\":pk\": {\"S\": \"FRUIT#APPLE\"}}" --endpoint-url http://localhost:4566

#command to query on the table using the PK as a search condition
	aws dynamodb query --table-name Challenges --key-condition-expression "PK= :pk" --expression-attribute-values '{":pk": {"S": "FRUIT#ORANGE"}}' --endpoint-url http://localhost:4566

#command to query on the table using the PK and SK as a search condition
	aws dynamodb query --table-name Challenges --key-condition-expression "PK= :pk and SK= :sk" --expression-attribute-values '{":pk": {"S": "FRUIT#"}, ":sk": {"S": "FRUIT#CHERRY"}}' --endpoint-url http://localhost:4566

#command to query on the table using GSI1PK as a search condition
	aws dynamodb query --table-name Challenges --index-name GSI1PK_GSI1SK --key-condition-expression "GSI1PK = :gsi1pk" --expression-attribute-values '{":gsi1pk": {"S": "TASTE#"}}' --endpoint-url http://localhost:4566

#command to query on the table using GSI1PK and GSI1SK as a search condition
	aws dynamodb query --table-name Challenges --index-name GSI1PK_GSI1SK --key-condition-expression "GSI1PK = :gsi1pk" --expression-attribute-values '{":gsi1pk": {"S": "TASTE#"}, ":sk": {"S": "TASTE#SOUR"}}' --endpoint-url http://localhost:4566

#command to create the table
createTable:
	aws dynamodb create-table --table-name TEST --attribute-definitions AttributeName=fruitName,AttributeType=S --key-schema AttributeName=fruitName,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 --endpoint-url http://localhost:4566

#command to describe the dynamo table
describeTable:
	aws dynamodb describe-table --table-name Challenges --endpoint-url http://localhost:4566

#"test"
#"tes"
#us-east-1
#json