# 🌿 EcoData – Environmental Data Reporting Tool

*EcoData* is a web-based platform that allows users to generate environmental reports and view ecological data from multiple public sources. It is designed to help people better understand the state of the environment in a specific region or over time by providing ready-made dashboards and customisable reports.

The system connects to open data APIs, processes the information, and presents it in a clear, visual format. Users can choose specific indicators, set filters by location and time, and receive updates when pollution levels exceed safe thresholds.

Intended for everyday users — such as journalists, students, researchers, or government staff — who need quick access to environmental statistics without having to work directly with raw data.

## Key Features

- **Custom Report Generation**
  Create detailed or summary reports filtered by region, date range, and environmental indicators such as air quality, water pollution, CO₂ emissions, etc.
- **Live Dashboard**
  Real-time visual overview of key ecological metrics. Includes trend charts, regional comparisons, and critical alerts.
- **Flexible Data Integration**
  Connect to multiple open data APIs and formats (JSON, XML, CSV), including meteorological services, environmental agencies, and scientific repositories.
- **Scheduled Data Syncing**
  Automatically update your datasets with the latest available information at scheduled intervals.
- **Notification System**
  Receive alerts when monitored indicators exceed safe thresholds, configurable per user.
- **Export Options**
  Download reports in PDF, Excel, or shareable web format. Public dashboards can be shared via links.

## Endpoints:

AUTHENTICATION

1. Sign-up

   ```
   curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{
   "firstName":"Bob","lastName":"Johnson",
   "username":"bob123","email":"bob@example.com",
   "phoneNumber":"+77011234567","password":"secret"
   }'
   ```
2. Sign-in  – returns { token, role }

   ```
   curl -X POST "http://localhost:8080/api/auth/login?username=bob123&password=secret"
   ```
3. Sign-out (stateless)

   ```
   curl -X POST "http://localhost:8080/api/auth/logout?username=bob123"
   ```

PUBLIC REFERENCE DATA

4. List all reference cities

   ```
   curl http://localhost:8080/api/reference/cities
   ```
   

AIR-QUALITY

5. Latest air-quality sample for a city

   ```
   curl http://localhost:8080/api/air/1/latest
   ```
6. Dashboard query (filter)

   ```
   curl -X POST http://localhost:8080/api/air/dashboard -H "Content-Type: application/json" -d '{
   "pollutant":"aqi",
   "cityId":1,
   "from":"2025-05-01T00:00:00Z",
   "to":"2025-05-06T00:00:00Z"
   }'
   ```

WEATHER

7. Dashboard query

   ```
   curl -X POST http://localhost:8080/api/weather/dashboard -H "Content-Type: application/json" -d '{
   "metric":"temperature",
   "cityId":1,
   "from":"2025-05-01T00:00:00Z",
   "to":"2025-05-06T00:00:00Z"
   }'
   ```

PROFILE  (JWT required)

8. Get my profile

   ```
   curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8080/api/profile
   ```
9. Update my profile

   ```
   curl -X PATCH http://localhost:8080/api/profile -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d '{"firstName":"Alice","phoneNumber":"+77012223344"}'Deactivate my profile
   ```


ADMIN ENDPOINTS  (ROLE_ADMIN)

10. Paged / filtered user list

    ```
    curl -X POST "http://localhost:8080/api/users/info?page=0&size=20" -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d '{"username":"", "email":"", "role":"", "isActive":true }'
    ```
11. All roles

    ```
    curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8080/api/users/roles
    ```

12. Create user

    ```
    curl -X POST http://localhost:8080/api/users -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d '{ "firstName":"Eve", "lastName":"Smith",
    "username":"eve01", "email":"eve@site.com",
    "phoneNumber":"+77015556677",
    "password":"demo", "role":{"code":"USER"} }'
    ```
13. Update user

    ```
    curl -X PUT http://localhost:8080/api/users/42 -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d '{ "firstName":"Eve-Renamed", "role":{"code":"ADMIN"} }'
    ```
14. Delete user

    ```
    curl -X DELETE http://localhost:8080/api/users/42 -H "Authorization: Bearer ${TOKEN}"
    ```
