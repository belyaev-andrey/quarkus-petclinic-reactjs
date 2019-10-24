import {IHttpMethod} from '../types';

declare var __API_SERVER_URL__;
const BACKEND_URL = (typeof __API_SERVER_URL__ === 'undefined' ? 'http://localhost:8080' : __API_SERVER_URL__);

export const url = (path: string): string => `${BACKEND_URL}${path}`;

export interface IHeaderElement {
    key: string;
    value: string;
}

export interface IUser {
    username: string;
    authdata: string;
}

export function authHeader(): string {
    // return authorization header with basic auth credentials
    let item = localStorage.getItem('user');
    if (item) {
        let user = JSON.parse(item);
        if (user && user.authdata) {
            return 'Basic ' + user.authdata;
        } else {
            return ''; // 'Basic YWRtaW46YWRtaW4=';
        }
    } else {
        return '';
    }
}

export function pushAuthUser(user: IUser): void {
    localStorage.setItem('user', JSON.stringify(user));
}

export function removeAuthUser(): void {
    localStorage.removeItem('user');
}

export function reqHeader(): RequestInit {
    let header = {
        'Accept': 'application/json;charset=utf-8',
        'Content-Type': 'application/json;charset=utf-8',
        'Authorization': authHeader()
    };
    return {headers: new Headers(header)};
}


/**
 * path: relative PATH without host and port (i.e. '/api/123')
 * data: object that will be passed as request body
 * onSuccess: callback handler if request succeeded. Succeeded means it could technically be handled (i.e. valid json is returned)
 * regardless of the HTTP status code.
 */
export const submitForm = (method: IHttpMethod, path: string, data: any, onSuccess: (status: number, response: any) => void) => {
    const requestUrl = url(path);

    const fetchParams = {
        headers: reqHeader().headers,
        method: method,
        body: JSON.stringify
        (data)
    };

    return fetch(requestUrl, fetchParams)
        .then(response => response.status === 204 ? onSuccess(response.status, {}) : response.json().then(result => onSuccess(response.status, result)));
};
