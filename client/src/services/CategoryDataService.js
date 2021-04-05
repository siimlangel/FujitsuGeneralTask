import http from "../http-common";

class CategoryDataService {
    getAll() {
        return http.get(`/category`);
    }
}

export default new CategoryDataService();
