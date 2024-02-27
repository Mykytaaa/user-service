import http from 'k6/http';
import {check, group} from 'k6';

const isNumeric = (value) => /^\d+$/.test(value);

const default_vus = 5;

const target_vus_env = `${__ENV.TARGET_VUS}`;
const target_vus = isNumeric(target_vus_env) ? Number(target_vus_env) : default_vus;

function randomIntBetween(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

function randomString(length) {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = '';
    for (let i = 0; i < length; i++) {
        const randomIndex = Math.floor(Math.random() * characters.length);
        result += characters.charAt(randomIndex);
    }
    return result;
}

export let options = {
    stages: [
        { duration: '10s', target: target_vus },
        { duration: '1m', target: target_vus },
        { duration: '10s', target: target_vus },
    ],
};

const BASE_URL = 'http://user-profile-service:8080/api/v1/users';


export default () => {

    group('Find Product by ID', function () {
        let userId = 1;
        let res = http.get(`${BASE_URL}/${userId}`);
        check(res, { 'status is 200': (r) => r.status === 200 });
    });


    group('Create User', function () {
        let uniqueSuffix = randomString(23);

        let userCreatePayload = JSON.stringify({
            "firstName": "New User" + uniqueSuffix,
            "lastName": "New Surname" + uniqueSuffix,
            "email": "newuser" + uniqueSuffix + "@example.com",
            "userDetailsCreateRequestDto": {
                "phoneNumber": "+1" + randomIntBetween(100000000, 999999999),
                "telegramId": "@newuser" + uniqueSuffix
            }
        });
        let headers = {
            'Content-Type': 'application/json',
        };
        let res = http.post(`${BASE_URL}`, userCreatePayload, { headers: headers });
        check(res, { 'status is 200': (r) => r.status === 200 });
    });

    group('Update User', function () {
        let uniqueSuffix = randomString(23);

        let userCreatePayload = JSON.stringify({
            "id": 1,
            "firstName": "New User" + uniqueSuffix,
            "lastName": "New Surname" + uniqueSuffix,
            "email": "newuser" + uniqueSuffix + "@example.com",
            "userDetailsUpdateDto": {
                "id": 1,
                "phoneNumber": "+1361046327",
                "telegramId": "@newuser0ajrhU0or2"
            }
        });
        let headers = {
            'Content-Type': 'application/json',
        };
        let res = http.put(`${BASE_URL}`+'/1', userCreatePayload, { headers: headers });
        check(res, { 'status is 200': (r) => r.status === 200 });
    });
}