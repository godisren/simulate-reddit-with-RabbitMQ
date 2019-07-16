import axios from 'axios'

const API_URL = 'http://localhost:8080'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
const TOKEN_NAME = 'auth_token'

axios.interceptors.request.use(
    (config) => {
        let token = sessionStorage.getItem(TOKEN_NAME)

        if (token) {
            config.headers.authorization = 'Bearer ' + token
        }
        return config
    }
)

axios.interceptors.response.use(
    function (response) {
        return response;
    }, function (error) {
        if (401 === error.response.status) {
            alert('Your session has expired. please login again.');
            window.location = '/login';
        } else {
            return Promise.reject(error);
        }
    }
);

class AuthenticationService {

    // basic authentication
    executeBasicAuthenticationService(username, password) {
        return axios.get(`${API_URL}/basicauth`,
            { headers: { authorization: this.createBasicAuthToken(username, password) } })
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    registerSuccessfulLogin(username, password) {
        //let basicAuthHeader = 'Basic ' +  window.btoa(username + ":" + password)
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        sessionStorage.setItem(TOKEN_NAME, this.createBasicAuthToken(username, password))
        //this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
    }

    // JWT authentication
    executeJWTAuthenticationService(username, password){
        return axios.post(`${API_URL}/authentication`,
            {username:username,  password:password}
        );
    }

    registerSuccessfulLoginForJWt(username, token) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        sessionStorage.setItem(TOKEN_NAME, token)
        
       //this.setupAxiosInterceptors(this.createJWTAuthToken(token))
    }

    setAuthenticationState(){
        let token = sessionStorage.getItem(TOKEN_NAME);
        if(token){
            this.setupAxiosInterceptors(this.createJWTAuthToken(token))
        }
    }

    createJWTAuthToken(token) {
        return 'Bearer ' + token
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return false
        return true
    }

    getUsername(){
        return sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    }

    setupAxiosInterceptors(token) {
        // axios.interceptors.request.use(
        //     (config) => {
        //         if (token) {
        //             config.headers.authorization = token
        //         }
        //         return config
        //     }
        // )

        // axios.interceptors.response.use(
        //     function (response) {
        //         return response;
        //     }, function (error) {
        //         if (401 === error.response.status) {
        //             alert('Your session has expired. please login again.');
        //             window.location = '/login';
        //         } else {
        //             return Promise.reject(error);
        //         }
        //     });
    }

    logout() {
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(TOKEN_NAME);
    }
}

export default new AuthenticationService()