import http from "../http-common";

class FeedbackDataService {
    /**
     * @sort which property to sort by, defaulted to createdDate to always get newest feedbacks.
     * @param {Number} size  number of feedbacks to get per page
     * @param {Number} page  which page to get feedbacks from
     * @returns paginated response where data.content is a collection of feedbacks
     */
    getPaginated(size, page) {
        return http.get(
            `/feedback/?page=${page}&size=${size}&sort=createdDate,desc`
        );
    }

    /**
     *
     * @returns
     */
    getAll() {
        return http.get("/feedback");
    }

    saveOne(feedback) {
        return http.post("/feedback", { ...feedback });
    }
}

export default new FeedbackDataService();
