import axios from "axios";

/**
 * Default configuration for all api requests
 * @baseUrl api url
 */
export default axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-Type": "application/json",
    },
});
