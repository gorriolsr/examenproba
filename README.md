# EXAM_AGE_OF_KINGDOMS_JORDI_BOIX

This project implements the **Age of Kingdoms** role game using Spring Boot and H2. It follows a layered architecture inspired by the provided Wild West Outlaws example.

## Endpoints
1. **Create Kingdom** `POST /kingdoms`
2. **Start Daily Production** `POST /kingdoms/{id}`
3. **Invest in Food or Citizens** `POST /kingdoms/{id}/invest?type=food|citizens`
4. **Get Kingdom Status** `GET /kingdoms/{id}`
5. **Richest Kingdom** `GET /kingdoms/richest`
6. **Attack Another Kingdom** `POST /kingdoms/{id}/attack/{target_id}`

Each kingdom keeps track of gold, citizens, food and its creation date. More details can be found in the exam statement.
