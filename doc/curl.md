curl -X GET http://localhost:8080/topjava/rest/meals

curl -X POST http://localhost:8080/topjava/rest/meals -H "Content-Type:application/json" -d '{"dateTime": "2015-06-01T18:00:00","description":"Созданный ужин", "calories": 300 }'

curl -X GET http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&startTime=10:00:00&endDate=2015-05-30&endTime=20:00:00

curl -X GET http://localhost:8080/topjava/rest/meals/100002

curl -X PUT http://localhost:8080/topjava/rest/meals/100002 -H "Content-Type:application/json" -d {"id":100002,"dateTime":"2015-05-30T10:00:00","description":"Обновленный завтрак","calories":200}

curl -X DELETE http://localhost:8080/topjava/rest/meals/100002